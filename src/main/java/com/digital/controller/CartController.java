package com.digital.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.digital.context.CartContext;
import com.digital.schema.Auth;
import com.digital.schema.Cart;
import com.digital.schema.CartList;
import com.digital.schema.ErrorMsg;
import com.digital.service.CartService;
import com.digital.utils.ExceptionUtils;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "장바구니", description = "Cart Related API")
@RequestMapping(value = "/rest/cart")

public class CartController {

	@Resource
	CartService cartSvc;
	@Resource
	CartContext cartContext;

	@RequestMapping(value = "/inquiry/{keyword}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "장바구니 조회", notes = "고객 ID로 장바구니 내역을 조회하는 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = CartList.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> personList(@PathVariable long keyword) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		CartList cartList = new CartList();
		ErrorMsg errors = new ErrorMsg();

		try {
			cartList = cartSvc.searchCartList(keyword);
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}
		return new ResponseEntity<CartList>(cartList, header, HttpStatus.valueOf(200)); // ResponseEntity를 활용한 응답 생성
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "장바구니 담기", notes = "상품을 담기 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Cart.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> shoppingCart(@Parameter(name = "장바구니", required = true) @RequestBody Cart cart,
			HttpServletRequest request) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		ErrorMsg errors = new ErrorMsg();
		try {
			String token = request.getHeader("Authorization");

			// Auth PersonID Routing
			Auth auth = new Auth();
			auth.setToken(token);
			auth = cartContext.searchPersonID(auth);

			cart = cartSvc.insertCartVO(cart, auth.getPersonId());

		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}
		return new ResponseEntity<Cart>(cart, header, HttpStatus.valueOf(200)); // ResponseEntity를 활용한 응답 생성
	}
}
