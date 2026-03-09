package com.fiapchallenge.garage.presenters.customer;

import com.fiapchallenge.garage.adapters.inbound.rest.customer.dto.CustomerResponseDTO;
import com.fiapchallenge.garage.domain.customer.Customer;
import org.springframework.data.domain.Page;

public class CustomerPresenter {

    public CustomerResponseDTO present(Customer customer) {
        return new CustomerResponseDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCpfCnpjValue()
        );
    }

    public Page<CustomerResponseDTO> present(Page<Customer> customers) {
        return customers.map(this::present);
    }
}
