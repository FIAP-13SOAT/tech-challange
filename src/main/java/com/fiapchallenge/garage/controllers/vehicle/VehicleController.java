package com.fiapchallenge.garage.controllers.vehicle;

import com.fiapchallenge.garage.adapters.inbound.controller.vehicle.dto.UpdateVehicleRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.vehicle.dto.VehicleDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.vehicle.dto.VehicleRequestDTO;
import com.fiapchallenge.garage.application.vehicle.create.CreateVehicleCommand;
import com.fiapchallenge.garage.application.vehicle.create.CreateVehicleService;
import com.fiapchallenge.garage.application.vehicle.create.CreateVehicleUseCase;
import com.fiapchallenge.garage.application.vehicle.delete.DeleteVehicleService;
import com.fiapchallenge.garage.application.vehicle.delete.DeleteVehicleUseCase;
import com.fiapchallenge.garage.application.vehicle.find.FindVehicleService;
import com.fiapchallenge.garage.application.vehicle.find.FindVehicleUseCase;
import com.fiapchallenge.garage.application.vehicle.list.ListVehicleService;
import com.fiapchallenge.garage.application.vehicle.list.ListVehicleUseCase;
import com.fiapchallenge.garage.application.vehicle.update.UpdateVehicleCommand;
import com.fiapchallenge.garage.application.vehicle.update.UpdateVehicleService;
import com.fiapchallenge.garage.application.vehicle.update.UpdateVehicleUseCase;
import com.fiapchallenge.garage.domain.customer.CustomerRepository;
import com.fiapchallenge.garage.domain.vehicle.VehicleGateway;
import com.fiapchallenge.garage.presenters.vehicle.VehiclePresenter;

import java.util.List;
import java.util.UUID;

public class VehicleController {

    private final VehiclePresenter vehiclePresenter;
    private final CreateVehicleUseCase createVehicleUseCase;
    private final FindVehicleUseCase findVehicleUseCase;
    private final ListVehicleUseCase listVehicleUseCase;
    private final UpdateVehicleUseCase updateVehicleUseCase;
    private final DeleteVehicleUseCase deleteVehicleUseCase;

    public VehicleController(
            VehicleGateway vehicleGateway,
            CustomerRepository customerRepository,
            VehiclePresenter vehiclePresenter
    ) {
        this.vehiclePresenter = vehiclePresenter;
        this.createVehicleUseCase = new CreateVehicleService(vehicleGateway, customerRepository);
        this.findVehicleUseCase = new FindVehicleService(vehicleGateway);
        this.listVehicleUseCase = new ListVehicleService(vehicleGateway);
        this.updateVehicleUseCase = new UpdateVehicleService(vehicleGateway);
        this.deleteVehicleUseCase = new DeleteVehicleService(vehicleGateway);
    }

    public VehicleDTO create(VehicleRequestDTO requestDTO) {
        CreateVehicleCommand command = new CreateVehicleCommand(
                requestDTO.model(),
                requestDTO.brand(),
                requestDTO.licensePlate().replace("-", ""),
                requestDTO.color(),
                requestDTO.year(),
                requestDTO.observations(),
                requestDTO.customerId()
        );

        return vehiclePresenter.present(createVehicleUseCase.handle(command));
    }

    public List<VehicleDTO> listByCustomer(UUID customerId) {
        return vehiclePresenter.present(listVehicleUseCase.handle(customerId));
    }

    public VehicleDTO findById(UUID id) {
        return vehiclePresenter.present(findVehicleUseCase.handle(id));
    }

    public VehicleDTO update(UUID id, UpdateVehicleRequestDTO requestDTO) {
        UpdateVehicleCommand command = new UpdateVehicleCommand(
                id,
                requestDTO.model(),
                requestDTO.brand(),
                requestDTO.color(),
                requestDTO.year(),
                requestDTO.observations()
        );

        return vehiclePresenter.present(updateVehicleUseCase.handle(command));
    }

    public void delete(UUID id) {
        deleteVehicleUseCase.handle(id);
    }
}
