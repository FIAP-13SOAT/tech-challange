package com.fiapchallenge.garage.domain.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiapchallenge.garage.domain.customer.command.CreateCustomerCommand;

import java.util.UUID;

public class Customer {

    private UUID id;
    private String name;
    private String email;
    private String phone;
    private CpfCnpj cpfCnpj;

    public Customer(CreateCustomerCommand createCustomerCommand) {
        this.name = createCustomerCommand.name();
        this.email = createCustomerCommand.email();
        this.phone = createCustomerCommand.phone();
        this.cpfCnpj = createCustomerCommand.cpfCnpj();
    }

    public Customer(UUID id, String name, String email, String phone, String cpfCnpj) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.cpfCnpj = new CpfCnpj(cpfCnpj);
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

    public CpfCnpj getCpfCnpj() {
        return cpfCnpj;
    }

    @JsonProperty("cpfCnpj")
    public String getCpfCnpjValue() {
        return cpfCnpj.getValue();
    }

    public Customer setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = new CpfCnpj(cpfCnpj);
        return this;
    }
}
