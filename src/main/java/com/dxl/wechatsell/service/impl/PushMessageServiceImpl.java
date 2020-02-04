package com.dxl.wechatsell.service.impl;

import com.dxl.wechatsell.config.WechatAccountConfig;
import com.dxl.wechatsell.dto.OrderDTO;
import com.dxl.wechatsell.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 21:57 on 2020/1/30.
 */
@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {

	@Autowired
	private WxMpService wxMpService;

	@Autowired
	private WechatAccountConfig accountConfig;

	@Override
	public void orderStatus(OrderDTO orderDTO) {
		WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
		templateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));
		templateMessage.setToUser(orderDTO.getBuyerOpenId());

		List<WxMpTemplateData> data = Arrays.asList(
				new WxMpTemplateData("first", "亲,请记得收货"),
				new WxMpTemplateData("keyword1", "微信点餐"),
				new WxMpTemplateData("keyword2", "18296788907"),
				new WxMpTemplateData("keyword3", orderDTO.getOrderId()),
				new WxMpTemplateData("keyword4", orderDTO.getOrderStatusEnum().getMessage()),
				new WxMpTemplateData("keyword5", "$" + orderDTO.getOrderAmount()),
				new WxMpTemplateData("remarl", "欢迎再次光临！")
		);
		templateMessage.setData(data);

		try {
			wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
		} catch (WxErrorException e) {
			log.error("【微信模板消息】发送失败, {}", e);
		}
	}
}
