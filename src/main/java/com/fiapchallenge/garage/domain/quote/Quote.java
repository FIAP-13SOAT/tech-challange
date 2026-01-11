package com.fiapchallenge.garage.domain.quote;

import com.fiapchallenge.garage.domain.quote.exceptions.CannotApproveQuoteException;
import com.fiapchallenge.garage.domain.quote.exceptions.CannotRejectQuoteException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Quote {

    private UUID id;
    private UUID customerId;
    private UUID serviceOrderId;
    private List<QuoteItem> items;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private QuoteStatus status;

    public Quote(UUID id, UUID customerId, UUID serviceOrderId, List<QuoteItem> items, QuoteStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.serviceOrderId = serviceOrderId;
        this.items = items;
        this.totalAmount = calculateTotal();
        this.createdAt = createdAt;
        this.status = status;
    }

    public Quote(UUID customerId, UUID serviceOrderId, List<QuoteItem> items) {
        this.customerId = customerId;
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

    public void approve() {
        if (this.status != QuoteStatus.PENDING) {
            throw new CannotApproveQuoteException(this.status);
        }
        this.status = QuoteStatus.APPROVED;
    }

    public void reject() {
        if (this.status != QuoteStatus.PENDING) {
            throw new CannotRejectQuoteException(this.status);
        }
        this.status = QuoteStatus.REJECTED;
    }

    public UUID getId() { return id; }
    public UUID getCustomerId() { return customerId; }
    public UUID getServiceOrderId() { return serviceOrderId; }
    public List<QuoteItem> getItems() { return items; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public QuoteStatus getStatus() { return status; }
}
