package com.dxl.wechatsell.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author：Daixinliang
 * @Description：商品状态枚举类
 * @Date：Created in 9:02 on 2019/10/24.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ProductStatusEnum implements CodeEnum {

	UP(0, "在架"),
	DOWN(1, "下架");

	private Integer code;

	private String message;
}
