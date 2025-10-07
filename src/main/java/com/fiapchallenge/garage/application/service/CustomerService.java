package com.fiapchallenge.garage.application.service;

import com.fiapchallenge.garage.adapters.outbound.repositories.customer.CustomerRepositoryImpl;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.customer.CustomerRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepositoryImpl customerRepository;

    public CustomerService(CustomerRepositoryImpl customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer create(CustomerRequestDTO data) {
        Customer customer = new Customer(data);
        customer = customerRepository.save(customer);

        return customer;
    }
}
