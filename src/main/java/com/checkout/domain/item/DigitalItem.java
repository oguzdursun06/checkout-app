package com.checkout.domain.item;

import com.checkout.domain.exception.DigitalItemCountExceedException;
import lombok.Data;

@Data
public class DigitalItem extends Item {

    private static final int DIGITAL_ITEM_MAXIMUM_COUNT = 5;

    @Override
    public void setQuantity(int quantity) {
        if(quantity > DIGITAL_ITEM_MAXIMUM_COUNT){
            throw new DigitalItemCountExceedException(DIGITAL_ITEM_MAXIMUM_COUNT);
        }
        super.setQuantity(quantity);
    }
}
