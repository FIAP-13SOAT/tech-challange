package com.fiapchallenge.garage.adapters.inbound.controller.customer;

import com.fiapchallenge.garage.application.commands.customer.CustomerFilterCmd;
import com.fiapchallenge.garage.application.commands.customer.UpdateCustomerCmd;
import com.fiapchallenge.garage.application.customer.CreateCustomerUseCase;
import com.fiapchallenge.garage.application.customer.ListCustomersService;
import com.fiapchallenge.garage.application.customer.UpdateCustomerService;

import com.fiapchallenge.garage.domain.customer.CpfCnpj;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.adapters.inbound.controller.customer.dto.CustomerRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.customer.dto.UpdateCustomerDTO;
import com.fiapchallenge.garage.domain.customer.command.CreateCustomerCommand;

import java.util.UUID;
import jakarta.validation.Valid;
import com.fiapchallenge.garage.shared.pagination.CustomPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController implements CustomerControllerOpenApiSpec {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final UpdateCustomerService updateCustomerService;
    private final ListCustomersService listCustomersService;

    public CustomerController(CreateCustomerUseCase createCustomerUseCase, UpdateCustomerService updateCustomerService, ListCustomersService listCustomersService) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.updateCustomerService = updateCustomerService;
        this.listCustomersService = listCustomersService;
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
        Page<Customer> customers = listCustomersService.list(filter, pageable);

        return ResponseEntity.ok(customers);
    }

    @Override
    @PostMapping
    public ResponseEntity<Customer> create(@Valid @RequestBody CustomerRequestDTO customerRequestDTO) {
        CpfCnpj cpfCnpj = new CpfCnpj(customerRequestDTO.cpfCnpj());

        CreateCustomerCommand command = new CreateCustomerCommand(
                customerRequestDTO.name(),
                customerRequestDTO.email(),
                customerRequestDTO.phone(),
                cpfCnpj
        );

        Customer customer = createCustomerUseCase.handle(command);
        return ResponseEntity.ok(customer);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable UUID id, @Valid @RequestBody UpdateCustomerDTO updateCustomerDTO) {
        UpdateCustomerCmd updateCustomerCmd = new UpdateCustomerCmd(
                updateCustomerDTO.name(),
                updateCustomerDTO.email(),
                updateCustomerDTO.phone()
        );

        Customer customer = updateCustomerService.update(id, updateCustomerCmd);
        return ResponseEntity.ok(customer);
    }
}
