package com.fiapchallenge.garage.application.serviceorder.command;

import java.util.List;
import java.util.UUID;

public record AddServiceTypesCommand(UUID serviceOrderId, List<UUID> serviceTypeIds) {
}