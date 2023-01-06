package com.digital.sql.vo;

import java.util.List;

import lombok.Data;

@Data
public class OrderVO {

	private long orderId;
	private long personId;
	private long phoneId;
	private long addressId;
	private List<CartVO> cartList;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

	public long getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(long phoneId) {
		this.phoneId = phoneId;
	}

	public long getAddressId() {
		return addressId;
	}

	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}

	public List<CartVO> getCartList() {
		return cartList;
	}

	public void setCartIdList(List<CartVO> cartList) {
		this.cartList = cartList;
	}

}
