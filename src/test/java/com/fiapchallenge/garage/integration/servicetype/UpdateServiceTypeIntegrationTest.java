package com.fiapchallenge.garage.integration.servicetype;

import com.fiapchallenge.garage.adapters.outbound.entities.ServiceTypeEntity;
import com.fiapchallenge.garage.adapters.outbound.repositories.servicetype.JpaServiceTypeRepository;
import com.fiapchallenge.garage.application.servicetype.CreateServiceTypeService;
import com.fiapchallenge.garage.domain.servicetype.ServiceType;
import com.fiapchallenge.garage.domain.user.UserRole;
import com.fiapchallenge.garage.integration.BaseIntegrationTest;
import com.fiapchallenge.garage.integration.fixtures.ServiceTypeFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class UpdateServiceTypeIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final JpaServiceTypeRepository serviceTypeRepository;
    private final CreateServiceTypeService createServiceTypeService;

    @Autowired
    public UpdateServiceTypeIntegrationTest(MockMvc mockMvc, JpaServiceTypeRepository serviceTypeRepository, CreateServiceTypeService createServiceTypeService) {
        this.mockMvc = mockMvc;
        this.serviceTypeRepository = serviceTypeRepository;
        this.createServiceTypeService = createServiceTypeService;
    }

    @Test
    @DisplayName("Deve atualizar um tipo de serviço existente")
    void shouldUpdateExistingServiceType() throws Exception {
        ServiceType createdServiceType = ServiceTypeFixture.createServiceType(createServiceTypeService);

        String updateServiceTypeJson = """
                {
                  "description": "Troca de óleo completa",
                  "value": 200.00
                }
                """;

        mockMvc.perform(put("/service-types/" + createdServiceType.getId())
                .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateServiceTypeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Troca de óleo completa"))
                .andExpect(jsonPath("$.value").value(200.00));

        ServiceTypeEntity updatedServiceType = serviceTypeRepository.findById(createdServiceType.getId()).get();
        assertThat(updatedServiceType.getDescription()).isEqualTo("Troca de óleo completa");
        assertThat(updatedServiceType.getValue()).isEqualTo(new BigDecimal("200.00"));
        assertThat(updatedServiceType.getId()).isEqualTo(createdServiceType.getId());
    }

    @Test
    @DisplayName("Deve retornar erro 404 para tipo de serviço inexistente")
    void shouldReturnNotFoundForNonExistentServiceType() throws Exception {
        String updateServiceTypeJson = """
                {
                  "description": "Serviço inexistente",
                  "value": 100.00
                }
                """;

        mockMvc.perform(put("/service-types/" + UUID.randomUUID())
                .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateServiceTypeJson))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar erro 400 para dados inválidos")
    void shouldReturnBadRequestForInvalidData() throws Exception {
        ServiceType createdServiceType = ServiceTypeFixture.createServiceType(createServiceTypeService);

        String invalidServiceTypeJson = """
                {
                  "description": null,
                  "value": null
                }
                """;

        mockMvc.perform(put("/service-types/" + createdServiceType.getId())
                .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidServiceTypeJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve permitir atualização com role STOCK_KEEPER")
    void shouldAllowUpdateWithStockKeeperRole() throws Exception {
        ServiceType createdServiceType = ServiceTypeFixture.createServiceType(createServiceTypeService);

        String updateServiceTypeJson = """
                {
                  "description": "Atualizado por STOCK_KEEPER",
                  "value": 250.00
                }
                """;

        mockMvc.perform(put("/service-types/" + createdServiceType.getId())
                .header("Authorization", getAuthTokenForRole(UserRole.STOCK_KEEPER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateServiceTypeJson))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar 403 para role CLERK")
    void shouldReturn403ForClerkRole() throws Exception {
        ServiceType createdServiceType = ServiceTypeFixture.createServiceType(createServiceTypeService);

        String updateServiceTypeJson = """
                {
                  "description": "Tentativa CLERK",
                  "value": 100.00
                }
                """;

        mockMvc.perform(put("/service-types/" + createdServiceType.getId())
                .header("Authorization", getAuthTokenForRole(UserRole.CLERK))
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateServiceTypeJson))
                .andExpect(status().isForbidden());
    }
}
