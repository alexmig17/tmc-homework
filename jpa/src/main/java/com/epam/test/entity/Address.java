package com.epam.test.entity;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Address {

    private String country;
    private String city;
    private String address1;
    private String address2;
    private String zipCode;
}
