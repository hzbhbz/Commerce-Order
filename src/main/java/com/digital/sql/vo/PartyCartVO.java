package com.digital.sql.vo;

import lombok.Data;

@Data
public class PartyCartVO {

	private long cartId;
	private long personId;

	public long getCartId() {
		return cartId;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

}
