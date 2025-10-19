package com.fiapchallenge.garage.domain.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerRepository {

    Customer save(Customer customer);

    boolean exists(UUID id);

    Optional<Customer> findById(UUID id);

    List<Customer> findAll();

    Page<Customer> findAll(Pageable pageable);

    List<Customer> findByFilters(String name, String email, String cpfCnpj);

    Page<Customer> findByFilters(String name, String email, String cpfCnpj, Pageable pageable);
}
