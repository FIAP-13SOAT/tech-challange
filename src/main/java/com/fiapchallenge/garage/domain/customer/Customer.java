package com.fiapchallenge.garage.domain.customer;

import com.fiapchallenge.garage.application.customer.create.CreateCustomerUseCase;

import java.util.UUID;

public class Customer {

    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String cpfCnpj;

    public Customer(CreateCustomerUseCase.CreateCustomerCommand customerRequestDTO) {
        this.name = customerRequestDTO.name();
        this.email = customerRequestDTO.email();
        this.phone = customerRequestDTO.phone();
        this.cpfCnpj = customerRequestDTO.cpfCnpj();
    }

    public Customer(UUID id, String name, String email, String phone, String cpfCnpj) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.cpfCnpj = cpfCnpj;
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

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public Customer setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
        return this;
    }
}
