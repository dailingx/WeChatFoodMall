package com.dxl.wechatsell.service;

import com.dxl.wechatsell.dataobject.ProductInfo;
import com.dxl.wechatsell.dto.ShoppingCartDTO;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 8:56 on 2019/10/24.
 */
public interface ProductInfoService {

	ProductInfo findOne(String productId);

	//客户端，查询所有在架商品
	List<ProductInfo> findUpAll();

	//管理端，查询所有商品且分页,带分页参数去查返回的是Page对象
	Page<ProductInfo> findAll(Pageable pageable);

	ProductInfo save(ProductInfo productInfo);

	//加库存
	void increaseStock(List<ShoppingCartDTO> shoppingCartDTOList);

	//减库存
	void decreaseStock(List<ShoppingCartDTO> shoppingCartDTOList);

//	商品上架
	ProductInfo onSale(String productId);

//	商品下架
	ProductInfo offSale(String productId);
}
