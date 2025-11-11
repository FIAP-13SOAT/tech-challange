package com.fiapchallenge.garage.integration.quote;

import com.fiapchallenge.garage.application.customer.create.CreateCustomerService;
import com.fiapchallenge.garage.application.quote.GenerateQuoteService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class ApproveQuoteIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final CreateCustomerService createCustomerService;
    private final CreateVehicleService createVehicleService;
    private final CreateServiceOrderService createServiceOrderService;
    private final CreateServiceTypeService createServiceTypeService;
    private final GenerateQuoteService generateQuoteService;
    private final ServiceOrderRepository serviceOrderRepository;

    @Autowired
    public ApproveQuoteIntegrationTest(MockMvc mockMvc,
                                     CreateCustomerService createCustomerService,
                                     CreateVehicleService createVehicleService,
                                     CreateServiceOrderService createServiceOrderService,
                                     CreateServiceTypeService createServiceTypeService,
                                     GenerateQuoteService generateQuoteService,
                                     ServiceOrderRepository serviceOrderRepository) {
        this.mockMvc = mockMvc;
        this.createCustomerService = createCustomerService;
        this.createVehicleService = createVehicleService;
        this.createServiceOrderService = createServiceOrderService;
        this.createServiceTypeService = createServiceTypeService;
        this.generateQuoteService = generateQuoteService;
        this.serviceOrderRepository = serviceOrderRepository;
    }

    @Test
    @DisplayName("Deve aprovar orçamento para ordem de serviço válida")
    void shouldApproveQuoteForValidServiceOrder() throws Exception {
        Customer customer = CustomerFixture.createCustomer(createCustomerService);
        Vehicle vehicle = VehicleFixture.createVehicle(customer.getId(), createVehicleService);
        ServiceOrder serviceOrder = ServiceOrderFixture.createServiceOrder(vehicle.getId(), customer.getId(), createServiceOrderService, createServiceTypeService, serviceOrderRepository);
        generateQuoteService.handle(serviceOrder.getId());

        mockMvc.perform(post("/quotes/service-order/" + serviceOrder.getId() + "/approve")
                .header("Authorization", getAuthToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceOrderId").value(serviceOrder.getId().toString()))
                .andExpect(jsonPath("$.status").value("APPROVED"));
    }

    @Test
    @DisplayName("Deve retornar erro 404 para ordem de serviço inexistente")
    void shouldReturnNotFoundForNonExistentServiceOrder() throws Exception {
        mockMvc.perform(post("/quotes/service-order/" + java.util.UUID.randomUUID() + "/approve")
                .header("Authorization", getAuthToken()))
                .andExpect(status().isNotFound());
    }
}
