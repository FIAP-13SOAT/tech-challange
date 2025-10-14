package com.fiapchallenge.garage.unit.user.utils.factory;

import com.fiapchallenge.garage.application.user.command.CreateUserCommand;
import com.fiapchallenge.garage.domain.user.User;

import java.util.UUID;

public class UserTestFactory {

    public static final UUID ID = UUID.randomUUID();
    public static final String FULLNAME = "John Doe";
    public static final String EMAIL = "john.doe@gmail.com";
    public static final String PASSWORD = "V9t#qZ3m!Fp8@rL2";
    public static final String ENCRYPTED_PASSWORD = "hashedPassword123";

    public static CreateUserCommand createUserCommand() {
        return new CreateUserCommand(
                FULLNAME,
                EMAIL,
                PASSWORD
        );
    }

    public static User createUser() {
        return new User(
                ID,
                FULLNAME,
                EMAIL,
                ENCRYPTED_PASSWORD
        );
    }
}
