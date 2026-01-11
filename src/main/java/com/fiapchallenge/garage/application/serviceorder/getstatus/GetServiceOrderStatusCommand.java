package com.fiapchallenge.garage.application.serviceorder.getstatus;

import java.util.UUID;

public record GetServiceOrderStatusCommand(UUID serviceOrderId) {
}