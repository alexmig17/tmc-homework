package com.epam.test.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.epam.test.dto.OrderDto;
import com.epam.test.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderConverter extends Converter<Order, OrderDto> {

    @Mappings({
            @Mapping(target = "productId", source = "entity.product.id"),
            @Mapping(target = "personId", source = "entity.person.id")
    })
    @Override
    OrderDto toDto(Order entity);
}
