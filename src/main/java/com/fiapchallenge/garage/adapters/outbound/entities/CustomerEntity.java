package com.fiapchallenge.garage.adapters.outbound.entities;

import com.fiapchallenge.garage.domain.customer.Customer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Table(name = "customer")
@Entity
public class CustomerEntity {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String cpfCnpj;

    public CustomerEntity() {
    }

    public CustomerEntity(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.phone = customer.getPhone();
        this.cpfCnpj = customer.getCpfCnpj().toString();
    }

    public UUID getId() {
        return id;
    }

    public CustomerEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CustomerEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CustomerEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public CustomerEntity setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public CustomerEntity setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
        return this;
    }
}
