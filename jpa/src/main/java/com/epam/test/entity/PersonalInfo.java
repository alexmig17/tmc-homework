package com.epam.test.entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class PersonalInfo extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;
    private LocalDate birthDate;
    private String workCity;
    private String workAddress;
    private String flor;
    private String office;
    private String place;
    private String title;
}
