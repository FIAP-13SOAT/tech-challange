package com.fiapchallenge.garage.application.budget;

import com.fiapchallenge.garage.domain.budget.Budget;
import com.fiapchallenge.garage.domain.budget.BudgetItem;
import com.fiapchallenge.garage.domain.budget.BudgetItemType;
import com.fiapchallenge.garage.domain.budget.BudgetRepository;
import com.fiapchallenge.garage.shared.exception.ResourceNotFoundException;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.stock.StockRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GenerateBudgetUseCase {
    private final ServiceOrderRepository serviceOrderRepository;
    private final StockRepository stockRepository;
    private final BudgetRepository budgetRepository;

    public GenerateBudgetUseCase(ServiceOrderRepository serviceOrderRepository, StockRepository stockRepository, BudgetRepository budgetRepository) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.stockRepository = stockRepository;
        this.budgetRepository = budgetRepository;
    }

    public Budget handle(UUID serviceOrderId) {
        ServiceOrder serviceOrder = serviceOrderRepository.findByIdOrThrow(serviceOrderId);
        List<BudgetItem> items = new ArrayList<>();

        serviceOrder.getServiceTypeList().forEach(serviceType -> 
            items.add(new BudgetItem(serviceType.getDescription(), serviceType.getValue(), 1, BudgetItemType.SERVICE)));

        serviceOrder.getStockItems().forEach(item -> {
            var stock = stockRepository.findById(item.getStockId())
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found: " + item.getStockId()));
            items.add(new BudgetItem(stock.getProductName(), stock.getUnitPrice(), item.getQuantity(), BudgetItemType.STOCK_ITEM));
        });

        Budget budget = new Budget(serviceOrderId, items);
        return budgetRepository.save(budget);
    }
}