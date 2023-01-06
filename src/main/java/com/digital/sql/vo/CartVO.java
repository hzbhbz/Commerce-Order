package com.digital.sql.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CartVO {

	private long cartId;
	@ApiModelProperty(required = true, example = "4001", dataType = "long", notes = "상품 ID")
	private long productId;
	@ApiModelProperty(required = true, example = "1", dataType = "long", notes = "수량")
	private long quantity;

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

	public long getCartId() {
		return cartId;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

}
