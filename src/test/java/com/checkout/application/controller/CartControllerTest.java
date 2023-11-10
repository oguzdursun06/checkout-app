package com.checkout.application.controller;

import com.checkout.application.CartApplicationService;
import com.checkout.application.dto.ItemAddDTO;
import com.checkout.application.dto.ItemRemoveDTO;
import com.checkout.application.dto.VasItemAddDTO;
import com.checkout.domain.cart.Cart;
import com.checkout.domain.item.DefaultItem;
import com.checkout.domain.item.Item;
import com.checkout.domain.price.Price;
import com.checkout.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartApplicationService cartApplicationService;


    @Test
    void should_ReturnSuccessMessage_When_ItemAddRequestIsValid() throws Exception {
        ItemAddDTO itemAddDTO = createItemAddDTO();
        doNothing().when(cartApplicationService).addItemToCart(itemAddDTO);

        mockMvc
                .perform(
                        post("/v1/cart/items")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(itemAddDTO))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.result").value(true));
    }

    @Test
    void should_ReturnErrorMessage_When_ItemAddDtoHasMissingFields() throws Exception {
        ItemAddDTO itemAddDTO = ItemAddDTO.builder()
                .itemId(1L)
                .build();
        doNothing().when(cartApplicationService).addItemToCart(itemAddDTO);

        mockMvc
                .perform(
                        post("/v1/cart/items")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(itemAddDTO))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result").value(false));
    }

    @Test
    void should_ReturnSuccessMessage_When_ItemRemoveRequestIsValid() throws Exception {
        Long itemId = 1L;
        ItemRemoveDTO itemRemoveDTO = createItemRemoveDTO(itemId);
        doNothing().when(cartApplicationService).removeItemFromCard(itemId);

        mockMvc
                .perform(
                        delete("/v1/cart/items")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(itemRemoveDTO))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true));
    }

    @Test
    void should_ReturnErrorMessage_When_ItemRemoveIdIsNegative() throws Exception {
        Long itemId = -2L;
        ItemRemoveDTO itemRemoveDTO = createItemRemoveDTO(itemId);
        doNothing().when(cartApplicationService).removeItemFromCard(itemId);

        mockMvc
                .perform(
                        delete("/v1/cart/items")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(itemRemoveDTO))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result").value(false));
    }

    @Test
    void should_ReturnSuccessMessage_When_VasItemAddRequestIsValid() throws Exception {
        VasItemAddDTO vasItemAddDTO = createVasItemAddDTO();
        doNothing().when(cartApplicationService).addVasItemToItem(vasItemAddDTO);

        mockMvc
                .perform(
                        post("/v1/cart/items/vas-items")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(vasItemAddDTO))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.result").value(true));
    }

    @Test
    void should_ReturnSuccessMessage_When_VasItemAddRequestIsEmpty() throws Exception {
        VasItemAddDTO vasItemAddDTO = VasItemAddDTO.builder().build();
        doNothing().when(cartApplicationService).addVasItemToItem(vasItemAddDTO);

        mockMvc
                .perform(
                        post("/v1/cart/items/vas-items")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(vasItemAddDTO))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result").value(false));
    }

    @Test
    void should_ReturnSuccessMessage_When_ResetCart() throws Exception {
        doNothing().when(cartApplicationService).resetCart();

        mockMvc
                .perform(
                        delete("/v1/cart")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true));
    }

    @Test
    void should_ReturnErrorMessage_When_ExceptionIsOccurred() throws Exception {
        doThrow(RuntimeException.class).when(cartApplicationService).resetCart();

        mockMvc
                .perform(
                        delete("/v1/cart")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.result").value(false));
    }

    @Test
    void should_ReturnCart_When_DisplayCart() throws Exception {
        Cart cart = createCartData();
        when(cartApplicationService.displayCart()).thenReturn(cart);

        mockMvc
                .perform(
                        get("/v1/cart")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.message.totalPrice").value(9));
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
    private VasItemAddDTO createVasItemAddDTO() {
        return VasItemAddDTO.builder()
                .itemId(1L)
                .vasItemId(23L)
                .categoryId(3242L)
                .sellerId(23L)
                .price(12)
                .quantity(3)
                .build();
    }

    private ItemRemoveDTO createItemRemoveDTO(Long itemId) {
        ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO();
        itemRemoveDTO.setItemId(itemId);
        return itemRemoveDTO;
    }

    private Cart createCartData() {
        List<Item> itemList = new ArrayList<>();
        for(int i = 1; i < 10; i++){
            DefaultItem item = new DefaultItem();
            item.setQuantity(i);
            item.setPrice(1);
            itemList.add(item);
        }
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setItems(itemList);
        cart.setTotalPrice(new Price(9));
        return cart;
    }





}