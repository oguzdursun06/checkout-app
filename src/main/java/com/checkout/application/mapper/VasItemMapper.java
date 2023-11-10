package com.checkout.application.mapper;

import com.checkout.application.dto.VasItemAddDTO;
import com.checkout.domain.vas.VasItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VasItemMapper {

    VasItem dtoToDefaultEntity(VasItemAddDTO vasItemAddDTO);
}
