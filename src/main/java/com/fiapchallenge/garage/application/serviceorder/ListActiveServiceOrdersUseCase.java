package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;

import java.util.List;

public interface ListActiveServiceOrdersUseCase {
    List<ServiceOrder> handle();
}