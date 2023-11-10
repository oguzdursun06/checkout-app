package com.checkout.domain.vas;

import com.checkout.application.dto.VasItemAddDTO;
import com.checkout.application.mapper.VasItemMapper;
import com.checkout.domain.exception.InvalidVasCategoryIdException;
import com.checkout.domain.exception.ItemSellerMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VasItemService {

    private final Long vasItemCategoryId;

    private final Long vasItemSellerId;

    private final VasItemMapper vasItemMapper;

    public VasItemService(VasItemMapper vasItemMapper,
                          @Value("${application.vasItem.categoryId}") Long vasItemCategoryId,
                          @Value("${application.vasItem.sellerId}") Long vasItemSellerId
                          ) {
        this.vasItemCategoryId = vasItemCategoryId;
        this.vasItemSellerId = vasItemSellerId;
        this.vasItemMapper = vasItemMapper;
    }

    public VasItem createVasItem(VasItemAddDTO vasItemAddDTO) {
        validateCategoryId(vasItemAddDTO);
        validateSellerId(vasItemAddDTO);
        return vasItemMapper.dtoToDefaultEntity(vasItemAddDTO);
    }

    private void validateCategoryId(VasItemAddDTO vasItemAddDTO) {
        Long categoryId = vasItemAddDTO.getCategoryId();
        if(!categoryId.equals(vasItemCategoryId)){
            throw new InvalidVasCategoryIdException(categoryId);
        }
    }

    private void validateSellerId(VasItemAddDTO vasItemAddDTO){
        Long sellerId = vasItemAddDTO.getSellerId();
        if(!sellerId.equals(vasItemSellerId)){
            throw new ItemSellerMismatchException(sellerId);
        }
    }

}
