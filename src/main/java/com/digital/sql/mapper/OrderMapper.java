package com.digital.sql.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.digital.sql.vo.OrderVO;
import com.digital.sql.vo.PartyOrderVO;

@Mapper
public interface OrderMapper {

	public void setOrder(OrderVO orderVO);

	public void setPartyOrder(PartyOrderVO poVO);

	public OrderVO getOrder(long personId);

	public PartyOrderVO getPartyOrderById(long orderId);

}
