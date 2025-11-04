package com.fiapchallenge.garage.integration;

import com.fiapchallenge.garage.application.user.CreateUserService;
import com.fiapchallenge.garage.application.user.LoginUserService;
import com.fiapchallenge.garage.domain.user.User;
import com.fiapchallenge.garage.integration.fixtures.UserFixture;
import org.junit.jupiter.api.AfterEach;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

    @Autowired
    private CreateUserService createUserService;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public BaseIntegrationTest() {
    }

    public BaseIntegrationTest(CreateUserService createUserService, LoginUserService loginUserService) {
        this.createUserService = createUserService;
        this.loginUserService = loginUserService;
    }

    @AfterEach
    void cleanDatabase() {
        String[] tables = {
            "service_order_execution",
            "service_order_items",
            "service_order_service_type",
            "quote",
            "service_order",
            "vehicle",
            "customer",
            "service_type",
            "\"user\"",
            "stock_movement",
            "stock",
            "notification"
        };

        for (String table : tables) {
            try {
                Integer count = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?" +
                    (table.contains("\"") ? "" : " AND table_schema = 'public'"),
                    Integer.class,
                    table.replace("\"", "")
                );
                if (count != null && count > 0) {
                    jdbcTemplate.execute("TRUNCATE TABLE " + table + " RESTART IDENTITY CASCADE");
                }
            } catch (Exception e) {
                LoggerFactory.getLogger("BaseIntegrationTest").error("Error cleaning database", e);
            }
        }
    }

    protected String getAuthToken() {
        User user = UserFixture.createUser(createUserService);
        return "Bearer " + UserFixture.login(user.getEmail(), loginUserService);
    }
}
