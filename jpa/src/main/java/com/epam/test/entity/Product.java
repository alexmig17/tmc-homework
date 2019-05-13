package com.epam.test.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Product extends AbstractEntity {

    @Column
    @Size(min = 1, max = 255)
    private String name;
    @Column
    private String description;
    @Column
    private BigDecimal price;
    @Column
    private byte[] image;

    public Product(Integer id) {
        this.id = id;
    }
}
