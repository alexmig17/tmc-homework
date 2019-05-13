package com.epam.test.converter;

import org.mapstruct.Mapper;

import com.epam.test.converter.field.BytesToBase64;
import com.epam.test.dto.ProductDto;
import com.epam.test.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductConverter extends Converter<Product, ProductDto>, BytesToBase64 {

}
