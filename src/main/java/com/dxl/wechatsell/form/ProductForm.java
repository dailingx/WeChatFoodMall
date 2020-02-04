package com.dxl.wechatsell.form;

import com.dxl.wechatsell.enums.ProductStatusEnum;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 23:01 on 2020/1/27.
 */
@Data
public class ProductForm {

	private String productId;

	private String productName;

	private BigDecimal productPrice;

	private Integer productStock;

	private String productDescription;

	private String productIcon;

	private Integer productStatus = ProductStatusEnum.UP.getCode();

	private Integer categoryType;
}
