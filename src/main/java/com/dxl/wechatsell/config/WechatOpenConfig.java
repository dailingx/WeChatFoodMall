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
 * @Description：
 * @Date：Created in 21:08 on 2020/1/29.
 */
@Component
public class WechatOpenConfig {

	@Autowired
	private WechatAccountConfig accountConfig;

	@Bean
	public WxMpService WxOpenService() {
		WxMpService wxOpenService = new WxMpServiceImpl();
		wxOpenService.setWxMpConfigStorage(WxOpenConfigStorage());
		return wxOpenService;
	}

	@Bean
	public WxMpConfigStorage WxOpenConfigStorage() {
		WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
		wxMpInMemoryConfigStorage.setAppId(accountConfig.getOpenAppId());
		wxMpInMemoryConfigStorage.setSecret(accountConfig.getOpenAppSecret());
		return wxMpInMemoryConfigStorage;
	}
}
