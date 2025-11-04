package com.fiapchallenge.garage.adapters.outbound.repositories.quote;

import com.fiapchallenge.garage.domain.quote.Quote;
import com.fiapchallenge.garage.domain.quote.QuoteRepository;
import com.fiapchallenge.garage.shared.exception.ResourceNotFoundException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class QuoteRepositoryImpl implements QuoteRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final QuoteRowMapper quoteRowMapper;

    public QuoteRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate, QuoteRowMapper quoteRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.quoteRowMapper = quoteRowMapper;
    }

    @Override
    public Quote save(Quote quote) {
        String quoteSql = """
            INSERT INTO quote (service_order_id, total_amount, status, created_at)
            VALUES (:serviceOrderId, :totalAmount, :status, :createdAt)
            ON CONFLICT (service_order_id)
            DO UPDATE SET status = :status, total_amount = :totalAmount
            RETURNING id
            """;

        UUID quoteId = jdbcTemplate.queryForObject(quoteSql, Map.of(
            "serviceOrderId", quote.getServiceOrderId(),
            "totalAmount", quote.getTotalAmount(),
            "status", quote.getStatus().name(),
            "createdAt", quote.getCreatedAt()
        ), UUID.class);

        jdbcTemplate.update("DELETE FROM quote_item WHERE quote_id = :quoteId", Map.of("quoteId", quoteId));

        String itemSql = """
            INSERT INTO quote_item (quote_id, description, unit_price, quantity, type)
            VALUES (:quoteId, :description, :unitPrice, :quantity, :type)
            """;

        for (var item : quote.getItems()) {
            jdbcTemplate.update(itemSql, Map.of(
                "quoteId", quoteId,
                "description", item.getDescription(),
                "unitPrice", item.getUnitPrice(),
                "quantity", item.getQuantity(),
                "type", item.getType().name()
            ));
        }

        return quote;
    }

    @Override
    public Quote findByServiceOrderIdOrThrow(UUID serviceOrderId) {
        String sql = "SELECT * FROM quote WHERE service_order_id = :serviceOrderId";

        List<Quote> result = jdbcTemplate.query(sql, Map.of("serviceOrderId", serviceOrderId), quoteRowMapper);
        if (result == null) {
            throw new ResourceNotFoundException("Quote not found for service order: " + serviceOrderId);
        }

        return result.stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Quote not found for service order: " + serviceOrderId));
    }
}
