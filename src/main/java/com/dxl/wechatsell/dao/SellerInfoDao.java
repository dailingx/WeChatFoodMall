package com.dxl.wechatsell.dao;

import com.dxl.wechatsell.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 20:30 on 2020/1/29.
 */
public interface SellerInfoDao extends JpaRepository<SellerInfo, String> {

	SellerInfo findByOpenId(String openId);
}
