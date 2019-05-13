package com.epam.test.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    public static final String MODEL = "product";
    public static final String MODEL_LIST = "products";

    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private String image;
}
