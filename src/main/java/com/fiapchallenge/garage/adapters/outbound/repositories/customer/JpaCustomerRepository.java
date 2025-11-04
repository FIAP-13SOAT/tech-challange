package com.fiapchallenge.garage.adapters.outbound.repositories.customer;

import com.fiapchallenge.garage.adapters.outbound.entities.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, UUID> {

    @Query("""
           SELECT c FROM CustomerEntity c WHERE
               (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND
               (:email IS NULL OR LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND
               (:cpfCnpj IS NULL OR c.cpfCnpj = :cpfCnpj)
           """)
    Page<CustomerEntity> findByFilters(@Param("name") String name, @Param("email") String email, @Param("cpfCnpj") String cpfCnpj, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CustomerEntity c WHERE c.cpfCnpj = :cpfCnpj")
    boolean existsByCpfCnpj(@Param("cpfCnpj") String cpfCnpj);
}
