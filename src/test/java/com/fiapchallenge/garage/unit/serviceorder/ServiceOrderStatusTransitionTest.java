package com.fiapchallenge.garage.unit.serviceorder;

import com.fiapchallenge.garage.domain.customer.CpfCnpj;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ServiceOrderStatusTransitionTest {
    private Customer customer = new Customer(UUID.randomUUID(), "Test Customer", "test@test.com", "12345678901", new CpfCnpj("667.713.590-00"));
    @Test
    @DisplayName("Deve iniciar progresso quando em status AWAITING_EXECUTION")
    void shouldStartProgressWhenInAwaitingExecutionStatus() {
        ServiceOrder serviceOrder = new ServiceOrder(UUID.randomUUID(), "Test", UUID.randomUUID(),
                ServiceOrderStatus.AWAITING_EXECUTION, List.of(), List.of(), this.customer);

        serviceOrder.startProgress();

        assertEquals(ServiceOrderStatus.IN_PROGRESS, serviceOrder.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção ao iniciar progresso de status incorreto")
    void shouldThrowExceptionWhenStartingProgressFromWrongStatus() {
        ServiceOrder serviceOrder = new ServiceOrder(UUID.randomUUID(), "Test", UUID.randomUUID(),
                ServiceOrderStatus.RECEIVED, List.of(), List.of(), this.customer);

        assertThrows(IllegalStateException.class, serviceOrder::startProgress);
    }

    @Test
    @DisplayName("Deve aprovar quando em status AWAITING_APPROVAL")
    void shouldApproveWhenInAwaitingApprovalStatus() {
        ServiceOrder serviceOrder = new ServiceOrder(UUID.randomUUID(), "Test", UUID.randomUUID(),
                ServiceOrderStatus.AWAITING_APPROVAL, List.of(), List.of(), this.customer);

        serviceOrder.approve();

        assertEquals(ServiceOrderStatus.AWAITING_EXECUTION, serviceOrder.getStatus());
    }

    @Test
    @DisplayName("Deve completar quando em status IN_PROGRESS")
    void shouldCompleteWhenInProgressStatus() {
        ServiceOrder serviceOrder = new ServiceOrder(UUID.randomUUID(), "Test", UUID.randomUUID(),
                ServiceOrderStatus.IN_PROGRESS, List.of(), List.of(), this.customer);

        serviceOrder.complete();

        assertEquals(ServiceOrderStatus.COMPLETED, serviceOrder.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção ao completar de status incorreto")
    void shouldThrowExceptionWhenCompletingFromWrongStatus() {
        ServiceOrder serviceOrder = new ServiceOrder(UUID.randomUUID(), "Test", UUID.randomUUID(),
                ServiceOrderStatus.AWAITING_APPROVAL, List.of(), List.of(), this.customer);

        assertThrows(IllegalStateException.class, serviceOrder::complete);
    }

    @Test
    @DisplayName("Deve entregar quando em status COMPLETED")
    void shouldDeliverWhenInCompletedStatus() {
        ServiceOrder serviceOrder = new ServiceOrder(UUID.randomUUID(), "Test", UUID.randomUUID(),
                ServiceOrderStatus.COMPLETED, List.of(), List.of(), this.customer);

        serviceOrder.deliver();

        assertEquals(ServiceOrderStatus.DELIVERED, serviceOrder.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção ao entregar de status incorreto")
    void shouldThrowExceptionWhenDeliveringFromWrongStatus() {
        ServiceOrder serviceOrder = new ServiceOrder(UUID.randomUUID(), "Test", UUID.randomUUID(),
                ServiceOrderStatus.IN_PROGRESS, List.of(), List.of(), this.customer);

        assertThrows(IllegalStateException.class, serviceOrder::deliver);
    }

    @Test
    @DisplayName("Não deve permitir cancelar ordens completadas ou entregues")
    void shouldNotAllowCancellingCompletedOrDeliveredOrders() {
        ServiceOrder completedOrder = new ServiceOrder(UUID.randomUUID(), "Test", UUID.randomUUID(),
                ServiceOrderStatus.COMPLETED, List.of(), List.of(), this.customer);
        ServiceOrder deliveredOrder = new ServiceOrder(UUID.randomUUID(), "Test", UUID.randomUUID(),
                ServiceOrderStatus.DELIVERED, List.of(), List.of(), this.customer);

        assertThrows(IllegalStateException.class, completedOrder::cancel);
        assertThrows(IllegalStateException.class, deliveredOrder::cancel);
    }
}