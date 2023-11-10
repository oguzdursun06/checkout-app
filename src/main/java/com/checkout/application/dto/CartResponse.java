package com.checkout.application.dto;

import lombok.Data;

@Data
public class CartResponse {

    private boolean result;

    private CartDisplayDTO message;

    public CartResponse(boolean result, CartDisplayDTO cartDisplayDTO) {
        this.result = result;
        this.message = cartDisplayDTO;
    }
}
