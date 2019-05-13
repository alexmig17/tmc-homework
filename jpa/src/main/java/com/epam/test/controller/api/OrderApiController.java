package com.epam.test.controller.api;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.test.dto.IdDto;
import com.epam.test.dto.OrderDto;
import com.epam.test.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    @GetMapping
    public Page<OrderDto> getOrders(Pageable pageable) {
        return orderService.getAll(pageable);
    }

    @GetMapping("{id}")
    public OrderDto getOrderById(@PathVariable Integer id) {
        return orderService.get(id);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        orderService.delete(id);
    }

    @PostMapping
    public OrderDto create(@Valid @RequestBody IdDto productId) {
        return orderService.save(productId.getId());
    }
}
