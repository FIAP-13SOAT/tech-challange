package com.fiapchallenge.garage.application.customer.update;

import com.fiapchallenge.garage.application.customer.exceptions.CustomerNotFoundException;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.customer.CustomerGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateCustomerService implements UpdateCustomerUseCase {

    private final CustomerGateway customerGateway;

    public UpdateCustomerService(CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
    }

    @Override
    public Customer handle(UpdateCustomerCmd cmd) {
        Customer existingCustomer = customerGateway.findById(cmd.id())
            .orElseThrow(() -> new CustomerNotFoundException(cmd.id()));

        existingCustomer.setName(cmd.name());
        existingCustomer.setEmail(cmd.email());
        existingCustomer.setPhone(cmd.phone());

        return customerGateway.save(existingCustomer);
    }
}
