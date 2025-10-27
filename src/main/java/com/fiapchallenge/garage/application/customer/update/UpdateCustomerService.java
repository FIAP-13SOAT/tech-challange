package com.fiapchallenge.garage.application.customer.update;

import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.customer.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateCustomerService implements UpdateCustomerUseCase {

    private final CustomerRepository customerRepository;

    public UpdateCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer handle(UpdateCustomerCmd cmd) {
        Customer existingCustomer = customerRepository.findById(cmd.id())
            .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + cmd.id()));

        existingCustomer.setName(cmd.name());
        existingCustomer.setEmail(cmd.email());
        existingCustomer.setPhone(cmd.phone());

        return customerRepository.save(existingCustomer);
    }
}
