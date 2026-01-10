package com.fiapchallenge.garage.integration.serviceorder;

import com.fiapchallenge.garage.application.customer.create.CreateCustomerService;
import com.fiapchallenge.garage.application.serviceorder.create.CreateServiceOrderService;
import com.fiapchallenge.garage.application.serviceorder.create.CreateServiceOrderUseCase;
import com.fiapchallenge.garage.application.servicetype.CreateServiceTypeService;
import com.fiapchallenge.garage.application.vehicle.CreateVehicleService;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderStatus;
import com.fiapchallenge.garage.domain.user.UserRole;
import com.fiapchallenge.garage.integration.BaseIntegrationTest;
import com.fiapchallenge.garage.integration.fixtures.CustomerFixture;
import com.fiapchallenge.garage.integration.fixtures.ServiceOrderFixture;
import com.fiapchallenge.garage.integration.fixtures.VehicleFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class ServiceOrderStatusIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private CreateCustomerService createCustomerService;

    @Autowired
    private CreateVehicleService createVehicleService;

    @Autowired
    private CreateServiceTypeService createServiceTypeService;

    @Autowired
    private ServiceOrderRepository serviceOrderRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnServiceOrderStatusWithTranslatedMessage() throws Exception {
        UUID serviceOrderId = createServiceOrder();

        mockMvc.perform(get("/service-orders/{id}/status", serviceOrderId)
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(serviceOrderId.toString()))
                .andExpect(jsonPath("$.status").value("AWAITING_APPROVAL"))
                .andExpect(jsonPath("$.statusMessage").value("Aguardando Aprovação"));
    }

    @Test
    void shouldReturn404WhenServiceOrderNotFound() throws Exception {
        UUID nonExistentId = UUID.randomUUID();

        mockMvc.perform(get("/service-orders/{id}/status", nonExistentId)
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldAllowAccessWithAllAuthorizedRoles() throws Exception {
        UUID serviceOrderId = createServiceOrder();

        mockMvc.perform(get("/service-orders/{id}/status", serviceOrderId)
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/service-orders/{id}/status", serviceOrderId)
                        .header("Authorization", getAuthTokenForRole(UserRole.CLERK)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/service-orders/{id}/status", serviceOrderId)
                        .header("Authorization", getAuthTokenForRole(UserRole.MECHANIC)))
                .andExpect(status().isOk());
    }

    private UUID createServiceOrder() {
        Customer customer = CustomerFixture.createCustomer(createCustomerService);

        return ServiceOrderFixture.createServiceOrder(
                customer,
                VehicleFixture.createVehicle(customer.getId(), createVehicleService).getId(),
                ServiceOrderStatus.AWAITING_APPROVAL,
                createServiceTypeService,
                serviceOrderRepository
        ).getId();
    }
}