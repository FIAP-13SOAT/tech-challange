package com.fiapchallenge.garage.application.user;

import com.fiapchallenge.garage.application.user.command.CreateUserCommand;
import com.fiapchallenge.garage.domain.user.User;
import com.fiapchallenge.garage.domain.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateUserService implements CreateUserUseCase {

    private final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;

    public CreateUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User handle(CreateUserCommand command) {
        String encryptedPassword = passwordEncoder.encode(command.password());

        User user = new User(
                null,
                command.fullname(),
                command.email(),
                encryptedPassword
        );

        return userRepository.create(user);
    }
}
