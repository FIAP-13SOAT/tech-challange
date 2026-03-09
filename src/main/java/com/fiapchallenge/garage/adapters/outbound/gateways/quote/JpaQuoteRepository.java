package com.fiapchallenge.garage.adapters.outbound.gateways.quote;

import com.fiapchallenge.garage.adapters.outbound.entities.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaQuoteRepository extends JpaRepository<QuoteEntity, UUID> {

    Optional<QuoteEntity> findByServiceOrderId(UUID serviceOrderId);
}
