package com.dxl.wechatsell.dto;

import com.dxl.wechatsell.dataobject.OrderDetail;
import com.dxl.wechatsell.enums.OrderStatusEnum;
import com.dxl.wechatsell.enums.PayStatusEnum;
import com.dxl.wechatsell.utils.Date2LongSerializer;
import com.dxl.wechatsell.utils.EnumUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author：Daixinliang
 * @Description：数据传输对象，专门在各个层之间传输用的
 * @Date：Created in 7:53 on 2019/10/30.
 */
@Entity
@Data
@DynamicUpdate
//当前实体类为null的字段在转json的时候过滤掉
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

	private String orderId;

	private String buyerName;

	private String buyerPhone;

	private String buyerAddress;

	private String buyerOpenId;

	private BigDecimal orderAmount;

	private Integer orderStatus;

	private Integer payStatus;

	@JsonSerialize(using = Date2LongSerializer.class)
	private Date createTime;

	@JsonSerialize(using = Date2LongSerializer.class)
	private Date updateTime;

	//比订单主表多一个订单详情list来用于传输
	//在这里赋一个初始值(new一个空列表)是为了避免返回null
	List<OrderDetail> orderDetailList = new ArrayList<>();

	//获取枚举的值即message,并且在该对象转json的时候需要忽略这个方法产生的字段
//	在后续需要使用枚举类code对应的message时，比如将该对象传给前端(前端去根据code判断来得message肯定是复用性不强的)，就可以orderDTO.getOrderStatusEnum().message
	@JsonIgnore
	public OrderStatusEnum getOrderStatusEnum() {
		return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
	}

	@JsonIgnore
	public PayStatusEnum getPayStatusEnum() {
		return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
	}
}
