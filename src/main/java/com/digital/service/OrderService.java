package com.digital.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.digital.context.OrderContext;
import com.digital.schema.Address;
import com.digital.schema.Cart;
import com.digital.schema.Order;
import com.digital.schema.Phone;
import com.digital.sql.mapper.CartMapper;
import com.digital.sql.mapper.OrderMapper;
import com.digital.sql.vo.CartVO;
import com.digital.sql.vo.OrderVO;
import com.digital.sql.vo.PartyOrderVO;

@Component
public class OrderService {

	@Resource
	CartService cartSvc;
	@Resource
	OrderContext orderContext;
	@Resource
	OrderMapper orderMapper;
	@Resource
	CartMapper cartMapper;

	/** 가주문서 조회 */
	public Order searchOrder(long personId) throws Exception {

		Order order = new Order();
		Phone phone = new Phone();
		Address address = new Address();
		List<Cart> cartList = new ArrayList<Cart>();

		OrderVO orderVO = orderMapper.getOrder(personId);

		if (orderVO != null) {
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

	/** 가주문서 등록 */
	public boolean insertOrder(Order order, Phone phone, Address address, long personId) throws Exception {

		OrderVO orderVO = new OrderVO();
		PartyOrderVO poVO = new PartyOrderVO();
		Cart cartRes = new Cart();
		try {
			order.setOrderId(System.currentTimeMillis());

			orderVO.setOrderId(order.getOrderId());
			orderVO.setPersonId(personId);
			orderVO.setPhoneId(phone.getPhoneId());
			orderVO.setAddressId(address.getAddressId());

			orderMapper.setOrder(orderVO);

			List<Cart> cartList = order.getCartList();
			for (Cart cart : cartList) {
				cartRes.setCartId(cart.getCartId());
			}
			if (cartSvc.searchCartById(cartRes, personId)) {
				// PartyOrder
				poVO.setOrderId(order.getOrderId());
				poVO.setCartId(cartRes.getCartId());

				orderMapper.setPartyOrder(poVO);
			}
			return true;
		} catch (Exception e) {
			throw e;
		}

	}

}
