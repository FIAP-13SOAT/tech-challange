package com.fiapchallenge.garage.application.customer.list;

import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.customer.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ListCustomersService implements ListCustomerUseCase {

    private final CustomerRepository customerRepository;

    public ListCustomersService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Page<Customer> handle(CustomerFilterCmd filter, Pageable pageable) {
        if (!filter.hasFilters()) {
            return customerRepository.findAll(pageable);
        }

        return customerRepository.findByFilters(filter.name(), filter.email(), filter.cpfCnpj(), pageable);
    }
}
