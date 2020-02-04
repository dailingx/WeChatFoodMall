package com.dxl.wechatsell.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 20:10 on 2020/1/31.
 */
@Component
@Slf4j
public class RedisLock {

	@Autowired
	private StringRedisTemplate redisTemplate;

	/**
	 * 加锁，使用setnx命令
	 * @param key  productId
	 * @param value  当前时间+超时时间
	 * @return
	 */
	public boolean lock(String key, String value) {
		//redis的setnx命令，能够设置value进去，表示原来没有值，那么就把值设置进去了，加锁成功。当前线程拿到了锁
		if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
			return true;
		}

		//解决因加锁成功后出现异常而未执行解锁操作(到达不了解锁那一步)导致的死锁问题——通过锁超时解决
		String currentValue = redisTemplate.opsForValue().get(key);
		//如果锁过期
		if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
			//获取上一个锁的时间，同时把当前的value设置进去
//			假设有两个线程同时到达此处，这一行代码只能有一个线程先去执行。设currentValue=A，两个线程value=B，下面的if可以保证只有一个线程拿到锁
			String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
			if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
				return true;
			}
		}

		//加锁失败
		return false;
	}

	/**
	 * 解锁
	 * @param key
	 * @param value
	 */
	public void unlock(String key, String value) {
		try {
			String currentValue = redisTemplate.opsForValue().get(key);
			if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
				//从redis中删除key
				redisTemplate.opsForValue().getOperations().delete(key);
			}
		} catch (Exception e) {
			log.error("【redis分布式锁】解锁异常，e={}",e);
		}
	}
}
