package com.fiapchallenge.garage.adapters.outbound.repositories.quote;

import com.fiapchallenge.garage.adapters.outbound.entities.QuoteEntity;
import com.fiapchallenge.garage.adapters.outbound.entities.ServiceOrderEntity;
import com.fiapchallenge.garage.adapters.outbound.repositories.serviceorder.JpaServiceOrderRepository;
import com.fiapchallenge.garage.domain.quote.Quote;
import com.fiapchallenge.garage.domain.quote.QuoteRepository;
import org.springframework.stereotype.Component;

@Component
public class QuoteRepositoryImpl implements QuoteRepository {

    private final JpaQuoteRepository jpaQuoteRepository;
    private final JpaServiceOrderRepository jpaServiceOrderRepository;

    public QuoteRepositoryImpl(JpaQuoteRepository jpaQuoteRepository, JpaServiceOrderRepository jpaServiceOrderRepository) {
        this.jpaQuoteRepository = jpaQuoteRepository;
        this.jpaServiceOrderRepository = jpaServiceOrderRepository;
    }

    @Override
    public Quote save(Quote quote) {
        ServiceOrderEntity serviceOrderEntity = jpaServiceOrderRepository.getReferenceById(quote.getServiceOrderId());

        QuoteEntity quoteEntity = new QuoteEntity(quote.getValue(), serviceOrderEntity);
        jpaQuoteRepository.save(quoteEntity);

        return convertFromEntity(quoteEntity);
    }

    private Quote convertFromEntity(QuoteEntity entity) {
        return new Quote(entity.getId(), entity.getServiceOrder().getId(), entity.getValue());
    }
}
