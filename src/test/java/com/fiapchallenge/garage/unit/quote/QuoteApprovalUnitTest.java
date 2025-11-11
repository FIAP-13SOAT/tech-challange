package com.fiapchallenge.garage.unit.quote;

import com.fiapchallenge.garage.application.quote.ApproveQuoteService;
import com.fiapchallenge.garage.application.quote.RejectQuoteService;
import com.fiapchallenge.garage.application.serviceorder.StartServiceOrderExecutionUseCase;
import com.fiapchallenge.garage.application.serviceorder.command.StartServiceOrderExecutionCommand;
import com.fiapchallenge.garage.domain.quote.Quote;
import com.fiapchallenge.garage.domain.quote.QuoteRepository;
import com.fiapchallenge.garage.domain.quote.QuoteStatus;
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
class QuoteApprovalUnitTest {

    @Mock
    private QuoteRepository quoteRepository;

    @Mock
    private ServiceOrderRepository serviceOrderRepository;

    @InjectMocks
    private ApproveQuoteService approveQuoteService;

    @InjectMocks
    private RejectQuoteService rejectQuoteService;

    @Mock
    private StartServiceOrderExecutionUseCase startServiceOrderExecutionUseCase;

    @Test
    void shouldChangeServiceOrderToInProgressWhenQuoteIsApproved() {
        UUID serviceOrderId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        Quote quote = new Quote(serviceOrderId, customerId, List.of());
        ServiceOrder serviceOrder = new ServiceOrder(
            serviceOrderId, "Test", UUID.randomUUID(), UUID.randomUUID(),
            ServiceOrderStatus.AWAITING_APPROVAL, List.of(), List.of()
        );

        when(quoteRepository.findByServiceOrderIdOrThrow(serviceOrderId)).thenReturn(quote);
        when(serviceOrderRepository.findByIdOrThrow(serviceOrderId)).thenReturn(serviceOrder);
        when(quoteRepository.save(any(Quote.class))).thenReturn(quote);
        when(serviceOrderRepository.save(any(ServiceOrder.class))).thenReturn(serviceOrder);
        when(startServiceOrderExecutionUseCase.handle(any(StartServiceOrderExecutionCommand.class))).thenReturn(serviceOrder);

        Quote result = approveQuoteService.handle(serviceOrderId);

        assertEquals(QuoteStatus.APPROVED, result.getStatus());
        verify(serviceOrderRepository).save(argThat(so -> so.getStatus() == ServiceOrderStatus.IN_PROGRESS));
    }

    @Test
    void shouldCancelServiceOrderWhenQuoteIsRejected() {
        UUID serviceOrderId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        Quote quote = new Quote(serviceOrderId, customerId, List.of());
        ServiceOrder serviceOrder = new ServiceOrder(
            serviceOrderId, "Test", UUID.randomUUID(), UUID.randomUUID(),
            ServiceOrderStatus.AWAITING_APPROVAL, List.of(), List.of()
        );

        when(quoteRepository.findByServiceOrderIdOrThrow(serviceOrderId)).thenReturn(quote);
        when(serviceOrderRepository.findByIdOrThrow(serviceOrderId)).thenReturn(serviceOrder);
        when(quoteRepository.save(any(Quote.class))).thenReturn(quote);
        when(serviceOrderRepository.save(any(ServiceOrder.class))).thenReturn(serviceOrder);

        Quote result = rejectQuoteService.handle(serviceOrderId);

        assertEquals(QuoteStatus.REJECTED, result.getStatus());
        verify(serviceOrderRepository).save(argThat(so -> so.getStatus() == ServiceOrderStatus.CANCELLED));
    }
}
