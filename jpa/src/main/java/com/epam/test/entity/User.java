package com.epam.test.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User extends AbstractEntity {

    @Column(name = "first_name")
    @Size(min = 1, max = 255)
    private String firstName;
    @Column(name = "last_name")
    @Size(min = 1, max = 255)
    private String lastName;
    @Column(unique = true)
    @Size(min = 1, max = 255)
    private String email;
    @Column
    @Size(min = 1, max = 255)
    private String password;
    @Column
    @Size(min = 1, max = 255)
    private String role;

    public User(Integer id) {
        this.id = id;
    }
}
