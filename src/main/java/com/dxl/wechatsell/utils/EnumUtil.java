package com.dxl.wechatsell.utils;

import com.dxl.wechatsell.enums.CodeEnum;

/**
 * @author：Daixinliang
 * @Description：根据枚举类的code得到message的工具
 * @Date：Created in 17:19 on 2020/1/27.
 */
public class EnumUtil {

	public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
		for (T each : enumClass.getEnumConstants()) {
			if (code.equals(each.getCode())) {
				return each;
			}
		}
		return null;
	}
}
