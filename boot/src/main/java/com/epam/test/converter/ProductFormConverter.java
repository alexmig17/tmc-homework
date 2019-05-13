package com.epam.test.converter;

import org.mapstruct.Mapper;

import com.epam.test.converter.field.MultipartImageToByte;
import com.epam.test.dto.ProductFormDto;
import com.epam.test.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductFormConverter extends ConverterToEntity<Product, ProductFormDto>, MultipartImageToByte {

}
