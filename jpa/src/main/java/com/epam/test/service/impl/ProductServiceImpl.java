package com.epam.test.service.impl;

import java.util.Base64;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.epam.test.converter.ProductConverter;
import com.epam.test.converter.ProductFormConverter;
import com.epam.test.dto.ProductDto;
import com.epam.test.dto.ProductFormDto;
import com.epam.test.entity.Product;
import com.epam.test.exception.NotFoundException;
import com.epam.test.repository.ProductRepository;
import com.epam.test.service.ProductService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final ProductFormConverter productFormConverter;

    @Override
    public Page<ProductDto> search(String search, Pageable pageable) {
        Page<Product> products = productRepository.findByNameContaining(search, pageable);
        return products.map(productConverter::toDto);
    }

    @Override
    public ProductDto update(ProductDto productDto) {
        Product productEntity = productConverter.toEntity(productDto);
        return productConverter.toDto(productRepository.save(productEntity));
    }

    @Override
    public void delete(Integer id) {
        productRepository.delete(new Product(id));
    }

    @Override
    public ProductDto get(Integer id) {
        Product pr = productRepository.findById(id).orElseThrow(NotFoundException::new);
        String encodedImage = Base64.getEncoder().encodeToString(pr.getImage());
        return new ProductDto(pr.getId(), pr.getName(), pr.getDescription(), pr.getPrice(), encodedImage);
    }

    @Override
    public Page<ProductDto> getAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(productConverter::toDto);
    }

    @Override
    public Integer update(ProductFormDto form) {
        Product productEntity = productFormConverter.toEntity(form);
        return productRepository.save(productEntity).getId();
    }
}
