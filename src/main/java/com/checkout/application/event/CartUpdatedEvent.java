package com.checkout.application.event;

import com.checkout.domain.cart.Cart;
import lombok.Getter;

@Getter
public class CartUpdatedEvent {

    private final Cart cart;

    public CartUpdatedEvent(Cart cart) {
        this.cart = cart;
    }
}