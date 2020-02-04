package com.dxl.wechatsell.service.impl;

import com.dxl.wechatsell.enums.ResultEnum;
import com.dxl.wechatsell.exception.SellException;
import com.dxl.wechatsell.lock.RedisLock;
import com.dxl.wechatsell.service.SecKillService;
import com.dxl.wechatsell.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 18:58 on 2020/1/31.
 */
@Service
public class SecKillServiceImpl implements SecKillService {

	private static final int TIMEOUT = 10 * 1000;//超时时间10秒

	@Autowired
	private RedisLock redisLock;

	/**
	 * 国庆活动，皮蛋粥特价限量100000份
	 *
	 * 模拟多个表，商品信息表、库存表、秒杀成功订单表
	 */
	static Map<String,Object> products;
	static Map<String,Object> stock;
	static Map<String,Object> orders;

	static {
		products = new HashMap<>();
		stock = new HashMap<>();
		orders = new HashMap<>();

		products.put("157875196366160022",100000);
		stock.put("157875196366160022",100000);
	}
	/**
	 * 模拟查询秒杀商品的信息
	 * @param productId
	 * @return
	 */
	@Override
	public String querySecKillProductInfo(String productId) {
		return "国庆活动，皮蛋粥特价，限量份"
				+ products.get(productId)
				+ "还剩" + stock.get(productId) + "份"
				+ "该商品成功下单用户数：" + orders.size() + "人";
	}

	/**
	 * 高并发秒杀操作.妙杀失败返回“哎呦喂，xxxxx”。
	 * @param productId
	 * @return
	 */
	@Override
	public void orderProductMockDiffUser(String productId) {
		seckill(productId);
	}

	/**
	 * 使用Redis分布式锁处理高并发
	 * @param productId
	 * 很多个请求进来，速度会比syn关键字快很多，但并不能保证这些请求都能下单成功，因为有很多请求是拿不到锁的，但可以保证结果不出错
	 */
	public void orderProductMockDiffUserByRedis(String productId) {
		//1. 加Redis分布式锁
		long time = System.currentTimeMillis() + TIMEOUT;
		if(!redisLock.lock(productId, String.valueOf(time))){
			//加锁失败
			throw new SellException(ResultEnum.ACTIVITY_BUSY);
		}

		//秒杀操作
		seckill(productId);

		//2.解除Redis分布式锁
		redisLock.unlock(productId, String.valueOf(time));
	}

	/**
	 * 秒杀操作
	 * @param productId
	 */
	private void seckill(String productId){
		//1. 查询该商品库存，为0则活动结束
		Integer stockNum = Integer.valueOf(stock.get(productId).toString());
		if(stockNum == 0){
			throw new SellException(ResultEnum.ACTIVITY_END);
		}else{
			//2. 下单（模拟不同用户openid不同）
			orders.put(KeyUtil.genUniqueKey(),productId);
			//3. 减库存
			stockNum -= 1;
			try {
//				可能会有io操作或者其他一些逻辑处理，所以休眠100ms
				Thread.sleep(100);
			}catch (InterruptedException e){
				e.printStackTrace();
			}
			stock.put(productId, stockNum);
		}
	}
}
