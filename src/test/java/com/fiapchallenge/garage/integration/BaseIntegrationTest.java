package com.fiapchallenge.garage.integration;

import com.fiapchallenge.garage.application.user.CreateUserService;
import com.fiapchallenge.garage.application.user.LoginUserService;
import com.fiapchallenge.garage.domain.user.User;
import com.fiapchallenge.garage.integration.fixtures.UserFixture;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

    private CreateUserService createUserService;
    private LoginUserService loginUserService;

    public BaseIntegrationTest(CreateUserService createUserService, LoginUserService loginUserService) {
        this.createUserService = createUserService;
        this.loginUserService = loginUserService;
    }

    protected String getAuthToken() {
        User user = UserFixture.createUser(createUserService);
        return "Bearer " + UserFixture.login(user.getEmail(), loginUserService);
    }
}
