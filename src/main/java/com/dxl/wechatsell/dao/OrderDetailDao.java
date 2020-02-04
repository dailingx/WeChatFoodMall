package com.dxl.wechatsell.dao;

import com.dxl.wechatsell.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 7:34 on 2019/10/30.
 */
public interface OrderDetailDao extends JpaRepository<OrderDetail, String> {

	//master表里一条记录可能对应detail里多条记录
	List<OrderDetail> findByOrderId(String orderId);
}
