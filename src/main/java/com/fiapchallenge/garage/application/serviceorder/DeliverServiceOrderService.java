package com.fiapchallenge.garage.application.serviceorder;

import com.fiapchallenge.garage.application.serviceorder.command.DeliverServiceOrderCommand;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.shared.service.BaseServiceOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeliverServiceOrderService extends BaseServiceOrderService implements DeliverServiceOrderUseCase {

    public DeliverServiceOrderService(ServiceOrderRepository serviceOrderRepository) {
        super(serviceOrderRepository);
    }

    @Override
    @Transactional
    public ServiceOrder handle(DeliverServiceOrderCommand command) {
        return executeServiceOrderOperationWithDirectFind(command.serviceOrderId(), ServiceOrder::deliver);
    }
}