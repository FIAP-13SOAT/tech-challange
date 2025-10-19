package com.fiapchallenge.garage.integration;

import com.fiapchallenge.garage.adapters.outbound.entities.ServiceOrderEntity;
import com.fiapchallenge.garage.adapters.outbound.repositories.serviceorder.JpaServiceOrderRepository;
import com.fiapchallenge.garage.application.customer.CreateCustomerService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class ServiceOrderIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final JpaServiceOrderRepository serviceOrderRepository;
    private final CreateCustomerService createCustomerService;
    private final CreateVehicleService createVehicleService;
    private final CreateServiceTypeService createServiceTypeService;

    @Autowired
    public ServiceOrderIntegrationTest(
            MockMvc mockMvc,
            JpaServiceOrderRepository serviceOrderRepository,
            CreateCustomerService createCustomerService,
            CreateVehicleService createVehicleService,
            CreateServiceTypeService createServiceTypeService
    ) {
        this.mockMvc = mockMvc;
        this.serviceOrderRepository = serviceOrderRepository;
        this.createCustomerService = createCustomerService;
        this.createVehicleService = createVehicleService;
        this.createServiceTypeService = createServiceTypeService;
    }

    @Test
    @DisplayName("Criação de Ordem de Serviço")
    void shouldCreateServiceOrder() throws Exception {
        UUID customerId = CustomerFixture.createCustomer(createCustomerService).getId();
        System.out.println(customerId);
        UUID vehicleId = VehicleFixture.createVehicle(customerId, createVehicleService).getId();
        UUID serviceTypeId = ServiceTypeFixture.createServiceType(createServiceTypeService).getId();

        String serviceOrderJson = """
                {
                	"vehicleId": "%s",
                	"observations": "Barulho ao trocar marcha",
                	"serviceTypeIdList": [
                		"%s"
                	]
                }
        """.formatted(vehicleId.toString(), serviceTypeId.toString());

        mockMvc.perform(post("/service-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serviceOrderJson)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleId").value(vehicleId.toString()))
                .andExpect(jsonPath("$.observations").value("Barulho ao trocar marcha"))
                .andExpect(jsonPath("$.serviceTypeList[0].id").value(serviceTypeId.toString()));

        ServiceOrderEntity serviceOrderEntity = serviceOrderRepository.findAll().getLast();
        assertThat(serviceOrderEntity.getObservations()).isEqualTo("Barulho ao trocar marcha");
        assertThat(serviceOrderEntity.getVehicleId()).isEqualTo(vehicleId);
        assertThat(serviceOrderEntity.getServiceTypeList().size()).isEqualTo(1);
        assertThat(serviceOrderEntity.getServiceTypeList().getFirst().getId()).isEqualTo(serviceTypeId);
    }
}
