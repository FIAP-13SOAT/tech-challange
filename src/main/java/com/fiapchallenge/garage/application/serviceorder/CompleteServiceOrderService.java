package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.CompleteServiceOrderCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.shared.service.BaseServiceOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompleteServiceOrderService extends BaseServiceOrderService implements CompleteServiceOrderUseCase {

    public CompleteServiceOrderService(ServiceOrderRepository serviceOrderRepository) {
        super(serviceOrderRepository);
    }

    @Override
    @Transactional
    public ServiceOrder handle(CompleteServiceOrderCommand command) {
        return executeServiceOrderOperationWithDirectFind(command.serviceOrderId(), ServiceOrder::complete);
    }
}