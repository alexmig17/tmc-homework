package com.epam.test.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class OrderDto implements Dto {

    private Integer id;
    private Integer productId;
    private Integer personId;
    private LocalDate date;
    private Double price;
}
