package com.fiapchallenge.garage.application.budget;

import com.fiapchallenge.garage.domain.budget.Budget;
import com.fiapchallenge.garage.domain.budget.BudgetRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RejectBudgetUseCase {
    private final BudgetRepository budgetRepository;
    private final ServiceOrderRepository serviceOrderRepository;

    public RejectBudgetUseCase(BudgetRepository budgetRepository, ServiceOrderRepository serviceOrderRepository) {
        this.budgetRepository = budgetRepository;
        this.serviceOrderRepository = serviceOrderRepository;
    }

    public Budget handle(UUID serviceOrderId) {
        Budget budget = budgetRepository.findByServiceOrderIdOrThrow(serviceOrderId);
        budget.reject();
        
        var serviceOrder = serviceOrderRepository.findByIdOrThrow(serviceOrderId);
        serviceOrder.cancel();
        serviceOrderRepository.save(serviceOrder);
        
        return budgetRepository.save(budget);
    }
}