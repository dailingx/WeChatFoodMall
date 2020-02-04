package com.dxl.wechatsell.service;

import com.dxl.wechatsell.dto.OrderDTO;

/**
 * @author：Daixinliang
 * @Description：推送消息
 * @Date：Created in 21:56 on 2020/1/30.
 */
public interface PushMessageService {

//	订单状态变更消息
	void orderStatus(OrderDTO orderDTO);
}
