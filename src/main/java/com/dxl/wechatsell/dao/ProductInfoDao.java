package com.dxl.wechatsell.dao;

import com.dxl.wechatsell.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 22:09 on 2019/10/23.
 */
public interface ProductInfoDao extends JpaRepository<ProductInfo, String> {

	//根据状态编号得到对应的上架商品或下架商品
	List<ProductInfo> findByProductStatus(Integer productStatus);
}
