package com.digital.context;

import org.springframework.stereotype.Component;

import com.digital.schema.Inventory;
import com.digital.utils.HttpConnectionUtils;

@Component
public class PurchaseContext {

	/** API Gateway를 통해 재고 정보 검색 */
	public Inventory searchInventory(Inventory inventory) throws Exception {
		
		String url = "http://commerce-apigateway-svc/api/inventory/info";
		String inventoryResponse = HttpConnectionUtils.postRequest(url, inventory);
		inventory = (Inventory) HttpConnectionUtils.jsonToObject(inventoryResponse, inventory.getClass());
		return inventory;
	}

	/** API Gateway를 통해 재고 변경 */
	public void updateInventory(Inventory inventory) throws Exception {
		
		String updateInventoryUrl = "http://commerce-apigateway-svc/api/inventory/insert";
		String updateInventoryResponse = HttpConnectionUtils.postRequest(updateInventoryUrl, inventory);
		inventory = (Inventory) HttpConnectionUtils.jsonToObject(updateInventoryResponse, inventory.getClass());
	}
}