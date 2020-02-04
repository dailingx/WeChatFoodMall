package com.dxl.wechatsell.service;

import com.dxl.wechatsell.dto.OrderDTO;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 21:09 on 2020/1/26.
 */
public interface BuyerService {

	OrderDTO findOrderOne(String openId, String orderId);

	OrderDTO cancelOrder(String openId, String orderId);
}
