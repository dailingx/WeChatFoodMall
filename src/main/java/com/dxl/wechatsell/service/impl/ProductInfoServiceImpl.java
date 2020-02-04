package com.dxl.wechatsell.service.impl;

import com.dxl.wechatsell.exception.SellException;
import com.dxl.wechatsell.dao.ProductInfoDao;
import com.dxl.wechatsell.dataobject.ProductInfo;
import com.dxl.wechatsell.dto.ShoppingCartDTO;
import com.dxl.wechatsell.enums.ProductStatusEnum;
import com.dxl.wechatsell.enums.ResultEnum;
import com.dxl.wechatsell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 9:00 on 2019/10/24.
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

	@Autowired
	private ProductInfoDao productInfoDao;

	@Override
	public ProductInfo findOne(String productId) {
		return productInfoDao.findOne(productId);
	}

	@Override
	public List<ProductInfo> findUpAll() {
		return productInfoDao.findByProductStatus(ProductStatusEnum.UP.getCode());
	}

	@Override
	public Page<ProductInfo> findAll(Pageable pageable) {
		return productInfoDao.findAll(pageable);
	}

	@Override
	public ProductInfo save(ProductInfo productInfo) {
		return productInfoDao.save(productInfo);
	}

	@Override
	@Transactional
	public void increaseStock(List<ShoppingCartDTO> shoppingCartDTOList) {

	}

	////可能并发量比较大的时候，在扣库存时先读取库存再扣可能就会有线程不安全,可以用redis的锁机制来避免这个问题
	@Override
	@Transactional
	public void decreaseStock(List<ShoppingCartDTO> shoppingCartDTOList) {
		for (ShoppingCartDTO shoppingCardDTO : shoppingCartDTOList) {
			ProductInfo productInfo = productInfoDao.findOne(shoppingCardDTO.getProductId());
			if (productInfo == null) {
				throw new SellException(ResultEnum.PRODECT_NO_EXIST);
			}

			Integer decreaseResult = productInfo.getProductStock() - shoppingCardDTO.getProductQuantity();

			if (decreaseResult < 0) {
				throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
			}

			productInfo.setProductStock(decreaseResult);

			productInfoDao.save(productInfo);
		}
	}

	@Override
	public ProductInfo onSale(String productId) {
		ProductInfo productInfo = productInfoDao.findOne(productId);
		if (productInfo == null) {
			throw new SellException(ResultEnum.PRODECT_NO_EXIST);
		}
		if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
			throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
		}
		productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
		return productInfoDao.save(productInfo);
	}

	@Override
	public ProductInfo offSale(String productId) {
		ProductInfo productInfo = productInfoDao.findOne(productId);
		if (productInfo == null) {
			throw new SellException(ResultEnum.PRODECT_NO_EXIST);
		}
		if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
			throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
		}
		productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
		return productInfoDao.save(productInfo);
	}
}
