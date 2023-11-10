package com.checkout.domain.item;

import com.checkout.domain.exception.DigitalItemCountExceedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DigitalItemTest {

    DigitalItem digitalItem;

    @BeforeEach
    void setUp() {
        digitalItem = new DigitalItem();
        digitalItem.setItemId(1L);
        digitalItem.setQuantity(2);
        digitalItem.setPrice(30);
        digitalItem.setTotalPrice(60);
    }

    @Test
    void should_UpdateQuantityWithGivenParameter_When_MaximumQuantityIsNotReached() {
        int quantity = 2;

        digitalItem.setQuantity(quantity);

        assertEquals(2, digitalItem.getQuantity());
    }


    @Test
    void should_ThrowException_When_MaximumQuantityIsReached() {
        int quantity = 7;

        assertThrows(DigitalItemCountExceedException.class, () -> digitalItem.setQuantity(quantity));
    }

    @Test
    void should_UpdatePrice_When_IncreaseTotalPrice(){
        double increaseAmount = 20;

        digitalItem.increaseTotalPrice(increaseAmount);

        assertEquals(80, digitalItem.getTotalPrice());
    }




}
