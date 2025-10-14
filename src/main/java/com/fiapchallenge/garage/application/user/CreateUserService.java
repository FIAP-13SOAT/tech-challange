package com.fiapchallenge.garage.application.user;

import com.fiapchallenge.garage.application.user.command.CreateUserCommand;
import com.fiapchallenge.garage.domain.user.User;
import com.fiapchallenge.garage.domain.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateUserService implements CreateUserUseCase {

    private final UserRepository userRepository;

    public CreateUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User handle(CreateUserCommand command) {
        User user = new User(
                null,
                command.fullname(),
                command.email(),
                command.password()
        );

        return userRepository.create(user);
    }
}
