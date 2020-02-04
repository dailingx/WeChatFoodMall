package com.dxl.wechatsell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author：Daixinliang
 * @Description：类目表映射过来的对象
 * 数据库中表名为product_category，如果数据库中表名不为此而类名为ProductCategory，则需要在类上加注解@Table(name = ""数据库对应表名)
 * @Date：Created in 16:56 on 2019/10/5.
 */
@Entity
@DynamicUpdate
@Data
public class ProductCategory {
	//类目id
	@Id
	@GeneratedValue
	private Integer categoryId;

	//类目名字
	private String categoryName;

	//类目编号
	private Integer categoryType;

	private Date createTime;

	private Date updateTime;

	public ProductCategory() {}

	public ProductCategory(String categoryName, Integer categoryType) {
		this.categoryName = categoryName;
		this.categoryType = categoryType;
	}
}
