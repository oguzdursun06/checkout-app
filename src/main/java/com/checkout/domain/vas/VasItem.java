package com.checkout.domain.vas;

import lombok.Data;

@Data
public class VasItem {

    private Long vasItemId;

    private Long categoryId;

    private Long sellerId;

    private double price;

    private int quantity;

}
