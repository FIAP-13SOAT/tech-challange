package com.fiapchallenge.garage.application.quote;

import com.fiapchallenge.garage.application.quote.command.CreateQuoteCommand;
import com.fiapchallenge.garage.domain.quote.Quote;
import com.fiapchallenge.garage.domain.quote.QuoteRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.vehicle.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Transactional
public class CreateQuoteService implements CreateQuoteUseCase {

    private final QuoteRepository quoteRepository;
    private final VehicleRepository vehicleRepository;

    public CreateQuoteService(QuoteRepository quoteRepository, VehicleRepository vehicleRepository) {
        this.quoteRepository  = quoteRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Quote handle(CreateQuoteCommand command) {
        ServiceOrder serviceOrder = command.serviceOrder();
        UUID customerId = vehicleRepository.findCustomerIdByVehicleId(serviceOrder.getVehicleId());
        Quote quote = new Quote(null, customerId, serviceOrder.getId(), calculateValue(serviceOrder));
        return quoteRepository.save(quote);
    }

    private BigDecimal calculateValue(ServiceOrder serviceOrder) {
        BigDecimal value = BigDecimal.ZERO;
        for (var serviceType : serviceOrder.getServiceTypeList()) {
            value = value.add(serviceType.getValue());
        }

        return value;
    }
}
