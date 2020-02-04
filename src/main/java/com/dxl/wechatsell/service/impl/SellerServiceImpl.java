package com.dxl.wechatsell.service.impl;

import com.dxl.wechatsell.dao.SellerInfoDao;
import com.dxl.wechatsell.dataobject.SellerInfo;
import com.dxl.wechatsell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 20:36 on 2020/1/29.
 */
@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private SellerInfoDao sellerInfoDao;

	@Override
	public SellerInfo findSellerInfoByOpenId(String openId) {
		return sellerInfoDao.findByOpenId(openId);
	}
}
