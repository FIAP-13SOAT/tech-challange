package com.fiapchallenge.garage.application.serviceorder.deliver;

import java.util.UUID;

public record DeliverServiceOrderCommand(UUID serviceOrderId) {
}
