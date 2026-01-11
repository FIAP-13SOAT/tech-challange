package com.fiapchallenge.garage.application.customer.delete;

import com.fiapchallenge.garage.application.customer.exceptions.CustomerNotFoundException;
import com.fiapchallenge.garage.domain.customer.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class DeleteCustomerService implements DeleteCustomerUseCase {

    private final CustomerRepository customerRepository;

    public DeleteCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void handle(DeleteCustomerUseCase.DeleteCustomerCmd cmd) {
        UUID id = cmd.id();

        if (!customerRepository.exists(id)) {
            throw new CustomerNotFoundException(id);
        }

        customerRepository.deleteById(id);
    }
}
