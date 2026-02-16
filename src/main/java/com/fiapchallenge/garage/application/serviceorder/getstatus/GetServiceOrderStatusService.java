package com.fiapchallenge.garage.application.serviceorder.getstatus;

import com.fiapchallenge.garage.adapters.inbound.controller.serviceorder.dto.ServiceOrderStatusDTO;
import com.fiapchallenge.garage.application.serviceorder.exceptions.ServiceOrderNotFoundException;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GetServiceOrderStatusService implements GetServiceOrderStatusUseCase {

    private final ServiceOrderGateway serviceOrderGateway;
    private final MessageSource messageSource;

    public GetServiceOrderStatusService(ServiceOrderGateway serviceOrderGateway, MessageSource messageSource) {
        this.serviceOrderGateway = serviceOrderGateway;
        this.messageSource = messageSource;
    }

    @Override
    public ServiceOrderStatusDTO handle(GetServiceOrderStatusCommand command) {
        ServiceOrder serviceOrder = serviceOrderGateway.findById(command.serviceOrderId())
                .orElseThrow(() -> new ServiceOrderNotFoundException(command.serviceOrderId()));

        String statusMessage = messageSource.getMessage(
                "serviceorder.status." + serviceOrder.getStatus().name(),
                null,
                LocaleContextHolder.getLocale()
        );

        return new ServiceOrderStatusDTO(
                serviceOrder.getId(),
                serviceOrder.getStatus(),
                statusMessage
        );
    }
}