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

import com.digital.context.OrderContext;
import com.digital.schema.Address;
import com.digital.schema.Auth;
import com.digital.schema.ErrorMsg;
import com.digital.schema.Order;
import com.digital.schema.Phone;
import com.digital.service.OrderService;
import com.digital.utils.ExceptionUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "주문", description = "Order Related API")
@RequestMapping(value = "/rest/order")

public class OrderController {

	@Resource
	OrderContext orderContext;
	@Resource
	OrderService orderSvc;

	@RequestMapping(value = "/inquiry/{keyword}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "가주문서 검색", notes = "고객 ID 입력해 주문 내역을 검색하는 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Order.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> searchOrder(@PathVariable long keyword) throws Exception {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		Order orderRes = new Order();
		ErrorMsg errors = new ErrorMsg();

		try {
			orderRes = orderSvc.searchOrder(keyword);
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}
		return new ResponseEntity<Order>(orderRes, header, HttpStatus.valueOf(200)); // ResponseEntity를 활용한 응답 생성
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "주문서 작성", notes = "상품을 주문하기 위한 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "주문이 완료되었습니다. 결제를 진행해주세요.", response = Order.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> writeOrder(@Parameter(name = "주문서", required = true) @RequestBody Order order, HttpServletRequest request) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		ErrorMsg errors = new ErrorMsg();
		Phone phone = new Phone();
		Address address = new Address();

		try {
			String token = request.getHeader("Authorization");
			
			// Auth PersonID Routing
			Auth auth = new Auth();
			auth.setToken(token);
			auth = orderContext.searchPersonID(auth);
			
			// Phone Info Routing
			phone.setPhoneNumber(order.getPhoneNumber());
			phone = orderContext.searchPhone(phone);
			
			// Address Info Routing
			address.setAddressDetail(order.getAddressDetail());
			address = orderContext.searchAddress(address);

			if (orderSvc.insertOrder(order, phone, address, auth.getPersonId())) {
				order = orderSvc.searchOrder(auth.getPersonId());
			}

		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}
		return new ResponseEntity<Order>(order, header, HttpStatus.valueOf(200)); // ResponseEntity를 활용한 응답 생성
	}

}
