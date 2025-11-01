package com.fiapchallenge.garage.integration.vehicle;

import com.fiapchallenge.garage.application.customer.create.CreateCustomerService;
import com.fiapchallenge.garage.application.vehicle.CreateVehicleService;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.integration.BaseIntegrationTest;
import com.fiapchallenge.garage.integration.fixtures.CustomerFixture;
import com.fiapchallenge.garage.integration.fixtures.VehicleFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class ListVehicleIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final CreateCustomerService createCustomerService;
    private final CreateVehicleService createVehicleService;

    @Autowired
    public ListVehicleIntegrationTest(MockMvc mockMvc, CreateCustomerService createCustomerService, CreateVehicleService createVehicleService) {
        this.mockMvc = mockMvc;
        this.createCustomerService = createCustomerService;
        this.createVehicleService = createVehicleService;
    }

    @Test
    @DisplayName("Deve listar veículos por cliente")
    void shouldListVehiclesByCustomer() throws Exception {
        Customer customer = CustomerFixture.createCustomer(createCustomerService);
        VehicleFixture.createVehicle(customer.getId(), createVehicleService);
        
        mockMvc.perform(get("/vehicles")
                        .param("customerId", customer.getId().toString())
                        .header("Authorization", getAuthToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].model").value(VehicleFixture.MODEL))
                .andExpect(jsonPath("$[0].brand").value(VehicleFixture.BRAND))
                .andExpect(jsonPath("$[0].customerId").value(customer.getId().toString()));
    }
}