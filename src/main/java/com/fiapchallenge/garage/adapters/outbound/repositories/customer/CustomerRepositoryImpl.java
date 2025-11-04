package com.fiapchallenge.garage.adapters.outbound.repositories.customer;

import com.fiapchallenge.garage.adapters.outbound.entities.CustomerEntity;
import com.fiapchallenge.garage.domain.customer.CpfCnpj;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.customer.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

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
    public boolean existsByCpfCnpj(CpfCnpj cpfCnpj) {
        return jpaCustomerRepository.existsByCpfCnpj(cpfCnpj.getValue());
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return jpaCustomerRepository.findById(id)
            .map(this::convertFromEntity);
    }

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        return jpaCustomerRepository.findAll(pageable).map(this::convertFromEntity);
    }

    @Override
    public Page<Customer> findByFilters(String name, String email, String cpfCnpj, Pageable pageable) {
        return jpaCustomerRepository.findByFilters(name, email, cpfCnpj, pageable).map(this::convertFromEntity);
    }

    @Override
    public void deleteById(UUID id) {
        jpaCustomerRepository.deleteById(id);
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
