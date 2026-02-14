package com.fiapchallenge.garage.application.serviceorder.track;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.ServiceOrderTrackingDTO;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.vehicle.VehicleGateway;
import com.fiapchallenge.garage.shared.exception.SoatNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.UUID;

@Service
public class TrackServiceOrderService implements TrackServiceOrderUseCase {

    private final ServiceOrderRepository serviceOrderRepository;
    private final VehicleGateway vehicleGateway;
    private final MessageSource messageSource;

    public TrackServiceOrderService(ServiceOrderRepository serviceOrderRepository,
                                  VehicleGateway vehicleGateway,
                                  MessageSource messageSource) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.vehicleGateway = vehicleGateway;
        this.messageSource = messageSource;
    }

    @Override
    public ServiceOrderTrackingDTO handle(UUID serviceOrderId, String cpfCnpj) {
        ServiceOrder serviceOrder = serviceOrderRepository.findByIdOrThrow(serviceOrderId);

        Vehicle vehicle = vehicleGateway.findById(serviceOrder.getVehicleId())
                .orElseThrow(() -> new SoatNotFoundException("Ordem de serviço não encontrada"));

        Customer customer = serviceOrder.getCustomer();
        if (customer == null) {
            throw new SoatNotFoundException("Ordem de serviço não encontrada");
        }

        if (!customer.getCpfCnpjValue().equals(cpfCnpj)) {
            throw new SoatNotFoundException("Ordem de serviço não encontrada");
        }

        String statusMessage = messageSource.getMessage(
                "serviceorder.status." + serviceOrder.getStatus().name(),
                null,
                serviceOrder.getStatus().name(),
                Locale.getDefault()
        );

        return new ServiceOrderTrackingDTO(
                vehicle.getLicensePlate(),
                vehicle.getModel(),
                vehicle.getBrand(),
                statusMessage
        );
    }
}
