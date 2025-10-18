package com.fiapchallenge.garage.application.user;

import com.fiapchallenge.garage.application.user.command.LoginUserCommand;
import com.fiapchallenge.garage.infra.JwtTokenVO;

public interface LoginUserUseCase {

    JwtTokenVO handle(LoginUserCommand command);
}
