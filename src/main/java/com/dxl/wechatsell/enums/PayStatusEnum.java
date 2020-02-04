package com.dxl.wechatsell.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 7:24 on 2019/10/30.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum PayStatusEnum implements CodeEnum {

	WAIT(0, "等待支付"),
	SUCCESS(1, "支付成功");

	private Integer code;

	private String message;


}
