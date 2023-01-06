package com.digital.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.digital.context.OrderContext;
import com.digital.context.PurchaseContext;
import com.digital.schema.Address;
import com.digital.schema.Cart;
import com.digital.schema.Inventory;
import com.digital.schema.Order;
import com.digital.schema.Phone;
import com.digital.schema.Purchase;
import com.digital.sql.mapper.CartMapper;
import com.digital.sql.mapper.OrderMapper;
import com.digital.sql.mapper.PurchaseMapper;
import com.digital.sql.vo.CartVO;
import com.digital.sql.vo.OrderVO;
import com.digital.sql.vo.PartyOrderVO;
import com.digital.sql.vo.PurchaseVO;

@Component
public class PurchaseService {

	@Resource
	OrderContext orderContext;
	@Resource
	PurchaseContext purchaseContext;
	@Resource
	PurchaseMapper purchaseMapper;
	@Resource
	OrderMapper orderMapper;
	@Resource
	CartMapper cartMapper;

	/** 구매 내역 조회 */
	public Order searchPurchase(long personId) throws Exception {

		Order order = new Order();
		Phone phone = new Phone();
		Address address = new Address();
		List<Cart> cartList = new ArrayList<Cart>();

		OrderVO orderVO = orderMapper.getOrder(personId);
		PurchaseVO purchaseVO = purchaseMapper.getPurchase(orderVO.getOrderId());

		if (orderVO != null && purchaseVO != null) {
			order.setOrderId(orderVO.getOrderId());
			order.setPersonId(orderVO.getPersonId());

			// PhoneNumber Routing
			phone.setPhoneId(orderVO.getPhoneId());
			phone = orderContext.searchPhoneById(phone);
			order.setPhoneNumber(phone.getPhoneNumber());

			// AddressDetail Routing
			address.setAddressId(orderVO.getAddressId());
			address = orderContext.searchAddressById(address);
			order.setAddressDetail(address.getAddressDetail());

			for (CartVO cartVO : orderVO.getCartList()) {
				Cart cart = new Cart();
				cart.setCartId(cartVO.getCartId());
				cart.setProductId(cartVO.getProductId());
				cart.setQuantity(cartVO.getQuantity());

				cartList.add(cart);
			}
			order.setCartList(cartList);
		}
		return order;
	}

	/** 결제 등록 */
	public Purchase insertPurchase(Purchase purchase) throws Exception {

		PurchaseVO purchaseVO = new PurchaseVO();

		try {
			purchaseVO.setOrderId(purchase.getOrderId());

			purchaseMapper.setPurchase(purchaseVO);

			/* 해당 상품 재고 변경 
			 * 
			 *  PartyOrder에 CartId를 통해 Cart에 저장된 주문 수량을 찾기
			 *  Cart에 저장된 ProductId를 통해 Inventory 정보 찾기
			 *  구매 수량에 따라 재고 수량 변경
			 */
			PartyOrderVO poVO = orderMapper.getPartyOrderById(purchase.getOrderId());
			List<CartVO> cartVOList = cartMapper.getCartById(poVO.getCartId());

			for (CartVO cartVO : cartVOList) {
				Cart cart = new Cart();
				cart.setCartId(cartVO.getCartId());
				cart.setProductId(cartVO.getProductId());
				cart.setQuantity(cartVO.getQuantity());

				// Inverntory Info Routing
				Inventory inventory = new Inventory();
				inventory.setProductId(cart.getProductId());
				inventory = purchaseContext.searchInventory(inventory);
		
				long cartQuantity = cart.getQuantity();
				long inventoryQuantity = inventory.getQuantity();
				inventory.setQuantity(inventoryQuantity - cartQuantity);

				// Update Inverntory Routing
				purchaseContext.updateInventory(inventory);
			}
			return purchase;

		} catch (Exception e) {
			throw e;
		}
	}

}
