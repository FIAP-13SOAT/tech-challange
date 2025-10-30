package com.fiapchallenge.garage.integration;

import com.fiapchallenge.garage.adapters.outbound.entities.ServiceTypeEntity;
import com.fiapchallenge.garage.adapters.outbound.repositories.servicetype.JpaServiceTypeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class ServiceTypeIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final JpaServiceTypeRepository serviceTypeRepository;

    @Autowired
    public ServiceTypeIntegrationTest(MockMvc mockMvc, JpaServiceTypeRepository serviceTypeRepository) {
        this.mockMvc = mockMvc;
        this.serviceTypeRepository = serviceTypeRepository;
    }

    @Test
    @DisplayName("Deve criar um tipo de serviço e persistir no banco de dados")
    void shouldCreateServiceTypeToDatabase() throws Exception {
        String serviceTypeJson = """
            {
              "description": "Troca de óleo e filtro",
              "value": 250.00
            }
        """;

        mockMvc.perform(post("/service-types")
                        .header("Authorization", getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serviceTypeJson)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Troca de óleo e filtro"))
                .andExpect(jsonPath("$.value").value(250.00));

        ServiceTypeEntity serviceTypeEntity = serviceTypeRepository.findAll().getLast();
        assertThat(serviceTypeEntity.getDescription()).isEqualTo("Troca de óleo e filtro");
        assertThat(serviceTypeEntity.getValue().doubleValue()).isEqualTo(250.00);
        assertThat(serviceTypeEntity.getId()).isNotNull();
    }
}
