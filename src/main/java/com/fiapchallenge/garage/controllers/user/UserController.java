package com.fiapchallenge.garage.controllers.user;

import com.fiapchallenge.garage.adapters.inbound.rest.user.dto.CreateUserRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.rest.user.dto.LoginUserRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.rest.user.dto.LoginUserResponseDTO;
import com.fiapchallenge.garage.adapters.inbound.rest.user.dto.UserDTO;
import com.fiapchallenge.garage.application.user.CreateUserService;
import com.fiapchallenge.garage.application.user.CreateUserUseCase;
import com.fiapchallenge.garage.application.user.LoginUserService;
import com.fiapchallenge.garage.application.user.LoginUserUseCase;
import com.fiapchallenge.garage.application.user.command.CreateUserCommand;
import com.fiapchallenge.garage.application.user.command.LoginUserCommand;
import com.fiapchallenge.garage.domain.user.UserGateway;
import com.fiapchallenge.garage.infra.JwtHelper;
import com.fiapchallenge.garage.presenters.user.UserPresenter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserController {

    private final UserPresenter userPresenter;
    private final CreateUserUseCase createUserUseCase;
    private final LoginUserUseCase loginUserUseCase;

    public UserController(
            UserGateway userGateway,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtHelper jwtHelper,
            UserPresenter userPresenter
    ) {
        this.userPresenter = userPresenter;
        this.createUserUseCase = new CreateUserService(userGateway, passwordEncoder);
        this.loginUserUseCase = new LoginUserService(authenticationManager, jwtHelper);
    }

    public UserDTO create(CreateUserRequestDTO createUserDTO) {
        CreateUserCommand command = new CreateUserCommand(
                createUserDTO.fullname(),
                createUserDTO.email(),
                createUserDTO.password(),
                createUserDTO.role()
        );

        return userPresenter.present(createUserUseCase.handle(command));
    }

    public LoginUserResponseDTO login(LoginUserRequestDTO loginUserDTO) {
        LoginUserCommand command = new LoginUserCommand(loginUserDTO.email(), loginUserDTO.password());

        return userPresenter.present(loginUserUseCase.handle(command));
    }
}
