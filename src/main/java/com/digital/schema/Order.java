package com.digital.schema;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;

@ArraySchema
public class Order {

	@ApiModelProperty(required = false, position = 1, example = "1001", dataType = "long", notes = "주문 ID")
	private long orderId;
	@ApiModelProperty(required = false, position = 2, example = "1001", dataType = "long", notes = "고객 ID")
	private long personId;
	@ApiModelProperty(required = true, position = 3, example = "010-3192-0379", dataType = "string", notes = "전화번호")
	private String phoneNumber;
	@ApiModelProperty(required = true, position = 4, example = "서울특별시 동작구", dataType = "string", notes = "주소")
	private String addressDetail;
	@ApiModelProperty(required = true, position = 5, example = "[{\"cartId\":3001,\"productId\":1001,\"quantity\":\"1\"}]", dataType = "array", notes = "상품목록")
	private List<Cart> cartList;

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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public List<Cart> getCartList() {
		return cartList;
	}

	public void setCartList(List<Cart> cartList) {
		this.cartList = cartList;
	}

}
