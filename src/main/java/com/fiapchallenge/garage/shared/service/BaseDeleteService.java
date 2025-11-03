package com.fiapchallenge.garage.shared.service;

import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class BaseDeleteService {
    
    protected void executeDelete(UUID id, Predicate<UUID> existsCheck, Runnable deleteOperation, Supplier<RuntimeException> exceptionSupplier) {
        if (!existsCheck.test(id)) {
            throw exceptionSupplier.get();
        }
        deleteOperation.run();
    }
}