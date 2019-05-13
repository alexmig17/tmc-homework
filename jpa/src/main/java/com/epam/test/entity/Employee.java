package com.epam.test.entity;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.epam.test.enums.EmployeeStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Employee extends User {

    private Address address;
    private Boolean external;
    private EmployeeStatus status;
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private PersonalInfo personalInfo;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Project> projects;
}
