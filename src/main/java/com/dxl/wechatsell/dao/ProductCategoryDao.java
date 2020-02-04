package com.dxl.wechatsell.dao;

import com.dxl.wechatsell.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author：Daixinliang
 * @Description：JpaRepository<对象类型, 主键类型>
 * @Date：Created in 17:04 on 2019/10/5.
 */
public interface ProductCategoryDao extends JpaRepository<ProductCategory, Integer> {

	//查商品列表时会先查商品再查类目，采用一次性查到的方式。使用Jpa的这种方式查，需要在实体类里有无参构造方法
	List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
