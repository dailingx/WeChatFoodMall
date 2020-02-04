package com.dxl.wechatsell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author：Daixinliang
 * @Description：返回给前端的对象，商品(包含类目),即多个类目下的多个商品
 * @Date：Created in 7:53 on 2019/10/25.
 */
@Data
public class ProductVO implements Serializable {

public static final long serialVersionUID = 5877549321548546257L;

	@JsonProperty("name")
	private String categoryName;

	@JsonProperty("type")
	private Integer categoryType;

	@JsonProperty("foods")
	private List<ProductInfoVO> productInfoVOList;
}
