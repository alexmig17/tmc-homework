package com.epam.test.dto;

import lombok.Data;

@Data
public class AddressDto {

    private String country;
    private String city;
    private String address1;
    private String address2;
    private String zipCode;
}
