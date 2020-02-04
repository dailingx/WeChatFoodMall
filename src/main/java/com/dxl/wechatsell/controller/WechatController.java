package com.dxl.wechatsell.controller;

import com.dxl.wechatsell.config.ProjectUrlConfig;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 21:18 on 2020/1/29.
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

	@Autowired
	private WxMpService wxMpService;

	@Autowired
	private WxMpService wxOpenMpService;

	@Autowired
	private ProjectUrlConfig projectUrlConfig;

	@GetMapping("/authorize")
	public String authorize(@RequestParam("returnUrl") String returnUrl) {
//		1、配置
//		2、调用方法
		String url = projectUrlConfig.getWechatMpAuthorize() + "/sell/wechat/userInfo";
		String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.O)
		return "redirect:" + redirectUrl;
	}

	@GetMapping("/userInfo")
	public String userInfo(@RequestParam("code") String code,
	                       @RequestParam("state") String returnUrl) {

	}

	@GetMapping("/qruserInfo")
	public String qruserInfo(@RequestParam("code") String code,
	                         @RequestParam("state") String returnUrl) {
		String url = projectUrlConfig.getWechatOpenAuthorize() + "/sell/wechat/userInfo";
		String redirectUrl = wxOpenMpService.
		return "redirect:" + redirectUrl;
	}

	@GetMapping("/qrUserInfo")
	public String qrUserInfo(@RequestParam("code") String code,
	                         @RequestParam("state") String returnUrl) {

	}
}
