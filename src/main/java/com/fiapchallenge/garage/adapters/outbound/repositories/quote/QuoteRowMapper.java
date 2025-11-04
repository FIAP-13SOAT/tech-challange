package com.fiapchallenge.garage.adapters.outbound.repositories.quote;

import com.fiapchallenge.garage.domain.quote.Quote;
import com.fiapchallenge.garage.domain.quote.QuoteItem;
import com.fiapchallenge.garage.domain.quote.QuoteItemType;
import com.fiapchallenge.garage.domain.quote.QuoteStatus;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class QuoteRowMapper implements ResultSetExtractor<List<Quote>> {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public QuoteRowMapper(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Quote> extractData(ResultSet rs) throws SQLException {
        List<Quote> quotes = new ArrayList<>();

        while (rs.next()) {
            UUID serviceOrderId = UUID.fromString(rs.getString("service_order_id"));
            QuoteStatus status = QuoteStatus.valueOf(rs.getString("status"));
            List<QuoteItem> items = getQuoteItems(serviceOrderId);

            Quote quote = new Quote(serviceOrderId, items);
            if (status == QuoteStatus.APPROVED) quote.approve();
            if (status == QuoteStatus.REJECTED) quote.reject();

            quotes.add(quote);
        }

        return quotes;
    }

    private List<QuoteItem> getQuoteItems(UUID serviceOrderId) {
        String sql = """
            SELECT bi.* FROM quote_item bi
            JOIN quote b ON bi.quote_id = b.id
            WHERE b.service_order_id = :serviceOrderId
            """;

        return jdbcTemplate.query(sql, Map.of("serviceOrderId", serviceOrderId), (rs, rowNum) ->
            new QuoteItem(
                rs.getString("description"),
                rs.getBigDecimal("unit_price"),
                rs.getInt("quantity"),
                QuoteItemType.valueOf(rs.getString("type"))
            )
        );
    }
}
