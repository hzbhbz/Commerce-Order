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

import com.digital.schema.ErrorMsg;
import com.digital.schema.Order;
import com.digital.schema.Purchase;
import com.digital.service.OrderService;
import com.digital.service.PurchaseService;
import com.digital.utils.ExceptionUtils;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "결제", description = "Purchase Related API")
@RequestMapping(value = "/rest/purchase")

public class PurchaseController {

	@Resource
	PurchaseService purchaseSvc;
	@Resource
	OrderService orderSvc;

	@RequestMapping(value = "/inquiry/{keyword}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "결제 내역 검색", notes = "고객 ID 입력해 결제 내역을 검색하는 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Order.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> searchOrder(@PathVariable long keyword) throws Exception {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		Order orderRes = new Order();
		ErrorMsg errors = new ErrorMsg();

		try {			
			orderRes = purchaseSvc.searchPurchase(keyword);
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}
		return new ResponseEntity<Order>(orderRes, header, HttpStatus.valueOf(200)); // ResponseEntity를 활용한 응답 생성
	}

	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "결제", notes = "주문서 결제를 위한 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Purchase.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> purchase(@Parameter(name = "결제", required = true) @RequestBody Purchase purchase, HttpServletRequest request) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		ErrorMsg errors = new ErrorMsg();
		try {
			purchase = purchaseSvc.insertPurchase(purchase);
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}
		return new ResponseEntity<Purchase>(purchase, header, HttpStatus.valueOf(200)); // ResponseEntity를 활용한 응답 생성
	}
}
