package com.fiapchallenge.garage.application.serviceorder.removeservicetypes;

import java.util.List;
import java.util.UUID;

public record RemoveServiceTypesCommand(UUID serviceOrderId, List<UUID> serviceTypeIds) {
}
