package com.fiapchallenge.garage.domain.customer;

import com.fiapchallenge.garage.domain.customer.command.CreateCustomerCommand;

import java.util.UUID;

public class Customer {

    private UUID id;
    private String name;
    private String email;
    private String phone;

    public Customer(CreateCustomerCommand customerRequestDTO) {
        this.name = customerRequestDTO.name();
        this.email = customerRequestDTO.email();
        this.phone = customerRequestDTO.phone();
    }

    public Customer(UUID id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public UUID getId() {
        return id;
    }

    public Customer setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Customer setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Customer setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Customer setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}
