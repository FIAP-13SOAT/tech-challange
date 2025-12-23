package com.fiapchallenge.garage.domain.serviceorder;

public class ServiceOrderTracking {
    private String status;
    private String estimatedCompletion;
    private String currentPhase;
    private String observations;

    public ServiceOrderTracking(String status, String estimatedCompletion, String currentPhase, String observations) {
        this.status = status;
        this.estimatedCompletion = estimatedCompletion;
        this.currentPhase = currentPhase;
        this.observations = observations;
    }

    public String getStatus() {
        return status;
    }

    public String getEstimatedCompletion() {
        return estimatedCompletion;
    }

    public String getCurrentPhase() {
        return currentPhase;
    }

    public String getObservations() {
        return observations;
    }
}