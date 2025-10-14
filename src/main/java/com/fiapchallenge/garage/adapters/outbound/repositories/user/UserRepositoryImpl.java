package com.fiapchallenge.garage.adapters.outbound.repositories.user;

import com.fiapchallenge.garage.adapters.outbound.entities.UserEntity;
import com.fiapchallenge.garage.domain.user.User;
import com.fiapchallenge.garage.domain.user.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryImpl implements UserRepository {

    JpaUserRepository jpaUserRepository;

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

    private User convertFromEntity(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getFullname(),
                userEntity.getEmail(),
                userEntity.getPassword()
        );
    }
}
