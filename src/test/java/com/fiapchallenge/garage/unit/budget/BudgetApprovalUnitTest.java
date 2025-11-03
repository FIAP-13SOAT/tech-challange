package com.fiapchallenge.garage.unit.budget;

import com.fiapchallenge.garage.application.budget.ApproveBudgetUseCase;
import com.fiapchallenge.garage.application.budget.RejectBudgetUseCase;
import com.fiapchallenge.garage.domain.budget.Budget;
import com.fiapchallenge.garage.domain.budget.BudgetRepository;
import com.fiapchallenge.garage.domain.budget.BudgetStatus;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BudgetApprovalUnitTest {

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private ServiceOrderRepository serviceOrderRepository;

    @InjectMocks
    private ApproveBudgetUseCase approveBudgetUseCase;

    @InjectMocks
    private RejectBudgetUseCase rejectBudgetUseCase;

    @Test
    void shouldChangeServiceOrderToInProgressWhenBudgetIsApproved() {
        UUID serviceOrderId = UUID.randomUUID();
        Budget budget = new Budget(serviceOrderId, List.of());
        ServiceOrder serviceOrder = new ServiceOrder(
            serviceOrderId, "Test", UUID.randomUUID(), 
            ServiceOrderStatus.AWAITING_APPROVAL, List.of(), List.of()
        );

        when(budgetRepository.findByServiceOrderIdOrThrow(serviceOrderId)).thenReturn(budget);
        when(serviceOrderRepository.findByIdOrThrow(serviceOrderId)).thenReturn(serviceOrder);
        when(budgetRepository.save(any(Budget.class))).thenReturn(budget);
        when(serviceOrderRepository.save(any(ServiceOrder.class))).thenReturn(serviceOrder);

        Budget result = approveBudgetUseCase.handle(serviceOrderId);

        assertEquals(BudgetStatus.APPROVED, result.getStatus());
        verify(serviceOrderRepository).save(argThat(so -> so.getStatus() == ServiceOrderStatus.IN_PROGRESS));
    }

    @Test
    void shouldCancelServiceOrderWhenBudgetIsRejected() {
        UUID serviceOrderId = UUID.randomUUID();
        Budget budget = new Budget(serviceOrderId, List.of());
        ServiceOrder serviceOrder = new ServiceOrder(
            serviceOrderId, "Test", UUID.randomUUID(), 
            ServiceOrderStatus.AWAITING_APPROVAL, List.of(), List.of()
        );

        when(budgetRepository.findByServiceOrderIdOrThrow(serviceOrderId)).thenReturn(budget);
        when(serviceOrderRepository.findByIdOrThrow(serviceOrderId)).thenReturn(serviceOrder);
        when(budgetRepository.save(any(Budget.class))).thenReturn(budget);
        when(serviceOrderRepository.save(any(ServiceOrder.class))).thenReturn(serviceOrder);

        Budget result = rejectBudgetUseCase.handle(serviceOrderId);

        assertEquals(BudgetStatus.REJECTED, result.getStatus());
        verify(serviceOrderRepository).save(argThat(so -> so.getStatus() == ServiceOrderStatus.CANCELLED));
    }
}