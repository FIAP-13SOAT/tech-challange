package com.fiapchallenge.garage.domain.quote;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Quote {
    private final UUID serviceOrderId;
    private final List<QuoteItem> items;
    private final BigDecimal totalAmount;
    private final LocalDateTime createdAt;
    private QuoteStatus status;

    public Quote(UUID serviceOrderId, List<QuoteItem> items) {
        this.serviceOrderId = serviceOrderId;
        this.items = items;
        this.totalAmount = calculateTotal();
        this.createdAt = LocalDateTime.now();
        this.status = QuoteStatus.PENDING;
    }

    private BigDecimal calculateTotal() {
        return items.stream()
                .map(QuoteItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void approve() { this.status = QuoteStatus.APPROVED; }
    public void reject() { this.status = QuoteStatus.REJECTED; }

    public UUID getServiceOrderId() { return serviceOrderId; }
    public List<QuoteItem> getItems() { return items; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public QuoteStatus getStatus() { return status; }
}
