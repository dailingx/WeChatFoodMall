package com.dxl.wechatsell.converter;

import com.dxl.wechatsell.dataobject.OrderMaster;
import com.dxl.wechatsell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 23:05 on 2020/1/11.
 */
public class OrderMaster2OrderDTOConverter {

	public static OrderDTO convert(OrderMaster orderMaster) {
		OrderDTO orderDTO = new OrderDTO();
		BeanUtils.copyProperties(orderMaster, orderDTO);
		return orderDTO;
	}

	public static List<OrderDTO> convert(List<OrderMaster> orderMasterList) {
		return orderMasterList.stream().map(orderMaster -> convert(orderMaster)).collect(Collectors.toList());
	}
}
