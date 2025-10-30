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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class UpdateVehicleIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final CreateCustomerService createCustomerService;
    private final CreateVehicleService createVehicleService;

    @Autowired
    public UpdateVehicleIntegrationTest(MockMvc mockMvc, CreateCustomerService createCustomerService, CreateVehicleService createVehicleService) {
        this.mockMvc = mockMvc;
        this.createCustomerService = createCustomerService;
        this.createVehicleService = createVehicleService;
    }

    @Test
    @DisplayName("Deve atualizar veículo sem alterar customerId e licensePlate")
    void shouldUpdateVehicleWithoutChangingCustomerIdAndLicensePlate() throws Exception {
        Customer customer = CustomerFixture.createCustomer(createCustomerService);
        Vehicle vehicle = VehicleFixture.createVehicle(customer.getId(), createVehicleService);
        
        String updateJson = """
            {
              "model": "Corolla",
              "brand": "Toyota",
              "color": "Branco",
              "year": 2023,
              "observations": "Revisão em dia"
            }
        """;
        
        mockMvc.perform(put("/vehicles/{id}", vehicle.getId())
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").value("Corolla"))
                .andExpect(jsonPath("$.brand").value("Toyota"))
                .andExpect(jsonPath("$.color").value("Branco"))
                .andExpect(jsonPath("$.year").value(2023))
                .andExpect(jsonPath("$.observations").value("Revisão em dia"))
                .andExpect(jsonPath("$.customerId").value(customer.getId().toString()))
                .andExpect(jsonPath("$.licensePlate").value(VehicleFixture.LICENSE_PLATE));
    }
}