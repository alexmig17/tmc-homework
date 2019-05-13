package com.epam.test.controller.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.test.dto.BasketDto;
import com.epam.test.service.BasketService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/basket")
@RequiredArgsConstructor
public class BasketApiController {

    private final BasketService basketService;

    @GetMapping
    public Page<BasketDto> getBasket(Pageable pageable) {
        return basketService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public BasketDto getBasket(@PathVariable Integer id) {
        return basketService.get(id);
    }

    @PostMapping
    public BasketDto addBasket(@RequestParam Integer productId) {
        return basketService.create(productId);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id) {
        basketService.delete(id);
    }
}
