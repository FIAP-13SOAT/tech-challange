package com.fiapchallenge.garage.shared.metrics;

/**
 * Interface for recording service order metrics.
 * Implementations must never propagate exceptions to the business layer.
 */
public interface ServiceOrderMetrics {

    void incrementCreated(String initialStatus);

    void incrementStatusChange(String fromStatus, String toStatus);

    void recordProcessingDuration(String operation, String status, long durationMs);

    void incrementError(String operation, String exceptionClass);
}
