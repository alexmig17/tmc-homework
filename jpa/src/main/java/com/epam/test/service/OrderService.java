package com.epam.test.service;

import com.epam.test.dto.OrderDto;

public interface OrderService extends CrudService<OrderDto, Integer> {

    OrderDto save(Integer productId);
}
