package com.epam.test.service;

import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;

import com.epam.test.converter.ProductConverter;
import com.epam.test.converter.ProductFormConverter;
import com.epam.test.dao.ProductDao;
import com.epam.test.dto.PaginationResult;
import com.epam.test.dto.ProductDto;
import com.epam.test.dto.ProductFormDto;
import com.epam.test.entity.Product;
import com.epam.test.enums.Sort;
import com.epam.test.exception.NotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductDao productDao;
    private final ProductConverter productConverter;
    private final ProductFormConverter productFormConverter;

    public boolean deleteProduct(Integer id) {
        return productDao.delete(new Product(id));
    }

    public ProductDto get(Integer id) {
        Product pr = productDao.get(id).orElseThrow(NotFoundException::new);
        String encodedImage = Base64.getEncoder().encodeToString(pr.getImage());
        return new ProductDto(pr.getId(), pr.getName(), pr.getDescription(), pr.getPrice(), encodedImage);
    }

    public List<ProductDto> getAll() {
        List<Product> products = productDao.getAll();
        return productConverter.toDtoList(products);
    }

    public PaginationResult<ProductDto> getAll(Integer offset, Integer elementCount, Sort sort) {
        List<Product> products = productDao.getAll(offset, elementCount, sort);
        List<ProductDto> productDtoList = productConverter.toDtoList(products);
        Integer totalCount = productDao.getTotalCount(null);
        return new PaginationResult<>(productDtoList, totalCount, elementCount, sort, null);
    }

    public PaginationResult<ProductDto> search(Integer offset, Integer elementCount, Sort sort, String search) {
        List<Product> products = productDao.search(offset, elementCount, sort, search);
        List<ProductDto> productDtoList = productConverter.toDtoList(products);
        Integer totalCount = productDao.getTotalCount(search);
        return new PaginationResult<>(productDtoList, totalCount, elementCount, sort, search);
    }

    public Integer update(ProductFormDto formDto) {
        Product productEntity = productFormConverter.toEntity(formDto);
        return productDao.update(productEntity);
    }

    public boolean delete(ProductFormDto formDto) {
        Product productEntity = productFormConverter.toEntity(formDto);
        return productDao.delete(productEntity);
    }

}
