package com.digital.context;

import org.springframework.stereotype.Component;

import com.digital.schema.Address;
import com.digital.schema.Auth;
import com.digital.schema.Phone;
import com.digital.utils.HttpConnectionUtils;

@Component
public class OrderContext {

	/** API Gateway를 통해 PersonID 가져오기 */
	public Auth searchPersonID(Auth auth) throws Exception {

		String url = "http://commerce-apigateway-svc/api/auth/personinfo";
		String response = HttpConnectionUtils.postRequest(url, auth);
		auth = (Auth) HttpConnectionUtils.jsonToObject(response, auth.getClass());
		return auth;
	}

	/** API Gateway를 통해 Phone(PhoneNumber) 정보 조회 */
	public Phone searchPhone(Phone phone) throws Exception {
		
		String url = "http://commerce-apigateway-svc/api/phone/info";
		String response = HttpConnectionUtils.postRequest(url, phone);
		phone = (Phone) HttpConnectionUtils.jsonToObject(response, phone.getClass());
		return phone;
	}

	/** API Gateway를 통해 Address(AddressDetail) 정보 조회 */
	public Address searchAddress(Address address) throws Exception {

		String url = "http://commerce-apigateway-svc/api/address/info";
		String response = HttpConnectionUtils.postRequest(url, address);
		address = (Address) HttpConnectionUtils.jsonToObject(response, address.getClass());
		return address;
	}

	/** API Gateway를 통해 Phone(PhoneID) 정보 조회 */
	public Phone searchPhoneById(Phone phone) throws Exception {
		
		String url = "http://commerce-apigateway-svc/api/phone/detailinfo";
		String response = HttpConnectionUtils.postRequest(url, phone);
		phone = (Phone) HttpConnectionUtils.jsonToObject(response, phone.getClass());
		return phone;
	}

	/** API Gateway를 통해 Address(AddressID) 정보 조회 */
	public Address searchAddressById(Address address) throws Exception {
		
		String url = "http://commerce-apigateway-svc/api/address/detailinfo";
		String response = HttpConnectionUtils.postRequest(url, address);
		address = (Address) HttpConnectionUtils.jsonToObject(response, address.getClass());
		return address;
	}

}