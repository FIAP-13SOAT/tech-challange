package com.fiapchallenge.garage.unit.customer;

import com.fiapchallenge.garage.adapters.outbound.repositories.customer.CustomerRepositoryImpl;
import com.fiapchallenge.garage.application.customer.create.CreateCustomerService;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.unit.customer.util.factory.CustomerTestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerUnitTest {

    @Mock
    private CustomerRepositoryImpl customerRepository;

    @InjectMocks
    private CreateCustomerService createCustomerService;

    @Test
    @DisplayName("Criar cliente")
    void shouldCreateCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(CustomerTestFactory.build());

        Customer customer = createCustomerService.handle(CustomerTestFactory.buildCommand());

        assertEquals(CustomerTestFactory.NAME, customer.getName());
        assertEquals(CustomerTestFactory.EMAIL, customer.getEmail());
        assertEquals(CustomerTestFactory.PHONE, customer.getPhone());
    }
}
