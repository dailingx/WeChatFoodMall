package com.dxl.wechatsell.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author：Daixinliang
 * @Description：购物车实体类
 * @Date：Created in 15:46 on 2020/1/5.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDTO {
	private String productId;

	private Integer productQuantity;
}
