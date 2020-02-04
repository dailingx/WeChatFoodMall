package com.dxl.wechatsell.utils;

import java.util.Random;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 23:02 on 2019/12/24.
 */
public class KeyUtil {

	//生成唯一的主键,格式：时间+随机数,需要防止多线程并发时产生相同的主键
	public static synchronized String genUniqueKey() {
		Random random = new Random();
		Integer randomNum = random.nextInt(900000) + 100000;
		return System.currentTimeMillis() + String.valueOf(randomNum);
	}
}
