package com.dxl.wechatsell.controller;

import com.dxl.wechatsell.service.SecKillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 18:56 on 2020/1/31.
 */
@RestController
@RequestMapping("/skill")
@Slf4j
public class SecKillController {

	@Autowired
	private SecKillService secKillService;

//	查询秒杀活动特价商品的信息
	@GetMapping("/query/{productId}")
	public String query(@PathVariable String productId) {
		return secKillService.querySecKillProductInfo(productId);
	}

//	秒杀，没有抢到会返回"哎呦喂，xxx"，抢到了则返回剩余的库存量
	@GetMapping("/order/{productId}")
	public String skill(@PathVariable String productId) {
		log.info("@skill request, productId:{}", productId);
		secKillService.orderProductMockDiffUser(productId);
		return secKillService.querySecKillProductInfo(productId);
	}

}
