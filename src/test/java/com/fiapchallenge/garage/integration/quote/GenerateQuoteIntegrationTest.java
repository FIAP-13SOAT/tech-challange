package com.fiapchallenge.garage.integration.quote;

import com.fiapchallenge.garage.application.customer.create.CreateCustomerService;
import com.fiapchallenge.garage.application.serviceorder.CreateServiceOrderService;
import com.fiapchallenge.garage.application.servicetype.CreateServiceTypeService;
import com.fiapchallenge.garage.application.vehicle.CreateVehicleService;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
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

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class GenerateQuoteIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final CreateCustomerService createCustomerService;
    private final CreateVehicleService createVehicleService;
    private final CreateServiceOrderService createServiceOrderService;
    private final CreateServiceTypeService createServiceTypeService;
    private final ServiceOrderRepository serviceOrderRepository;

    @Autowired
    public GenerateQuoteIntegrationTest(MockMvc mockMvc,
                                      CreateCustomerService createCustomerService,
                                      CreateVehicleService createVehicleService,
                                      CreateServiceOrderService createServiceOrderService,
                                      CreateServiceTypeService createServiceTypeService,
                                      ServiceOrderRepository serviceOrderRepository) {
        this.mockMvc = mockMvc;
        this.createCustomerService = createCustomerService;
        this.createVehicleService = createVehicleService;
        this.createServiceOrderService = createServiceOrderService;
        this.createServiceTypeService = createServiceTypeService;
        this.serviceOrderRepository = serviceOrderRepository;
    }

    @Test
    @DisplayName("Deve gerar orçamento para ordem de serviço válida")
    void shouldGenerateQuoteForValidServiceOrder() throws Exception {
        Customer customer = CustomerFixture.createCustomer(createCustomerService);
        Vehicle vehicle = VehicleFixture.createVehicle(customer.getId(), createVehicleService);
        ServiceOrder serviceOrder = ServiceOrderFixture.createServiceOrder(vehicle.getId(), customer.getId(), createServiceOrderService, createServiceTypeService, serviceOrderRepository);

        mockMvc.perform(get("/quotes/service-order/" + serviceOrder.getId())
                .header("Authorization", getAuthToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceOrderId").value(serviceOrder.getId().toString()))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.totalAmount").exists())
                .andExpect(jsonPath("$.createdAt").exists());
    }

    @Test
    @DisplayName("Deve retornar erro 404 para ordem de serviço inexistente")
    void shouldReturnNotFoundForNonExistentServiceOrder() throws Exception {
        mockMvc.perform(get("/quotes/service-order/" + UUID.randomUUID())
                .header("Authorization", getAuthToken()))
                .andExpect(status().isNotFound());
    }
}
