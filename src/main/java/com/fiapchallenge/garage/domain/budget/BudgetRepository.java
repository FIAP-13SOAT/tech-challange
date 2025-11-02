package com.fiapchallenge.garage.domain.budget;

import java.util.UUID;

public interface BudgetRepository {
    Budget save(Budget budget);
    Budget findByServiceOrderIdOrThrow(UUID serviceOrderId);
}