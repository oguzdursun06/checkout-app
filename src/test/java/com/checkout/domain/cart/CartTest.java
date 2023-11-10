package com.checkout.domain.cart;

import com.checkout.domain.exception.ItemNotFoundException;
import com.checkout.domain.item.DefaultItem;
import com.checkout.domain.item.Item;
import com.checkout.domain.price.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CartTest {

    Cart cart;

    List<Item> itemList;

    @BeforeEach
    public void setUp(){
        createInitialTestData();
    }


    @Test
    void should_IncreaseItemSize_When_AddedItemToCart(){
        DefaultItem defaultItem = mock(DefaultItem.class);

        cart.addItemToCart(defaultItem);

        assertEquals(10, cart.getItems().size());
    }

    @Test
    void should_UpdatePrice_When_IncreaseTotalPrice(){
        double increaseAmount = 11;
        Price expectedTotalPrice = new Price(20.0);

        cart.increaseTotalPrice(increaseAmount);

        Assertions.assertEquals(expectedTotalPrice, cart.getTotalPrice());
    }

    @Test
    void should_RemoveItemFromCart_When_ItemIdExistInCart(){
        Long itemId = 1L;
        Item firstItem = itemList.get(0);
        when(firstItem.getItemId()).thenReturn(1L);
        when(firstItem.getQuantity()).thenReturn(1);
        when(firstItem.getPrice()).thenReturn(15.0);

        cart.removeItemFromCart(itemId);

        assertEquals(8, cart.getItems().size());
    }

    @Test
    void should_ThrowException_When_ItemIdIsNotFound(){
        Long itemId = 100L;

        assertThrows(ItemNotFoundException.class, () -> cart.removeItemFromCart(itemId));
    }

    @Test
    void should_ReturnDefaultValues_When_ClearCart(){
        Cart expectedCart = new Cart();
        expectedCart.setTotalPrice(new Price(0));

        cart.resetCart();

        assertEquals(expectedCart, cart);
    }

    @Test
    void should_ReturnItem_When_IdIsFound(){
        Long itemId = 1L;
        Item firstItem = itemList.get(0);
        when(firstItem.getItemId()).thenReturn(1L);

        Item actualItem = cart.getItemById(itemId);

        assertEquals(itemList.get(0), actualItem);
    }

    @Test
    void should_ThrowException_When_IdIsNotFound(){
        Long itemId = 30L;

        assertThrows(ItemNotFoundException.class, () -> cart.getItemById(itemId));
    }

    @Test
    void should_ReturnTrue_When_IdIsFound(){
        Long itemId = 1L;
        Item firstItem = itemList.get(0);
        when(firstItem.getItemId()).thenReturn(1L);

        boolean foundItem = cart.containsItem(itemId);

        assertTrue(foundItem);
    }

    @Test
    void should_ReturnFalse_When_IdIsNotFound(){
        Long itemId = 30L;

        boolean foundItem = cart.containsItem(itemId);

        assertFalse(foundItem);
    }

    @Test
    void should_ReturnTrue_When_ItemFoundWithCategoryId(){
        Long categoryId = 1L;
        Item firstItem = itemList.get(0);
        when(firstItem.getCategoryId()).thenReturn(1L);

        boolean foundItem = cart.checkPromotionCategoryIdExist(categoryId);

        assertTrue(foundItem);
    }

    @Test
    void should_ReturnFalse_When_ItemNotFoundWithCategoryId(){
        Long categoryId = 30L;

        boolean foundItem = cart.checkPromotionCategoryIdExist(categoryId);

        assertFalse(foundItem);
    }

    @Test
    void should_SetDiscount_When_TotalPriceGreaterEqualThanDiscount(){
        double discount = 4;

        cart.setTotalDiscount(discount);

        assertEquals(discount, cart.getTotalDiscount());
    }

    @Test
    void should_NotSetDiscount_When_TotalPriceLessThanDiscount(){
        double discount = 12;

        cart.setTotalDiscount(discount);

        assertNotEquals(discount, cart.getTotalDiscount());
    }



    private void createInitialTestData() {
        itemList = new ArrayList<>();
        for(int i = 1; i < 10; i++){
            DefaultItem item = mock(DefaultItem.class);
            itemList.add(item);
        }
        cart = new Cart();
        cart.setItems(itemList);
        cart.setTotalItemQuantity(28);
        cart.setTotalPrice(new Price(9));
    }




}