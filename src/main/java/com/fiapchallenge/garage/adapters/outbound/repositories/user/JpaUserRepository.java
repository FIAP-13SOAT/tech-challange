package com.fiapchallenge.garage.adapters.outbound.repositories.user;

import com.fiapchallenge.garage.adapters.outbound.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {

    UserEntity findByEmail(String email);
}
