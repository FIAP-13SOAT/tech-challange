package com.fiapchallenge.garage.integration.serviceorder;

import com.fiapchallenge.garage.application.customer.create.CreateCustomerUseCase;
import com.fiapchallenge.garage.application.servicetype.create.CreateServiceTypeService;
import com.fiapchallenge.garage.application.vehicle.CreateVehicleService;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderRepository;
import com.fiapchallenge.garage.domain.serviceorder.ServiceOrderStatus;
import com.fiapchallenge.garage.domain.user.UserRole;
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

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ListActiveServiceOrdersIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CreateCustomerUseCase createCustomerUseCase;
    @Autowired
    private CreateVehicleService createVehicleService;
    @Autowired
    private CreateServiceTypeService createServiceTypeService;
    @Autowired
    private ServiceOrderRepository serviceOrderRepository;

    @Test
    @DisplayName("Deve listar ordens ativas ordenadas por prioridade")
    void shouldListActiveOrdersByPriority() throws Exception {
        String token = getAuthToken();

        Customer customer = CustomerFixture.createCustomer(createCustomerUseCase);
        UUID vehicleId = VehicleFixture.createVehicle(customer.getId(), createVehicleService).getId();

        ServiceOrderFixture.createServiceOrder(customer, vehicleId, ServiceOrderStatus.RECEIVED, createServiceTypeService, serviceOrderRepository);
        ServiceOrderFixture.createServiceOrder(customer, vehicleId, ServiceOrderStatus.IN_DIAGNOSIS, createServiceTypeService, serviceOrderRepository);
        ServiceOrderFixture.createServiceOrder(customer, vehicleId, ServiceOrderStatus.AWAITING_APPROVAL, createServiceTypeService, serviceOrderRepository);
        ServiceOrderFixture.createServiceOrder(customer, vehicleId, ServiceOrderStatus.IN_PROGRESS, createServiceTypeService, serviceOrderRepository);
        ServiceOrderFixture.createServiceOrder(customer, vehicleId, ServiceOrderStatus.COMPLETED, createServiceTypeService, serviceOrderRepository);

        mockMvc.perform(get("/service-orders")
                .header("Authorization", getAuthTokenForRole(UserRole.CLERK)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4))
                .andExpect(jsonPath("$[0].status").value("IN_PROGRESS"))
                .andExpect(jsonPath("$[1].status").value("AWAITING_APPROVAL"))
                .andExpect(jsonPath("$[2].status").value("IN_DIAGNOSIS"))
                .andExpect(jsonPath("$[3].status").value("RECEIVED"));
    }
}
