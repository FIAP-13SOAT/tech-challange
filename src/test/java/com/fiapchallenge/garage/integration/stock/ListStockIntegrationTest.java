package com.fiapchallenge.garage.integration.stock;

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
class ListStockIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldListStockWithPagination() throws Exception {
        mockMvc.perform(post("/stock")
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(StockFixture.createStockJson()))
                .andExpect(status().isOk());

        mockMvc.perform(post("/stock")
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "productName": "Filtro de Ar",
                                "description": "Filtro de ar para veículos",
                                "unitPrice": 25.50,
                                "category": "Filtros",
                                "minThreshold": 3
                            }
                            """))
                .andExpect(status().isOk());

        mockMvc.perform(get("/stock")
                        .header("Authorization", getAuthToken())
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.number").value(0));
    }

    @Test
    void shouldListEmptyStockWhenNoItems() throws Exception {
        mockMvc.perform(get("/stock")
                        .header("Authorization", getAuthToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(0))
                .andExpect(jsonPath("$.totalElements").value(0));
    }

    @Test
    void shouldListStockWithCustomPageSize() throws Exception {
        for (int i = 1; i <= 3; i++) {
            mockMvc.perform(post("/stock")
                            .header("Authorization", getAuthToken())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(String.format("""
                                {
                                    "productName": "Produto %d",
                                    "description": "Descrição %d",
                                    "unitPrice": %d.00,
                                    "category": "Categoria",
                                    "minThreshold": 1
                                }
                                """, i, i, i * 10)))
                    .andExpect(status().isOk());
        }

        mockMvc.perform(get("/stock")
                        .header("Authorization", getAuthToken())
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.totalElements").value(3))
                .andExpect(jsonPath("$.totalPages").value(2));
    }
}
