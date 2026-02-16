package com.fiapchallenge.garage.adapters.outbound.repositories.user;

import com.fiapchallenge.garage.adapters.outbound.entities.UserEntity;
import com.fiapchallenge.garage.domain.user.User;
import com.fiapchallenge.garage.domain.user.UserGateway;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserGatewayImpl implements UserGateway {

    private final JpaUserRepository jpaUserRepository;

    public UserGatewayImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User create(User user) {
        UserEntity userEntity = new UserEntity(
                user.getId(),
                user.getFullname(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
        userEntity = jpaUserRepository.save(userEntity);

        return convertFromEntity(userEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> userEntity = jpaUserRepository.findByEmail(email);

        return userEntity.map(this::convertFromEntity);
    }

    private User convertFromEntity(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getFullname(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRole()
        );
    }
}
