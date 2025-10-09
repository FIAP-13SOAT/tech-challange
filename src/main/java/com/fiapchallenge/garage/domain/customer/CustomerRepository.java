package com.fiapchallenge.garage.domain.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer save(Customer customer);

    boolean exists(UUID id);

    Optional<Customer> findById(UUID id);

    List<Customer> findAll();

    List<Customer> findByFilters(String name, String email, String cpfCnpj);

    void deleteById(UUID id);
}
