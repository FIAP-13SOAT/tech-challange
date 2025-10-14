package com.fiapchallenge.garage.application.user;

import com.fiapchallenge.garage.application.user.command.CreateUserCommand;
import com.fiapchallenge.garage.domain.user.User;

public interface CreateUserUseCase {

    User handle(CreateUserCommand command);
}
