package com.fiapchallenge.garage.domain.user.exceptions;

public abstract class UserDomainException extends RuntimeException {
    protected UserDomainException(String message) {
        super(message);
    }
}
