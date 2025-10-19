package com.fiapchallenge.garage.integration.customer;

import com.fiapchallenge.garage.adapters.outbound.repositories.customer.JpaCustomerRepository;
import com.fiapchallenge.garage.adapters.outbound.entities.CustomerEntity;
import com.fiapchallenge.garage.application.customer.CreateCustomerUseCase;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.integration.BaseIntegrationTest;
import com.fiapchallenge.garage.integration.fixtures.CustomerFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class UpdateCustomerIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final JpaCustomerRepository customerRepository;
    private final CreateCustomerUseCase createCustomerUseCase;

    @Autowired
    public UpdateCustomerIntegrationTest(MockMvc mockMvc, JpaCustomerRepository customerRepository, CreateCustomerUseCase createCustomerUseCase) {
        this.mockMvc = mockMvc;
        this.customerRepository = customerRepository;
        this.createCustomerUseCase = createCustomerUseCase;
    }

    @Test
    @DisplayName("Deve atualizar um cliente existente")
    void shouldUpdateExistingCustomer() throws Exception {
        Customer createdCustomer = CustomerFixture.createCustomer(createCustomerUseCase);

        String updateCustomerJson = """
                {
                  "name": "Jane Doe",
                  "email": "jane@example.com",
                  "phone": "987654321"
                }
                """;

        mockMvc.perform(put("/customers/" + createdCustomer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateCustomerJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("jane@example.com"))
                .andExpect(jsonPath("$.phone").value("987654321"));

        CustomerEntity updatedCustomer = customerRepository.findById(createdCustomer.getId()).get();
        assertThat(updatedCustomer.getName()).isEqualTo("Jane Doe");
        assertThat(updatedCustomer.getEmail()).isEqualTo("jane@example.com");
        assertThat(updatedCustomer.getPhone()).isEqualTo("987654321");
        assertThat(updatedCustomer.getId()).isEqualTo(createdCustomer.getId());
    }
}
