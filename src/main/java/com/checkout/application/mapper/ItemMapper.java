package com.checkout.application.mapper;

import com.checkout.application.dto.ItemAddDTO;
import com.checkout.domain.item.DefaultItem;
import com.checkout.domain.item.DigitalItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    DefaultItem dtoToDefaultEntity(ItemAddDTO itemAddDTO);

    DigitalItem dtoToDigitalEntity(ItemAddDTO itemAddDTO);


}
