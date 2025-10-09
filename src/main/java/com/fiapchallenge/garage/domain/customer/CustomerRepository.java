package com.fiapchallenge.garage.domain.customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Customer save(Customer event);

    boolean exists(UUID id);

    Optional<Customer> findById(UUID id);
}
