package com.fiapchallenge.garage.integration.serviceorder;

import com.fiapchallenge.garage.application.customer.create.CreateCustomerService;
import com.fiapchallenge.garage.application.serviceorder.create.CreateServiceOrderService;
import com.fiapchallenge.garage.application.servicetype.create.CreateServiceTypeService;
import com.fiapchallenge.garage.application.vehicle.create.CreateVehicleService;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderGateway;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderStatus;
import com.fiapchallenge.garage.domain.user.UserRole;
import com.fiapchallenge.garage.domain.vehicle.Vehicle;
import com.fiapchallenge.garage.integration.BaseIntegrationTest;
import com.fiapchallenge.garage.integration.fixtures.CustomerFixture;
import com.fiapchallenge.garage.integration.fixtures.ServiceOrderFixture;
import com.fiapchallenge.garage.integration.fixtures.VehicleFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class StartServiceOrderExecutionIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CreateCustomerService createCustomerService;

    @Autowired
    private CreateVehicleService createVehicleService;

    @Autowired
    private CreateServiceOrderService createServiceOrderService;

    @Autowired
    private CreateServiceTypeService createServiceTypeService;

    @Autowired
    private ServiceOrderGateway serviceOrderGateway;

    @Test
    @DisplayName("Deve iniciar execução quando ordem está em AWAITING_EXECUTION")
    void shouldStartExecutionWhenServiceOrderIsAwaitingExecution() throws Exception {
        Customer customer = CustomerFixture.createCustomer(createCustomerService);
        Vehicle vehicle = VehicleFixture.createVehicle(customer.getId(), createVehicleService);
        ServiceOrder serviceOrder = ServiceOrderFixture.createServiceOrder(vehicle.getId(), customer.getId(), createServiceOrderService, createServiceTypeService, serviceOrderGateway);
        
        // Simular aprovação do orçamento
        serviceOrder.startDiagnostic();
        serviceOrder.sendToApproval();
        serviceOrder.approve();
        serviceOrderGateway.save(serviceOrder);

        mockMvc.perform(post("/service-orders/" + serviceOrder.getId() + "/start-execution")
                        .header("Authorization", getAuthTokenForRole(UserRole.MECHANIC)))
                .andExpect(status().isOk());

        ServiceOrder updatedServiceOrder = serviceOrderGateway.findByIdOrThrow(serviceOrder.getId());
        assertEquals(ServiceOrderStatus.IN_PROGRESS, updatedServiceOrder.getStatus());
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar iniciar execução com status incorreto")
    void shouldReturnErrorWhenTryingToStartExecutionWithWrongStatus() throws Exception {
        Customer customer = CustomerFixture.createCustomer(createCustomerService);
        Vehicle vehicle = VehicleFixture.createVehicle(customer.getId(), createVehicleService);
        ServiceOrder serviceOrder = ServiceOrderFixture.createServiceOrder(customer, vehicle.getId(), ServiceOrderStatus.AWAITING_APPROVAL, createServiceTypeService, serviceOrderGateway);

        mockMvc.perform(post("/service-orders/" + serviceOrder.getId() + "/start-execution")
                        .header("Authorization", getAuthTokenForRole(UserRole.MECHANIC)))
                .andExpect(status().isBadRequest());
    }
}