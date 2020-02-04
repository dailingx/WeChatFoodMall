package com.dxl.wechatsell.controller;

import com.dxl.wechatsell.exception.SellException;
import com.dxl.wechatsell.config.ProjectUrlConfig;
import com.dxl.wechatsell.constant.CookieConstant;
import com.dxl.wechatsell.constant.RedisConstant;
import com.dxl.wechatsell.dataobject.SellerInfo;
import com.dxl.wechatsell.enums.ResultEnum;
import com.dxl.wechatsell.service.SellerService;
import com.dxl.wechatsell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author：Daixinliang
 * @Description：卖家用户相关操作
 * @Date：Created in 22:48 on 2020/1/29.
 */
//涉及到页面跳转等一些页面的呈现，因此不用RestController
@Controller
@RequestMapping("/seller")
@Slf4j
public class SellerUserController {

	@Autowired
	private SellerService sellerService;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	private ProjectUrlConfig projectUrlConfig;

	@GetMapping("/login")
	public ModelAndView login(@RequestParam("openId") String openId,
	                          HttpServletResponse response) {
//		1、使用openId去和数据库里的数据匹配
		SellerInfo sellerInfo = sellerService.findSellerInfoByOpenId(openId);
		if (sellerInfo == null) {
			throw new SellException(ResultEnum.LOGIN_FAIL);
		}

//		2、设置token到redis
		String token = UUID.randomUUID().toString();
		Integer expire = RedisConstant.EXPIRE;
		redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openId, expire, TimeUnit.SECONDS);

//		3、设置token到cookie
		CookieUtil.set(response, CookieConstant.TOKEN, token, expire);

//		最好写成绝对地址，相对地址的话是会把跳转url接在当前页面地址后面
		return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/sell/seller/order/list");
	}

	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest request,
	                           HttpServletResponse response,
	                           Map<String, Object> map) {
//		1、从cookie里查询
		Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
		if (cookie != null) {
//			2、清除redis
			redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));

//			3、清除cookie
			CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
		}
		map.put("msg", ResultEnum.LOGOUT_SUCCESS);
//		需要跳转到的页面
		map.put("url", "/sell/seller/order/list");

		return new ModelAndView("/common/success", map);
	}
}
