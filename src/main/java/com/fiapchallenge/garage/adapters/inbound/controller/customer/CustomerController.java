package com.fiapchallenge.garage.adapters.inbound.controller.customer;

import com.fiapchallenge.garage.application.commands.customer.UpdateCustomerCmd;
import com.fiapchallenge.garage.application.customer.CreateCustomerUseCase;
import com.fiapchallenge.garage.application.customer.UpdateCustomerService;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.adapters.inbound.controller.customer.dto.CustomerRequestDTO;
import com.fiapchallenge.garage.domain.customer.command.CreateCustomerCommand;

import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController implements CustomerControllerOpenApiSpec {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final UpdateCustomerService updateCustomerService;

    public CustomerController(CreateCustomerUseCase createCustomerUseCase, UpdateCustomerService updateCustomerService) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.updateCustomerService = updateCustomerService;
    }

    @Override
    @PostMapping
    public ResponseEntity<Customer> create(@Valid @RequestBody CustomerRequestDTO customerRequestDTO) {
        CreateCustomerCommand command = new CreateCustomerCommand(
                customerRequestDTO.name(),
                customerRequestDTO.email(),
                customerRequestDTO.phone()
        );
        Customer customer = createCustomerUseCase.handle(command);
        return ResponseEntity.ok(customer);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable UUID id, @Valid @RequestBody UpdateCustomerCmd updateCustomerCmd) {
        Customer customer = updateCustomerService.update(id, updateCustomerCmd);
        return ResponseEntity.ok(customer);
    }
}
