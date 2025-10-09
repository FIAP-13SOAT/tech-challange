package com.fiapchallenge.garage.application.customer.list;

import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.customer.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ListCustomersService implements ListCustomerUseCase {

    private final CustomerRepository customerRepository;

    public ListCustomersService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> handle(ListCustomerUseCase.CustomerFilterCmd filter) {
        if (!filter.hasFilters()) {
            return customerRepository.findAll();
        }

        return customerRepository.findByFilters(filter.name(), filter.email(), filter.cpfCnpj());
    }
}
