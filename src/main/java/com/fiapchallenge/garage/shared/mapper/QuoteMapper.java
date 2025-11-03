package com.fiapchallenge.garage.shared.mapper;

import com.fiapchallenge.garage.adapters.outbound.entities.QuoteEntity;
import com.fiapchallenge.garage.domain.quote.Quote;
import org.springframework.stereotype.Component;

@Component
public class QuoteMapper extends BaseMapper<Quote, QuoteEntity> {

    @Override
    public Quote toDomain(QuoteEntity entity) {
        return new Quote(
                entity.getId(), 
                entity.getCustomer().getId(), 
                entity.getServiceOrder().getId(), 
                entity.getValue()
        );
    }

    @Override
    public QuoteEntity toEntity(Quote quote) {
        // This method is not used in the current implementation
        // as QuoteRepositoryImpl creates the entity manually with references
        throw new UnsupportedOperationException("Use QuoteRepositoryImpl.save() method instead");
    }
}