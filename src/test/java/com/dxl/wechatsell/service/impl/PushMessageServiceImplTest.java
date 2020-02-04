package com.dxl.wechatsell.service.impl;

import com.dxl.wechatsell.dto.OrderDTO;
import com.dxl.wechatsell.service.OrderService;
import com.dxl.wechatsell.service.PushMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 22:12 on 2020/1/30.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImplTest {

	@Autowired
	private PushMessageService pushMessageService;

	@Autowired
	private OrderService orderService;

	@Test
	public void orderStatus() throws Exception {
		OrderDTO orderDTO = orderService.findOne("****");
		pushMessageService.orderStatus(orderDTO);
	}

}