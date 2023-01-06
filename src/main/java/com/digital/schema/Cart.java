package com.digital.schema;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;

@ArraySchema
public class Cart {

	@ApiModelProperty(required = false, example = "5001", dataType = "long", notes = "장바구니 ID")
	private long cartId;
	@ApiModelProperty(required = true, example = "4001", dataType = "long", notes = "상품 ID")
	private long productId;
	@ApiModelProperty(required = true, example = "1", dataType = "long", notes = "수량")
	private long quantity;

	public long getCartId() {
		return cartId;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

}
