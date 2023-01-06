package com.digital.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.digital.schema.Cart;
import com.digital.schema.CartList;
import com.digital.schema.Inventory;
import com.digital.context.CartContext;
import com.digital.sql.mapper.CartMapper;
import com.digital.sql.vo.CartVO;
import com.digital.sql.vo.PartyCartVO;

@Component
public class CartService {

	@Resource
	CartMapper cartMapper;
	@Resource
	CartContext cartContext;

	/** 장바구니 목록 검색 */
	public CartList searchCartList(long personId) throws Exception {

		CartList cartList_res = new CartList();		
		
		List<CartVO> cartVOListByPersonId = cartMapper.getCart(personId);
		List<Cart> cartList = new ArrayList<Cart>();
		for (CartVO cartVO : cartVOListByPersonId) {
			Cart cart = new Cart();

			cart.setCartId(cartVO.getCartId());
			cart.setProductId(cartVO.getProductId());
			cart.setQuantity(cartVO.getQuantity());

			cartList.add(cart);
		}
		cartList_res.setCartList(cartList);

		return cartList_res;
	}
	
	/** 장바구니 단일 검색 */
	public Cart searchCart(long personId) throws Exception {
		
		List<CartVO> cartVOList = cartMapper.getCart(personId);
		
		Cart cart = new Cart();
		for (CartVO cartVO : cartVOList) {
			cart.setCartId(cartVO.getCartId());
			cart.setProductId(cartVO.getProductId());
			cart.setQuantity(cartVO.getQuantity());
		}
		return cart;
	}

	/** 가주문서 등록 전, CartID 찾기 (없을 경우, 새로 장바구니 작성) */
	public boolean searchCartById(Cart cart, long personId) throws Exception {

		List<CartVO> cartVOList = cartMapper.getCartById(cart.getCartId());

		for (CartVO cartVO : cartVOList) {
			if (cartVO == null) {
				cart = insertCartVO(cart, personId);
			}
		}
		return true;
	}

	/** 장바구니 담기 */
	public Cart insertCartVO(Cart cart, long personId) throws Exception {

		CartVO cartVO = new CartVO();
		PartyCartVO pcVO = new PartyCartVO();

		// API Gateway를 통해 Inventoy 정보 가져오기
		Inventory inventory = new Inventory();
		inventory = cartContext.searchInventory(cart);
		
		try {
			cart.setCartId(System.currentTimeMillis());

			if (cart.getQuantity() <= inventory.getQuantity()) {

				cartVO.setCartId(cart.getCartId());
				cartVO.setProductId(cart.getProductId());
				cartVO.setQuantity(cart.getQuantity());
				cartMapper.setCart(cartVO);

				// Set PartyCart
				pcVO.setCartId(cart.getCartId());
				pcVO.setPersonId(personId);
				cartMapper.setPartyCart(pcVO);
			}
			return cart;
		} catch (Exception e) {
			throw e;
		}
	}

}
