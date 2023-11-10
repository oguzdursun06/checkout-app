package com.checkout.domain.item;

import com.checkout.domain.exception.DefaultItemCountExceedException;
import com.checkout.domain.exception.VasItemCountExceedException;
import com.checkout.domain.vas.VasItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DefaultItem extends Item implements VasItemAddable {

    List<VasItem> vasItems = new ArrayList<>();

    private static final int DEFAULT_ITEM_MAXIMUM_COUNT = 10;

    private static final int MAX_VAS_ITEM = 3;

    @Override
    public void setQuantity(int quantity) {
        if(quantity > DEFAULT_ITEM_MAXIMUM_COUNT){
            throw new DefaultItemCountExceedException(DEFAULT_ITEM_MAXIMUM_COUNT);
        }
        super.setQuantity(quantity);
    }

    @Override
    public void addVasItemToItem(VasItem vasItem) {
        if(vasItems.size() >= MAX_VAS_ITEM){
            throw new VasItemCountExceedException(3);
        }
        vasItems.add(vasItem);
    }
}
