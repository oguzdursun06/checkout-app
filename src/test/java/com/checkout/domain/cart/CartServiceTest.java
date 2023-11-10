package com.checkout.domain.cart;

import com.checkout.domain.exception.CartTotalItemCountExceededException;
import com.checkout.domain.exception.CartTypeMismatchException;
import com.checkout.domain.exception.CartUniqueItemCountExceededException;
import com.checkout.domain.exception.ItemAlreadyExistException;
import com.checkout.domain.item.DefaultItem;
import com.checkout.domain.item.DigitalItem;
import com.checkout.domain.item.Item;
import com.checkout.domain.price.Price;
import com.checkout.domain.vas.VasItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartServiceTest {

    private CartService cartService;

    @BeforeEach
    void setUp() {
        int maximumUniqueItemCount = 10;
        int maximumTotalItemCount = 30;
        cartService = new CartService(maximumUniqueItemCount, maximumTotalItemCount);
    }

    @Test
    void should_AddItemToCart_When_ItemIsValid(){
        Cart cart = createTestData();
        DefaultItem defaultItem = createDefaultItem();

        cartService.addItemToCart(cart, defaultItem);

        assertEquals(5, cart.getItems().size());
    }

    @Test
    void should_ThrowException_When_ItemAndCartTypesAreDifferent(){
        Cart cart = createTestData();
        DigitalItem digitalItem = new DigitalItem();

        assertThrows(CartTypeMismatchException.class, () -> cartService.addItemToCart(cart, digitalItem));
    }

    @Test
    void should_ThrowException_When_MaximumTotalItemCountExceeded(){
        Cart cart = createTestData();
        DefaultItem defaultItem = createDefaultItem();
        defaultItem.setQuantity(5);

        assertThrows(CartTotalItemCountExceededException.class, () -> cartService.addItemToCart(cart, defaultItem));
    }

    @Test
    void should_ThrowException_When_AddedItemAlreadyExist(){
        Cart cart = createTestData();
        DefaultItem defaultItem = createDefaultItem();
        defaultItem.setItemId(1L);

        assertThrows(ItemAlreadyExistException.class, () -> cartService.addItemToCart(cart, defaultItem));
    }

    @Test
    void should_ThrowException_When_MaximumUniqueItemCountExceeded(){
        Cart cart = createTestData();
        DefaultItem defaultItem = createDefaultItem();
        cart.setItems(createItems());

        assertThrows(CartUniqueItemCountExceededException.class, () -> cartService.addItemToCart(cart, defaultItem));
    }

    @Test
    void should_RemoveItemFromCart_IdIsValid(){
        Long itemId = 1L;
        Cart cart = createTestData();

        cartService.removeItemFromCart(cart, itemId);

        assertEquals(3, cart.getItems().size());
    }

    @Test
    void should_IncreaseTotalPrice_VasAdded(){
        Cart cart = createTestData();
        VasItem vasItem = new VasItem();
        vasItem.setQuantity(2);
        vasItem.setPrice(3);
        Price totalPrice = new Price(15);

        cartService.increaseTotalPrice(cart, vasItem);

        Assertions.assertEquals(totalPrice, cart.getTotalPrice());
    }

    @Test
    void should_ReturnItem_When_IdIsGiven(){
        Long itemId = 1L;
        Cart cart = createTestData();
        List<Item> items = cart.getItems();

        Item actualItem = cartService.getItemById(cart, itemId);

        assertEquals(items.get(0), actualItem);
    }


    private Cart createTestData(){
        List<Item> itemList = new ArrayList<>();
        for(long i = 1; i < 5; i++){
            DefaultItem item = new DefaultItem();
            item.setItemId(i);
            itemList.add(item);
        }
        Cart cart = new Cart();
        cart.setItems(itemList);
        cart.setTotalItemQuantity(28);
        cart.setTotalPrice(new Price(9));
        return cart;
    }

    private DefaultItem createDefaultItem() {
        DefaultItem defaultItem = new DefaultItem();
        defaultItem.setItemId(12L);
        defaultItem.setSellerId(5004L);
        defaultItem.setCategoryId(1001L);
        defaultItem.setQuantity(1);
        defaultItem.setPrice(20);
        defaultItem.setTotalPrice(20);
        return defaultItem;
    }

    private List<Item> createItems(){
        List<Item> itemList = new ArrayList<>();
        for(long i = 1; i < 11; i++){
            DefaultItem item = new DefaultItem();
            item.setItemId(i);
            itemList.add(item);
        }
        return itemList;
    }

}