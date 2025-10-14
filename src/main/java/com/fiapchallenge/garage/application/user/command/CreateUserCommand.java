package com.fiapchallenge.garage.application.user.command;

public record CreateUserCommand(
        String fullname,
        String email,
        String password
) {
}
