package com.fiapchallenge.garage.controllers.servicetype;

import com.fiapchallenge.garage.adapters.inbound.controller.servicetype.dto.ServiceTypeDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.servicetype.dto.ServiceTypeRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.servicetype.dto.UpdateServiceTypeDTO;
import com.fiapchallenge.garage.application.servicetype.create.CreateServiceTypeCommand;
import com.fiapchallenge.garage.application.servicetype.create.CreateServiceTypeUseCase;
import com.fiapchallenge.garage.application.servicetype.delete.DeleteServiceTypeUseCase;
import com.fiapchallenge.garage.application.servicetype.update.UpdateServiceTypeUseCase;
import com.fiapchallenge.garage.domain.servicetype.ServiceTypeGateway;
import com.fiapchallenge.garage.presenters.servicetype.ServiceTypePresenter;

import java.util.List;
import java.util.UUID;

public class ServiceTypeController {

    private final ServiceTypeGateway serviceTypeGateway;
    private final ServiceTypePresenter serviceTypePresenter;
    private final CreateServiceTypeUseCase createServiceTypeUseCase;
    private final UpdateServiceTypeUseCase updateServiceTypeUseCase;
    private final DeleteServiceTypeUseCase deleteServiceTypeUseCase;

    public ServiceTypeController(
            ServiceTypeGateway serviceTypeGateway,
            ServiceTypePresenter serviceTypePresenter,
            CreateServiceTypeUseCase createServiceTypeUseCase,
            UpdateServiceTypeUseCase updateServiceTypeUseCase,
            DeleteServiceTypeUseCase deleteServiceTypeUseCase
    ) {
        this.serviceTypeGateway = serviceTypeGateway;
        this.serviceTypePresenter = serviceTypePresenter;
        this.createServiceTypeUseCase = createServiceTypeUseCase;
        this.updateServiceTypeUseCase = updateServiceTypeUseCase;
        this.deleteServiceTypeUseCase = deleteServiceTypeUseCase;
    }

    public ServiceTypeDTO create(ServiceTypeRequestDTO serviceTypeRequestDTO) {
        CreateServiceTypeCommand command = new CreateServiceTypeCommand(
                serviceTypeRequestDTO.description(),
                serviceTypeRequestDTO.value()
        );

        return serviceTypePresenter.present(createServiceTypeUseCase.handle(command));
    }

    public List<ServiceTypeDTO> getAll() {
        return serviceTypePresenter.present(serviceTypeGateway.findAll());
    }

    public ServiceTypeDTO update(UUID id, UpdateServiceTypeDTO updateServiceTypeDTO) {
        UpdateServiceTypeUseCase.UpdateServiceTypeCmd cmd = new UpdateServiceTypeUseCase.UpdateServiceTypeCmd(
                id,
                updateServiceTypeDTO.description(),
                updateServiceTypeDTO.value()
        );

        return serviceTypePresenter.present(updateServiceTypeUseCase.handle(cmd));
    }

    public void delete(UUID id) {
        DeleteServiceTypeUseCase.DeleteServiceTypeCmd cmd = new DeleteServiceTypeUseCase.DeleteServiceTypeCmd(id);
        deleteServiceTypeUseCase.handle(cmd);
    }
}
