package com.fiapchallenge.garage.domain.user;

import java.util.Optional;

public interface UserGateway {

    User create(User user);

    Optional<User> findByEmail(String email);
}
