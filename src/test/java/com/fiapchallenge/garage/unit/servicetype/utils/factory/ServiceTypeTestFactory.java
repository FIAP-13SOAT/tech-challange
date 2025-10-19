package com.fiapchallenge.garage.unit.servicetype.utils.factory;

import com.fiapchallenge.garage.application.servicetype.command.CreateServiceTypeCommand;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;

import java.math.BigDecimal;
import java.util.UUID;

public class ServiceTypeTestFactory {

    public static final UUID ID = UUID.randomUUID();
    public static final BigDecimal VALUE = new BigDecimal("150.00");
    public static final String DESCRIPTION = "Trcola de óleo";

    public static ServiceType build() {
        return new ServiceType(ID, VALUE, DESCRIPTION);
    }

    public static CreateServiceTypeCommand buildCommand() {
        return new CreateServiceTypeCommand(DESCRIPTION, VALUE);
    }
}
