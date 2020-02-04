package com.dxl.wechatsell.dataobject.dao;

import com.dxl.wechatsell.dataobject.ProductCategory;
import com.dxl.wechatsell.dataobject.mapper.ProductCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author：Daixinliang
 * @Description：使用mybatis的mapper,service里使用的话直接Autowired这个Dao就行了
 * @Date：Created in 16:12 on 2020/1/31.
 */
public class ProductCategoryDao {

	@Autowired
	ProductCategoryMapper mapper;

	public int insertByMap(Map<String, Object> map) {
		return mapper.insertByMap(map);
	}
}
