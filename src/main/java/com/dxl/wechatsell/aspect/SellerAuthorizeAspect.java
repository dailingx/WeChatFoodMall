package com.dxl.wechatsell.aspect;

import com.dxl.wechatsell.constant.CookieConstant;
import com.dxl.wechatsell.constant.RedisConstant;
import com.dxl.wechatsell.exception.SellerAuthorizeException;
import com.dxl.wechatsell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 20:54 on 2020/1/30.
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

	@Autowired
	private StringRedisTemplate redisTemplate;

//	切入到所有的卖家操作方法，并且排掉登录和登出方法
	@Pointcut("execution(public * com.dxl.wechatsell.controller.Seller*.*(..))" +
	"&& !execution(public * com.dxl.wechatsell.controller.SellerUserController.*(..))")
	public void verify() {}

//	在切入点之前执行该方法的操作
	@Before("verify()")
	public void doVerify() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
		if (cookie == null) {
			log.warn("【登录校验】Cookie中查不到token");
			throw new SellerAuthorizeException();
		}

		String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
		if (StringUtils.isEmpty(tokenValue)) {
			log.warn("【登录校验】Redis查不到token");
			throw new SellerAuthorizeException();
		}
	}
}
