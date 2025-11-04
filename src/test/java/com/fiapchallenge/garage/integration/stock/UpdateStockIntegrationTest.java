package com.fiapchallenge.garage.integration.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiapchallenge.garage.integration.BaseIntegrationTest;
import com.fiapchallenge.garage.integration.fixtures.StockFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class UpdateStockIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldUpdateStockSuccessfully() throws Exception {
        String createResponse = mockMvc.perform(post("/stock")
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(StockFixture.createStockJson()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String stockId = objectMapper.readTree(createResponse).get("id").asText();

        mockMvc.perform(put("/stock/{id}", stockId)
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(StockFixture.updateStockJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(stockId))
                .andExpect(jsonPath("$.productName").value("Óleo Motor 10W40 Atualizado"))
                .andExpect(jsonPath("$.description").value("Óleo semi-sintético atualizado"))
                .andExpect(jsonPath("$.unitPrice").value(52.90))
                .andExpect(jsonPath("$.category").value("Lubrificantes Premium"))
                .andExpect(jsonPath("$.minThreshold").value(10));
    }

    @Test
    void shouldFailToUpdateNonExistentStock() throws Exception {
        mockMvc.perform(put("/stock/{id}", "550e8400-e29b-41d4-a716-446655440000")
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(StockFixture.updateStockJson()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldFailToUpdateWithInvalidData() throws Exception {
        String createResponse = mockMvc.perform(post("/stock")
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(StockFixture.createStockJson()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String stockId = objectMapper.readTree(createResponse).get("id").asText();

        // Tentar atualizar com dados inválidos
        mockMvc.perform(put("/stock/{id}", stockId)
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "productName": "",
                                "unitPrice": -10.00,
                                "minThreshold": -5
                            }
                            """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateOnlyProvidedFields() throws Exception {
        // Criar estoque
        String createResponse = mockMvc.perform(post("/stock")
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(StockFixture.createStockJson()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String stockId = objectMapper.readTree(createResponse).get("id").asText();

        // Atualizar apenas o preço
        mockMvc.perform(put("/stock/{id}", stockId)
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "productName": "Óleo Motor 5W30",
                                "description": "Óleo sintético para motor",
                                "unitPrice": 99.99,
                                "category": "Lubrificantes",
                                "minThreshold": 5
                            }
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.unitPrice").value(99.99))
                .andExpect(jsonPath("$.productName").value("Óleo Motor 5W30"));
    }
}
