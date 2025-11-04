package com.fiapchallenge.garage.domain.quote;

import java.math.BigDecimal;

public class QuoteItem {
    private final String description;
    private final BigDecimal unitPrice;
    private final Integer quantity;
    private final QuoteItemType type;

    public QuoteItem(String description, BigDecimal unitPrice, Integer quantity, QuoteItemType type) {
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
    public QuoteItemType getType() { return type; }
}
