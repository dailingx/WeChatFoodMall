package com.dxl.wechatsell.converter;

import com.dxl.wechatsell.exception.SellException;
import com.dxl.wechatsell.dataobject.OrderDetail;
import com.dxl.wechatsell.dto.OrderDTO;
import com.dxl.wechatsell.enums.ResultEnum;
import com.dxl.wechatsell.form.OrderForm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 14:36 on 2020/1/26.
 */
@Slf4j
public class OrderForm2OrderDTOComverter {

	public static OrderDTO convert(OrderForm orderForm) {
		OrderDTO orderDTO = new OrderDTO();

		orderDTO.setBuyerName(orderForm.getName());
		orderDTO.setBuyerPhone(orderForm.getPhone());
		orderDTO.setBuyerAddress(orderForm.getAddress());
		orderDTO.setBuyerOpenId(orderForm.getOpenId());

		//将字符串的购物车转成OrderDetail的list，自定义try catch来捕获转换过程中的出错
		Gson gson = new Gson();
		List<OrderDetail> orderDetailList = new ArrayList<>();
		try {
			orderDetailList = gson.fromJson(orderForm.getItems(),
					new TypeToken<List<OrderDetail>>(){}.getType());
		} catch (Exception e) {
			log.error("【对象转换】转换出错，string={}", orderForm.getItems());
			throw new SellException(ResultEnum.PARAM_ERROR);
		}
		orderDTO.setOrderDetailList(orderDetailList);
		return orderDTO;
	}
}
