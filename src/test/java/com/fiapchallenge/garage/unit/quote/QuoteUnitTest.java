package com.fiapchallenge.garage.unit.quote;

import com.fiapchallenge.garage.application.quote.ApproveQuoteService;
import com.fiapchallenge.garage.application.quote.CreateQuoteService;
import com.fiapchallenge.garage.application.quote.command.ApproveQuoteCommand;
import com.fiapchallenge.garage.application.quote.command.CreateQuoteCommand;
import com.fiapchallenge.garage.application.serviceorder.StartServiceOrderUseCase;
import com.fiapchallenge.garage.domain.quote.Quote;
import com.fiapchallenge.garage.domain.quote.QuoteRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderStatus;
import com.fiapchallenge.garage.domain.vehicle.VehicleRepository;
import com.fiapchallenge.garage.unit.quote.util.factory.QuoteTestFactory;
import com.fiapchallenge.garage.unit.serviceorder.util.factory.ServiceOrderTestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuoteUnitTest {

    @InjectMocks
    private CreateQuoteService createQuoteService;

    @Mock
    private QuoteRepository quoteRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private ApproveQuoteService approveQuoteService;

    @Mock
    private StartServiceOrderUseCase approveServiceOrderUseCase;

    @Test
    @DisplayName("Criação de Orçamento")
    void shouldCreateQuote() {
        UUID vehicleId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        Optional<ServiceOrder> mockedServiceOrder = Optional.of(ServiceOrderTestFactory.createServiceOrder(vehicleId, ServiceOrderStatus.AWAITING_APPROVAL));

        when(vehicleRepository.findCustomerIdByVehicleId(vehicleId)).thenReturn(customerId);
        when(quoteRepository.save(any(Quote.class))).thenReturn(QuoteTestFactory.createQuote(mockedServiceOrder.get().getId(), customerId));

        Quote quote = createQuoteService.handle(new CreateQuoteCommand(mockedServiceOrder.get()));

        assertEquals(mockedServiceOrder.get().getId(), quote.getServiceOrderId());
        assertEquals(QuoteTestFactory.VALUE, quote.getValue());
        verify(quoteRepository).save(any(Quote.class));
    }

    @Test
    @DisplayName("Aprovação de Orçamento")
    void shouldApproveQuote() {
        UUID vehicleId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        Optional<ServiceOrder> mockedServiceOrder = Optional.of(ServiceOrderTestFactory.createServiceOrder(vehicleId, ServiceOrderStatus.AWAITING_APPROVAL));

        UUID quoteId = UUID.randomUUID();
        Quote mockedQuote = QuoteTestFactory.createQuote(
                mockedServiceOrder.get().getId(),
                customerId
        );
        when(quoteRepository.findById(quoteId)).thenReturn(Optional.of(mockedQuote));

        Quote quote = approveQuoteService.handle(new ApproveQuoteCommand(quoteId));

        assertEquals(mockedServiceOrder.get().getId(), quote.getServiceOrderId());
        verify(approveServiceOrderUseCase).handle(any());
    }
}
