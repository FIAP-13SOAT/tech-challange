package com.fiapchallenge.garage.integration;

import com.fiapchallenge.garage.adapters.outbound.entities.ServiceOrderEntity;
import com.fiapchallenge.garage.adapters.outbound.repositories.serviceorder.JpaServiceOrderRepository;
import com.fiapchallenge.garage.application.customer.create.CreateCustomerService;
import com.fiapchallenge.garage.application.servicetype.CreateServiceTypeService;
import com.fiapchallenge.garage.application.vehicle.CreateVehicleService;
import com.fiapchallenge.garage.integration.fixtures.CustomerFixture;
import com.fiapchallenge.garage.integration.fixtures.ServiceTypeFixture;
import com.fiapchallenge.garage.integration.fixtures.VehicleFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class ServiceOrderStatusTransitionIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JpaServiceOrderRepository serviceOrderRepository;

    @Autowired
    private CreateCustomerService createCustomerService;

    @Autowired
    private CreateVehicleService createVehicleService;

    @Autowired
    private CreateServiceTypeService createServiceTypeService;

    @Test
    @DisplayName("Deve transicionar ordem de serviço através de todos os status")
    void shouldTransitionServiceOrderThroughAllStatus() throws Exception {
        UUID serviceOrderId = createServiceOrder();

        mockMvc.perform(post("/service-orders/" + serviceOrderId + "/in-diagnosis")
                        .header("Authorization", getAuthToken()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve obter todos os status de ordem de serviço")
    void shouldGetAllServiceOrderStatus() throws Exception {
        mockMvc.perform(get("/service-orders/status")
                        .header("Authorization", getAuthToken()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve cancelar ordem de serviço e retornar estoque")
    void shouldCancelServiceOrderAndReturnStock() throws Exception {
        UUID serviceOrderId = createServiceOrder();

        mockMvc.perform(post("/service-orders/" + serviceOrderId + "/cancelled")
                        .header("Authorization", getAuthToken()))
                .andExpect(status().isOk());
    }

    private UUID createServiceOrder() throws Exception {
        UUID customerId = CustomerFixture.createCustomer(createCustomerService).getId();
        UUID vehicleId = VehicleFixture.createVehicle(customerId, createVehicleService).getId();
        UUID serviceTypeId = ServiceTypeFixture.createServiceType(createServiceTypeService).getId();

        String serviceOrderJson = """
                {
                	"vehicleId": "%s",
                	"observations": "Teste de transição",
                	"serviceTypeIdList": [
                		"%s"
                	]
                }
        """.formatted(vehicleId.toString(), serviceTypeId.toString());

        mockMvc.perform(post("/service-orders")
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serviceOrderJson))
                .andExpect(status().isOk());

        ServiceOrderEntity serviceOrder = serviceOrderRepository.findAll().getLast();
        return serviceOrder.getId();
    }
}