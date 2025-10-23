package com.fiapchallenge.garage.adapters.outbound.repositories.user;

import com.fiapchallenge.garage.adapters.outbound.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {
}
