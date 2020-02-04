package com.dxl.wechatsell.controller;

import com.dxl.wechatsell.exception.SellException;
import com.dxl.wechatsell.dataobject.ProductCategory;
import com.dxl.wechatsell.form.CategoryForm;
import com.dxl.wechatsell.service.ProductCategoryService;
import com.dxl.wechatsell.utils.ResultUtil;
import com.dxl.wechatsell.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 17:16 on 2020/1/29.
 */
@RestController
@RequestMapping("/seller/category")
public class SellerCategoryController {

	@Autowired
	private ProductCategoryService productCategoryService;

	@GetMapping("/list")
	public Result list() {
		List<ProductCategory> productCategoryList = productCategoryService.findAll();
		return ResultUtil.success(productCategoryList);
	}

	@GetMapping("/get")
	public Result get(@RequestParam(value = "categoryId", required = false) Integer categoryId) {
		ProductCategory productCategory = new ProductCategory();
		if (categoryId != null) {
			productCategory = productCategoryService.findOne(categoryId);
		}
		return ResultUtil.success(productCategory);
	}

	@PostMapping("/save")
	public Result save(@Valid CategoryForm categoryForm,
	                   BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
//			return ResultUtil.error()
		}

		ProductCategory productCategory = new ProductCategory();
		try {
			if (categoryForm.getCategoryId() != null) {
				productCategory = productCategoryService.findOne(categoryForm.getCategoryId());
			}
			BeanUtils.copyProperties(categoryForm, productCategory);
		} catch (SellException e) {

		}

		productCategoryService.save(productCategory);

		return ResultUtil.success();
	}
}
