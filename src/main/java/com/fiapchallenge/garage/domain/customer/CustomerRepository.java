package com.fiapchallenge.garage.domain.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer save(Customer customer);

    boolean exists(UUID id);

    boolean existsByCpfCnpj(CpfCnpj cpfCnpj);

    Optional<Customer> findById(UUID id);

    Page<Customer> findAll(Pageable pageable);

    Page<Customer> findByFilters(String name, String email, String cpfCnpj, Pageable pageable);

    void deleteById(UUID id);
}
