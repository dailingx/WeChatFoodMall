package com.dxl.wechatsell.service;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 18:57 on 2020/1/31.
 */
public interface SecKillService {

	String querySecKillProductInfo(String productId);

	void orderProductMockDiffUser(String productId);

	void orderProductMockDiffUserByRedis(String productId);
}
