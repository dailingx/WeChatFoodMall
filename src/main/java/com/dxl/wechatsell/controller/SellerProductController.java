package com.dxl.wechatsell.controller;

import com.dxl.wechatsell.exception.SellException;
import com.dxl.wechatsell.dataobject.ProductCategory;
import com.dxl.wechatsell.dataobject.ProductInfo;
import com.dxl.wechatsell.form.ProductForm;
import com.dxl.wechatsell.service.ProductCategoryService;
import com.dxl.wechatsell.service.ProductInfoService;
import com.dxl.wechatsell.utils.KeyUtil;
import com.dxl.wechatsell.utils.ResultUtil;
import com.dxl.wechatsell.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 21:18 on 2020/1/27.
 */
@RestController
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

	@Autowired
	private ProductInfoService productInfoService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@GetMapping("list")
	public Result list(@RequestParam(value = "page", defaultValue = "0") Integer page,
	                   @RequestParam(value = "size", defaultValue = "10") Integer size) {
		PageRequest pageRequest = new PageRequest(page - 1, size);
		Page<ProductInfo> productInfoPage = productInfoService.findAll(pageRequest);
		return ResultUtil.success(productInfoPage.getContent());
	}

	@GetMapping("/onSale")
	public Result onSale(@RequestParam("productId") String productId) {
		try {
			productInfoService.onSale(productId);
		} catch (SellException e) {
//			return ResultUtil.error(500, e.getMessage());
		}
		return ResultUtil.success();
	}

	@GetMapping("/offSale")
	public Result offSale(@RequestParam("productId") String productId) {
		try {
			productInfoService.offSale(productId);
		} catch (SellException e) {
		}
		return ResultUtil.success();
	}

	@GetMapping("/get")
	public Result get(@RequestParam(value = "productId", required = false) String productId) {
		ProductInfo productInfo = new ProductInfo();
		if (!StringUtils.isEmpty(productId)) {
			productInfo = productInfoService.findOne(productId);
		}

		//查询所有的类目
		List<ProductCategory> productCategoryList = productCategoryService.findAll();
		return ResultUtil.success(productInfo);
	}

//	新增和更新都使用这一方法
	@PostMapping("/save")
	public Result save(@Valid ProductForm productForm,
	                   BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			log.error("【保存商品信息】参数不正确, productForm={}", productForm);
		}

		ProductInfo productInfo = new ProductInfo();
		try {
			if (!StringUtils.isEmpty(productForm.getProductId())) {
//				修改操作,先查出旧的值，再用提交表单productForm包含的字段进行商品信息的覆盖
				productInfo = productInfoService.findOne(productForm.getProductId());

			} else {
				productForm.setProductId(KeyUtil.genUniqueKey());
			}
			BeanUtils.copyProperties(productForm, productInfo);
			productInfoService.save(productInfo);
		} catch (SellException e) {

		}

		return ResultUtil.success();
	}
}
