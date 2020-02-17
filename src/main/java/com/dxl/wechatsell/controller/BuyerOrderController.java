package com.dxl.wechatsell.controller;

import com.dxl.wechatsell.exception.SellException;
import com.dxl.wechatsell.converter.OrderForm2OrderDTOComverter;
import com.dxl.wechatsell.dto.OrderDTO;
import com.dxl.wechatsell.enums.ResultEnum;
import com.dxl.wechatsell.form.OrderForm;
import com.dxl.wechatsell.service.BuyerService;
import com.dxl.wechatsell.service.OrderService;
import com.dxl.wechatsell.utils.ResultUtil;
import com.dxl.wechatsell.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 23:00 on 2020/1/25.
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController implements Serializable {

	public static final long serialVersionUID = 5877549321548546283L;

	@Autowired
	private OrderService orderService;

	@Autowired
	private BuyerService buyerService;

	@PostMapping("/create")
	public Result<Map<String, String>> create(@Valid OrderForm orderForm,
	                                          BindingResult bindingResult) {
		//判断表单校验是否有错误
		if (bindingResult.hasErrors()) {
			log.error("【创建订单】参数不正确, orderForm={}", orderForm);
			throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}

		OrderDTO orderDTO = OrderForm2OrderDTOComverter.convert(orderForm);
		if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
			log.error("【创建订单】购物车不能为空");
			throw new SellException(ResultEnum.SHOPPINGCART_EMPTY);
		}
		OrderDTO createResult = orderService.create(orderDTO);

		Map<String, String> map = new HashMap<>();
		map.put("orderId", createResult.getOrderId());

		return ResultUtil.success(map);
	}

	public Result<List<OrderDTO>> list(@RequestParam("openId") String openId,
	                                   @RequestParam(value = "page", defaultValue = "0") Integer page,
	                                   @RequestParam(value = "size", defaultValue = "10") Integer size) {
		if (StringUtils.isEmpty(openId)) {
			log.error("【查询订单列表】openId为空");
			throw new SellException(ResultEnum.PARAM_ERROR);
		}

		PageRequest pageRequest = new PageRequest(page, size);
		Page<OrderDTO> orderDTOPage = orderService.findList(openId, pageRequest);

		return ResultUtil.success(orderDTOPage.getContent());
	}

	@GetMapping("/detail")
	public Result<OrderDTO> detail(@RequestParam("openId") String openId,
	                               @RequestParam("orderId") String orderId) {
		//具体逻辑都在service层
		OrderDTO orderDTO = buyerService.findOrderOne(openId, orderId);
		return ResultUtil.success(orderDTO);
	}

	@PostMapping("/cancel")
	public Result cancel(@RequestParam("openId") String openId,
	                     @RequestParam("orderId") String orderId) {
		buyerService.cancelOrder(openId, orderId);
		return ResultUtil.success();
	}
}
