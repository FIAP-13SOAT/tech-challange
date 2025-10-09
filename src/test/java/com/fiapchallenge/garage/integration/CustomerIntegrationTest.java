package com.fiapchallenge.garage.integration;

import com.fiapchallenge.garage.adapters.outbound.repositories.customer.JpaCustomerRepository;
import com.fiapchallenge.garage.adapters.outbound.entities.CustomerEntity;
import com.fiapchallenge.garage.application.customer.create.CreateCustomerUseCase;
import com.fiapchallenge.garage.domain.customer.Customer;
import com.fiapchallenge.garage.utils.CustomerMockUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final JpaCustomerRepository customerRepository;
    private final CreateCustomerUseCase createCustomerUseCase;

    @Autowired
    public CustomerIntegrationTest(MockMvc mockMvc, JpaCustomerRepository customerRepository, CreateCustomerUseCase createCustomerUseCase) {
        this.mockMvc = mockMvc;
        this.customerRepository = customerRepository;
        this.createCustomerUseCase = createCustomerUseCase;
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
    @DisplayName("Deve atualizar um cliente existente")
    void shouldUpdateExistingCustomer() throws Exception {
        Customer createdCustomer = CustomerMockUtils.createCustomer(createCustomerUseCase);

        String updateCustomerJson = """
                {
                  "name": "Jane Doe",
                  "email": "jane@example.com",
                  "phone": "987654321",
                  "cpfCnpj": "11222333000181"
                }
                """;

        mockMvc.perform(put("/customers/" + createdCustomer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateCustomerJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("jane@example.com"))
                .andExpect(jsonPath("$.phone").value("987654321"))
                .andExpect(jsonPath("$.cpfCnpj").value("11222333000181"));

        CustomerEntity updatedCustomer = customerRepository.findById(createdCustomer.getId()).get();
        assertThat(updatedCustomer.getName()).isEqualTo("Jane Doe");
        assertThat(updatedCustomer.getEmail()).isEqualTo("jane@example.com");
        assertThat(updatedCustomer.getPhone()).isEqualTo("987654321");
        assertThat(updatedCustomer.getCpfCnpj()).isEqualTo("11222333000181");
        assertThat(updatedCustomer.getId()).isEqualTo(createdCustomer.getId());
    }

    @Test
    @DisplayName("Deve listar todos os clientes")
    void shouldListAllCustomers() throws Exception {
        CustomerMockUtils.createCustomer(createCustomerUseCase);
        CustomerMockUtils.createCustomer(createCustomerUseCase, "Jane Smith", "jane@example.com", "987654321", "11222333000181");

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"));

        assertThat(customerRepository.findAll()).hasSize(2);
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

    @Test
    @DisplayName("Deve deletar um cliente existente")
    void shouldDeleteExistingCustomer() throws Exception {
        Customer createdCustomer = CustomerMockUtils.createCustomer(createCustomerUseCase);

        mockMvc.perform(delete("/customers/" + createdCustomer.getId()))
                .andExpect(status().isNoContent());

        assertThat(customerRepository.findById(createdCustomer.getId())).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar erro 404 ao tentar deletar cliente inexistente")
    void shouldReturnNotFoundWhenDeletingNonExistentCustomer() throws Exception {
        mockMvc.perform(delete("/customers/" + UUID.randomUUID()))
                .andExpect(status().isBadRequest());
    }
}
