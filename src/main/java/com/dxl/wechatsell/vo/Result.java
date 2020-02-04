package com.dxl.wechatsell.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @author：Daixinliang
 * @Description：vueobject，http请求返回给vue前端数据的最外层对象
 * @Date：Created in 7:46 on 2019/10/25.
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

	public static final long serialVersionUID = 5877549321548546228L;

	private Integer code;

	private String msg;

	//具体内容，可能返回多种对象，因此采用泛型
	private T data;
}
