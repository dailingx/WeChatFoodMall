package com.dxl.wechatsell.handler;

import com.dxl.wechatsell.config.ProjectUrlConfig;
import com.dxl.wechatsell.exception.RealResponseCodeException;
import com.dxl.wechatsell.exception.SellException;
import com.dxl.wechatsell.exception.SellerAuthorizeException;
import com.dxl.wechatsell.utils.ResultUtil;
import com.dxl.wechatsell.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author：Daixinliang
 * @Description：捕获异常
 * @Date：Created in 21:23 on 2020/1/30.
 */
@ControllerAdvice
public class SellExceptionHandler {

	@Autowired
	private ProjectUrlConfig projectUrlConfig;

//	拦截登录异常
	@ExceptionHandler(value = SellerAuthorizeException.class)
	public ModelAndView handlerSellerAuthorizeException() {
		return new ModelAndView("redirect:"
				.concat(projectUrlConfig.getWechatOpenAuthorize())
				.concat("/sell/wechat/qrAuthorize")
				.concat("?returnUrl=")
				.concat(projectUrlConfig.getSell())
				.concat("/sell/seller/login")
		);
	}

//	避免SellException异常往前端返回而打乱了原本的Result样式
	@ExceptionHandler(value = SellException.class)
//	因为不是RestController因此需要这个注解
	@ResponseBody
	public Result handlerSellException(SellException e) {
		return ResultUtil.error(e.getCode(), e.getMessage());
	}

	@ExceptionHandler(value = RealResponseCodeException.class)
//	不会返回内容，只会得到真实的403响应
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public void handlerRealResponseCodeException(RealResponseCodeException e) {
	}
}
