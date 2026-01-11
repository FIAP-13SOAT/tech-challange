package com.fiapchallenge.garage.integration.fixtures;

import com.fiapchallenge.garage.application.servicetype.create.CreateServiceTypeService;
import com.fiapchallenge.garage.application.servicetype.create.CreateServiceTypeCommand;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;

import java.math.BigDecimal;

public class ServiceTypeFixture {

    public static final BigDecimal VALUE = new BigDecimal("150.00");
    public static final String DESCRIPTION = "Troca de óleo";

    public static ServiceType createServiceType(CreateServiceTypeService createServiceTypeService) {
        CreateServiceTypeCommand command = buildCommand();
        return createServiceTypeService.handle(command);
    }

    private static CreateServiceTypeCommand buildCommand() {
        return new CreateServiceTypeCommand(DESCRIPTION, VALUE);
    }
}
