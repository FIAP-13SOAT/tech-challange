package com.fiapchallenge.garage.adapters.outbound.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "service_order_items")
public class ServiceOrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "service_order_id", nullable = false)
    private UUID serviceOrderId;

    @Column(name = "stock_id", nullable = false)
    private UUID stockId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public ServiceOrderItemEntity() {}

    public ServiceOrderItemEntity(UUID serviceOrderId, UUID stockId, Integer quantity) {
        this.serviceOrderId = serviceOrderId;
        this.stockId = stockId;
        this.quantity = quantity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getServiceOrderId() {
        return serviceOrderId;
    }

    public void setServiceOrderId(UUID serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public UUID getStockId() {
        return stockId;
    }

    public void setStockId(UUID stockId) {
        this.stockId = stockId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}