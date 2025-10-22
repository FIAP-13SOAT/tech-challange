package com.fiapchallenge.garage.adapters.outbound.repositories.quote;

import com.fiapchallenge.garage.adapters.outbound.entities.CustomerEntity;
import com.fiapchallenge.garage.adapters.outbound.entities.QuoteEntity;
import com.fiapchallenge.garage.adapters.outbound.entities.ServiceOrderEntity;
import com.fiapchallenge.garage.adapters.outbound.repositories.customer.JpaCustomerRepository;
import com.fiapchallenge.garage.adapters.outbound.repositories.serviceorder.JpaServiceOrderRepository;
import com.fiapchallenge.garage.domain.quote.Quote;
import com.fiapchallenge.garage.domain.quote.QuoteRepository;
import org.springframework.stereotype.Component;

@Component
public class QuoteRepositoryImpl implements QuoteRepository {

    private final JpaQuoteRepository jpaQuoteRepository;
    private final JpaServiceOrderRepository jpaServiceOrderRepository;
    private final JpaCustomerRepository jpaCustomerRepository;

    public QuoteRepositoryImpl(JpaQuoteRepository jpaQuoteRepository, JpaServiceOrderRepository jpaServiceOrderRepository, JpaCustomerRepository jpaCustomerRepository) {
        this.jpaQuoteRepository = jpaQuoteRepository;
        this.jpaServiceOrderRepository = jpaServiceOrderRepository;
        this.jpaCustomerRepository = jpaCustomerRepository;
    }

    @Override
    public Quote save(Quote quote) {
        ServiceOrderEntity serviceOrderEntity = jpaServiceOrderRepository.getReferenceById(quote.getServiceOrderId());
        CustomerEntity customer = jpaCustomerRepository.getReferenceById(quote.getCustomerId());

        QuoteEntity quoteEntity = new QuoteEntity(quote.getValue(), customer, serviceOrderEntity);
        jpaQuoteRepository.save(quoteEntity);

        return convertFromEntity(quoteEntity);
    }

    private Quote convertFromEntity(QuoteEntity entity) {
        return new Quote(entity.getId(), entity.getCustomer().getId(), entity.getServiceOrder().getId(), entity.getValue());
    }
}
