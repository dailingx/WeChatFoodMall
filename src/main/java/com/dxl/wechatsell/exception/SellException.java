package com.dxl.wechatsell.exception;

import com.dxl.wechatsell.enums.ResultEnum;
import lombok.Getter;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 23:04 on 2019/12/22.
 */
@Getter
public class SellException extends RuntimeException {

	private Integer code;

	public SellException(ResultEnum resultEnum) {
		//把message的内容传到父类的构造方法里
		super(resultEnum.getMessage());

		this.code = resultEnum.getCode();
	}

	public SellException(Throwable cause) {
		super(cause);
	}

	public SellException(Integer code, String message) {
		super(message);
		this.code = code;
	}

	public SellException(String message, Throwable cause) {
		super(message, cause);
	}

	public SellException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
