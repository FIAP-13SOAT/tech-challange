package com.fiapchallenge.garage.unit.quote;

import com.fiapchallenge.garage.application.quote.ApproveQuoteService;
import com.fiapchallenge.garage.application.quote.RejectQuoteService;
import com.fiapchallenge.garage.domain.customer.CpfCnpj;
import com.fiapchallenge.garage.domain.customer.Customer;
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

    private Customer customer = new Customer(UUID.randomUUID(), "Test Customer", "test@test.com", "12345678901", new CpfCnpj("667.713.590-00"));
    private UUID serviceOrderId = UUID.randomUUID();
    @Test
    void shouldChangeServiceOrderToAwaitingExecutionWhenQuoteIsApproved() {
        Quote quote = new Quote(this.serviceOrderId, this.customer.getId(), List.of());
        ServiceOrder serviceOrder = new ServiceOrder(
            this.serviceOrderId, "Test", UUID.randomUUID(),
            ServiceOrderStatus.AWAITING_APPROVAL, List.of(), List.of(), this.customer
        );

        when(quoteRepository.findByServiceOrderIdOrThrow(serviceOrderId)).thenReturn(quote);
        when(serviceOrderRepository.findByIdOrThrow(serviceOrderId)).thenReturn(serviceOrder);
        when(quoteRepository.save(any(Quote.class))).thenReturn(quote);
        when(serviceOrderRepository.save(any(ServiceOrder.class))).thenReturn(serviceOrder);

        Quote result = approveQuoteService.handle(serviceOrderId);

        assertEquals(QuoteStatus.APPROVED, result.getStatus());
        verify(serviceOrderRepository).save(argThat(so -> so.getStatus() == ServiceOrderStatus.AWAITING_EXECUTION));
    }

    @Test
    void shouldCancelServiceOrderWhenQuoteIsRejected() {
        Quote quote = new Quote(this.serviceOrderId, this.customer.getId(), List.of());
        ServiceOrder serviceOrder = new ServiceOrder(
            this.serviceOrderId, "Test", UUID.randomUUID(),
            ServiceOrderStatus.AWAITING_APPROVAL, List.of(), List.of(), this.customer
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
