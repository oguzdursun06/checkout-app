package com.checkout.domain.promotion;

import com.checkout.domain.cart.Cart;
import com.checkout.domain.exception.CartTotalPriceExceededException;
import com.checkout.domain.price.Price;
import com.checkout.infrastructure.messaging.EventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PromotionCalculatorServiceTest {

    PromotionCalculatorService promotionCalculatorService;

    Promotion firstPromotion;

    Promotion secondPromotion;

    EventPublisher eventPublisher;

    @BeforeEach
    void setUp() {
        double maximumTotalPrice = 500000;
        firstPromotion = mock(Promotion.class);
        secondPromotion = mock(Promotion.class);
        eventPublisher = mock(EventPublisher.class);
        List<Promotion> promotions = List.of(firstPromotion, secondPromotion);
        promotionCalculatorService = new PromotionCalculatorService(promotions, maximumTotalPrice, eventPublisher);
    }

    @Test
    void should_ChooseLargestPromotion_When_MultiplePromotionIsApplicable(){
        Cart cart = createCart();
        double firstDiscount = 1200;
        double secondDiscount = 2600;
        when(firstPromotion.calculateDiscount(any())).thenReturn(firstDiscount);
        when(secondPromotion.calculateDiscount(any())).thenReturn(secondDiscount);
        when(firstPromotion.getId()).thenReturn(1L);
        when(secondPromotion.getId()).thenReturn(2L);

        promotionCalculatorService.applyMaximumDiscountToCart(cart);

        verify(eventPublisher, times(1)).publishPromotionApplied(any());
    }

    @Test
    void should_ThrowException_When_PaidPriceIsGreaterThanMaximumPrice(){
        Cart cart = createCart();
        cart.setTotalPrice(new Price(600000));
        double firstDiscount = 1200;
        double secondDiscount = 2600;
        when(firstPromotion.calculateDiscount(any())).thenReturn(firstDiscount);
        when(secondPromotion.calculateDiscount(any())).thenReturn(secondDiscount);
        when(firstPromotion.getId()).thenReturn(1L);
        when(secondPromotion.getId()).thenReturn(2L);

        assertThrows(CartTotalPriceExceededException.class, () -> promotionCalculatorService.applyMaximumDiscountToCart(cart));
    }

    private Cart createCart() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setTotalItemQuantity(12);
        cart.setTotalPrice(new Price(6500));
        return cart;
    }


}