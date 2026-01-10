package com.fiapchallenge.garage.integration.serviceorder;

import com.fiapchallenge.garage.application.customer.create.CreateCustomerService;
import com.fiapchallenge.garage.application.servicetype.CreateServiceTypeService;
import com.fiapchallenge.garage.application.vehicle.CreateVehicleService;
import com.fiapchallenge.garage.domain.stock.Stock;
import com.fiapchallenge.garage.domain.stock.StockRepository;
import com.fiapchallenge.garage.domain.stockmovement.StockMovement;
import com.fiapchallenge.garage.domain.stockmovement.StockMovementRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class ServiceOrderAddStockItemsAfterCreationIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockMovementRepository stockMovementRepository;

    @Autowired
    private CreateCustomerService createCustomerService;

    @Autowired
    private CreateVehicleService createVehicleService;

    @Autowired
    private CreateServiceTypeService createServiceTypeService;

    @Test
    @DisplayName("Deve consumir estoque de itens adicionados após criação da ordem de serviço ao finalizar")
    void shouldConsumeStockForItemsAddedAfterServiceOrderCreation() throws Exception {
        UUID stockId1 = createStock("Filtro de Óleo", 100);
        UUID stockId2 = createStock("Filtro de Ar", 50);

        Stock initialStock1 = stockRepository.findById(stockId1).orElseThrow();
        Stock initialStock2 = stockRepository.findById(stockId2).orElseThrow();

        UUID customerId = CustomerFixture.createCustomer(createCustomerService).getId();
        UUID vehicleId = VehicleFixture.createVehicle(customerId, createVehicleService).getId();
        UUID serviceTypeId = ServiceTypeFixture.createServiceType(createServiceTypeService).getId();

        String serviceOrderJson = """
                {
                    "customerId": "%s",
                    "vehicleId": "%s",
                    "observations": "Teste adição de peças",
                    "serviceTypeIdList": ["%s"],
                    "stockItems": [
                        {
                            "stockId": "%s",
                            "quantity": 5
                        }
                    ]
                }
        """.formatted(customerId, vehicleId, serviceTypeId, stockId1);

        String createResponse = mockMvc.perform(post("/service-orders")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serviceOrderJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        UUID serviceOrderId = UUID.fromString(createResponse.split("\"id\":\"")[1].split("\"")[0]);

        Stock stockAfterCreation1 = stockRepository.findById(stockId1).orElseThrow();
        assertThat(stockAfterCreation1.getQuantity()).isEqualTo(initialStock1.getQuantity() - 5);

        mockMvc.perform(post("/service-orders/" + serviceOrderId + "/start-diagnosis")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN)))
                .andExpect(status().isOk());

        String addStockItemsJson = """
                [
                    {
                        "stockId": "%s",
                        "quantity": 3
                    }
                ]
        """.formatted(stockId2);

        mockMvc.perform(post("/service-orders/" + serviceOrderId + "/stock-items")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addStockItemsJson))
                .andExpect(status().isOk());

        mockMvc.perform(post("/service-orders/" + serviceOrderId + "/finish-diagnosis")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/quotes/service-order/" + serviceOrderId)
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/quotes/service-order/" + serviceOrderId + "/approve")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/service-orders/" + serviceOrderId + "/start-execution")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/service-orders/" + serviceOrderId + "/finish")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN)))
                .andExpect(status().isOk());

        Stock finalStock1 = stockRepository.findById(stockId1).orElseThrow();
        Stock finalStock2 = stockRepository.findById(stockId2).orElseThrow();

        assertThat(finalStock1.getQuantity()).isEqualTo(initialStock1.getQuantity() - 5);

        assertThat(finalStock2.getQuantity())
                .as("Item adicionado após criação deve ser consumido ao finalizar ordem de serviço")
                .isEqualTo(initialStock2.getQuantity() - 3);

        Page<StockMovement> movements1 = stockMovementRepository.findByStockId(stockId1, PageRequest.of(0, 10));
        Page<StockMovement> movements2 = stockMovementRepository.findByStockId(stockId2, PageRequest.of(0, 10));

        assertThat(movements1.getContent()).hasSize(2);
        assertThat(movements1.getContent().get(1).getMovementType()).isEqualTo(StockMovement.MovementType.OUT);
        assertThat(movements1.getContent().get(1).getQuantity()).isEqualTo(5);

        assertThat(movements2.getContent())
                .as("Deve haver movimentação de saída para item adicionado posteriormente")
                .hasSize(2);
        assertThat(movements2.getContent().get(1).getMovementType()).isEqualTo(StockMovement.MovementType.OUT);
        assertThat(movements2.getContent().get(1).getQuantity()).isEqualTo(3);
    }

    private UUID createStock(String productName, int quantity) throws Exception {
        String stockJson = """
                {
                    "productName": "%s",
                    "description": "Descrição de %s",
                    "unitPrice": 25.00,
                    "category": "Filtros",
                    "minThreshold": 10
                }
        """.formatted(productName, productName);

        String stockResponse = mockMvc.perform(post("/stock")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stockJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        UUID stockId = UUID.fromString(stockResponse.split("\"id\":\"")[1].split("\"")[0]);

        mockMvc.perform(post("/stock/" + stockId + "/add")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"quantity\": " + quantity + "}"))
                .andExpect(status().isOk());

        return stockId;
    }
}
