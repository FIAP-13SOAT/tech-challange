package com.fiapchallenge.garage.integration.servicetype;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class DeleteServiceTypeIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final JpaServiceTypeRepository serviceTypeRepository;
    private final CreateServiceTypeService createServiceTypeService;

    @Autowired
    public DeleteServiceTypeIntegrationTest(MockMvc mockMvc, JpaServiceTypeRepository serviceTypeRepository, CreateServiceTypeService createServiceTypeService) {
        this.mockMvc = mockMvc;
        this.serviceTypeRepository = serviceTypeRepository;
        this.createServiceTypeService = createServiceTypeService;
    }

    @Test
    @DisplayName("Deve remover um tipo de serviço existente")
    void shouldDeleteExistingServiceType() throws Exception {
        ServiceType createdServiceType = ServiceTypeFixture.createServiceType(createServiceTypeService);

        mockMvc.perform(delete("/service-types/" + createdServiceType.getId())
                .header("Authorization", getAuthTokenForRole(UserRole.ADMIN)))
                .andExpect(status().isNoContent());

        assertThat(serviceTypeRepository.existsById(createdServiceType.getId())).isFalse();
    }

    @Test
    @DisplayName("Deve retornar erro 404 para tipo de serviço inexistente")
    void shouldReturnBadRequestForNonExistentServiceType() throws Exception {
        mockMvc.perform(delete("/service-types/" + UUID.randomUUID())
                .header("Authorization", getAuthTokenForRole(UserRole.ADMIN)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar erro 403 quando não autenticado")
    void shouldReturnUnauthorizedWhenNotAuthenticated() throws Exception {
        ServiceType createdServiceType = ServiceTypeFixture.createServiceType(createServiceTypeService);

        mockMvc.perform(delete("/service-types/" + createdServiceType.getId()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Deve permitir exclusão com role STOCK_KEEPER")
    void shouldAllowDeleteWithStockKeeperRole() throws Exception {
        ServiceType createdServiceType = ServiceTypeFixture.createServiceType(createServiceTypeService);

        mockMvc.perform(delete("/service-types/" + createdServiceType.getId())
                .header("Authorization", getAuthTokenForRole(UserRole.STOCK_KEEPER)))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar 403 para role CLERK")
    void shouldReturn403ForClerkRole() throws Exception {
        ServiceType createdServiceType = ServiceTypeFixture.createServiceType(createServiceTypeService);

        mockMvc.perform(delete("/service-types/" + createdServiceType.getId())
                .header("Authorization", getAuthTokenForRole(UserRole.CLERK)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Deve retornar 403 para role MECHANIC")
    void shouldReturn403ForMechanicRole() throws Exception {
        ServiceType createdServiceType = ServiceTypeFixture.createServiceType(createServiceTypeService);

        mockMvc.perform(delete("/service-types/" + createdServiceType.getId())
                .header("Authorization", getAuthTokenForRole(UserRole.MECHANIC)))
                .andExpect(status().isForbidden());
    }
}
