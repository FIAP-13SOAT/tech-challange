package com.fiapchallenge.garage.application.customer.create;

import com.fiapchallenge.garage.domain.customer.CpfCnpj;
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
    public Customer handle(CreateCustomerCommand cmd) {
        CpfCnpj cpfCnpj = new CpfCnpj(cmd.cpfCnpj());

        Customer customer = new Customer(null, cmd.name(), cmd.email(), cmd.phone(), cpfCnpj);
        customer = customerRepository.save(customer);

        return customer;
    }
}
