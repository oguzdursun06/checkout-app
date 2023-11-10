package com.checkout.application.mapper;


import com.checkout.application.dto.CartDisplayDTO;
import com.checkout.domain.cart.Cart;

public class CartMapper {

    public CartDisplayDTO cartToDisplayCartDTO(Cart cart){
        CartDisplayDTO cartDisplayDTO = new CartDisplayDTO();

        cartDisplayDTO.setItems(cart.getItems());
        cartDisplayDTO.setTotalPrice(cart.getTotalPrice().getAmount());
        cartDisplayDTO.setAppliedPromotionId(cart.getAppliedPromotionId());
        cartDisplayDTO.setTotalDiscount(cart.getTotalDiscount());
        return cartDisplayDTO;
    }
}
