package com.fiapchallenge.garage.application.serviceorder.command;

import java.util.List;
import java.util.UUID;

public record RemoveServiceTypesCommand(UUID serviceOrderId, List<UUID> serviceTypeIds) {
}