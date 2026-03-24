package com.fiapchallenge.garage.controllers.user;

import com.fiapchallenge.garage.adapters.inbound.rest.user.dto.CreateUserRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.rest.user.dto.UserDTO;
import com.fiapchallenge.garage.application.user.CreateUserService;
import com.fiapchallenge.garage.application.user.CreateUserUseCase;
import com.fiapchallenge.garage.application.user.command.CreateUserCommand;
import com.fiapchallenge.garage.domain.user.UserGateway;
import com.fiapchallenge.garage.presenters.user.UserPresenter;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserController {

    private final UserPresenter userPresenter;
    private final CreateUserUseCase createUserUseCase;

    public UserController(
            UserGateway userGateway,
            PasswordEncoder passwordEncoder,
            UserPresenter userPresenter
    ) {
        this.userPresenter = userPresenter;
        this.createUserUseCase = new CreateUserService(userGateway, passwordEncoder);
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
}
