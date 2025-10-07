package com.fiapchallenge.garage.adapters.inbound.controller.serviceorder;

import com.fiapchallenge.garage.application.service.CustomerService;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.customer.CustomerRequestDTO;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service-orders")
public class ServiceOrderController implements ServiceOrderOpenApiSpec {

    private final CustomerService customerService;

    public ServiceOrderController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    @PostMapping
    public ResponseEntity<Customer> create(@Valid @RequestBody ServiceOrderRequestDTO serviceOrderRequestDTO) {
        Customer customer = customerService.create(customerRequestDTO);
        return ResponseEntity.ok(customer);
    }
}
