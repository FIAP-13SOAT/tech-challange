package com.fiapchallenge.garage.adapters.outbound.repositories.customer;

import com.fiapchallenge.garage.adapters.outbound.entities.CustomerEntity;
import com.fiapchallenge.garage.domain.customer.CpfCnpj;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.customer.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CustomerRepositoryImpl implements CustomerRepository {

    private final JpaCustomerRepository jpaCustomerRepository;

    public CustomerRepositoryImpl(JpaCustomerRepository jpaCustomerRepository) {
        this.jpaCustomerRepository = jpaCustomerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity customerEntity = new CustomerEntity(customer);

        customerEntity = jpaCustomerRepository.save(customerEntity);

        return convertFromEntity(customerEntity);
    }

    @Override
    public boolean exists(UUID id) {
        return jpaCustomerRepository.existsById(id);
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return jpaCustomerRepository.findById(id)
            .map(this::convertFromEntity);
    }

    @Override
    public List<Customer> findAll() {
        return jpaCustomerRepository.findAll()
            .stream()
            .map(this::convertFromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public List<Customer> findByFilters(String name, String email, String cpfCnpj) {
        return jpaCustomerRepository.findByFilters(name, email, cpfCnpj)
            .stream()
            .map(this::convertFromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        return jpaCustomerRepository.findAll(pageable).map(this::convertFromEntity);
    }

    @Override
    public Page<Customer> findByFilters(String name, String email, String cpfCnpj, Pageable pageable) {
        return jpaCustomerRepository.findByFilters(name, email, cpfCnpj, pageable).map(this::convertFromEntity);
    }

    private Customer convertFromEntity(CustomerEntity entity) {
        return new Customer(
            entity.getId(),
            entity.getName(),
            entity.getEmail(),
            entity.getPhone(),
            new CpfCnpj(entity.getCpfCnpj())
        );
    }
}
