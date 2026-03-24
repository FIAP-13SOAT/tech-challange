package com.fiapchallenge.garage.adapters.inbound.rest.user;

import com.fiapchallenge.garage.adapters.inbound.rest.user.dto.CreateUserRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.rest.user.dto.UserDTO;
import com.fiapchallenge.garage.controllers.user.UserController;
import com.fiapchallenge.garage.domain.user.UserGateway;
import com.fiapchallenge.garage.presenters.user.UserPresenter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserResource implements UserResourceOpenApiSpec {

    private final UserController userController;

    public UserResource(
            UserGateway userGateway,
            PasswordEncoder passwordEncoder
    ) {
        this.userController = new UserController(
                userGateway,
                passwordEncoder,
                new UserPresenter()
        );
    }

    @Override
    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody CreateUserRequestDTO createUserDTO) {
        return ResponseEntity.ok(userController.create(createUserDTO));
    }
}
