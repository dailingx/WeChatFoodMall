package com.dxl.wechatsell.service;

import com.dxl.wechatsell.dataobject.SellerInfo;

/**
 * @author：Daixinliang
 * @Description：卖家端
 * @Date：Created in 20:34 on 2020/1/29.
 */
public interface SellerService {

	SellerInfo findSellerInfoByOpenId(String openId);
}
