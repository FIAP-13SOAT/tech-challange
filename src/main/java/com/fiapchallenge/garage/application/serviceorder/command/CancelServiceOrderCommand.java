package com.fiapchallenge.garage.application.serviceorder.command;

import java.util.UUID;

public record CancelServiceOrderCommand(UUID serviceOrderId) {
}