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
class DeleteStockIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldDeleteStockSuccessfully() throws Exception {
        String createResponse = mockMvc.perform(post("/stock")
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(StockFixture.createStockJson()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String stockId = objectMapper.readTree(createResponse).get("id").asText();

        mockMvc.perform(delete("/stock/{id}", stockId)
                        .header("Authorization", getAuthToken()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/stock")
                        .header("Authorization", getAuthToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(0));
    }

    @Test
    void shouldFailToDeleteNonExistentStock() throws Exception {
        mockMvc.perform(delete("/stock/{id}", "550e8400-e29b-41d4-a716-446655440000")
                        .header("Authorization", getAuthToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteStockAndNotAffectOthers() throws Exception {
        String createResponse1 = mockMvc.perform(post("/stock")
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(StockFixture.createStockJson()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String createResponse2 = mockMvc.perform(post("/stock")
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "productName": "Filtro de Óleo",
                                "description": "Filtro de óleo para motor",
                                "unitPrice": 15.90,
                                "category": "Filtros",
                                "minThreshold": 2
                            }
                            """))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String stockId1 = objectMapper.readTree(createResponse1).get("id").asText();
        String stockId2 = objectMapper.readTree(createResponse2).get("id").asText();

        mockMvc.perform(delete("/stock/{id}", stockId1)
                        .header("Authorization", getAuthToken()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/stock")
                        .header("Authorization", getAuthToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].id").value(stockId2))
                .andExpect(jsonPath("$.content[0].productName").value("Filtro de Óleo"));
    }

    @Test
    void shouldFailToDeleteStockWithoutAuthentication() throws Exception {
        String createResponse = mockMvc.perform(post("/stock")
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(StockFixture.createStockJson()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String stockId = objectMapper.readTree(createResponse).get("id").asText();

        mockMvc.perform(delete("/stock/{id}", stockId))
                .andExpect(status().isForbidden());
    }
}
