package com.dxl.wechatsell.utils;

import com.dxl.wechatsell.vo.Result;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 13:16 on 2019/10/25.
 */
public class ResultUtil {

	public static Result success(Object object) {
		Result result = new Result();
		result.setCode(200);
		result.setMsg("成功");
		result.setData(object);
		return result;
	}

	public static Result success() {
		return null;
	}

	public static Result error(Integer code, String msg) {
		Result result = new Result();
		result.setCode(code);
		result.setMsg(msg);
		return result;
	}
}
