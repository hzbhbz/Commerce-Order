package com.digital.context;


import org.springframework.stereotype.Component;

import com.digital.schema.Auth;
import com.digital.schema.Cart;
import com.digital.schema.Inventory;
import com.digital.utils.HttpConnectionUtils;

@Component
public class CartContext {

	/** API Gateway를 통해 Inventory 정보 가져오기 */
	public Inventory searchInventory(Cart cart) throws Exception {

		Inventory inventory = new Inventory();
		inventory.setProductId(cart.getProductId());
		String url = "http://commerce-apigateway-svc/api/inventory/info";
		String inventoryResponse = HttpConnectionUtils.postRequest(url, inventory);
		inventory = (Inventory) HttpConnectionUtils.jsonToObject(inventoryResponse, inventory.getClass());
		return inventory;
	}

	/** API Gateway를 통해 PersonID 가져오기 */
	public Auth searchPersonID(Auth auth) throws Exception {

		String url = "http://commerce-apigateway-svc/api/auth/personinfo";
		String response = HttpConnectionUtils.postRequest(url, auth);
		auth = (Auth) HttpConnectionUtils.jsonToObject(response, auth.getClass());
		return auth;
	}

}