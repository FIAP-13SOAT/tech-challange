package com.fiapchallenge.garage.application.serviceorder.cancel;

import java.util.UUID;

public record CancelServiceOrderCommand(UUID serviceOrderId) {
}
