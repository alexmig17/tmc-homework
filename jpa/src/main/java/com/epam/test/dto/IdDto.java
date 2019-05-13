package com.epam.test.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IdDto {

    @NotNull
    private Integer id;

    public IdDto(Integer id) {
        this.id = id;
    }
}
