package com.checkout.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ItemRemoveDTO {

    @NotNull(message = "Item id can not be null")
    @Positive(message = "Item id must be a positive number")
    private Long itemId;
}
