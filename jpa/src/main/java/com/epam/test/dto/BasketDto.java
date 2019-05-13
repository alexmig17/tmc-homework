package com.epam.test.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasketDto implements Dto {

    Map<String, String> attributes = new HashMap<>();
    private Integer id;
    private List<ProductDto> products;
    private Integer personId;

    public BasketDto(Integer id) {
        this.id = id;
    }
}
