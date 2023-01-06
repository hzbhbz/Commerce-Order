package com.digital.schema;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class CartList {
	@ApiModelProperty(required = false, dataType = "array", notes = "장바구니 목록")
	private List<Cart> cartList;

	public List<Cart> getCartList() {
		return cartList;
	}

	public void setCartList(List<Cart> cartList) {
		this.cartList = cartList;
	}

}
