package com.dxl.wechatsell.controller;

import com.dxl.wechatsell.dataobject.ProductCategory;
import com.dxl.wechatsell.dataobject.ProductInfo;
import com.dxl.wechatsell.service.ProductCategoryService;
import com.dxl.wechatsell.service.ProductInfoService;
import com.dxl.wechatsell.utils.ResultUtil;
import com.dxl.wechatsell.vo.ProductInfoVO;
import com.dxl.wechatsell.vo.ProductVO;
import com.dxl.wechatsell.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author：Daixinliang
 * @Description：买家商品相关
 * @Date：Created in 13:33 on 2019/10/24.
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

	@Autowired
	private ProductInfoService productInfoService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	当第一次访问进来的时候，会访问方法里并返回Result对象，这个注解会把这个对象存到redis里作为缓存，后面访问就不会到方法里了而是直接取redis缓存
	@Cacheable(cacheNames = "product", key = "123")
	public Result list() {
		//1.查询所有的上架商品
		List<ProductInfo> productInfoList = productInfoService.findUpAll();

		//2.查询需要的类目(一次性查询，不能查出10条商品然后for循环10次去查他们的所属类目)
		//使用lambda表达式,先转成一个stream，然后我们需要的是getCategoryType，再作为一个list来收集
		List<Integer> categoryTypeList = productInfoList.stream()
				.map(e -> e.getCategoryType())
				.collect(Collectors.toList());
		List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);

		//3.数据拼装
		List<ProductVO> productVOList = new ArrayList<>();
		for (ProductCategory productCategory : productCategoryList) {
			ProductVO productVO = new ProductVO();
			productVO.setCategoryName(productCategory.getCategoryName());
			productVO.setCategoryType(productCategory.getCategoryType());

			List<ProductInfoVO> productInfoVOList = new ArrayList<>();
			for (ProductInfo productInfo : productInfoList) {
				if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
					ProductInfoVO productInfoVO = new ProductInfoVO();
					BeanUtils.copyProperties(productInfo, productInfoVO);
					productInfoVOList.add(productInfoVO);
				}
			}
			productVO.setProductInfoVOList(productInfoVOList);

			productVOList.add(productVO);
		}

		return ResultUtil.success(productVOList);
	}
}
