package com.fiapchallenge.garage.integration.customer;

import com.fiapchallenge.garage.adapters.outbound.repositories.customer.JpaCustomerRepository;
import com.fiapchallenge.garage.application.customer.create.CreateCustomerService;
import com.fiapchallenge.garage.integration.BaseIntegrationTest;
import com.fiapchallenge.garage.integration.fixtures.CustomerFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class ListCustomerIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final JpaCustomerRepository customerRepository;
    private final CreateCustomerService createCustomerService;

    @Autowired
    public ListCustomerIntegrationTest(MockMvc mockMvc, JpaCustomerRepository customerRepository, CreateCustomerService createCustomerService) {
        this.mockMvc = mockMvc;
        this.customerRepository = customerRepository;
        this.createCustomerService = createCustomerService;
    }

    @Test
    @DisplayName("Deve listar todos os clientes")
    void shouldListAllCustomers() throws Exception {
        CustomerFixture.createCustomer(createCustomerService);
        CustomerFixture.createCustomer(createCustomerService, "Jane Smith", "jane@example.com", "987654321", "73525879008");

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].name").value("John Doe"))
                .andExpect(jsonPath("$.content[1].name").value("Jane Smith"))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1));

        assertThat(customerRepository.findAll()).hasSize(2);
    }

    @Test
    @DisplayName("Deve paginar corretamente os clientes")
    void shouldPaginateCustomersCorrectly() throws Exception {
        String[] validCpfs = {"11144477735", "22255588846", "33366699957", "35178293070", "80316061026"};
        for (int i = 1; i <= 5; i++) {
            CustomerFixture.createCustomer(createCustomerService, "Customer " + i, "customer" + i + "@example.com", "12345678" + i, validCpfs[i-1]);
        }

        mockMvc.perform(get("/customers?page=0&size=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.totalElements").value(5))
                .andExpect(jsonPath("$.totalPages").value(3))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.size").value(2))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.last").value(false));

        mockMvc.perform(get("/customers?page=2&size=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.number").value(2))
                .andExpect(jsonPath("$.first").value(false))
                .andExpect(jsonPath("$.last").value(true));
    }
}
