package com.dxl.wechatsell.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 23:19 on 2019/12/22.
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {

	SUCCESS(0, "成功"),
	PARAM_ERROR(1, "参数不正确"),
	PRODECT_NO_EXIST(10, "商品不存在"),
	PRODUCT_STOCK_ERROR(11, "商品库存不正确"),
	ORDER_NOT_EXIST(12, "订单不存在"),
	ORDERDETAIL_NOT_EXIST(13, "订单详情不存在"),
	ORDER_STATUS_ERROR(14, "订单状态错误"),
	ORDER_UPDATE_FAIL(15, "订单更新失败"),
	ORDER_PAY_STATUS_ERROR(16, "订单支付状态错误"),
	SHOPPINGCART_EMPTY(17, "购物车为空"),
	ORDER_OWNER_ERROR(18, "该订单不属于当前用户"),
	WECHAT_MP_ERROR(19, "微信公众号方面错误"),
	PRODUCT_STATUS_ERROR(20, "商品状态错误"),
	LOGIN_FAIL(21, "登录失败,登录信息不正确"),
	LOGOUT_SUCCESS(22, "登出成功"),
	ACTIVITY_END(100, "活动结束"),
	ACTIVITY_BUSY(101, "活动繁忙"),
	;

	private Integer code;

	private String message;
}
