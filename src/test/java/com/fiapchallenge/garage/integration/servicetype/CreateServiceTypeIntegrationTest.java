package com.fiapchallenge.garage.integration.servicetype;

import com.fiapchallenge.garage.adapters.outbound.entities.ServiceTypeEntity;
import com.fiapchallenge.garage.adapters.outbound.repositories.servicetype.JpaServiceTypeRepository;
import com.fiapchallenge.garage.domain.user.UserRole;
import com.fiapchallenge.garage.integration.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class CreateServiceTypeIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JpaServiceTypeRepository serviceTypeRepository;

    @Test
    @DisplayName("Deve criar um tipo de serviço com sucesso")
    void shouldCreateServiceTypeSuccessfully() throws Exception {
        String serviceTypeJson = """
                {
                    "description": "Troca de óleo do motor",
                    "value": 150.00
                }
        """;

        mockMvc.perform(post("/service-types")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serviceTypeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Troca de óleo do motor"))
                .andExpect(jsonPath("$.value").value(150.00))
                .andExpect(jsonPath("$.id").exists());

        ServiceTypeEntity savedEntity = serviceTypeRepository.findAll().stream()
                .filter(entity -> "Troca de óleo do motor".equals(entity.getDescription()))
                .findFirst()
                .orElseThrow();

        assertThat(savedEntity.getDescription()).isEqualTo("Troca de óleo do motor");
        assertThat(savedEntity.getValue()).isEqualTo(new BigDecimal("150.00"));
    }

    @Test
    @DisplayName("Deve retornar erro 400 quando descrição for nula")
    void shouldReturnBadRequestWhenDescriptionIsNull() throws Exception {
        String serviceTypeJson = """
                {
                    "description": null,
                    "value": 150.00
                }
        """;

        mockMvc.perform(post("/service-types")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serviceTypeJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar erro 400 quando valor for nulo")
    void shouldReturnBadRequestWhenValueIsNull() throws Exception {
        String serviceTypeJson = """
                {
                    "description": "Troca de óleo",
                    "value": null
                }
        """;

        mockMvc.perform(post("/service-types")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serviceTypeJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar erro 400 quando descrição for vazia")
    void shouldReturnBadRequestWhenDescriptionIsEmpty() throws Exception {
        String serviceTypeJson = """
                {
                    "description": "",
                    "value": 150.00
                }
        """;

        mockMvc.perform(post("/service-types")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serviceTypeJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve permitir criação com role STOCK_KEEPER")
    void shouldAllowCreationWithStockKeeperRole() throws Exception {
        String serviceTypeJson = """
                {
                    "description": "Serviço STOCK_KEEPER",
                    "value": 100.00
                }
        """;

        mockMvc.perform(post("/service-types")
                        .header("Authorization", getAuthTokenForRole(UserRole.STOCK_KEEPER))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serviceTypeJson))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar 403 para role CLERK")
    void shouldReturn403ForClerkRole() throws Exception {
        String serviceTypeJson = """
                {
                    "description": "Serviço CLERK",
                    "value": 100.00
                }
        """;

        mockMvc.perform(post("/service-types")
                        .header("Authorization", getAuthTokenForRole(UserRole.CLERK))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serviceTypeJson))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Deve retornar 403 para role MECHANIC")
    void shouldReturn403ForMechanicRole() throws Exception {
        String serviceTypeJson = """
                {
                    "description": "Serviço MECHANIC",
                    "value": 100.00
                }
        """;

        mockMvc.perform(post("/service-types")
                        .header("Authorization", getAuthTokenForRole(UserRole.MECHANIC))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serviceTypeJson))
                .andExpect(status().isForbidden());
    }
}