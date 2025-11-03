package com.fiapchallenge.garage.adapters.outbound.repositories.quote;

import com.fiapchallenge.garage.adapters.outbound.entities.CustomerEntity;
import com.fiapchallenge.garage.adapters.outbound.entities.QuoteEntity;
import com.fiapchallenge.garage.adapters.outbound.entities.ServiceOrderEntity;
import com.fiapchallenge.garage.adapters.outbound.repositories.customer.JpaCustomerRepository;
import com.fiapchallenge.garage.adapters.outbound.repositories.serviceorder.JpaServiceOrderRepository;
import com.fiapchallenge.garage.domain.quote.Quote;
import com.fiapchallenge.garage.domain.quote.QuoteRepository;
import com.fiapchallenge.garage.shared.mapper.QuoteMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class QuoteRepositoryImpl implements QuoteRepository {

    private final JpaQuoteRepository jpaQuoteRepository;
    private final JpaServiceOrderRepository jpaServiceOrderRepository;
    private final JpaCustomerRepository jpaCustomerRepository;
    private final QuoteMapper quoteMapper;

    public QuoteRepositoryImpl(JpaQuoteRepository jpaQuoteRepository, JpaServiceOrderRepository jpaServiceOrderRepository, JpaCustomerRepository jpaCustomerRepository, QuoteMapper quoteMapper) {
        this.jpaQuoteRepository = jpaQuoteRepository;
        this.jpaServiceOrderRepository = jpaServiceOrderRepository;
        this.jpaCustomerRepository = jpaCustomerRepository;
        this.quoteMapper = quoteMapper;
    }

    @Override
    public Quote save(Quote quote) {
        ServiceOrderEntity serviceOrderEntity = jpaServiceOrderRepository.getReferenceById(quote.getServiceOrderId());
        CustomerEntity customer = jpaCustomerRepository.getReferenceById(quote.getCustomerId());

        QuoteEntity quoteEntity = new QuoteEntity(quote.getValue(), customer, serviceOrderEntity);
        quoteEntity = jpaQuoteRepository.save(quoteEntity);

        return quoteMapper.toDomain(quoteEntity);
    }

    @Override
    public Optional<Quote> findById(UUID id) {
        return jpaQuoteRepository.findById(id).map(quoteMapper::toDomain);
    }

}
