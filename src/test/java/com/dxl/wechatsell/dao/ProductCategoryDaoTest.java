package com.dxl.wechatsell.dao;

import com.dxl.wechatsell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author：Daixinliang
 * @Description：单元测试
 * @Date：Created in 17:07 on 2019/10/5.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {

	@Autowired
	private ProductCategoryDao productCategoryDao;

	@Test
	public void findOne() {
		ProductCategory productCategory = productCategoryDao.findOne(1);
		System.out.println(productCategory.toString());
	}

	@Test
	@Transactional
	public void saveOne() {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setCategoryName("蔬菜");
		productCategory.setCategoryType(3);
		ProductCategory result = productCategoryDao.save(productCategory);
		Assert.assertNotNull(result);
	}

	@Test
	public void findByCategoryTypeInTest() {
		List<Integer> list = Arrays.asList(2, 3, 4);
		List<ProductCategory> result = productCategoryDao.findByCategoryTypeIn(list);
		//不期望result.size()为0，即非0则表示测试成功
		Assert.assertNotEquals(0, result.size());
//		也可以是这样的写法,当不满足后面的条件语句时会输出message
		Assert.assertTrue("***", result.size() > 0);
	}

	@Test
	public void TestJDBC() {
		Connection conn = null;
		String url = "jdbc:mysql://127.0.0.1:3306/wechatsell?characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai";
		String username = "root";
		String password = "123456";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("驱动加载成功！");
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("已连接上"+url+"：\n数据库"+conn);
		} catch (ClassNotFoundException e) {
			System.out.println("没有找到驱动！");
		} catch (SQLException e) {
			System.out.println("获取链接失败");
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("JDBC连接关闭失败");
			}
		}
	}

}