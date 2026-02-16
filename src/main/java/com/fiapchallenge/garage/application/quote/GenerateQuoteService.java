package com.fiapchallenge.garage.application.quote;

import com.fiapchallenge.garage.application.stock.exceptions.StockItemNotFoundException;
import com.fiapchallenge.garage.application.vehicle.exceptions.VehicleNotFoundException;
import com.fiapchallenge.garage.domain.quote.Quote;
import com.fiapchallenge.garage.domain.quote.QuoteItem;
import com.fiapchallenge.garage.domain.quote.QuoteItemType;
import com.fiapchallenge.garage.domain.quote.QuoteRepository;
import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;
import com.fiapchallenge.garage.domain.stock.StockGateway;
import com.fiapchallenge.garage.domain.vehicle.VehicleGateway;
import com.fiapchallenge.garage.shared.exception.SoatNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GenerateQuoteService implements GenerateQuoteUseCase {

    private final ServiceOrderGateway serviceOrderGateway;
    private final StockGateway stockGateway;
    private final QuoteRepository quoteRepository;
    private final VehicleGateway vehicleGateway;

    public GenerateQuoteService(ServiceOrderGateway serviceOrderGateway, StockGateway stockGateway, QuoteRepository quoteRepository, VehicleGateway vehicleGateway) {
        this.serviceOrderGateway = serviceOrderGateway;
        this.stockGateway = stockGateway;
        this.quoteRepository = quoteRepository;
        this.vehicleGateway = vehicleGateway;
    }

    public Quote handle(UUID serviceOrderId) {
        ServiceOrder serviceOrder = serviceOrderGateway.findById(serviceOrderId).orElseThrow(() -> new SoatNotFoundException("Ordem de serviço não encontrado"));

        Vehicle vehicle = vehicleGateway.findById(serviceOrder.getVehicleId())
            .orElseThrow(() -> new VehicleNotFoundException(serviceOrder.getVehicleId()));

        List<QuoteItem> items = new ArrayList<>();

        serviceOrder.getServiceTypeList().forEach(serviceType ->
            items.add(new QuoteItem(serviceType.getDescription(), serviceType.getValue(), 1, QuoteItemType.SERVICE)));

        serviceOrder.getStockItems().forEach(item -> {
            var stock = stockGateway.findById(item.getStockId())
                .orElseThrow(() -> new StockItemNotFoundException(item.getStockId()));
            items.add(new QuoteItem(stock.getProductName(), stock.getUnitPrice(), item.getQuantity(), QuoteItemType.STOCK_ITEM));
        });

        Quote quote = new Quote(vehicle.getCustomerId(), serviceOrderId, items);
        return quoteRepository.save(quote);
    }
}
