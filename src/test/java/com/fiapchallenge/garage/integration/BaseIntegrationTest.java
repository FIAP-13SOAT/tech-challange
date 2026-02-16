package com.fiapchallenge.garage.integration;

import com.fiapchallenge.garage.application.user.CreateUserService;
import com.fiapchallenge.garage.application.user.LoginUserService;
import com.fiapchallenge.garage.application.user.command.CreateUserCommand;
import com.fiapchallenge.garage.application.vehicle.create.CreateVehicleService;
import com.fiapchallenge.garage.domain.user.User;
import com.fiapchallenge.garage.domain.user.UserRole;
import com.fiapchallenge.garage.integration.config.VehicleTestUseCaseConfig;
import com.fiapchallenge.garage.integration.fixtures.UserFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@ActiveProfiles("test")
@Import(VehicleTestUseCaseConfig.class)
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public abstract class BaseIntegrationTest {

    @Autowired
    private CreateUserService createUserService;
    @Autowired
    private LoginUserService loginUserService;

    public BaseIntegrationTest() {
    }

    public BaseIntegrationTest(CreateUserService createUserService, LoginUserService loginUserService) {
        this.createUserService = createUserService;
        this.loginUserService = loginUserService;
    }

    protected String getAuthToken() {
        return getAuthTokenForRole(UserRole.CLERK);
    }

    protected String getAuthTokenForRole(UserRole role) {
        CreateUserCommand command = new CreateUserCommand(
                "Test User",
                "test" + System.currentTimeMillis() + "@test.com",
                "password123",
                role
        );
        User user = createUserService.handle(command);
        return "Bearer " + UserFixture.login(user.getEmail(), "password123", loginUserService);
    }
}
