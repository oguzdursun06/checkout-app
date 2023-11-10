package com.checkout.domain.promotion;

import com.checkout.domain.cart.Cart;
import com.checkout.domain.item.Item;
import com.checkout.domain.price.Price;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SameSellerPromotion implements Promotion{


    private final Long id;

    private final int discountPercentage;

    public SameSellerPromotion(@Value("${application.promotion.sameSeller.id}") Long id,
                               @Value("${application.promotion.sameSeller.discountPercentage}") int discountPercentage) {
        this.id = id;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double calculateDiscount(Cart cart) {
        Long sellerId;
        List<Item> items = cart.getItems();
        for (Item item: items) {
            sellerId = items.get(0).getSellerId();
            if(!item.getSellerId().equals(sellerId)){
                return 0;
            }
        }
        Price totalPrice = cart.getTotalPrice();
        return (totalPrice.getAmount() * discountPercentage) / 100;
    }

    @Override
    public Long getId() {
        return id;
    }
}
