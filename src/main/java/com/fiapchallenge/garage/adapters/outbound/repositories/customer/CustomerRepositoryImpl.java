package com.fiapchallenge.garage.adapters.outbound.repositories.customer;

import com.fiapchallenge.garage.adapters.outbound.entities.CustomerEntity;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.customer.CustomerRepository;
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

        return new Customer(
                customerEntity.getId(),
                customerEntity.getName(),
                customerEntity.getEmail(),
                customerEntity.getPhone(),
                customerEntity.getCpfCnpj()
        );
    }

    @Override
    public boolean exists(UUID id) {
        return jpaCustomerRepository.existsById(id);
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return jpaCustomerRepository.findById(id)
                .map(entity -> new Customer(
                        entity.getId(),
                        entity.getName(),
                        entity.getEmail(),
                        entity.getPhone(),
                        entity.getCpfCnpj()
                ));
    }

    @Override
    public List<Customer> findAll() {
        return jpaCustomerRepository.findAll().stream()
                .map(entity -> new Customer(
                        entity.getId(),
                        entity.getName(),
                        entity.getEmail(),
                        entity.getPhone(),
                        entity.getCpfCnpj()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> findByFilters(String name, String email, String cpfCnpj) {
        return jpaCustomerRepository.findByFilters(name, email, cpfCnpj).stream()
                .map(entity -> new Customer(
                        entity.getId(),
                        entity.getName(),
                        entity.getEmail(),
                        entity.getPhone(),
                        entity.getCpfCnpj()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaCustomerRepository.deleteById(id);
    }
}
