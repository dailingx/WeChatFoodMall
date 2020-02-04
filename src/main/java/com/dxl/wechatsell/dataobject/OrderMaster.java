package com.dxl.wechatsell.dataobject;

import com.dxl.wechatsell.enums.OrderStatusEnum;
import com.dxl.wechatsell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author：Daixinliang
 * @Description：订单主表
 * @Date：Created in 8:18 on 2019/10/29.
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {

	@Id
	private String orderId;

	private String buyerName;

	private String buyerPhone;

	private String buyerAddress;

	private String buyerOpenId;

	private BigDecimal orderAmount;

	//订单状态，默认为0新下单
	private Integer orderStatus = OrderStatusEnum.NEW.getCode();

	//支付状态，默认为0未支付
	private Integer payStatus = PayStatusEnum.WAIT.getCode();

	private Date createTime;

	private Date updateTime;


}
