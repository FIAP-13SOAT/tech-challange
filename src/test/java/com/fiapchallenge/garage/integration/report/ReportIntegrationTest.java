package com.fiapchallenge.garage.integration.report;

import com.fiapchallenge.garage.domain.user.UserRole;
import com.fiapchallenge.garage.integration.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class ReportIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;

    @Autowired
    public ReportIntegrationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @DisplayName("Deve gerar relatório com role ADMIN")
    void shouldGenerateReportWithAdminRole() throws Exception {
        mockMvc.perform(get("/reports")
                .param("startDate", "01-01-2024")
                .param("endDate", "31-12-2024")
                .header("Authorization", getAuthTokenForRole(UserRole.ADMIN)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve permitir acesso para role CLERK (auth no API Gateway)")
    void shouldAllowAccessForClerkRole() throws Exception {
        mockMvc.perform(get("/reports")
                .param("startDate", "01-01-2024")
                .param("endDate", "31-12-2024")
                .header("Authorization", getAuthTokenForRole(UserRole.CLERK)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve permitir acesso para role MECHANIC (auth no API Gateway)")
    void shouldAllowAccessForMechanicRole() throws Exception {
        mockMvc.perform(get("/reports")
                .param("startDate", "01-01-2024")
                .param("endDate", "31-12-2024")
                .header("Authorization", getAuthTokenForRole(UserRole.MECHANIC)))
                .andExpect(status().isOk());
    }
}