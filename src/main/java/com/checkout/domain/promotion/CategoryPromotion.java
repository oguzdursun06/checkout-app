package com.checkout.domain.promotion;

import com.checkout.domain.cart.Cart;
import com.checkout.domain.price.Price;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CategoryPromotion implements Promotion{


    private final Long id;

    private final Long categoryId;

    private final int discountPercentage;

    public CategoryPromotion(@Value("${application.promotion.category.id}") Long id,
                             @Value("${application.promotion.category.categoryId}") Long categoryId,
                             @Value("${application.promotion.category.discountPercentage}") int discountPercentage) {
        this.id = id;
        this.categoryId = categoryId;
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double calculateDiscount(Cart cart) {
        if(cart.checkPromotionCategoryIdExist(categoryId)){
            Price totalPrice = cart.getTotalPrice();
            return (totalPrice.getAmount() * discountPercentage) / 100;
        }
        return 0;
    }

    @Override
    public Long getId() {
        return id;
    }
}
