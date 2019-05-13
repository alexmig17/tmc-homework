package com.epam.test.service;

import com.epam.test.dto.ProductDto;
import com.epam.test.dto.ProductFormDto;

public interface ProductService extends CrudService<ProductDto, Integer>, SearchAbleService<ProductDto> {

    Integer update(ProductFormDto form);
}
