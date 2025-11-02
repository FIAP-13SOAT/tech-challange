package com.fiapchallenge.garage.unit.serviceorder;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderItem;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderStatus;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ServiceOrderManagementUnitTest {

    private ServiceOrder serviceOrder;
    private UUID vehicleId;

    @BeforeEach
    void setUp() {
        vehicleId = UUID.randomUUID();
        serviceOrder = new ServiceOrder(
                UUID.randomUUID(),
                "Teste",
                vehicleId,
                ServiceOrderStatus.CREATED,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    @Test
    @DisplayName("Deve adicionar itens de estoque à ordem")
    void shouldAddStockItemsToOrder() {
        List<ServiceOrderItem> items = List.of(
                new ServiceOrderItem(UUID.randomUUID(), UUID.randomUUID(), 2),
                new ServiceOrderItem(UUID.randomUUID(), UUID.randomUUID(), 3)
        );

        serviceOrder.addStockItems(items);

        assertThat(serviceOrder.getStockItems()).hasSize(2);
        assertThat(serviceOrder.getStockItems()).containsAll(items);
    }

    @Test
    @DisplayName("Deve remover itens de estoque da ordem")
    void shouldRemoveStockItemsFromOrder() {
        ServiceOrderItem item1 = new ServiceOrderItem(UUID.randomUUID(), UUID.randomUUID(), 2);
        ServiceOrderItem item2 = new ServiceOrderItem(UUID.randomUUID(), UUID.randomUUID(), 3);
        
        serviceOrder.addStockItems(List.of(item1, item2));
        serviceOrder.removeStockItems(List.of(item1));

        assertThat(serviceOrder.getStockItems()).hasSize(1);
        assertThat(serviceOrder.getStockItems()).contains(item2);
        assertThat(serviceOrder.getStockItems()).doesNotContain(item1);
    }

    @Test
    @DisplayName("Deve adicionar tipos de serviço à ordem")
    void shouldAddServiceTypesToOrder() {
        List<ServiceType> services = List.of(
                new ServiceType(UUID.randomUUID(), BigDecimal.valueOf(50.0), "Serviço 1"),
                new ServiceType(UUID.randomUUID(), BigDecimal.valueOf(75.0), "Serviço 2")
        );

        serviceOrder.addServiceTypes(services);

        assertThat(serviceOrder.getServiceTypeList()).hasSize(2);
        assertThat(serviceOrder.getServiceTypeList()).containsAll(services);
    }

    @Test
    @DisplayName("Deve remover tipos de serviço da ordem")
    void shouldRemoveServiceTypesFromOrder() {
        ServiceType service1 = new ServiceType(UUID.randomUUID(), BigDecimal.valueOf(50.0), "Serviço 1");
        ServiceType service2 = new ServiceType(UUID.randomUUID(), BigDecimal.valueOf(75.0), "Serviço 2");
        
        serviceOrder.addServiceTypes(List.of(service1, service2));
        serviceOrder.removeServiceTypes(List.of(service1));

        assertThat(serviceOrder.getServiceTypeList()).hasSize(1);
        assertThat(serviceOrder.getServiceTypeList()).contains(service2);
        assertThat(serviceOrder.getServiceTypeList()).doesNotContain(service1);
    }

    @Test
    @DisplayName("Deve inicializar listas quando nulas ao adicionar itens")
    void shouldInitializeListsWhenNullOnAdd() {
        ServiceOrder orderWithNullLists = new ServiceOrder(
                UUID.randomUUID(),
                "Teste",
                vehicleId,
                ServiceOrderStatus.CREATED,
                null,
                null
        );

        ServiceOrderItem item = new ServiceOrderItem(UUID.randomUUID(), UUID.randomUUID(), 1);
        ServiceType service = new ServiceType(UUID.randomUUID(), BigDecimal.valueOf(25.0), "Serviço");

        orderWithNullLists.addStockItems(List.of(item));
        orderWithNullLists.addServiceTypes(List.of(service));

        assertThat(orderWithNullLists.getStockItems()).isNotNull().hasSize(1);
        assertThat(orderWithNullLists.getServiceTypeList()).isNotNull().hasSize(1);
    }

    @Test
    @DisplayName("Deve lidar com listas nulas ao remover itens")
    void shouldHandleNullListsOnRemove() {
        ServiceOrder orderWithNullLists = new ServiceOrder(
                UUID.randomUUID(),
                "Teste",
                vehicleId,
                ServiceOrderStatus.CREATED,
                null,
                null
        );

        ServiceOrderItem item = new ServiceOrderItem(UUID.randomUUID(), UUID.randomUUID(), 1);
        ServiceType service = new ServiceType(UUID.randomUUID(), BigDecimal.valueOf(25.0), "Serviço");

        orderWithNullLists.removeStockItems(List.of(item));
        orderWithNullLists.removeServiceTypes(List.of(service));

        assertThat(orderWithNullLists.getStockItems()).isNull();
        assertThat(orderWithNullLists.getServiceTypeList()).isNull();
    }
}