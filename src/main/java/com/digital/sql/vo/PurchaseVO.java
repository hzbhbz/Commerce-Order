package com.digital.sql.vo;

import lombok.Data;

@Data
public class PurchaseVO {

	private long orderId;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

}
