package com.fiapchallenge.garage.application.servicetype.create;

import java.math.BigDecimal;

public record CreateServiceTypeCommand(
        String description,
        BigDecimal value
) {
}
