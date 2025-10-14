package com.fiapchallenge.garage.adapters.inbound.controller.user;

import com.fiapchallenge.garage.adapters.inbound.controller.user.dto.CreateUserRequestDTO;
import com.fiapchallenge.garage.application.user.CreateUserUseCase;
import com.fiapchallenge.garage.application.user.command.CreateUserCommand;
import com.fiapchallenge.garage.domain.user.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody CreateUserRequestDTO createUserDTO) {
        CreateUserCommand command = new CreateUserCommand(
                createUserDTO.fullname(),
                createUserDTO.email(),
                createUserDTO.password()
        );
        return ResponseEntity.ok(createUserUseCase.handle(command));
    }
}
