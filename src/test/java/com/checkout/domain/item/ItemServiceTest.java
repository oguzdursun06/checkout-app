package com.checkout.domain.item;


import com.checkout.application.dto.ItemAddDTO;
import com.checkout.application.mapper.ItemMapper;
import com.checkout.domain.exception.ItemSellerMismatchException;
import com.checkout.domain.exception.VasItemNotAllowedException;
import com.checkout.domain.vas.VasItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ItemServiceTest {

    private ItemService itemService;
    private ItemMapper itemMapper;

    @BeforeEach
    void setUp() {
        Long digitalItemCategoryId = 7889L;
        Long vasItemCategoryId = 3242L;
        Long vasItemSellerId = 5003L;
        List<Long> vasItemCategories = List.of(1001L, 3004L);
        itemMapper = mock(ItemMapper.class);

        itemService = new ItemService(itemMapper, digitalItemCategoryId, vasItemCategoryId, vasItemSellerId, vasItemCategories);
    }

    @Test
    void should_CreateDigitalItem_When_DigitalItemIsGiven(){
        ItemAddDTO itemAddDTO = createDigitalItemAddDTO();
        DigitalItem digitalItem = createDigitalItem();
        when(itemMapper.dtoToDigitalEntity(any())).thenReturn(digitalItem);

        Item actualItem = itemService.createItemById(itemAddDTO);

        assertEquals(digitalItem, actualItem);
    }

    @Test
    void should_ThrowException_When_DigitalItemHasVasSeller(){
        ItemAddDTO itemAddDTO = createDigitalItemAddDTO();
        itemAddDTO.setSellerId(5003L);
        DigitalItem digitalItem = createDigitalItem();
        when(itemMapper.dtoToDigitalEntity(any())).thenReturn(digitalItem);

        assertThrows(ItemSellerMismatchException.class, () -> itemService.createItemById(itemAddDTO));
    }

    @Test
    void should_ThrowException_When_ItemHasVasCategoryId(){
        ItemAddDTO itemAddDTO = createDigitalItemAddDTO();
        itemAddDTO.setCategoryId(3242L);

        assertThrows(VasItemNotAllowedException.class, () -> itemService.createItemById(itemAddDTO));
    }

    @Test
    void should_CreateDefaultItem_When_DefaultItemIsGiven(){
        ItemAddDTO itemAddDTO = createDefaultItemAddDTO();
        DefaultItem defaultItem = createDefaultItem();
        when(itemMapper.dtoToDefaultEntity(any())).thenReturn(defaultItem);

        Item actualItem = itemService.createItemById(itemAddDTO);

        assertEquals(defaultItem, actualItem);
    }

    @Test
    void should_AddVasToItem_When_CreateVasToDefaultItem(){
        DefaultItem defaultItem = createDefaultItem();
        VasItem vasItem = createVasItem();

        itemService.addVasItemToItem(defaultItem, vasItem);

        assertEquals(30, defaultItem.getTotalPrice());
    }

    @Test
    void should_ThrowException_When_VasPriceIsGreaterThanItemPrice(){
        DefaultItem defaultItem = createDefaultItem();
        VasItem vasItem = createVasItem();
        vasItem.setPrice(500);

        assertThrows(VasItemNotAllowedException.class,() -> itemService.addVasItemToItem(defaultItem, vasItem));
    }

    @Test
    void should_ThrowException_When_CreateVasToDigitalItem(){
        DigitalItem digitalItem = createDigitalItem();
        digitalItem.setCategoryId(1001L);
        VasItem vasItem = createVasItem();

        assertThrows(VasItemNotAllowedException.class,() -> itemService.addVasItemToItem(digitalItem, vasItem));
    }

    private ItemAddDTO createDigitalItemAddDTO() {
        return ItemAddDTO.builder()
                .itemId(12L)
                .sellerId(5004L)
                .categoryId(7889L)
                .price(20)
                .build();
    }

    private ItemAddDTO createDefaultItemAddDTO() {
        return ItemAddDTO.builder()
                .itemId(12L)
                .sellerId(5004L)
                .categoryId(1001L)
                .price(20)
                .build();
    }

    private DigitalItem createDigitalItem() {
        DigitalItem digitalItem = new DigitalItem();
        digitalItem.setItemId(12L);
        digitalItem.setSellerId(5004L);
        digitalItem.setCategoryId(7889L);
        digitalItem.setPrice(20);
        return digitalItem;
    }

    private DefaultItem createDefaultItem() {
        DefaultItem defaultItem = new DefaultItem();
        defaultItem.setItemId(12L);
        defaultItem.setSellerId(5004L);
        defaultItem.setCategoryId(1001L);
        defaultItem.setPrice(20);
        defaultItem.setTotalPrice(20);
        return defaultItem;
    }

    private VasItem createVasItem() {
        VasItem vasItem = new VasItem();
        vasItem.setPrice(10);
        vasItem.setQuantity(1);
        return vasItem;
    }


}