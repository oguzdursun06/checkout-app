package com.checkout.application.dto;

import com.checkout.domain.item.Item;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartDisplayDTO {

    private List<Item> items = new ArrayList<>();

    private double totalPrice;

    private Long appliedPromotionId;

    private double totalDiscount;

}
