package com.dxl.wechatsell.dao;

import com.dxl.wechatsell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 7:31 on 2019/10/30.
 */
public interface OrderMasterDao extends JpaRepository<OrderMaster, String> {

	//分页查
	Page<OrderMaster> findByBuyerOpenId(String buyerOpenId, Pageable pageable);


}
