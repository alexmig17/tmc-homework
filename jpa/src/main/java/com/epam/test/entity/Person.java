package com.epam.test.entity;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Person extends User {

    private Address address;
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Order> orders;
    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Basket basket;
    @Column
    @CreationTimestamp
    private LocalDate creationDate;

    public Person(Integer id) {
        super(id);
    }
}
