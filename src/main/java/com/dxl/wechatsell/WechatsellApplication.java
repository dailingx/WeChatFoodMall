package com.dxl.wechatsell;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//mybati使用mapper文件时需要扫描mapper文件
@MapperScan(basePackages = "com.dxl.wechatsell.dataobject.mapper")
//使用缓存
@EnableCaching
public class WechatsellApplication {

	public static void main(String[] args) {
	SpringApplication.run(WechatsellApplication.class, args);
}

}
