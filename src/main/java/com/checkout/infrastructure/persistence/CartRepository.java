package com.checkout.infrastructure.persistence;

import com.checkout.domain.cart.Cart;

public interface CartRepository {

    Cart findById();

    void save(Cart cart);
}
