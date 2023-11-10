package com.checkout;

import com.checkout.application.dto.ItemAddDTO;
import com.checkout.application.dto.ItemRemoveDTO;
import com.checkout.application.dto.VasItemAddDTO;
import com.checkout.domain.cart.Cart;
import com.checkout.domain.item.DefaultItem;
import com.checkout.domain.item.DigitalItem;
import com.checkout.domain.item.Item;
import com.checkout.domain.price.Price;
import com.checkout.infrastructure.persistence.InMemoryCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CheckoutIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InMemoryCartRepository inMemoryCartRepository;

    @BeforeEach
    void setUp() {
        inMemoryCartRepository.clear();
    }

    @Test
    void should_ReturnSuccessMessage_When_ItemAddRequestIsValid() throws Exception {
        ItemAddDTO itemAddDTO = createItemAddDTO();

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
    void should_ReturnBadRequest_When_CartDefaultItemCountExceeded() throws Exception {
        ItemAddDTO itemAddDTO = createItemAddDTO();
        itemAddDTO.setQuantity(11);

        mockMvc
                .perform(
                        post("/v1/cart/items")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(itemAddDTO))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result").value(false))
                .andExpect(jsonPath("$.message").value(String.format("A single default item can not be more than %d.", 10)));
    }

    @Test
    void should_ReturnBadRequest_When_CartDigitalItemCountExceeded() throws Exception {
        ItemAddDTO itemAddDTO = createItemAddDTO();
        itemAddDTO.setCategoryId(7889L);
        itemAddDTO.setQuantity(6);

        mockMvc
                .perform(
                        post("/v1/cart/items")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(itemAddDTO))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result").value(false))
                .andExpect(jsonPath("$.message").value(String.format("A single digital item can not be more than %d.", 5)));
    }

    @Test
    void should_ReturnBadRequest_When_CartTotalPriceExceeded() throws Exception {
        ItemAddDTO itemAddDTO = createItemAddDTO();
        itemAddDTO.setPrice(600000);

        mockMvc
                .perform(
                        post("/v1/cart/items")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(itemAddDTO))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result").value(false))
                .andExpect(jsonPath("$.message").value(String.format("Cart total price can not be more than %d TL.", 500000)));
    }

    @Test
    void should_ReturnBadRequest_When_SellerIsNotCompatibleWithDefaultItem() throws Exception {
        ItemAddDTO itemAddDTO = createItemAddDTO();
        itemAddDTO.setSellerId(5003L);

        mockMvc
                .perform(
                        post("/v1/cart/items")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(itemAddDTO))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result").value(false))
                .andExpect(jsonPath("$.message").value(String.format("Seller %d can not sell this item.", 5003)));
    }

    @Test
    void should_ReturnBadRequest_When_ItemIsVasInAddingToCart() throws Exception {
        ItemAddDTO itemAddDTO = createItemAddDTO();
        itemAddDTO.setCategoryId(3242L);

        mockMvc
                .perform(
                        post("/v1/cart/items")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(itemAddDTO))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result").value(false))
                .andExpect(jsonPath("$.message").value("Vas item can not be added to this item."));
    }

    @Test
    void should_ReturnBadRequest_When_TotalItemCountExceeded() throws Exception {
        Cart cart = inMemoryCartRepository.findById();
        cart.setTotalItemQuantity(25);
        inMemoryCartRepository.save(cart);
        ItemAddDTO itemAddDTO = createItemAddDTO();
        itemAddDTO.setQuantity(7);
        inMemoryCartRepository.save(cart);

        mockMvc
                .perform(
                        post("/v1/cart/items")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(itemAddDTO))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result").value(false))
                .andExpect(jsonPath("$.message").value(String.format("Total number of items in the cart can not be more than %d.", 30)));
    }

    @Test
    void should_ReturnBadRequest_When_CartTypeAndItemTypeAreDifferent() throws Exception {
        Cart cart = inMemoryCartRepository.findById();
        cart.setItems(List.of(new DigitalItem()));
        ItemAddDTO itemAddDTO = createItemAddDTO();
        inMemoryCartRepository.save(cart);

        mockMvc
                .perform(
                        post("/v1/cart/items")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(itemAddDTO))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result").value(false))
                .andExpect(jsonPath("$.message").value("Given item type can not be added to this cart due to the different types."));
    }

    @Test
    void should_ReturnBadRequest_When_UniqueItemCountExceeded() throws Exception {
        Cart cart = inMemoryCartRepository.findById();
        createDefaultItemsToCart(cart);
        ItemAddDTO itemAddDTO = createItemAddDTO();
        inMemoryCartRepository.save(cart);

        mockMvc
                .perform(
                        post("/v1/cart/items")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(itemAddDTO))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result").value(false))
                .andExpect(jsonPath("$.message").value(String.format("Total number of unique items in the cart can not be more than %d.", 10)));
    }

    @Test
    void should_ReturnBadRequest_When_ItemAlreadyExist() throws Exception {
        Cart cart = inMemoryCartRepository.findById();
        DefaultItem defaultItem = new DefaultItem();
        defaultItem.setItemId(1L);
        cart.setItems(List.of(defaultItem));
        ItemAddDTO itemAddDTO = createItemAddDTO();
        inMemoryCartRepository.save(cart);

        mockMvc
                .perform(
                        post("/v1/cart/items")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(itemAddDTO))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result").value(false))
                .andExpect(jsonPath("$.message").value(String.format("Item id %d already exist in the cart, can not be added again.", 1)));
    }

    @Test
    void should_ReturnSuccessMessage_When_ItemRemoveRequestIsValid() throws Exception {
        inMemoryCartRepository.save(createSingleItemCart());

        ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO();
        itemRemoveDTO.setItemId(1L);

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
    void should_ReturnSuccessMessage_When_ItemIsNotExist() throws Exception {
        ItemRemoveDTO itemRemoveDTO = new ItemRemoveDTO();
        itemRemoveDTO.setItemId(1L);

        mockMvc
                .perform(
                        delete("/v1/cart/items")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtil.convertObjectToJsonBytes(itemRemoveDTO))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result").value(false))
                .andExpect(jsonPath("$.message").value(String.format("There is no item with id %d in the cart.", 1)));
    }

    @Test
    void should_ReturnSuccessMessage_When_VasItemAddRequestIsValid() throws Exception {
        VasItemAddDTO vasItemAddDTO = createVasItemAddDTO();
        inMemoryCartRepository.save(createSingleItemCart());

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
    void should_ReturnSuccessMessage_When_ResetCart() throws Exception {
        mockMvc
                .perform(
                        delete("/v1/cart")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true));
    }

    @Test
    void should_ReturnCart_When_ResetDisplayCart() throws Exception {
        mockMvc
                .perform(
                        get("/v1/cart")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.message.totalPrice").value(0));
    }


    private VasItemAddDTO createVasItemAddDTO() {
        return VasItemAddDTO.builder()
                .itemId(1L)
                .vasItemId(23L)
                .categoryId(3242L)
                .sellerId(5003L)
                .price(12)
                .quantity(3)
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

    private Cart createSingleItemCart(){
        Cart cart = new Cart();
        cart.setTotalPrice(new Price(0));
        DefaultItem defaultItem = new DefaultItem();
        defaultItem.setItemId(1L);
        defaultItem.setCategoryId(1001L);
        defaultItem.setPrice(33);
        defaultItem.setSellerId(11L);
        cart.setItems(List.of(defaultItem));
        return cart;
    }

    private void createDefaultItemsToCart(Cart cart) {
        List<Item> items = new ArrayList<>();
        for (long i = 10; i < 20; i++) {
            DefaultItem defaultItem = new DefaultItem();
            defaultItem.setItemId(i);
            items.add(defaultItem);
        }
        cart.setItems(items);
    }



}
