package com.dxl.wechatsell.dataobject.mapper;

import com.dxl.wechatsell.dataobject.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author：Daixinliang
 * @Description：mybatis使用注解和xml的方式对数据库操作
 * @Date：Created in 0:06 on 2020/1/31.
 */
public interface ProductCategoryMapper {

//	categoryName和categoryType是从Map中取值的，需要保持名称一致
	@Insert("insert into product_category(category_name, category_type) values(#{categoryName, jdbcType=VARCHAR}, #{categoryType, jdbcType=INTEGER})")
	int insertByMap(Map<String, Object> map);

	@Insert("insert into product_category(category_name, category_type) values(#{categoryName, jdbcType=VARCHAR}, #{categoryType, jdbcType=INTEGER})")
	int insertByObject(ProductCategory productCategory);

	@Select("select * from product_category where category_type = #{categoryType}")
	@Results({
			@Result(column = "category_id", property = "categoryId"),
			@Result(column = "category_type", property = "categoryType"),
			@Result(column = "category_name", property = "categoryName")
	})
	ProductCategory findByCategoryType(Integer categoryType);

	@Select("select * from product_category where category_name = #{categoryName}")
	@Results({
			@Result(column = "category_id", property = "categoryId"),
			@Result(column = "category_type", property = "categoryType"),
			@Result(column = "category_name", property = "categoryName")
	})
	List<ProductCategory> findByCategoryName(String categoryName);

	@Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
	int updateByCategoryType(@Param("categoryName") String categoryName, @Param("categoryType") Integer categoryType);

	@Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
	int updateByObject(ProductCategory productCategory);

	@Delete("delete from product_category where category_type = #{categoryType}")
	int deleteByCategoryType(Integer categoryType);

//	使用xml的方式
	ProductCategory findByCategoryId(Integer categoryId);
}
