package com.dxl.wechatsell.dataobject;

import com.dxl.wechatsell.enums.ProductStatusEnum;
import com.dxl.wechatsell.utils.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 22:02 on 2019/10/23.
 */
@Entity
@Data
public class ProductInfo {

	@Id
	private String productId;

	private String productName;

	private BigDecimal productPrice;

	//库存
	private Integer productStock;

	private String productDescription;

	private String productIcon;

	//状态，0正常，1下架
	private Integer productStatus;

	//类目编号，商品和类目的关系用该字段做关联
	private Integer categoryType;

	@JsonIgnore
	public ProductStatusEnum getProductStatusEnum() {
		return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
	}
}
