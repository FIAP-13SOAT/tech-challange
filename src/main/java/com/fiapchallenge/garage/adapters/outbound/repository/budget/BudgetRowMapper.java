package com.fiapchallenge.garage.adapters.outbound.repository.budget;

import com.fiapchallenge.garage.domain.budget.Budget;
import com.fiapchallenge.garage.domain.budget.BudgetItem;
import com.fiapchallenge.garage.domain.budget.BudgetItemType;
import com.fiapchallenge.garage.domain.budget.BudgetStatus;
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
public class BudgetRowMapper implements ResultSetExtractor<List<Budget>> {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BudgetRowMapper(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Budget> extractData(ResultSet rs) throws SQLException {
        List<Budget> budgets = new ArrayList<>();
        
        while (rs.next()) {
            UUID serviceOrderId = UUID.fromString(rs.getString("service_order_id"));
            BudgetStatus status = BudgetStatus.valueOf(rs.getString("status"));
            List<BudgetItem> items = getBudgetItems(serviceOrderId);
            
            Budget budget = new Budget(serviceOrderId, items);
            if (status == BudgetStatus.APPROVED) budget.approve();
            if (status == BudgetStatus.REJECTED) budget.reject();
            
            budgets.add(budget);
        }
        
        return budgets;
    }

    private List<BudgetItem> getBudgetItems(UUID serviceOrderId) {
        String sql = """
            SELECT bi.* FROM budget_item bi 
            JOIN budget b ON bi.budget_id = b.id 
            WHERE b.service_order_id = :serviceOrderId
            """;
        
        return jdbcTemplate.query(sql, Map.of("serviceOrderId", serviceOrderId), (rs, rowNum) -> 
            new BudgetItem(
                rs.getString("description"),
                rs.getBigDecimal("unit_price"),
                rs.getInt("quantity"),
                BudgetItemType.valueOf(rs.getString("type"))
            )
        );
    }
}