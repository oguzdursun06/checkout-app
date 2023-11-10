package com.checkout.domain.item;

import com.checkout.application.dto.ItemAddDTO;
import com.checkout.application.mapper.ItemMapper;
import com.checkout.domain.exception.ItemSellerMismatchException;
import com.checkout.domain.exception.VasItemNotAllowedException;
import com.checkout.domain.vas.VasItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final Long digitalItemCategoryId;

    private final Long vasItemCategoryId;

    private final Long vasItemSellerId;

    private final List<Long> vasItemCategories;

    private final ItemMapper itemMapper;

    public ItemService(ItemMapper itemMapper,
                       @Value("${application.digitalItem.categoryId}") Long digitalItemCategoryId,
                       @Value("${application.vasItem.categoryId}") Long vasItemCategoryId,
                       @Value("${application.vasItem.sellerId}") Long vasItemSellerId,
                       @Value("${application.defaultItem.vasCategories}") List<Long> vasItemCategories) {
        this.digitalItemCategoryId = digitalItemCategoryId;
        this.vasItemCategoryId = vasItemCategoryId;
        this.vasItemSellerId = vasItemSellerId;
        this.vasItemCategories = vasItemCategories;
        this.itemMapper = itemMapper;
    }

    public Item createItemById(ItemAddDTO itemAddDTO){
        Long categoryId = itemAddDTO.getCategoryId();

        if(categoryId.equals(digitalItemCategoryId)){
            return createDigitalItem(itemAddDTO);
        } else if (categoryId.equals(vasItemCategoryId)) {
            throw new VasItemNotAllowedException();
        }
        else{
            return createDefaultItem(itemAddDTO);
        }
    }

    public void addVasItemToItem(Item cartItem, VasItem vasItem) {
        checkAdditionValidity(cartItem, vasItem);
        
        if(!(cartItem instanceof VasItemAddable)){
            throw new VasItemNotAllowedException();
        }
        ((VasItemAddable) cartItem).addVasItemToItem(vasItem);
        cartItem.increaseTotalPrice(vasItem.getPrice() * vasItem.getQuantity());
    }

    private DefaultItem createDefaultItem(ItemAddDTO itemAddDTO) {
        DefaultItem defaultItem = itemMapper.dtoToDefaultEntity(itemAddDTO);
        validateSellerId(itemAddDTO);
        return defaultItem;
    }

    private DigitalItem createDigitalItem(ItemAddDTO itemAddDTO) {
        DigitalItem digitalItem = itemMapper.dtoToDigitalEntity(itemAddDTO);
        validateSellerId(itemAddDTO);
        return digitalItem;
    }

    private void checkAdditionValidity(Item cartItem, VasItem vasItem) {
        if(!isVasItemAdditionAllowed(cartItem) || vasItem.getPrice() > cartItem.getPrice()){
            throw new VasItemNotAllowedException();
        }
    }

    private boolean isVasItemAdditionAllowed(Item cartItem) {
        return vasItemCategories.contains(cartItem.getCategoryId());
    }

    private void validateSellerId(ItemAddDTO itemAddDTO){
        Long sellerId = itemAddDTO.getSellerId();
        if(sellerId.equals(vasItemSellerId)){
            throw new ItemSellerMismatchException(sellerId);
        }
    }

}
