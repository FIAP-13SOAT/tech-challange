package com.fiapchallenge.garage.adapters.inbound.rest.customer;

import com.fiapchallenge.garage.adapters.inbound.rest.customer.dto.CreateCustomerRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.rest.customer.dto.CustomerResponseDTO;
import com.fiapchallenge.garage.adapters.inbound.rest.customer.dto.UpdateCustomerRequestDTO;
import com.fiapchallenge.garage.controllers.customer.CustomerController;
import com.fiapchallenge.garage.domain.customer.CustomerGateway;
import com.fiapchallenge.garage.presenters.customer.CustomerPresenter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerResource implements CustomerResourceOpenApiSpec {

    private final CustomerController customerController;

    public CustomerResource(CustomerGateway customerGateway) {
        this.customerController = new CustomerController(customerGateway, new CustomerPresenter());
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<CustomerResponseDTO>> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String cpfCnpj,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        return ResponseEntity.ok(customerController.list(name, email, cpfCnpj, page, size));
    }

    @Override
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLERK')")
    public ResponseEntity<CustomerResponseDTO> create(@Valid @RequestBody CreateCustomerRequestDTO customerRequestDTO) {
        return ResponseEntity.ok(customerController.create(customerRequestDTO));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody UpdateCustomerRequestDTO updateCustomerDTO) {
        return ResponseEntity.ok(customerController.update(id, updateCustomerDTO));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        customerController.delete(id);
        return ResponseEntity.noContent().build();
    }
}
