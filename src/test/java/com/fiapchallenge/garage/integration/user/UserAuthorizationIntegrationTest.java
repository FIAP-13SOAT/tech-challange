package com.fiapchallenge.garage.integration.user;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class UserAuthorizationIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;

    @Autowired
    public UserAuthorizationIntegrationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @DisplayName("Qualquer role pode criar usuários (auth no API Gateway)")
    void shouldAllowAnyRoleToCreateUsers() throws Exception {
        String userJsonAdmin = """
                {
                  "fullname": "Test User",
                  "email": "test@example.com",
                  "password": "password123",
                  "role": "CLERK"
                }
        """;

        String userJsonClerk = """
                {
                  "fullname": "Test User",
                  "email": "test-clerk@example.com",
                  "password": "password123",
                  "role": "CLERK"
                }
        """;

        String userJsonMechanic = """
                {
                  "fullname": "Test User",
                  "email": "test-mechanic@example.com",
                  "password": "password123",
                  "role": "CLERK"
                }
        """;

        mockMvc.perform(post("/users")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJsonAdmin))
                .andExpect(status().isOk());

        mockMvc.perform(post("/users")
                        .header("Authorization", getAuthTokenForRole(UserRole.CLERK))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJsonClerk))
                .andExpect(status().isOk());

        mockMvc.perform(post("/users")
                        .header("Authorization", getAuthTokenForRole(UserRole.MECHANIC))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJsonMechanic))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Qualquer role pode criar clientes (auth no API Gateway)")
    void shouldAllowAnyRoleToCreateCustomers() throws Exception {
        String customerJsonAdmin = """
                {
                  "name": "Test Customer Admin",
                  "email": "customer-admin@example.com",
                  "phone": "123456789",
                  "cpfCnpj": "11144477735"
                }
        """;

        String customerJsonClerk = """
                {
                  "name": "Test Customer Clerk",
                  "email": "customer-clerk@example.com",
                  "phone": "987654321",
                  "cpfCnpj": "22255588846"
                }
        """;

        String customerJsonMechanic = """
                {
                  "name": "Test Customer Mechanic",
                  "email": "customer-mechanic@example.com",
                  "phone": "555666777",
                  "cpfCnpj": "33366699957"
                }
        """;

        mockMvc.perform(post("/customers")
                        .header("Authorization", getAuthTokenForRole(UserRole.ADMIN))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJsonAdmin))
                .andExpect(status().isOk());

        mockMvc.perform(post("/customers")
                        .header("Authorization", getAuthTokenForRole(UserRole.CLERK))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJsonClerk))
                .andExpect(status().isOk());

        mockMvc.perform(post("/customers")
                        .header("Authorization", getAuthTokenForRole(UserRole.MECHANIC))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJsonMechanic))
                .andExpect(status().isOk());
    }
}
