package com.fiapchallenge.garage.shared.mapper;

import java.util.function.Function;

public abstract class BaseMapper<D, E> {
    
    public abstract D toDomain(E entity);
    public abstract E toEntity(D domain);
    
    public Function<E, D> toDomainFunction() {
        return this::toDomain;
    }
    
    public Function<D, E> toEntityFunction() {
        return this::toEntity;
    }
}