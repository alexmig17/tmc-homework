package com.epam.test.service;

import com.epam.test.dto.BasketDto;

public interface BasketService extends CrudService<BasketDto, Integer> {

    BasketDto create(Integer productId);
}
