package com.dxl.wechatsell.controller;

import com.dxl.wechatsell.exception.SellException;
import com.dxl.wechatsell.dto.OrderDTO;
import com.dxl.wechatsell.enums.ResultEnum;
import com.dxl.wechatsell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author：Daixinliang
 * @Description：卖家端订单
 * @Date：Created in 16:03 on 2020/1/27.
 */
//使用模板渲染因此不使用RestController,且接口方法应该返回ModelAndView
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping("/list")
	public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
	                         @RequestParam(value = "soze", defaultValue = "10") Integer size,
	                         Map<String, Object> map) {
//		接口定义的是从第一页开始查，但实际上service层时从第0页开始查的，因此page-1才能对应上数据
		PageRequest pageRequest = new PageRequest(page-1, size);
		Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);
//		把结果写到模板里,再返回
		map.put("orderDTOPage", orderDTOPage);
		map.put("currentPage", page);
		map.put("size", size);
		return new ModelAndView("order/list", map);
	}

	@GetMapping("/cancel")
	public ModelAndView cancel(@RequestParam("orderId") String orderId,
	                           Map<String, Object> map) {
		OrderDTO orderDTO = orderService.findOne(orderId);
		if (orderDTO == null) {
			log.error("【卖家取消订单】查询不到订单，orderId={}", orderId);
			map.put("msg", ResultEnum.ORDER_NOT_EXIST);
			return new ModelAndView("common/error", map);
		}
		try {
			orderService.cancel(orderDTO);
		} catch (SellException e) {
			log.error("【卖家取消订单】发生异常, {}", e.getMessage());
		}
		return new ModelAndView("order/success");
	}

	@GetMapping("/detail")
	public ModelAndView detail(@RequestParam("orderId") String orderId,
	                           Map<String, Object> map) {
		OrderDTO orderDTO = new OrderDTO();
		try {
			orderDTO = orderService.findOne(orderId);
		} catch (SellException e) {
			log.error("【卖家查询订单】发生异常, {}", e.getMessage());
		}
		map.put("orderDTO", orderDTO);
		return new ModelAndView("order/detail", map);
	}

	@GetMapping("/finish")
	public ModelAndView finish(@RequestParam("orderId") String orderId,
	                           Map<String, Object> map) {
		OrderDTO orderDTO = orderService.findOne(orderId);
		if (orderDTO == null) {
			log.error("【卖家完结订单】查询不到订单，orderId={}", orderId);
			map.put("msg", ResultEnum.ORDER_NOT_EXIST);
			return new ModelAndView("common/error", map);
		}
		try {
			orderService.finish(orderDTO);
		} catch (SellException e) {
			log.error("【卖家完结订单】发生异常, {}", e.getMessage());
		}
		return new ModelAndView("order/success");
	}
}
