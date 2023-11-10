package com.checkout.domain.promotion;

import com.checkout.domain.cart.Cart;

public interface Promotion {

    double calculateDiscount(Cart cart);

    Long getId();
}
