package com.dxl.wechatsell.service.impl;

import com.dxl.wechatsell.exception.RealResponseCodeException;
import com.dxl.wechatsell.exception.SellException;
import com.dxl.wechatsell.converter.OrderMaster2OrderDTOConverter;
import com.dxl.wechatsell.dao.OrderDetailDao;
import com.dxl.wechatsell.dao.OrderMasterDao;
import com.dxl.wechatsell.dataobject.OrderDetail;
import com.dxl.wechatsell.dataobject.OrderMaster;
import com.dxl.wechatsell.dataobject.ProductInfo;
import com.dxl.wechatsell.dto.OrderDTO;
import com.dxl.wechatsell.dto.ShoppingCartDTO;
import com.dxl.wechatsell.enums.OrderStatusEnum;
import com.dxl.wechatsell.enums.PayStatusEnum;
import com.dxl.wechatsell.enums.ResultEnum;
import com.dxl.wechatsell.service.OrderService;
import com.dxl.wechatsell.service.ProductInfoService;
import com.dxl.wechatsell.service.PushMessageService;
import com.dxl.wechatsell.service.WebSocket;
import com.dxl.wechatsell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 22:07 on 2019/11/15.
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private ProductInfoService productInfoService;

	@Autowired
	private OrderDetailDao orderDetailDao;

	@Autowired
	private OrderMasterDao orderMasterDao;

	@Autowired
	private PushMessageService pushMessageService;

	@Autowired
	private WebSocket webSocket;

	@Override
	@Transactional
	public OrderDTO create(OrderDTO orderDTO) {

		String orderId = KeyUtil.genUniqueKey();
		BigDecimal orderAmout = new BigDecimal(BigInteger.ZERO);

		//1、查询商品（数量，价格）-------必须从自己数据库里查出来，而不能从前端传过来
		for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
			ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
			if (productInfo == null) {
				throw new SellException(ResultEnum.PRODECT_NO_EXIST);
//				throw new RealResponseCodeException();
			}

			//2、计算订单总价
			orderAmout = orderAmout.add(productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())));

			//订单详情入库，写入orderDetail
			//订单从前端传到后端只会传商品id和数量，需要注意订单id和详情id是没有的
			orderDetail.setOrderId(orderId);
			orderDetail.setDetailId(KeyUtil.genUniqueKey());
			//并且还需要把其他属性(在productInfo有如productIcon等)设置进orderDetail，使用spring的工具进行对象的属性拷贝
			BeanUtils.copyProperties(productInfo, orderDetail);
			orderDetailDao.save(orderDetail);
		}

		//3、写入订单数据库，写入orderMaster
		OrderMaster orderMaster = new OrderMaster();
		//先拷贝再设值，防止拷贝到某属性null值，最后orderMaster的某属性也为null。注意null值的覆盖
		orderDTO.setOrderId(orderId);
		BeanUtils.copyProperties(orderDTO, orderMaster);
		orderMaster.setOrderAmount(orderAmout);
		orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
		orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
		orderMasterDao.save(orderMaster);

		//4、扣库存
		//虽然订单里有很多商品，最好也是只调一次来扣库存
		List<ShoppingCartDTO> shoppingCartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
															new ShoppingCartDTO(e.getProductId(), e.getProductQuantity())
													).collect(Collectors.toList());
		productInfoService.decreaseStock(shoppingCartDTOList);

//		用户下单完成，发送websocket消息
		webSocket.sendMessage(orderDTO.getOrderId());

		return orderDTO;
	}

	@Override
	public OrderDTO findOne(String orderId) {
		OrderMaster orderMaster = orderMasterDao.findOne(orderId);
		if (orderMaster == null) {
			throw new SellException(ResultEnum.PRODECT_NO_EXIST);
		}

		//再查询订单详情
		List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId(orderId);
		if (CollectionUtils.isEmpty(orderDetailList)) {
			throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
		}

		OrderDTO orderDTO = new OrderDTO();
		BeanUtils.copyProperties(orderMaster, orderDTO);
		orderDTO.setOrderDetailList(orderDetailList);
		return orderDTO;
	}

	@Override
	public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable) {
		Page<OrderMaster> orderMasterPage = orderMasterDao.findByBuyerOpenId(buyerOpenId, pageable);
		List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
		return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
	}

	@Override
	@Transactional
	public OrderDTO cancel(OrderDTO orderDTO) {
		//判断订单状态
		if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.FINISHED.getCode())) {
			log.error("【取消订单】订单状态不正确，orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}

		//修改订单状态
		orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterDao.save(orderMaster);
		if (updateResult == null) {
			log.error("【取消订单】更新失败，orderMaster={}", orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}

		//返回库存
		if (!CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
			log.error("【取消订单】订单中无商品详情，orderDTO={}", orderDTO);
			throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
		}
		List<ShoppingCartDTO> shoppingCartDTOList = orderDTO.getOrderDetailList().stream()
				.map(orderDetail -> new ShoppingCartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity()))
				.collect(Collectors.toList());
		productInfoService.increaseStock(shoppingCartDTOList);

		//如果已支付，需要退款
		if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {

		}
		return orderDTO;
	}

	@Override
	@Transactional
	public OrderDTO finish(OrderDTO orderDTO) {
		//判断订单状态
		if (orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【完结订单】订单状态不正确，orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}

		//修改订单状态
		orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterDao.save(orderMaster);
		if (updateResult == null) {
			log.error("【完结订单】更新失败，orderMaster={}", orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}

//		推送微信模板消息
//		消息推送的service不需要抛出异常，因为如果抛出的话，前面都ok却在消息推送的时候出现异常会导致整个finish方法回滚，这是没有必要的
		pushMessageService.orderStatus(orderDTO);

		return orderDTO;
	}

	@Override
	@Transactional
	public OrderDTO paid(OrderDTO orderDTO) {
		//判断订单状态
		if (orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【订单支付完成】订单状态不正确，orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}

		//判断支付状态
		if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
			log.error("【订单支付完成】订单支付状态不正确，orderDTO={}", orderDTO);
			throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
		}

		//修改支付状态
		orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterDao.save(orderMaster);
		if (updateResult == null) {
			log.error("【订单支付完成】更新失败，orderMaster={}", orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}

		return orderDTO;
	}

	@Override
	public Page<OrderDTO> findList(Pageable pageable) {
		Page<OrderMaster> orderMasterPage = orderMasterDao.findAll(pageable);
		List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
		return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());

	}
}
