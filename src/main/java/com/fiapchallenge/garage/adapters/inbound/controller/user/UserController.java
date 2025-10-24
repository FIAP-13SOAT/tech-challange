package com.fiapchallenge.garage.adapters.inbound.controller.user;

import com.fiapchallenge.garage.adapters.inbound.controller.user.dto.CreateUserRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.user.dto.LoginUserRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.user.dto.LoginUserResponseDTO;
import com.fiapchallenge.garage.application.user.CreateUserUseCase;
import com.fiapchallenge.garage.application.user.LoginUserUseCase;
import com.fiapchallenge.garage.application.user.command.CreateUserCommand;
import com.fiapchallenge.garage.application.user.command.LoginUserCommand;
import com.fiapchallenge.garage.domain.user.User;
import com.fiapchallenge.garage.infra.JwtTokenVO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController implements UserControllerOpenApiSpec {

    private final CreateUserUseCase createUserUseCase;
    private final LoginUserUseCase loginUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase, LoginUserUseCase loginUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.loginUserUseCase = loginUserUseCase;
    }

    @PostMapping
    @Override
    public ResponseEntity<User> create(@Valid @RequestBody CreateUserRequestDTO createUserDTO) {
        CreateUserCommand command = new CreateUserCommand(
                createUserDTO.fullname(),
                createUserDTO.email(),
                createUserDTO.password()
        );
        return ResponseEntity.ok(createUserUseCase.handle(command));
    }

    @PostMapping("/login")
    @Override
    public ResponseEntity<LoginUserResponseDTO> login(@Valid @RequestBody LoginUserRequestDTO loginUserDTO) {
        LoginUserCommand command = new LoginUserCommand(loginUserDTO.email(), loginUserDTO.password());
        JwtTokenVO tokenVo = loginUserUseCase.handle(command);
        return ResponseEntity.ok(new LoginUserResponseDTO(tokenVo.token(), tokenVo.expiration()));
    }
}
