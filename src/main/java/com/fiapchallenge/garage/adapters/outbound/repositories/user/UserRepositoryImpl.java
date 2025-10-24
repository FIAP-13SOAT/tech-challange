package com.fiapchallenge.garage.adapters.outbound.repositories.user;

import com.fiapchallenge.garage.adapters.outbound.entities.UserEntity;
import com.fiapchallenge.garage.domain.user.User;
import com.fiapchallenge.garage.domain.user.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    public User create(User user) {
        UserEntity userEntity = new UserEntity(
                user.getId(),
                user.getFullname(),
                user.getEmail(),
                user.getPassword()
        );
        userEntity = jpaUserRepository.save(userEntity);
        return convertFromEntity(userEntity);
    }

    public Optional<User> findByEmail(String email) {
        UserEntity userEntity = jpaUserRepository.findByEmail(email);
        if (userEntity == null) {
            return Optional.empty();
        }
        return Optional.of(convertFromEntity(userEntity));
    }

    private User convertFromEntity(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getFullname(),
                userEntity.getEmail(),
                userEntity.getPassword()
        );
    }
}
