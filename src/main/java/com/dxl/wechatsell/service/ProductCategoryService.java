package com.dxl.wechatsell.service;

import com.dxl.wechatsell.dataobject.ProductCategory;

import java.util.List;

/**
 * @author：Daixinliang
 * @Description：商品类目service
 * @Date：Created in 18:16 on 2019/10/20.
 */
public interface ProductCategoryService {

	ProductCategory findOne(Integer categoryId);

	List<ProductCategory> findAll();

	ProductCategory save(ProductCategory productCategory);

	//买家端需要用到的方法
	List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryList);
}
