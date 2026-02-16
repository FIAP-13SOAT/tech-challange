package com.fiapchallenge.garage.adapters.outbound.repositories.serviceorderexecution;

import com.fiapchallenge.garage.adapters.outbound.entities.ServiceOrderExecutionEntity;
import com.fiapchallenge.garage.domain.serviceorderexecution.ServiceOrderExecution;
import com.fiapchallenge.garage.domain.serviceorderexecution.ServiceOrderExecutionGateway;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ServiceOrderExecutionGatewayImpl implements ServiceOrderExecutionGateway {

    private final JpaServiceOrderExecutionRepository jpaServiceOrderExecutionRepository;

    public ServiceOrderExecutionGatewayImpl(JpaServiceOrderExecutionRepository jpaServiceOrderExecutionRepository) {
        this.jpaServiceOrderExecutionRepository = jpaServiceOrderExecutionRepository;
    }

    @Override
    public void save(ServiceOrderExecution serviceOrderExecution) {
        ServiceOrderExecutionEntity serviceOrderExecutionEntity = new ServiceOrderExecutionEntity(serviceOrderExecution);
        jpaServiceOrderExecutionRepository.save(serviceOrderExecutionEntity);
    }

    @Override
    public Optional<ServiceOrderExecution> findById(UUID id) {
        Optional<ServiceOrderExecutionEntity> serviceOrderExecutionEntity = jpaServiceOrderExecutionRepository.findById(id);
        return serviceOrderExecutionEntity.map(this::convertFromEntity);
    }

    @Override
    public List<ServiceOrderExecution> findByStartDateBetweenOrderByStartDateAsc(
            LocalDateTime startRange,
            LocalDateTime endRange
    ) {
        return jpaServiceOrderExecutionRepository.findByStartDateBetweenOrderByStartDateAsc(startRange, endRange)
                .stream()
                .map(this::convertFromEntity)
                .toList();
    }

    private ServiceOrderExecution convertFromEntity(ServiceOrderExecutionEntity serviceOrderExecutionEntity) {
        return new ServiceOrderExecution(
                serviceOrderExecutionEntity.getServiceOrderId(),
                serviceOrderExecutionEntity.getStartDate(),
                serviceOrderExecutionEntity.getEndDate(),
                serviceOrderExecutionEntity.getExecutionTime()
        );
    }
}
