package com.fiapchallenge.garage.integration.vehicle;

import com.fiapchallenge.garage.application.customer.create.CreateCustomerService;
import com.fiapchallenge.garage.application.vehicle.CreateVehicleService;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.vehicle.Vehicle;
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

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class DeleteVehicleIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final CreateCustomerService createCustomerService;
    private final CreateVehicleService createVehicleService;

    @Autowired
    public DeleteVehicleIntegrationTest(MockMvc mockMvc, CreateCustomerService createCustomerService, CreateVehicleService createVehicleService) {
        this.mockMvc = mockMvc;
        this.createCustomerService = createCustomerService;
        this.createVehicleService = createVehicleService;
    }

    @Test
    @DisplayName("Deve deletar veículo com sucesso")
    void shouldDeleteVehicleSuccessfully() throws Exception {
        Customer customer = CustomerFixture.createCustomer(createCustomerService);
        Vehicle vehicle = VehicleFixture.createVehicle(customer.getId(), createVehicleService);

        mockMvc.perform(delete("/vehicles/{id}", vehicle.getId())
                        .header("Authorization", getAuthToken()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar erro para veículo inexistente")
    void shouldReturnErrorForNonExistentVehicle() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        mockMvc.perform(delete("/vehicles/{id}", nonExistentId)
                        .header("Authorization", getAuthToken()))
                .andExpect(status().isNotFound());
    }
}
