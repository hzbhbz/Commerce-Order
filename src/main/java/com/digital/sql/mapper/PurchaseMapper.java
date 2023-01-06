package com.digital.sql.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.digital.sql.vo.PurchaseVO;

@Mapper
public interface PurchaseMapper {

	public void setPurchase(PurchaseVO purchaseVO);

	public PurchaseVO getPurchase(long orderId);
}
