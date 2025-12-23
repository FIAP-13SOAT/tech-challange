package com.fiapchallenge.garage.adapters.inbound.controller.user;

import com.fiapchallenge.garage.adapters.inbound.controller.user.dto.CreateUserRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.user.dto.LoginUserRequestDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.user.dto.LoginUserResponseDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.user.dto.UserDTO;
import com.fiapchallenge.garage.adapters.inbound.controller.user.mapper.UserMapper;
import com.fiapchallenge.garage.application.user.CreateUserUseCase;
import com.fiapchallenge.garage.application.user.LoginUserUseCase;
import com.fiapchallenge.garage.application.user.command.CreateUserCommand;
import com.fiapchallenge.garage.application.user.command.LoginUserCommand;
import com.fiapchallenge.garage.domain.user.User;
import com.fiapchallenge.garage.infra.JwtTokenVO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Override
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> create(@Valid @RequestBody CreateUserRequestDTO createUserDTO) {
        CreateUserCommand command = new CreateUserCommand(
                createUserDTO.fullname(),
                createUserDTO.email(),
                createUserDTO.password(),
                createUserDTO.role()
        );

        User user = createUserUseCase.handle(command);
        return ResponseEntity.ok(UserMapper.toResponseDTO(user));
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDTO> login(@Valid @RequestBody LoginUserRequestDTO loginUserDTO) {
        LoginUserCommand command = new LoginUserCommand(loginUserDTO.email(), loginUserDTO.password());
        JwtTokenVO tokenVo = loginUserUseCase.handle(command);
        return ResponseEntity.ok(new LoginUserResponseDTO(tokenVo.token(), tokenVo.expiration()));
    }
}
