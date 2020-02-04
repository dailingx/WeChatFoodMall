package com.dxl.wechatsell.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 8:21 on 2019/10/29.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum OrderStatusEnum implements CodeEnum{
	NEW(0, "新下单"),
	FINISHED(1, "完成"),
	CANCEL(2, "已取消");

	private Integer code;

	private String message;
}
