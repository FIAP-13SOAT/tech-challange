package com.fiapchallenge.garage.application.customer.create;

import com.fiapchallenge.garage.domain.customer.CpfCnpj;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.customer.CustomerGateway;
import com.fiapchallenge.garage.application.customer.exceptions.DuplicateCpfCnpjException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateCustomerService implements CreateCustomerUseCase {

    private final CustomerGateway customerGateway;

    public CreateCustomerService(CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
    }

    @Override
    public Customer handle(CreateCustomerCommand cmd) {
        CpfCnpj cpfCnpj = new CpfCnpj(cmd.cpfCnpj());

        if (customerGateway.existsByCpfCnpj(cpfCnpj)) {
            throw new DuplicateCpfCnpjException();
        }

        Customer customer = new Customer(null, cmd.name(), cmd.email(), cmd.phone(), cpfCnpj);
        customer = customerGateway.save(customer);

        return customer;
    }
}
