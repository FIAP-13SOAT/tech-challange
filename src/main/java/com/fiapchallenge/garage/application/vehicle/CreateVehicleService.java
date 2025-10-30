package com.fiapchallenge.garage.application.vehicle;

import com.fiapchallenge.garage.domain.customer.CustomerRepository;
import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.vehicle.VehicleRepository;
import com.fiapchallenge.garage.application.vehicle.command.CreateVehicleCommand;
import com.fiapchallenge.garage.shared.exception.SoatValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateVehicleService implements CreateVehicleUseCase {

    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;

    public CreateVehicleService(VehicleRepository vehicleRepository, CustomerRepository customerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Vehicle handle(CreateVehicleCommand command) {
        if (!this.isValidBrazilianLicensePlate(command.licensePlate())) {
            throw new SoatValidationException("Placa informada no formato inválido.");
        }

        if (!customerRepository.exists(command.customerId())) {
            throw new SoatValidationException("Necessário informar um cliente válido.");
        }

        Vehicle vehicle = new Vehicle(
            null,
            command.model(),
            command.brand(),
            command.licensePlate(),
            command.customerId(),
            command.color(),
            command.year(),
            command.observations()
        );

        return vehicleRepository.save(vehicle);
    }

    private boolean isValidBrazilianLicensePlate(String licensePlate) {
        if (licensePlate == null) return false;
        return licensePlate.matches("^[A-Z]{3}\\d{4}$") || licensePlate.matches("^[A-Z]{3}\\d[A-Z]\\d{2}$");
    }
}
