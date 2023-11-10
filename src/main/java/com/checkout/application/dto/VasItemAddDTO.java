package com.checkout.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VasItemAddDTO {

    @NotNull(message = "Item id can not be null")
    @Positive(message = "Item id must be a positive number")
    private Long itemId;

    @NotNull(message = "Vas item id can not be null")
    @Positive(message = "Vas item id must be a positive number")
    private Long vasItemId;

    @NotNull(message = "Category id can not be null")
    @Positive(message = "Category id must be a positive number")
    private Long categoryId;

    @NotNull(message = "Seller id can not be null")
    @Positive(message = "Seller id must be a positive number")
    private Long sellerId;

    @NotNull(message = "Price can not be null")
    @Positive(message = "Price must be a positive number")
    private double price;

    @NotNull(message = "Quantity can not be null")
    @Positive(message = "Quantity must be a positive number")
    private double quantity;
}
