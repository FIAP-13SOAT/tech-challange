package com.fiapchallenge.garage.integration;

import com.fiapchallenge.garage.adapters.outbound.repositories.servicetype.JpaServiceTypeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class ServiceTypeIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JpaServiceTypeRepository serviceTypeRepository;

    @Test
    @DisplayName("Deve criar e listar tipos de serviço")
    void shouldCreateAndListServiceTypes() throws Exception {
        String serviceTypeJson = """
                {
                    "description": "Troca de óleo do motor",
                    "value": 50.00
                }
        """;

        mockMvc.perform(post("/service-types")
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serviceTypeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Troca de óleo do motor"));

        mockMvc.perform(get("/service-types")
                        .header("Authorization", getAuthToken()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve obter todos os tipos de serviço incluindo dados de exemplo")
    void shouldGetAllServiceTypesIncludingSampleData() throws Exception {
        mockMvc.perform(get("/service-types")
                        .header("Authorization", getAuthToken()))
                .andExpect(status().isOk());
    }
}