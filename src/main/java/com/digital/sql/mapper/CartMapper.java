package com.digital.sql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.digital.sql.vo.CartVO;
import com.digital.sql.vo.PartyCartVO;

@Mapper
public interface CartMapper {

	public List<CartVO> getCart(long personId);

	public List<CartVO> getCartById(long cartId);

	public void setCart(CartVO cartVO);

	public void setPartyCart(PartyCartVO partyCartVO);

}
