package com.fiapchallenge.garage.application.user.command;

public record LoginUserCommand(
        String email,
        String password
) {
}
