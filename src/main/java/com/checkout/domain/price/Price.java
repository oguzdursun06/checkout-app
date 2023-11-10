package com.checkout.domain.price;

import lombok.Data;

@Data
public class Price {

    private final double amount;

    public Price(double amount) {
        this.amount = amount;
    }

    public Price changeAmount(double amount){
        return new Price(amount);
    }
}
