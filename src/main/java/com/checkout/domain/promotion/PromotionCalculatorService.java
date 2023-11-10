package com.checkout.domain.promotion;

import com.checkout.application.event.CartUpdatedEvent;
import com.checkout.application.event.PromotionAppliedEvent;
import com.checkout.infrastructure.messaging.EventPublisher;
import com.checkout.domain.cart.Cart;
import com.checkout.domain.exception.CartTotalPriceExceededException;
import com.checkout.domain.price.Price;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionCalculatorService {

    private final List<Promotion> promotionList;

    private final double maximumTotalPrice;

    private final EventPublisher eventPublisher;


    public PromotionCalculatorService(List<Promotion> promotionList,
                                      @Value("${application.cart.maximumTotalPrice}") double maximumTotalPrice, EventPublisher eventPublisher) {
        this.promotionList = promotionList;
        this.maximumTotalPrice = maximumTotalPrice;
        this.eventPublisher = eventPublisher;
    }

    public void applyMaximumDiscountToCart(Cart cart){
         double maximumDiscount = 0;
         Long promotionId = null;

         for(Promotion promotion : promotionList){
             double discountOfPromotion = promotion.calculateDiscount(cart);
             if(discountOfPromotion > maximumDiscount){
                 maximumDiscount = discountOfPromotion;
                 promotionId = promotion.getId();
             }
         }

         validateCartMaximumPriceExceeded(cart, maximumDiscount);
         eventPublisher.publishPromotionApplied(new PromotionAppliedEvent(cart, promotionId, maximumDiscount));
    }

    private void validateCartMaximumPriceExceeded(Cart cart, double discount) {
        Price cartTotalPrice = cart.getTotalPrice();
        double totalPriceAmount = cartTotalPrice.getAmount();
        double discountedPrice = totalPriceAmount - discount;

        if(discountedPrice > maximumTotalPrice){
            throw new CartTotalPriceExceededException(maximumTotalPrice);
        }
    }

    @EventListener
    public void handleCartUpdatedEvent(CartUpdatedEvent cartUpdatedEvent) {
        Cart cart = cartUpdatedEvent.getCart();
        applyMaximumDiscountToCart(cart);
    }
}
