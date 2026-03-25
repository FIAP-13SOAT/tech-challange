package com.fiapchallenge.garage.shared.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class DatadogServiceOrderMetrics implements ServiceOrderMetrics {

    private static final Logger log = LoggerFactory.getLogger(DatadogServiceOrderMetrics.class);

    private final MeterRegistry meterRegistry;
    private final AtomicLong activeCount = new AtomicLong(0);

    public DatadogServiceOrderMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        Gauge.builder("garage.service_order.active.count", activeCount, AtomicLong::get)
                .register(meterRegistry);
    }

    @Override
    public void incrementCreated(String initialStatus) {
        try {
            Counter.builder("garage.service_order.created.count")
                    .tag("status", initialStatus)
                    .register(meterRegistry)
                    .increment();
        } catch (Exception e) {
            log.error("Failed to increment service order created counter for status={}", initialStatus, e);
        }
    }

    @Override
    public void incrementStatusChange(String fromStatus, String toStatus) {
        try {
            Counter.builder("garage.service_order.status_change.count")
                    .tag("from_status", fromStatus)
                    .tag("to_status", toStatus)
                    .register(meterRegistry)
                    .increment();
        } catch (Exception e) {
            log.error("Failed to increment status change counter from={} to={}", fromStatus, toStatus, e);
        }
    }

    @Override
    public void recordProcessingDuration(String operation, String status, long durationMs) {
        try {
            Timer.builder("garage.service_order.processing.duration")
                    .tag("operation", operation)
                    .tag("status", status)
                    .register(meterRegistry)
                    .record(durationMs, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("Failed to record processing duration for operation={} status={}", operation, status, e);
        }
    }

    @Override
    public void incrementError(String operation, String exceptionClass) {
        try {
            Counter.builder("garage.service_order.error.count")
                    .tag("operation", operation)
                    .tag("exception_class", exceptionClass)
                    .register(meterRegistry)
                    .increment();
        } catch (Exception e) {
            log.error("Failed to increment error counter for operation={} exception={}", operation, exceptionClass, e);
        }
    }
}
