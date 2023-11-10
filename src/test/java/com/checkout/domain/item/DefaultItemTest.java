package com.checkout.domain.item;


import com.checkout.domain.exception.DefaultItemCountExceedException;
import com.checkout.domain.exception.VasItemCountExceedException;
import com.checkout.domain.vas.VasItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultItemTest {

    DefaultItem defaultItem;

    @BeforeEach
    void setUp() {
        createTestData();
    }

    @Test
    void should_UpdateQuantityWithGivenParameter_When_MaximumQuantityIsNotReached() {
        int quantity = 2;

        defaultItem.setQuantity(quantity);

        assertEquals(2, defaultItem.getQuantity());
    }


    @Test
    void should_ThrowException_When_MaximumQuantityIsReached() {
        int quantity = 12;
        
        assertThrows(DefaultItemCountExceedException.class, () -> defaultItem.setQuantity(quantity));
    }

    @Test
    void should_AddVasItem_When_ValidationIsPassed(){
        VasItem vasItem = new VasItem();
        vasItem.setVasItemId(1L);
        vasItem.setSellerId(5003L);
        vasItem.setPrice(10);

        defaultItem.addVasItemToItem(vasItem);

        assertEquals(3, defaultItem.getVasItems().size());
    }

    @Test
    void should_ThrowException_When_VasItemSizeIsGreaterThanLimit(){
        List<VasItem> vasItems = defaultItem.getVasItems();
        vasItems.add(new VasItem());
        defaultItem.setVasItems(vasItems);
        VasItem vasItem = new VasItem();
        vasItem.setVasItemId(1L);
        vasItem.setSellerId(5003L);
        vasItem.setPrice(10);

        assertThrows(VasItemCountExceedException.class, () -> defaultItem.addVasItemToItem(vasItem));
    }

    private void createTestData() {
        defaultItem = new DefaultItem();
        defaultItem.setItemId(1L);
        defaultItem.setCategoryId(1001L);
        defaultItem.setQuantity(4);
        defaultItem.setPrice(30);
        defaultItem.setSellerId(1L);
        List<VasItem> vasItems = new ArrayList<>(List.of(new VasItem(), new VasItem()));
        defaultItem.setVasItems(vasItems);
    }


}
