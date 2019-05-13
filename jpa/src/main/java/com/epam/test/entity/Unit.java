package com.epam.test.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Unit extends AbstractEntity {

    @Column(unique = true)
    private String name;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Employee> employees;
}
