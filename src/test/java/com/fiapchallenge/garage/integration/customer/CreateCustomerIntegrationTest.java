package com.fiapchallenge.garage.integration.customer;

import com.fiapchallenge.garage.adapters.outbound.repositories.customer.JpaCustomerRepository;
import com.fiapchallenge.garage.adapters.outbound.entities.CustomerEntity;
import com.fiapchallenge.garage.integration.BaseIntegrationTest;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class CreateCustomerIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final JpaCustomerRepository customerRepository;

    @Autowired
    public CreateCustomerIntegrationTest(MockMvc mockMvc, JpaCustomerRepository customerRepository) {
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
                  "phone": "123456789",
                  "cpfCnpj": "11144477735"
                }
                """;

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.phone").value("123456789"))
                .andExpect(jsonPath("$.cpfCnpj").value("11144477735"));

        CustomerEntity savedCustomer = customerRepository.findAll().get(0);
        assertThat(savedCustomer.getName()).isEqualTo("John Doe");
        assertThat(savedCustomer.getEmail()).isEqualTo("john@example.com");
        assertThat(savedCustomer.getPhone()).isEqualTo("123456789");
        assertThat(savedCustomer.getCpfCnpj()).isEqualTo("11144477735");
        assertThat(savedCustomer.getId()).isNotNull();
    }

    @Test
    @DisplayName("Deve retornar erro 400 para CPF/CNPJ inválido")
    void shouldReturnBadRequestForInvalidCpfCnpj() throws Exception {
        String invalidCustomerJson = """
                {
                  "name": "John Doe",
                  "email": "john@example.com",
                  "phone": "123456789",
                  "cpfCnpj": "12345678901"
                }
                """;

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidCustomerJson))
                .andExpect(status().isBadRequest());

        assertThat(customerRepository.findAll()).hasSize(0);
    }
}