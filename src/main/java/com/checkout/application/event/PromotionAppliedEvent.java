package com.checkout.application.event;

import com.checkout.domain.cart.Cart;
import lombok.Getter;

@Getter
public class PromotionAppliedEvent {

    private final Cart cart;
    private final Long appliedPromotionId;

    private final double totalDiscount;

    public PromotionAppliedEvent(Cart cart, Long appliedPromotionId, double totalDiscount) {
        this.cart = cart;
        this.appliedPromotionId = appliedPromotionId;
        this.totalDiscount = totalDiscount;
    }
}