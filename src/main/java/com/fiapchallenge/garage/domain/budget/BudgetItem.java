package com.fiapchallenge.garage.domain.budget;

import java.math.BigDecimal;

public class BudgetItem {
    private final String description;
    private final BigDecimal unitPrice;
    private final Integer quantity;
    private final BudgetItemType type;

    public BudgetItem(String description, BigDecimal unitPrice, Integer quantity, BudgetItemType type) {
        this.description = description;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.type = type;
    }

    public BigDecimal getSubtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public String getDescription() { return description; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public Integer getQuantity() { return quantity; }
    public BudgetItemType getType() { return type; }
}