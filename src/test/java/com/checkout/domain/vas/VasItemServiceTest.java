package com.checkout.domain.vas;

import com.checkout.application.dto.VasItemAddDTO;
import com.checkout.application.mapper.VasItemMapper;
import com.checkout.domain.exception.InvalidVasCategoryIdException;
import com.checkout.domain.exception.ItemSellerMismatchException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VasItemServiceTest {


    private VasItemService vasItemService;

    private VasItemMapper vasItemMapper;

    @BeforeEach
    void setUp() {
        Long vasItemCategoryId = 3242L;
        Long vasItemSellerId = 5003L;
        vasItemMapper = mock(VasItemMapper.class);

        vasItemService = new VasItemService(vasItemMapper, vasItemCategoryId, vasItemSellerId);
    }

    @Test
    void should_CreateVasItem_When_VasItemAddDTOValid(){
        VasItemAddDTO vasItemAddDTO = createVasItemAddDTO();
        VasItem vasItem = createVasItem();
        when(vasItemMapper.dtoToDefaultEntity(any())).thenReturn(vasItem);

        VasItem actualVasItem = vasItemService.createVasItem(vasItemAddDTO);

        assertEquals(vasItem, actualVasItem);
    }

    @Test
    void should_ThrowException_When_CategoryIdIsNotVas(){
        VasItemAddDTO vasItemAddDTO = createVasItemAddDTO();
        vasItemAddDTO.setCategoryId(1L);

        assertThrows(InvalidVasCategoryIdException.class, () -> vasItemService.createVasItem(vasItemAddDTO));
    }

    @Test
    void should_ThrowException_When_SellerIdIsNotSuitableForVas(){
        VasItemAddDTO vasItemAddDTO = createVasItemAddDTO();
        vasItemAddDTO.setSellerId(1L);

        assertThrows(ItemSellerMismatchException.class, () -> vasItemService.createVasItem(vasItemAddDTO));
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

    private VasItem createVasItem(){
        VasItem vasItem = new VasItem();
        vasItem.setVasItemId(23L);
        vasItem.setCategoryId(3242L);
        vasItem.setSellerId(5003L);
        vasItem.setPrice(12);
        vasItem.setQuantity(3);
        return vasItem;
    }

}