package com.fiapchallenge.garage.integration.serviceorder;

import com.fiapchallenge.garage.adapters.outbound.repositories.serviceorder.JpaServiceOrderRepository;
import com.fiapchallenge.garage.application.customer.create.CreateCustomerService;
import com.fiapchallenge.garage.application.servicetype.CreateServiceTypeService;
import com.fiapchallenge.garage.application.vehicle.CreateVehicleService;
import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.StockRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class ServiceOrderStockIntegrationTest extends BaseIntegrationTest {

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
    @DisplayName("Deve diminuir estoque na criação da ordem de serviço")
    void shouldDecreaseStockAfterServiceOrderCreation() throws Exception {
        String stockJson = """
                {
                    "productName": "Filtro de Óleo",
                    "description": "Filtro de óleo para motor",
                    "unitPrice": 25.00,
                    "category": "Filtros",
                    "minThreshold": 10
                }
        """;

        String stockResponse = mockMvc.perform(post("/stock")
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stockJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        UUID stockId = UUID.fromString(stockResponse.split("\"id\":\"")[1].split("\"")[0]);

        mockMvc.perform(post("/stock/" + stockId + "/add?quantity=100")
                        .header("Authorization", getAuthToken()))
                .andExpect(status().isOk());

        Stock initialStock = stockRepository.findById(stockId).orElseThrow();

        UUID customerId = CustomerFixture.createCustomer(createCustomerService).getId();
        UUID vehicleId = VehicleFixture.createVehicle(customerId, createVehicleService).getId();
        UUID serviceTypeId = ServiceTypeFixture.createServiceType(createServiceTypeService).getId();

        String serviceOrderJson = """
                {
                    "vehicleId": "%s",
                    "observations": "Teste estoque",
                    "serviceTypeIdList": ["%s"],
                    "stockItems": [
                        {
                            "stockId": "%s",
                            "quantity": 5
                        }
                    ]
                }
        """.formatted(vehicleId, serviceTypeId, stockId);

        mockMvc.perform(post("/service-orders")
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serviceOrderJson))
                .andExpect(status().isOk());

        Stock stockAfterOrder = stockRepository.findById(stockId).orElseThrow();
        assertThat(stockAfterOrder.getQuantity()).isEqualTo(initialStock.getQuantity() - 5);
    }

    @Test
    @DisplayName("Deve retornar estoque ao cancelar ordem de serviço")
    void shouldReturnStockWhenCancellingServiceOrder() throws Exception {
        String stockJson = """
                {
                    "productName": "Filtro de Ar",
                    "description": "Filtro de ar para motor",
                    "unitPrice": 30.00,
                    "category": "Filtros",
                    "minThreshold": 5
                }
        """;

        String stockResponse = mockMvc.perform(post("/stock")
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stockJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        UUID stockId = UUID.fromString(stockResponse.split("\"id\":\"")[1].split("\"")[0]);

        mockMvc.perform(post("/stock/" + stockId + "/add?quantity=50")
                        .header("Authorization", getAuthToken()))
                .andExpect(status().isOk());

        Stock initialStock = stockRepository.findById(stockId).orElseThrow();

        UUID customerId = CustomerFixture.createCustomer(createCustomerService).getId();
        UUID vehicleId = VehicleFixture.createVehicle(customerId, createVehicleService).getId();
        UUID serviceTypeId = ServiceTypeFixture.createServiceType(createServiceTypeService).getId();

        String serviceOrderJson = """
                {
                    "vehicleId": "%s",
                    "observations": "Teste cancelamento",
                    "serviceTypeIdList": ["%s"],
                    "stockItems": [
                        {
                            "stockId": "%s",
                            "quantity": 3
                        }
                    ]
                }
        """.formatted(vehicleId, serviceTypeId, stockId);

        mockMvc.perform(post("/service-orders")
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serviceOrderJson))
                .andExpect(status().isOk());

        com.fiapchallenge.garage.adapters.outbound.entities.ServiceOrderEntity createdOrder =
            serviceOrderRepository.findAll().getLast();

        Stock stockAfterOrder = stockRepository.findById(stockId).orElseThrow();
        assertThat(stockAfterOrder.getQuantity()).isEqualTo(initialStock.getQuantity() - 3);

        mockMvc.perform(post("/service-orders/" + createdOrder.getId() + "/cancelled")
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Stock stockAfterCancel = stockRepository.findById(stockId).orElseThrow();
        assertThat(stockAfterCancel.getQuantity()).isEqualTo(initialStock.getQuantity());
    }
}
