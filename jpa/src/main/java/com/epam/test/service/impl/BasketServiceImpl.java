package com.epam.test.service.impl;

import static java.util.Optional.ofNullable;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.epam.test.converter.BasketConverter;
import com.epam.test.dto.BasketDto;
import com.epam.test.entity.Basket;
import com.epam.test.entity.Person;
import com.epam.test.entity.Product;
import com.epam.test.exception.NotFoundException;
import com.epam.test.repository.BasketRepository;
import com.epam.test.repository.PersonRepository;
import com.epam.test.repository.ProductRepository;
import com.epam.test.service.BasketService;
import com.epam.test.service.PersonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository repository;
    private final BasketConverter converter;
    private final PersonRepository personRepository;
    private final ProductRepository productRepository;
    private final PersonService personService;

    @Override
    public BasketDto update(BasketDto dto) {
        return converter.toDto(repository.save(converter.toEntity(dto)));
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public BasketDto get(Integer id) {
        Basket basket = repository.findById(id).orElseThrow(NotFoundException::new);
        return converter.toDto(basket);
    }

    @Override
    public Page<BasketDto> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(converter::toDto);
    }

    @Override
    public BasketDto create(Integer productId) {
        Person person = personService.getPersonFormAuthContext();
        Product product = productRepository.findById(productId).orElseThrow(NotFoundException::new);
        Basket basket = ofNullable(person.getBasket()).orElseGet(() -> new Basket(person));
        basket.addProduct(product);
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        map.put("key2", "value2");
        basket.setAttributes(map);
        return converter.toDto(repository.save(basket));
    }
}
