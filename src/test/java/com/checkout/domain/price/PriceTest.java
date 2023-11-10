package com.checkout.domain.price;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    Price price;

    @BeforeEach
    void setUp() {
        price = new Price(100);
    }

    @Test
    void should_ChangeAmount_WhenNewAmountIsGiven(){
        double newPriceAmount = 250;

        Price newPrice = price.changeAmount(newPriceAmount);

        assertEquals(newPriceAmount, newPrice.getAmount());
    }

}