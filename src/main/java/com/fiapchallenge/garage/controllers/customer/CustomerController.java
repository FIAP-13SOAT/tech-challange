package com.fiapchallenge.garage.controllers.customer;

import com.fiapchallenge.garage.adapters.inbound.rest.customer.dto.CreateCustomerRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.rest.customer.dto.CustomerResponseDTO;
import com.fiapchallenge.garage.adapters.inbound.rest.customer.dto.UpdateCustomerRequestDTO;
import com.fiapchallenge.garage.application.customer.create.CreateCustomerService;
import com.fiapchallenge.garage.application.customer.create.CreateCustomerUseCase;
import com.fiapchallenge.garage.application.customer.delete.DeleteCustomerService;
import com.fiapchallenge.garage.application.customer.delete.DeleteCustomerUseCase;
import com.fiapchallenge.garage.application.customer.list.ListCustomerUseCase;
import com.fiapchallenge.garage.application.customer.list.ListCustomersService;
import com.fiapchallenge.garage.application.customer.update.UpdateCustomerService;
import com.fiapchallenge.garage.application.customer.update.UpdateCustomerUseCase;
import com.fiapchallenge.garage.domain.customer.CustomerGateway;
import com.fiapchallenge.garage.presenters.customer.CustomerPresenter;
import com.fiapchallenge.garage.shared.pagination.CustomPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

import static com.fiapchallenge.garage.application.customer.list.ListCustomerUseCase.CustomerFilterCmd;

public class CustomerController {

    private final CustomerPresenter customerPresenter;
    private final CreateCustomerUseCase createCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;
    private final ListCustomerUseCase listCustomersUseCase;

    public CustomerController(CustomerGateway customerGateway, CustomerPresenter customerPresenter) {
        this.customerPresenter = customerPresenter;
        this.createCustomerUseCase = new CreateCustomerService(customerGateway);
        this.updateCustomerUseCase = new UpdateCustomerService(customerGateway);
        this.deleteCustomerUseCase = new DeleteCustomerService(customerGateway);
        this.listCustomersUseCase = new ListCustomersService(customerGateway);
    }

    public Page<CustomerResponseDTO> list(String name, String email, String cpfCnpj, Integer page, Integer size) {
        CustomerFilterCmd filter = new CustomerFilterCmd(name, email, cpfCnpj);
        Pageable pageable = CustomPageRequest.of(page, size);

        return customerPresenter.present(listCustomersUseCase.handle(filter, pageable));
    }

    public CustomerResponseDTO create(CreateCustomerRequestDTO customerRequestDTO) {
        CreateCustomerUseCase.CreateCustomerCommand cmd = new CreateCustomerUseCase.CreateCustomerCommand(
                customerRequestDTO.name(),
                customerRequestDTO.email(),
                customerRequestDTO.phone(),
                customerRequestDTO.cpfCnpj()
        );

        return customerPresenter.present(createCustomerUseCase.handle(cmd));
    }

    public CustomerResponseDTO update(UUID id, UpdateCustomerRequestDTO updateCustomerDTO) {
        UpdateCustomerUseCase.UpdateCustomerCmd cmd = new UpdateCustomerUseCase.UpdateCustomerCmd(
                id,
                updateCustomerDTO.name(),
                updateCustomerDTO.email(),
                updateCustomerDTO.phone()
        );

        return customerPresenter.present(updateCustomerUseCase.handle(cmd));
    }

    public void delete(UUID id) {
        DeleteCustomerUseCase.DeleteCustomerCmd cmd = new DeleteCustomerUseCase.DeleteCustomerCmd(id);
        deleteCustomerUseCase.handle(cmd);
    }
}
