package com.checkout.domain.promotion;


import com.checkout.domain.cart.Cart;
import com.checkout.domain.price.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CategoryPromotionTest {

    CategoryPromotion categoryPromotion;

    private final Long categoryId = 5676L;

    private final Long id = 3003L;

    private final int discountPercentage = 5;

    @BeforeEach
    public void setUp(){
        categoryPromotion = new CategoryPromotion(id, categoryId, discountPercentage);
    }


    @Test
    void should_ReturnDiscount_When_CategoryIdExistInCart(){
        Price totalPrice = new Price(500.0);
        double expectedDiscount = 25.0;
        Cart cart = mock(Cart.class);
        when(cart.checkPromotionCategoryIdExist(anyLong())).thenReturn(true);
        when(cart.getTotalPrice()).thenReturn(totalPrice);

        double actualDiscount = categoryPromotion.calculateDiscount(cart);

        assertEquals(expectedDiscount, actualDiscount);

    }

    @Test
    void should_ReturnZero_When_CategoryIdNotExistInCart(){
        double expectedDiscount = 0;
        Cart cart = mock(Cart.class);
        when(cart.checkPromotionCategoryIdExist(anyLong())).thenReturn(false);

        double actualDiscount = categoryPromotion.calculateDiscount(cart);

        assertEquals(expectedDiscount, actualDiscount);
    }

    @Test
    void should_ReturnId_When_GetId(){
        Long actualId = categoryPromotion.getId();

        assertEquals(id, actualId);
    }



}