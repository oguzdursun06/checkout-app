package com.checkout.application;

import com.checkout.application.dto.ItemAddDTO;
import com.checkout.application.dto.VasItemAddDTO;
import com.checkout.domain.cart.Cart;
import com.checkout.domain.cart.CartService;
import com.checkout.domain.item.DefaultItem;
import com.checkout.domain.item.Item;
import com.checkout.domain.item.ItemService;
import com.checkout.domain.vas.VasItem;
import com.checkout.domain.vas.VasItemService;
import com.checkout.infrastructure.messaging.EventPublisher;
import com.checkout.infrastructure.persistence.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartApplicationServiceTest {
    
    @InjectMocks
    CartApplicationService cartApplicationService;

    @Mock
    CartRepository inMemoryCartRepository;
    
    @Mock
    CartService cartService;
    
    @Mock
    ItemService itemService;
    
    @Mock
    VasItemService vasItemService;
    
    @Mock
    EventPublisher eventPublisher;

    Item defaultItem;
    Cart cart;

    @BeforeEach
    void setUp() {
        defaultItem = createItem();
        cart = createCart();
    }

    @Test
    void should_AddToCartAndCalculatePromotion_WhenItemAddDTOIsGiven(){
        ItemAddDTO itemAddDTO = createItemAddDTO();
        when(itemService.createItemById(any())).thenReturn(defaultItem);
        when(inMemoryCartRepository.findById()).thenReturn(cart);
        doNothing().when(cartService).addItemToCart(any(), any());
        doNothing().when(eventPublisher).publishCartUpdated(any());

        cartApplicationService.addItemToCart(itemAddDTO);

        verify(cartService, times(1)).addItemToCart(any(), any());
    }

    @Test
    void should_RemoveFromCartAndCalculatePromotion_WhenItemIdIsGiven(){
        Long itemId = 1L;
        when(inMemoryCartRepository.findById()).thenReturn(cart);
        doNothing().when(cartService).removeItemFromCart(any(), anyLong());
        doNothing().when(eventPublisher).publishCartUpdated(any());

        cartApplicationService.removeItemFromCard(itemId);

        verify(cartService, times(1)).removeItemFromCart(any(), any());
    }

    @Test
    void should_AddVasToCartAndCalculatePromotion_WhenVasItemAddDTOIsGiven(){
        VasItem vasItem = new VasItem();
        VasItemAddDTO vasItemAddDTO = createVasItemAddDTO();
        when(inMemoryCartRepository.findById()).thenReturn(cart);
        when(cartService.getItemById(any(),anyLong())).thenReturn(defaultItem);
        when(vasItemService.createVasItem(any())).thenReturn(vasItem);
        doNothing().when(itemService).addVasItemToItem(any(), any());
        doNothing().when(cartService).increaseTotalPrice(any(), any());
        doNothing().when(eventPublisher).publishCartUpdated(any());

        cartApplicationService.addVasItemToItem(vasItemAddDTO);

        verify(itemService, times(1)).addVasItemToItem(any(), any());
    }

    @Test
    void should_ResetCart(){
        Cart cartMock = mock(Cart.class);
        when(inMemoryCartRepository.findById()).thenReturn(cartMock);
        doNothing().when(cartMock).resetCart();

        cartApplicationService.resetCart();

        verify(cartMock, times(1)).resetCart();
    }

    @Test
    void should_DisplayCart(){
        when(inMemoryCartRepository.findById()).thenReturn(cart);

        Cart actualCart = cartApplicationService.displayCart();

        assertEquals(cart, actualCart);
    }

    private VasItemAddDTO createVasItemAddDTO() {
        return VasItemAddDTO.builder()
                .itemId(1L)
                .vasItemId(12L)
                .categoryId(3242L)
                .build();
    }


    private ItemAddDTO createItemAddDTO() {
        return ItemAddDTO.builder()
                .itemId(1L)
                .categoryId(1001L)
                .sellerId(23L)
                .price(12)
                .quantity(3)
                .build();
    }

    private Item createItem() {
        DefaultItem defaultItem = new DefaultItem();
        defaultItem.setItemId(1L);
        return defaultItem;
    }

    private Cart createCart() {
        Cart cart = new Cart();
        cart.setId(1L);
        return cart;
    }

}