package com.fiapchallenge.garage.application.serviceorder.command;

import java.util.UUID;

public record StartServiceOrderProgressCommand(UUID serviceOrderId) {
}