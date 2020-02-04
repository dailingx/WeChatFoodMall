package com.dxl.wechatsell.service.impl;

import com.dxl.wechatsell.dao.ProductCategoryDao;
import com.dxl.wechatsell.dataobject.ProductCategory;
import com.dxl.wechatsell.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 18:23 on 2019/10/20.
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryDao productCategoryDao;

	@Override
	public ProductCategory findOne(Integer categoryId) {
		return productCategoryDao.findOne(categoryId);
	}

	@Override
	public List<ProductCategory> findAll() {
		return productCategoryDao.findAll();
	}

	@Override
	public ProductCategory save(ProductCategory productCategory) {
		return productCategoryDao.save(productCategory);
	}

	@Override
	public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryList) {
		return productCategoryDao.findByCategoryTypeIn(categoryList);
	}
}
