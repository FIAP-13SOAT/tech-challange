package com.fiapchallenge.garage.adapters.inbound.controller.customer;

import com.fiapchallenge.garage.adapters.inbound.controller.customer.dto.CustomerRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.customer.dto.UpdateCustomerDTO;
import com.fiapchallenge.garage.application.customer.create.CreateCustomerUseCase;
import com.fiapchallenge.garage.application.customer.create.CreateCustomerUseCase.CreateCustomerCommand;
import com.fiapchallenge.garage.application.customer.delete.DeleteCustomerUseCase;
import com.fiapchallenge.garage.application.customer.delete.DeleteCustomerUseCase.DeleteCustomerCmd;
import com.fiapchallenge.garage.application.customer.list.ListCustomerUseCase;
import com.fiapchallenge.garage.application.customer.update.UpdateCustomerUseCase;
import com.fiapchallenge.garage.application.customer.update.UpdateCustomerUseCase.UpdateCustomerCmd;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.shared.pagination.CustomPageRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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

import static com.fiapchallenge.garage.application.customer.list.ListCustomerUseCase.CustomerFilterCmd;

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
    public ResponseEntity<Page<Customer>> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String cpfCnpj,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        CustomerFilterCmd filter = new CustomerFilterCmd(name, email, cpfCnpj);

        Pageable pageable = CustomPageRequest.of(page, size);
        Page<Customer> customers = listCustomersUseCase.handle(filter, pageable);

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
                updateCustomerDTO.phone()
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
