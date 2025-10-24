package com.fiapchallenge.garage.integration.fixtures;

import com.fiapchallenge.garage.application.user.CreateUserService;
import com.fiapchallenge.garage.application.user.LoginUserService;
import com.fiapchallenge.garage.application.user.command.CreateUserCommand;
import com.fiapchallenge.garage.application.user.command.LoginUserCommand;
import com.fiapchallenge.garage.domain.user.User;

public class UserFixture {

    public static final String PASSWORD = "123";

    public static User createUser(CreateUserService createUserService) {
        CreateUserCommand command = new CreateUserCommand(
                "John Silva",
                "john" + System.currentTimeMillis() + "@outlook.com",
                PASSWORD
        );
        return createUserService.handle(command);
    }

    public static String login(String email, LoginUserService loginUserService) {
        LoginUserCommand command = new LoginUserCommand(email, PASSWORD);
        return loginUserService.handle(command).token();
    }
}
