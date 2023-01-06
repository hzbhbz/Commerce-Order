package com.digital.sql.vo;

import lombok.Data;

@Data
public class PartyOrderVO {

	private long orderId;
	private long cartId;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getCartId() {
		return cartId;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

}
