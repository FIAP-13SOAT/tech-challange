package com.fiapchallenge.garage.application.customer;

import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.customer.CustomerRepository;
import com.fiapchallenge.garage.domain.customer.command.CreateCustomerCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateCustomerService implements CreateCustomerUseCase {

    private final CustomerRepository customerRepository;

    public CreateCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer handle(CreateCustomerCommand cmd) {
        Customer customer = new Customer(cmd);
        customer = customerRepository.save(customer);

        return customer;
    }
}
