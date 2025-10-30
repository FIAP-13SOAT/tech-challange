package com.fiapchallenge.garage.adapters.outbound.repositories.serviceorderexecution;

import com.fiapchallenge.garage.adapters.outbound.entities.ServiceOrderExecutionEntity;
import com.fiapchallenge.garage.domain.serviceorderexecution.ServiceOrderExecution;
import com.fiapchallenge.garage.domain.serviceorderexecution.ServiceOrderExecutionRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ServiceOrderExecutionRepositoryImpl implements ServiceOrderExecutionRepository {

    private final JpaServiceOrderExecutionRepository jpaServiceOrderExecutionRepository;

    public ServiceOrderExecutionRepositoryImpl(JpaServiceOrderExecutionRepository jpaServiceOrderExecutionRepository) {
        this.jpaServiceOrderExecutionRepository = jpaServiceOrderExecutionRepository;
    }

    public void save(ServiceOrderExecution serviceOrderExcecution) {
        ServiceOrderExecutionEntity serviceOrderExecutionEntity = new ServiceOrderExecutionEntity(serviceOrderExcecution);
        jpaServiceOrderExecutionRepository.save(serviceOrderExecutionEntity);
    }

    @Override
    public Optional<ServiceOrderExecution> findById(UUID id) {
        Optional<ServiceOrderExecutionEntity> serviceOrderExecutionEntity = jpaServiceOrderExecutionRepository.findById(id);
        return serviceOrderExecutionEntity.map(this::convertFromEntity);
    }

    public List<ServiceOrderExecution> findByStartDateBetweenOrderByStartDateAsc(
            LocalDateTime startRange,
            LocalDateTime endRange
    ){
        return jpaServiceOrderExecutionRepository.findByStartDateBetweenOrderByStartDateAsc(startRange, endRange).stream().map(this::convertFromEntity).collect(Collectors.toList());
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
