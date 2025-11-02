package com.fiapchallenge.garage.adapters.outbound.repository.budget;

import com.fiapchallenge.garage.domain.budget.Budget;
import com.fiapchallenge.garage.domain.budget.BudgetRepository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
public class BudgetRepositoryImpl implements BudgetRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final BudgetRowMapper budgetRowMapper;

    public BudgetRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate, BudgetRowMapper budgetRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.budgetRowMapper = budgetRowMapper;
    }

    @Override
    public Budget save(Budget budget) {
        String budgetSql = """
            INSERT INTO budget (service_order_id, total_amount, status, created_at)
            VALUES (:serviceOrderId, :totalAmount, :status, :createdAt)
            ON CONFLICT (service_order_id) 
            DO UPDATE SET status = :status, total_amount = :totalAmount
            RETURNING id
            """;
        
        UUID budgetId = jdbcTemplate.queryForObject(budgetSql, Map.of(
            "serviceOrderId", budget.getServiceOrderId(),
            "totalAmount", budget.getTotalAmount(),
            "status", budget.getStatus().name(),
            "createdAt", budget.getCreatedAt()
        ), UUID.class);
        
        jdbcTemplate.update("DELETE FROM budget_item WHERE budget_id = :budgetId", Map.of("budgetId", budgetId));
        
        String itemSql = """
            INSERT INTO budget_item (budget_id, description, unit_price, quantity, type)
            VALUES (:budgetId, :description, :unitPrice, :quantity, :type)
            """;
        
        for (var item : budget.getItems()) {
            jdbcTemplate.update(itemSql, Map.of(
                "budgetId", budgetId,
                "description", item.getDescription(),
                "unitPrice", item.getUnitPrice(),
                "quantity", item.getQuantity(),
                "type", item.getType().name()
            ));
        }
        
        return budget;
    }

    @Override
    public Budget findByServiceOrderIdOrThrow(UUID serviceOrderId) {
        String sql = "SELECT * FROM budget WHERE service_order_id = :serviceOrderId";
        
        return jdbcTemplate.query(sql, Map.of("serviceOrderId", serviceOrderId), budgetRowMapper)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Budget not found for service order: " + serviceOrderId));
    }
}