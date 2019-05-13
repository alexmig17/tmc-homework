package com.epam.test.controller.api;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.test.dto.IdDto;
import com.epam.test.dto.ProductDto;
import com.epam.test.dto.ProductFormDto;
import com.epam.test.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductApiController {

    private final ProductService productService;

    @GetMapping
    public Page<ProductDto> getProduct(Pageable pageable) {
        return productService.getAll(pageable);
    }

    @GetMapping(params = "search")
    public Page<ProductDto> searchProduct(String search, Pageable pageable) {
        return productService.search(search, pageable);
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Integer id) {
        return productService.get(id);
    }

    @PostMapping(consumes = {MULTIPART_FORM_DATA_VALUE})
    public IdDto saveProduct(@Valid ProductFormDto form) {
        return new IdDto(productService.update(form));
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.delete(id);
    }


}
