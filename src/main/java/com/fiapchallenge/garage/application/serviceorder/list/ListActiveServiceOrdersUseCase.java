package com.fiapchallenge.garage.application.serviceorder.list;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

import java.util.List;

public interface ListActiveServiceOrdersUseCase {
    List<ServiceOrder> handle();
}
