package com.dxl.wechatsell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 22:37 on 2020/1/29.
 */
@Data
@ConfigurationProperties(prefix = "ProjectUrl")
@Component
public class ProjectUrlConfig {

//	微信公众账号授权url
	public String wechatMpAuthorize;

//	微信开放平台授权url
	public String wechatOpenAuthorize;

//	点餐系统url
	public String sell;
}
