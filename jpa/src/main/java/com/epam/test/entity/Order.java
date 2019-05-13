package com.epam.test.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(name = "ORDER_TABLE")
@Entity
@EqualsAndHashCode(callSuper = true)
public class Order extends AbstractEntity {

    @ManyToOne(optional = false)
    private Product product;
    @ManyToOne(optional = false)
    private Person person;
    @CreationTimestamp
    private LocalDate date;
    private BigDecimal price;
}
