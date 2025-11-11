package com.fiapchallenge.garage.integration.serviceorder;

import com.fiapchallenge.garage.application.customer.create.CreateCustomerService;
import com.fiapchallenge.garage.application.serviceorder.CreateServiceOrderService;
import com.fiapchallenge.garage.application.servicetype.CreateServiceTypeService;
import com.fiapchallenge.garage.application.vehicle.CreateVehicleService;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrder;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
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
class TrackServiceOrderIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final CreateCustomerService createCustomerService;
    private final CreateVehicleService createVehicleService;
    private final CreateServiceOrderService createServiceOrderService;
    private final CreateServiceTypeService createServiceTypeService;
    private final ServiceOrderRepository serviceOrderRepository;

    @Autowired
    public TrackServiceOrderIntegrationTest(MockMvc mockMvc,
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
    @DisplayName("Deve rastrear ordem de serviço com sucesso")
    void shouldTrackServiceOrderSuccessfully() throws Exception {
        Customer customer = CustomerFixture.createCustomer(createCustomerService);
        Vehicle vehicle = VehicleFixture.createVehicle(customer.getId(), createVehicleService);
        ServiceOrder serviceOrder = ServiceOrderFixture.createServiceOrder(vehicle.getId(), customer.getId(), createServiceOrderService, createServiceTypeService, serviceOrderRepository);

        mockMvc.perform(get("/public/service-orders/" + serviceOrder.getId() + "/track")
                .param("cpfCnpj", customer.getCpfCnpjValue()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licensePlate").value(vehicle.getLicensePlate()))
                .andExpect(jsonPath("$.model").value(vehicle.getModel()))
                .andExpect(jsonPath("$.brand").value(vehicle.getBrand()))
                .andExpect(jsonPath("$.status").exists());
    }

    @Test
    @DisplayName("Deve retornar erro 404 para ordem de serviço inexistente")
    void shouldReturnNotFoundForNonExistentServiceOrder() throws Exception {
        mockMvc.perform(get("/public/service-orders/" + UUID.randomUUID() + "/track")
                .param("cpfCnpj", "12345678901"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar erro 404 quando CPF/CNPJ não confere")
    void shouldReturnNotFoundWhenCpfCnpjDoesNotMatch() throws Exception {
        Customer customer = CustomerFixture.createCustomer(createCustomerService);
        Vehicle vehicle = VehicleFixture.createVehicle(customer.getId(), createVehicleService);
        ServiceOrder serviceOrder = ServiceOrderFixture.createServiceOrder(vehicle.getId(), customer.getId(), createServiceOrderService, createServiceTypeService, serviceOrderRepository);

        mockMvc.perform(get("/public/service-orders/" + serviceOrder.getId() + "/track")
                .param("cpfCnpj", "99999999999"))
                .andExpect(status().isNotFound());
    }
}