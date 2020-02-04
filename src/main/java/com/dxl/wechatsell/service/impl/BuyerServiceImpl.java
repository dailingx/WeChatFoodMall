package com.dxl.wechatsell.service.impl;

import com.dxl.wechatsell.exception.SellException;
import com.dxl.wechatsell.dto.OrderDTO;
import com.dxl.wechatsell.enums.ResultEnum;
import com.dxl.wechatsell.service.BuyerService;
import com.dxl.wechatsell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 21:11 on 2020/1/26.
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

	@Autowired
	private OrderService orderService;

	@Override
	public OrderDTO findOrderOne(String openId, String orderId) {
		return checkOrderOwner(openId, orderId);
	}

	@Override
	public OrderDTO cancelOrder(String openId, String orderId) {
		OrderDTO orderDTO = checkOrderOwner(openId, orderId);
		if (orderDTO == null) {
			log.error("【取消订单】没有查到该订单，orderid={}", orderId);
			throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
		}
		return orderService.cancel(orderDTO);
	}

	//提取公共的判断逻辑,减少复写
	private OrderDTO checkOrderOwner(String openId, String orderId) {
		OrderDTO orderDTO = orderService.findOne(orderId);
		if (orderDTO == null) {
			return null;
		}
		if (orderDTO.getBuyerOpenId().equalsIgnoreCase(openId)) {
			log.error("【查询订单】订单的openId不一致，openId={}", openId);
			throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
		}

		return orderDTO;
	}
}
