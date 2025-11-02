package com.fiapchallenge.garage.domain.budget;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Budget {
    private final UUID serviceOrderId;
    private final List<BudgetItem> items;
    private final BigDecimal totalAmount;
    private final LocalDateTime createdAt;
    private BudgetStatus status;

    public Budget(UUID serviceOrderId, List<BudgetItem> items) {
        this.serviceOrderId = serviceOrderId;
        this.items = items;
        this.totalAmount = calculateTotal();
        this.createdAt = LocalDateTime.now();
        this.status = BudgetStatus.PENDING;
    }

    private BigDecimal calculateTotal() {
        return items.stream()
                .map(BudgetItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void approve() { this.status = BudgetStatus.APPROVED; }
    public void reject() { this.status = BudgetStatus.REJECTED; }

    public UUID getServiceOrderId() { return serviceOrderId; }
    public List<BudgetItem> getItems() { return items; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public BudgetStatus getStatus() { return status; }
}