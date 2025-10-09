package com.fiapchallenge.garage.application.customer.create;

import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.customer.CustomerRepository;
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
    public Customer handle(CreateCustomerCommand data) {
        Customer customer = new Customer(data);
        customer = customerRepository.save(customer);

        return customer;
    }
}
