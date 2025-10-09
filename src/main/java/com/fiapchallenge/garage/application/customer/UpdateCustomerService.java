package com.fiapchallenge.garage.application.customer;

import com.fiapchallenge.garage.application.commands.customer.UpdateCustomerCmd;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.customer.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UpdateCustomerService {

    private final CustomerRepository customerRepository;

    public UpdateCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer update(UUID id, UpdateCustomerCmd cmd) {
        Customer existingCustomer = customerRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));

        existingCustomer.setName(cmd.name());
        existingCustomer.setEmail(cmd.email());
        existingCustomer.setPhone(cmd.phone());

        return customerRepository.save(existingCustomer);
    }
}
