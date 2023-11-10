package com.checkout.domain.promotion;

import com.checkout.domain.cart.Cart;
import com.checkout.domain.item.DefaultItem;
import com.checkout.domain.price.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SameSellerPromotionTest {

    SameSellerPromotion sameSellerPromotion;

    private final Long id = 3003L;

    private final int discountPercentage = 10;

    @BeforeEach
    void setUp() {
        sameSellerPromotion = new SameSellerPromotion(id, discountPercentage);
    }

    @Test
    void should_ReturnDiscount_When_SameSellerIdExistInCart(){
        Price totalPrice = new Price(500.0);
        double expectedDiscount = 50.0;
        DefaultItem defaultItem = mock(DefaultItem.class);
        Cart cart = mock(Cart.class);
        when(cart.getItems()).thenReturn(List.of(defaultItem));
        when(defaultItem.getSellerId()).thenReturn(1L);
        when(cart.getTotalPrice()).thenReturn(totalPrice);

        double actualDiscount = sameSellerPromotion.calculateDiscount(cart);

        assertEquals(expectedDiscount, actualDiscount);

    }

    @Test
    void should_ReturnZero_When_SameSellerIdNotExistInCart(){
        double expectedDiscount = 0;
        Cart cart = mock(Cart.class);
        DefaultItem firstItem = mock(DefaultItem.class);
        DefaultItem secondItem = mock(DefaultItem.class);
        when(cart.getItems()).thenReturn(List.of(firstItem, secondItem));
        when(firstItem.getSellerId()).thenReturn(1L);
        when(secondItem.getSellerId()).thenReturn(2L);

        double actualDiscount = sameSellerPromotion.calculateDiscount(cart);

        assertEquals(expectedDiscount, actualDiscount);
    }

    @Test
    void should_ReturnId_When_GetId(){
        Long actualId = sameSellerPromotion.getId();

        assertEquals(id, actualId);
    }



}