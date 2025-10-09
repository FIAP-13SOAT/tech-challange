package com.fiapchallenge.garage.adapters.inbound.controller.customer;

import com.fiapchallenge.garage.application.customer.create.CreateCustomerUseCase;

import java.util.List;

import com.fiapchallenge.garage.application.customer.delete.DeleteCustomerUseCase;
import com.fiapchallenge.garage.application.customer.delete.DeleteCustomerUseCase.DeleteCustomerCmd;
import com.fiapchallenge.garage.application.customer.list.ListCustomerUseCase;
import com.fiapchallenge.garage.application.customer.update.UpdateCustomerUseCase;
import com.fiapchallenge.garage.application.customer.update.UpdateCustomerUseCase.UpdateCustomerCmd;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.adapters.inbound.controller.customer.dto.CustomerRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.customer.dto.UpdateCustomerDTO;

import java.util.UUID;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.fiapchallenge.garage.application.customer.create.CreateCustomerUseCase.*;
import static com.fiapchallenge.garage.application.customer.list.ListCustomerUseCase.*;

@RestController
@RequestMapping("/customers")
public class CustomerController implements CustomerControllerOpenApiSpec {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;
    private final ListCustomerUseCase listCustomersUseCase;

    public CustomerController(CreateCustomerUseCase createCustomerUseCase, UpdateCustomerUseCase updateCustomerUseCase, DeleteCustomerUseCase deleteCustomerUseCase, ListCustomerUseCase listCustomersUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
        this.listCustomersUseCase = listCustomersUseCase;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Customer>> list(@RequestParam(required = false) String name, @RequestParam(required = false) String email, @RequestParam(required = false) String cpfCnpj) {
        CustomerFilterCmd filter = new CustomerFilterCmd(name, email, cpfCnpj);
        List<Customer> customers = listCustomersUseCase.handle(filter);
        return ResponseEntity.ok(customers);
    }

    @Override
    @PostMapping
    public ResponseEntity<Customer> create(@Valid @RequestBody CustomerRequestDTO customerRequestDTO) {
        CreateCustomerCommand cmd = new CreateCustomerCommand(
                customerRequestDTO.name(),
                customerRequestDTO.email(),
                customerRequestDTO.phone(),
                customerRequestDTO.cpfCnpj()
        );

        Customer customer = createCustomerUseCase.handle(cmd);
        return ResponseEntity.ok(customer);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable UUID id, @Valid @RequestBody UpdateCustomerDTO updateCustomerDTO) {
        UpdateCustomerCmd cmd = new UpdateCustomerCmd(
                id,
                updateCustomerDTO.name(),
                updateCustomerDTO.email(),
                updateCustomerDTO.phone(),
                updateCustomerDTO.cpfCnpj()
        );

        Customer customer = updateCustomerUseCase.handle(cmd);
        return ResponseEntity.ok(customer);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        try {
            DeleteCustomerCmd cmd = new DeleteCustomerCmd(id);

            deleteCustomerUseCase.handle(cmd);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
