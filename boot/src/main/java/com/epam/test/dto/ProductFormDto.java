package com.epam.test.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductFormDto {

    public static final String MODEL = "productForm";

    private Integer id;
    @NotNull
    @Size(min = 1, max = 100)
    private String name;
    @NotNull
    @Size(min = 1, max = 100)
    private String description;
    @NotNull
    private BigDecimal price;
    @NotNull
    private MultipartFile image;
}
