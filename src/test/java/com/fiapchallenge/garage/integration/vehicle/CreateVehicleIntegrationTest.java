package com.fiapchallenge.garage.integration.vehicle;

import com.fiapchallenge.garage.adapters.outbound.entities.VehicleEntity;
import com.fiapchallenge.garage.adapters.outbound.repositories.vehicle.JpaVehicleRepository;
import com.fiapchallenge.garage.application.customer.create.CreateCustomerService;
import com.fiapchallenge.garage.integration.BaseIntegrationTest;
import com.fiapchallenge.garage.integration.fixtures.CustomerFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class CreateVehicleIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final JpaVehicleRepository vehicleRepository;
    private final CreateCustomerService createCustomerService;

    @Autowired
    public CreateVehicleIntegrationTest(MockMvc mockMvc, JpaVehicleRepository vehicleRepository, CreateCustomerService createCustomerService) {
        this.mockMvc = mockMvc;
        this.vehicleRepository = vehicleRepository;
        this.createCustomerService = createCustomerService;
    }

    @Test
    @DisplayName("Deve criar um veículo vinculado a um cliente existente e persistir")
    void shouldCreateVehicleAndPersistToDatabase() throws Exception {
        UUID customerId = CustomerFixture.createCustomer(createCustomerService).getId();

        String vehicleJson = """
            {
              "model": "Civic",
              "brand": "Honda",
              "licensePlate": "ABC1D23",
              "color": "Prata",
              "year": 2020,
              "observations": "Troca de óleo recente",
              "customerId": "%s"
            }
        """.formatted(customerId.toString());

        mockMvc.perform(post("/vehicles")
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehicleJson)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").value("Civic"))
                .andExpect(jsonPath("$.brand").value("Honda"))
                .andExpect(jsonPath("$.licensePlate").value("ABC1D23"));

        VehicleEntity savedVehicle = vehicleRepository.findAll().getFirst();
        assertThat(savedVehicle.getModel()).isEqualTo("Civic");
        assertThat(savedVehicle.getBrand()).isEqualTo("Honda");
        assertThat(savedVehicle.getLicensePlate()).isEqualTo("ABC1D23");
        assertThat(savedVehicle.getColor()).isEqualTo("Prata");
        assertThat(savedVehicle.getYear()).isEqualTo(2020);
        assertThat(savedVehicle.getObservations()).isEqualTo("Troca de óleo recente");
        assertThat(savedVehicle.getCustomerId()).isEqualTo(customerId);
        assertThat(savedVehicle.getId()).isNotNull();
    }
}
