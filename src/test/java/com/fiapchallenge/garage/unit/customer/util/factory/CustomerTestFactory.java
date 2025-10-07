package com.fiapchallenge.garage.unit.customer.util.factory;

import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.customer.CustomerRequestDTO;

import java.util.UUID;

public class CustomerTestFactory {

    public static final UUID ID = UUID.randomUUID();
    public static final String NAME = "Pix JR";
    public static final String EMAIL = "pixjr@gmail.com";
    public static final String PHONE = "+5547999999999";

    public static Customer build() {
        return new Customer(ID, NAME, EMAIL, PHONE);
    }

    public static CustomerRequestDTO buildRequestDTO() {
        return new CustomerRequestDTO(NAME, EMAIL, PHONE);
    }
}
