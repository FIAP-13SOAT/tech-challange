package com.fiapchallenge.garage.adapters.outbound.repositories.quote;

import com.fiapchallenge.garage.adapters.outbound.entities.QuoteEntity;
import com.fiapchallenge.garage.adapters.outbound.entities.QuoteItemEntity;
import com.fiapchallenge.garage.application.quote.exceptions.QuoteNotFoundException;
import com.fiapchallenge.garage.domain.quote.Quote;
import com.fiapchallenge.garage.domain.quote.QuoteItem;
import com.fiapchallenge.garage.domain.quote.QuoteRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class QuoteRepositoryImpl implements QuoteRepository {

    private final JpaQuoteRepository jpaQuoteRepository;

    public QuoteRepositoryImpl(JpaQuoteRepository jpaQuoteRepository) {
        this.jpaQuoteRepository = jpaQuoteRepository;
    }

    @Override
    public Quote save(Quote quote) {
        QuoteEntity quoteEntity = new QuoteEntity(quote);

        if (quote.getItems() != null) {
            final QuoteEntity finalQuoteEntity = quoteEntity;
            var itemEntities = quote.getItems().stream()
                .map(item -> {
                    QuoteItemEntity itemEntity = new QuoteItemEntity(item);
                    itemEntity.setQuote(finalQuoteEntity);
                    return itemEntity;
                })
                .collect(Collectors.toList());
            quoteEntity.setItems(itemEntities);
        }

        quoteEntity = jpaQuoteRepository.save(quoteEntity);
        return convertFromEntity(quoteEntity);
    }

    @Override
    public Quote findByServiceOrderIdOrThrow(UUID serviceOrderId) {
        return jpaQuoteRepository.findByServiceOrderId(serviceOrderId)
            .map(this::convertFromEntity)
            .orElseThrow(() -> new QuoteNotFoundException(serviceOrderId));
    }

    private Quote convertFromEntity(QuoteEntity entity) {
        var items = entity.getItems() != null ?
            entity.getItems().stream()
                .map(this::convertItemFromEntity)
                .collect(Collectors.toList()) : null;

        return new Quote(
            entity.getId(),
            entity.getCustomerId(),
            entity.getServiceOrderId(),
            items,
            entity.getStatus(),
            entity.getCreatedAt()
        );
    }

    private QuoteItem convertItemFromEntity(QuoteItemEntity entity) {
        return new QuoteItem(
            entity.getDescription(),
            entity.getUnitPrice(),
            entity.getQuantity(),
            entity.getType()
        );
    }
}
