package com.dxl.wechatsell.service.impl;

import com.dxl.wechatsell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author：Daixinliang
 * @Description：impl实现类的测试
 * @Date：Created in 8:03 on 2019/10/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

	@Autowired
	private ProductCategoryServiceImpl productCategoryService;

	@Test
	public void findOne() {
		ProductCategory productCategory = productCategoryService.findOne(1);
		Assert.assertEquals(new Integer(1), productCategory.getCategoryId());
	}

	@Test
	public void findAll() {
		List<ProductCategory> list = productCategoryService.findAll();
		Assert.assertEquals(2, list.size());
	}

	@Test
	public void save() {
	}

	@Test
	public void findByCategoryTypeIn() {
	}
}