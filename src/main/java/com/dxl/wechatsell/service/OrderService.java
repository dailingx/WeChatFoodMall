package com.dxl.wechatsell.service;

import com.dxl.wechatsell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 7:48 on 2019/10/30.
 */
public interface OrderService {

	//创建订单,创建一个订单可能会买多个商品即对应多个OrderDetail，因此需要返回多个OrderDetail(List)，即1对多
	OrderDTO create(OrderDTO orderDTO);

	//查询单个订单
	OrderDTO findOne(String orderId);

	//查询订单列表
	Page<OrderDTO> findList(String buyerOpenId, Pageable pageable);

	//取消订单
	OrderDTO cancel(OrderDTO orderDTO);

	//完结订单
	OrderDTO finish(OrderDTO orderDTO);

	//支付订单
	OrderDTO paid(OrderDTO orderDTO);

	//卖家查询订单列表，即所有买家的订单
	Page<OrderDTO> findList(Pageable pageable);

}
