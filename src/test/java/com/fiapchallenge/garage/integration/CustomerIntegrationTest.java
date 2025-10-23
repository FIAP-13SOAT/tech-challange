package com.fiapchallenge.garage.integration;

import com.fiapchallenge.garage.adapters.outbound.repositories.customer.JpaCustomerRepository;
import com.fiapchallenge.garage.adapters.outbound.entities.CustomerEntity;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final JpaCustomerRepository customerRepository;

    @Autowired
    public CustomerIntegrationTest(MockMvc mockMvc, JpaCustomerRepository customerRepository) {
        this.mockMvc = mockMvc;
        this.customerRepository = customerRepository;
    }

    @Test
    @DisplayName("Deve cadastrar um cliente e persistir")
    void shouldCreateCustomerAndPersistToDatabase() throws Exception {
        String customerJson = """
                {
                  "name": "John Doe",
                  "email": "john@example.com",
                  "phone": "123456789"
                }
                """;

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.phone").value("123456789"));

        CustomerEntity savedCustomer = customerRepository.findAll().get(0);
        assertThat(savedCustomer.getName()).isEqualTo("John Doe");
        assertThat(savedCustomer.getEmail()).isEqualTo("john@example.com");
        assertThat(savedCustomer.getPhone()).isEqualTo("123456789");
        assertThat(savedCustomer.getId()).isNotNull();
    }

    @Test
    @DisplayName("Deve atualizar um cliente existente")
    void shouldUpdateExistingCustomer() throws Exception {
        String createCustomerJson = """
                {
                  "name": "John Doe",
                  "email": "john@example.com",
                  "phone": "123456789"
                }
                """;

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createCustomerJson))
                .andExpect(status().isOk());

        CustomerEntity createdCustomer = customerRepository.findAll().get(0);

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
