package com.checkout.domain.promotion;

import com.checkout.domain.cart.Cart;
import com.checkout.domain.price.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TotalPricePromotionTest {

    TotalPricePromotion totalPricePromotion;

    private final Long id = 1232L;
    private final double smallPrice = 5000;
    private final double smallDiscount = 250;
    private final double mediumPrice = 10000;
    private final double mediumDiscount = 500;
    private final double largePrice = 50000;
    private final double largeDiscount = 1000;
    private final double xlargeDiscount = 2000;

    @BeforeEach
    void setUp() {
        totalPricePromotion = new TotalPricePromotion(id, smallPrice, smallDiscount, mediumPrice, mediumDiscount, largePrice, largeDiscount, xlargeDiscount);
    }

    @Test
    void should_ReturnSmallDiscount_When_PriceIsLessThanSmallPrice(){
        Price totalPrice = new Price(1000.0);
        Cart cart = mock(Cart.class);
        when(cart.getTotalPrice()).thenReturn(totalPrice);

        double actualDiscount = totalPricePromotion.calculateDiscount(cart);

        assertEquals(smallDiscount, actualDiscount);
    }

    @Test
    void should_ReturnMediumDiscount_When_PriceIsLessThanSmallPrice(){
        Price totalPrice = new Price(6000.0);
        Cart cart = mock(Cart.class);
        when(cart.getTotalPrice()).thenReturn(totalPrice);

        double actualDiscount = totalPricePromotion.calculateDiscount(cart);

        assertEquals(mediumDiscount, actualDiscount);
    }

    @Test
    void should_ReturnLargeDiscount_When_PriceIsLessThanSmallPrice(){
        Price totalPrice = new Price(49000.0);
        Cart cart = mock(Cart.class);
        when(cart.getTotalPrice()).thenReturn(totalPrice);

        double actualDiscount = totalPricePromotion.calculateDiscount(cart);

        assertEquals(largeDiscount, actualDiscount);
    }

    @Test
    void should_ReturnXlargeDiscount_When_PriceIsLessThanSmallPrice(){
        Price totalPrice = new Price(51000.0);
        Cart cart = mock(Cart.class);
        when(cart.getTotalPrice()).thenReturn(totalPrice);

        double actualDiscount = totalPricePromotion.calculateDiscount(cart);

        assertEquals(xlargeDiscount, actualDiscount);
    }


    @Test
    void should_ReturnId_When_GetId() {
        Long actualId = totalPricePromotion.getId();

        assertEquals(id, actualId);
    }



}