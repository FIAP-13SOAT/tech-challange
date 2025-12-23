package com.fiapchallenge.garage.integration.serviceorder;

import com.fiapchallenge.garage.adapters.outbound.repositories.serviceorder.JpaServiceOrderRepository;
import com.fiapchallenge.garage.application.customer.create.CreateCustomerService;
import com.fiapchallenge.garage.application.servicetype.CreateServiceTypeService;
import com.fiapchallenge.garage.application.vehicle.CreateVehicleService;
import com.fiapchallenge.garage.domain.stock.StockRepository;
import com.fiapchallenge.garage.domain.user.UserRole;
import com.fiapchallenge.garage.integration.BaseIntegrationTest;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class ServiceOrderManagementIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private CreateCustomerService createCustomerService;

    @Autowired
    private CreateVehicleService createVehicleService;

    @Autowired
    private CreateServiceTypeService createServiceTypeService;

    @Autowired
    private JpaServiceOrderRepository serviceOrderRepository;

    @Test
    @DisplayName("Deve obter detalhes da ordem de serviço")
    void shouldGetServiceOrderDetails() throws Exception {
        UUID orderId = createServiceOrder();

        mockMvc.perform(get("/service-orders/" + orderId)
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderId.toString()))
                .andExpect(jsonPath("$.observations").exists())
                .andExpect(jsonPath("$.status").exists());
    }

    @Test
    @DisplayName("Deve adicionar itens de estoque à ordem")
    void shouldAddStockItemsToServiceOrder() throws Exception {
        UUID orderId = createServiceOrder();
        UUID stockId = createStock();

        String stockItemsJson = """
                [
                    {
                        "stockId": "%s",
                        "quantity": 2
                    }
                ]
        """.formatted(stockId);

        mockMvc.perform(post("/service-orders/" + orderId + "/stock-items")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stockItemsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceItems").isArray());

        mockMvc.perform(get("/service-orders/" + orderId)
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceItems").isArray())
                .andExpect(jsonPath("$.serviceItems[0].stockId").value(stockId.toString()))
                .andExpect(jsonPath("$.serviceItems[0].quantity").value(2));
    }

    @Test
    @DisplayName("Deve remover itens de estoque da ordem")
    void shouldRemoveStockItemsFromServiceOrder() throws Exception {
        UUID orderId = createServiceOrderWithStock();
        UUID stockId = createStock();

        String addItemsJson = """
                [
                    {
                        "stockId": "%s",
                        "quantity": 3
                    }
                ]
        """.formatted(stockId);

        mockMvc.perform(post("/service-orders/" + orderId + "/stock-items")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addItemsJson))
                .andExpect(status().isOk());

        String removeItemsJson = """
                [
                    {
                        "stockId": "%s",
                        "quantity": 3
                    }
                ]
        """.formatted(stockId);

        mockMvc.perform(delete("/service-orders/" + orderId + "/stock-items")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(removeItemsJson))
                .andExpect(status().isOk());

        mockMvc.perform(get("/service-orders/" + orderId)
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceItems").isEmpty());
    }

    @Test
    @DisplayName("Deve adicionar tipos de serviço à ordem")
    void shouldAddServiceTypesToServiceOrder() throws Exception {
        UUID orderId = createServiceOrder();
        UUID serviceTypeId = ServiceTypeFixture.createServiceType(createServiceTypeService).getId();

        String serviceTypesJson = """
                ["%s"]
        """.formatted(serviceTypeId);

        mockMvc.perform(post("/service-orders/" + orderId + "/service-types")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serviceTypesJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceTypeList").isArray());

        mockMvc.perform(get("/service-orders/" + orderId)
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceTypeList").isArray())
                .andExpect(jsonPath("$.serviceTypeList[?(@.id == '%s')]", serviceTypeId).exists());
    }

    @Test
    @DisplayName("Deve remover tipos de serviço da ordem")
    void shouldRemoveServiceTypesFromServiceOrder() throws Exception {
        UUID orderId = createServiceOrder();
        UUID serviceTypeId = ServiceTypeFixture.createServiceType(createServiceTypeService).getId();

        String serviceTypesJson = """
                ["%s"]
        """.formatted(serviceTypeId);

        mockMvc.perform(post("/service-orders/" + orderId + "/service-types")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serviceTypesJson))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/service-orders/" + orderId + "/service-types")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serviceTypesJson))
                .andExpect(status().isOk());

        mockMvc.perform(get("/service-orders/" + orderId)
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceTypeList[?(@.id == '%s')]", serviceTypeId).doesNotExist());
    }

    private UUID createServiceOrder() throws Exception {
        UUID customerId = CustomerFixture.createCustomer(createCustomerService).getId();
        UUID vehicleId = VehicleFixture.createVehicle(customerId, createVehicleService).getId();
        UUID serviceTypeId = ServiceTypeFixture.createServiceType(createServiceTypeService).getId();

        String serviceOrderJson = """
                {
                    "customerId": "%s",
                    "vehicleId": "%s",
                    "observations": "Teste gerenciamento",
                    "serviceTypeIdList": ["%s"]
                }
        """.formatted(customerId, vehicleId, serviceTypeId);

        mockMvc.perform(post("/service-orders")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serviceOrderJson))
                .andExpect(status().isOk());

        return serviceOrderRepository.findAll().getLast().getId();
    }

    private UUID createStock() throws Exception {
        String stockJson = """
                {
                    "productName": "Peça Teste",
                    "description": "Peça para teste",
                    "unitPrice": 15.00,
                    "category": "Teste",
                    "minThreshold": 5
                }
        """;

        String stockResponse = mockMvc.perform(post("/stock")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stockJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return UUID.fromString(stockResponse.split("\"id\":\"")[1].split("\"")[0]);
    }

    private UUID createServiceOrderWithStock() throws Exception {
        UUID customerId = CustomerFixture.createCustomer(createCustomerService).getId();
        UUID vehicleId = VehicleFixture.createVehicle(customerId, createVehicleService).getId();
        UUID serviceTypeId = ServiceTypeFixture.createServiceType(createServiceTypeService).getId();

        String serviceOrderJson = """
                {
                    "customerId": "%s",
                    "vehicleId": "%s",
                    "observations": "Teste com estoque",
                    "serviceTypeIdList": ["%s"],
                    "stockItems": []
                }
        """.formatted(customerId, vehicleId, serviceTypeId);

        mockMvc.perform(post("/service-orders")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serviceOrderJson))
                .andExpect(status().isOk());

        return serviceOrderRepository.findAll().getLast().getId();
    }
}