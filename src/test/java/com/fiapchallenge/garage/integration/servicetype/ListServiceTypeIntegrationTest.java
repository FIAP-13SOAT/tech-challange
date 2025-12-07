package com.fiapchallenge.garage.integration.servicetype;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class ListServiceTypeIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CreateServiceTypeService createServiceTypeService;

    @Test
    @DisplayName("Deve listar todos os tipos de serviço incluindo dados de exemplo")
    void shouldListAllServiceTypesIncludingSampleData() throws Exception {
        mockMvc.perform(get("/service-types")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("Deve listar tipos de serviço criados dinamicamente")
    void shouldListDynamicallyCreatedServiceTypes() throws Exception {
        ServiceType createdServiceType = ServiceTypeFixture.createServiceType(createServiceTypeService);

        mockMvc.perform(get("/service-types")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.id == '" + createdServiceType.getId() + "')]").exists())
                .andExpect(jsonPath("$[?(@.description == '" + ServiceTypeFixture.DESCRIPTION + "')]").exists());
    }

    @Test
    @DisplayName("Deve retornar erro 403 quando não autenticado")
    void shouldReturnUnauthorizedWhenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/service-types"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há tipos de serviço além dos de exemplo")
    void shouldReturnEmptyListWhenNoServiceTypesExist() throws Exception {
        mockMvc.perform(get("/service-types")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("Deve permitir listagem com qualquer role autenticada")
    void shouldAllowListingWithAnyAuthenticatedRole() throws Exception {
        mockMvc.perform(get("/service-types")
                        .header("Authorization", getAuthTokenForRole(UserRole.CLERK)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/service-types")
                        .header("Authorization", getAuthTokenForRole(UserRole.MECHANIC)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/service-types")
                        .header("Authorization", getAuthTokenForRole(UserRole.STOCK_KEEPER)))
                .andExpect(status().isOk());
    }
}
