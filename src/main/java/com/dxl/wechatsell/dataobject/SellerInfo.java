package com.dxl.wechatsell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author：Daixinliang
 * @Description：
 * @Date：Created in 20:27 on 2020/1/29.
 */
@Data
@Entity
public class SellerInfo {

	@Id
	private String sellerId;

	private String username;

	private String password;

	private String openId;
}
