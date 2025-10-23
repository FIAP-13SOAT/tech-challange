package com.fiapchallenge.garage.integration;

import com.fiapchallenge.garage.adapters.outbound.entities.CustomerEntity;
import com.fiapchallenge.garage.adapters.outbound.entities.VehicleEntity;
import com.fiapchallenge.garage.adapters.outbound.repositories.customer.JpaCustomerRepository;
import com.fiapchallenge.garage.adapters.outbound.repositories.vehicle.JpaVehicleRepository;
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
public class VehicleIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final JpaVehicleRepository vehicleRepository;
    private final JpaCustomerRepository customerRepository;

    @Autowired
    public VehicleIntegrationTest(MockMvc mockMvc, JpaVehicleRepository vehicleRepository, JpaCustomerRepository customerRepository) {
        this.mockMvc = mockMvc;
        this.vehicleRepository = vehicleRepository;
        this.customerRepository = customerRepository;
    }

    @Test
    @DisplayName("Deve criar um veículo vinculado a um cliente existente e persistir")
    void shouldCreateVehicleAndPersistToDatabase() throws Exception {
        UUID customerId = createCustomer();

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

    private UUID createCustomer() throws Exception {
        String customerJson = """
                {
                  "name": "Pix JR",
                  "email": "pixjr@example.com",
                  "phone": "9871111"
                }
                """;

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pix JR"))
                .andExpect(jsonPath("$.email").value("pixjr@example.com"))
                .andExpect(jsonPath("$.phone").value("9871111"));

        CustomerEntity savedCustomer = customerRepository.findAll().get(0);
        assertThat(savedCustomer.getName()).isEqualTo("Pix JR");
        assertThat(savedCustomer.getEmail()).isEqualTo("pixjr@example.com");
        assertThat(savedCustomer.getPhone()).isEqualTo("9871111");
        assertThat(savedCustomer.getId()).isNotNull();

        return savedCustomer.getId();
    }
}
