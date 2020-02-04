package com.dxl.wechatsell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 7:28 on 2019/10/30.
 */
@Entity
@Data
public class OrderDetail {

	@Id
	private String detailId;

	private String orderId;

	private String productId;

	private String productName;

	private BigDecimal productPrice;

	private Integer productQuantity;

	private String productIcon;
}
