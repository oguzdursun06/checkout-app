package com.checkout.domain.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


@Data
public abstract class Item {

    private Long itemId;

    private Long categoryId;

    private Long sellerId;

    private double price;

    private int quantity;

    @JsonIgnore
    private double totalPrice;

    public void increaseTotalPrice(double increaseAmount){
        double currentTotalPrice = getTotalPrice();
        double increasedTotalPrice = currentTotalPrice + increaseAmount;
        setTotalPrice(increasedTotalPrice);
    }
}
