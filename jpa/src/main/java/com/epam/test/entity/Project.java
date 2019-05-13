package com.epam.test.entity;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Project extends AbstractEntity {

    private Integer id;
    private String name;
    private String owner;
}
