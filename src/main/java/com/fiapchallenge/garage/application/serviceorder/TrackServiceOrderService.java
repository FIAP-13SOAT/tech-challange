package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.ServiceOrderTrackingDTO;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.customer.CustomerRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.vehicle.VehicleRepository;
import com.fiapchallenge.garage.shared.exception.SoatNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Service
public class TrackServiceOrderService implements TrackServiceOrderUseCase {

    private final ServiceOrderRepository serviceOrderRepository;
    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;
    private final MessageSource messageSource;

    public TrackServiceOrderService(ServiceOrderRepository serviceOrderRepository,
                                  VehicleRepository vehicleRepository,
                                  CustomerRepository customerRepository,
                                  MessageSource messageSource) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.vehicleRepository = vehicleRepository;
        this.customerRepository = customerRepository;
        this.messageSource = messageSource;
    }

    @Override
    public ServiceOrderTrackingDTO handle(UUID serviceOrderId, String cpfCnpj) {
        ServiceOrder serviceOrder = serviceOrderRepository.findByIdOrThrow(serviceOrderId);

        Vehicle vehicle = vehicleRepository.findById(serviceOrder.getVehicleId())
                .orElseThrow(() -> new SoatNotFoundException("Ordem de serviço não encontrada"));

        Customer customer = customerRepository.findById(serviceOrder.getCustomerId())
                .orElseThrow(() -> new SoatNotFoundException("Ordem de serviço não encontrada"));

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
