package com.dxl.wechatsell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author：Daixinliang
 * @Description：返回给前端的商品详情对象
 * @Date：Created in 7:58 on 2019/10/25.
 */
@Data
public class ProductInfoVO implements Serializable {

	public static final long serialVersionUID = 5877549321548546281L;

	@JsonProperty("id")
	private String productId;

	@JsonProperty("name")
	private String productName;

	@JsonProperty("price")
	private BigDecimal productPrice;

	@JsonProperty("description")
	private String productDescription;

	@JsonProperty("icon")
	private String productIcon;
}
