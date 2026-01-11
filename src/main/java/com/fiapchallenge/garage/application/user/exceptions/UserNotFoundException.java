package com.fiapchallenge.garage.application.user.exceptions;

import com.fiapchallenge.garage.shared.exception.SoatNotFoundException;

public class UserNotFoundException extends SoatNotFoundException {
    public UserNotFoundException(String email) {
        super("User not found with email: " + email);
    }
}
