package com.dxl.wechatsell.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author：Daixinliang
 * @Description：公众平台
 * @Date：Created in 21:06 on 2020/1/29.
 */
@Component
public class WechatMpConfig {

	@Autowired
	private WechatAccountConfig accountConfig;

	@Bean
	public WxMpService wxMpService() {
		WxMpService wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(WxMpConfigStorage());
		return wxMpService;
	}

	@Bean
	public WxMpConfigStorage WxMpConfigStorage() {
		WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
		wxMpConfigStorage.setAppId(accountConfig.getMpAppId());
		wxMpConfigStorage.setSecret(accountConfig.getMpAppSecret());
		return wxMpConfigStorage;
	}
}
