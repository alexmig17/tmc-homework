package com.epam.test.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.epam.test.converter.OrderConverter;
import com.epam.test.dto.OrderDto;
import com.epam.test.entity.Order;
import com.epam.test.entity.Person;
import com.epam.test.entity.Product;
import com.epam.test.exception.NotFoundException;
import com.epam.test.repository.OrderRepository;
import com.epam.test.repository.ProductRepository;
import com.epam.test.service.OrderService;
import com.epam.test.service.PersonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderConverter orderConverter;
    private final PersonService personService;

    @Override
    public OrderDto update(OrderDto dto) {
        throw new UnsupportedOperationException("update is not supported, user save instead");
    }

    @Override
    public void delete(Integer id) {
        orderRepository.deleteById(id);
    }

    @Override
    public OrderDto get(Integer id) {
        return orderConverter.toDto(orderRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public Page<OrderDto> getAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderConverter::toDto);
    }

    @Override
    public OrderDto save(Integer productId) {
        Person person = personService.getPersonFormAuthContext();
        Product product = productRepository.findById(productId).orElseThrow(NotFoundException::new);
        Order order = new Order();
        order.setProduct(product);
        order.setPerson(person);
        order.setPrice(product.getPrice());
        return orderConverter.toDto(orderRepository.save(order));
    }
}
