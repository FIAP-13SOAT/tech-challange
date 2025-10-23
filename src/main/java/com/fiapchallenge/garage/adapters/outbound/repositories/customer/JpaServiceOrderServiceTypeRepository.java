package com.fiapchallenge.garage.adapters.outbound.repositories.customer;

import com.fiapchallenge.garage.adapters.outbound.entities.ServiceOrderServiceTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaServiceOrderServiceTypeRepository extends JpaRepository<ServiceOrderServiceTypeEntity, UUID> {
}
