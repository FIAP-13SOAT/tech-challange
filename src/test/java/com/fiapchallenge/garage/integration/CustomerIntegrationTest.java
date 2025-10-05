package com.fiapchallenge.garage.integration;

import com.fiapchallenge.garage.adapters.outbound.repositories.JpaCustomerRepository;
import com.fiapchallenge.garage.adapters.outbound.entities.CustomerEntity;
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
public class CustomerIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final JpaCustomerRepository customerRepository;

    @Autowired
    public CustomerIntegrationTest(MockMvc mockMvc, JpaCustomerRepository customerRepository) {
        this.mockMvc = mockMvc;
        this.customerRepository = customerRepository;
    }

    @Test
    void shouldCreateCustomerAndPersistToDatabase() throws Exception {
        mockMvc.perform(post("/customers")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("name", "John Doe")
                .param("email", "john@example.com")
                .param("phone", "123456789"))
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
}
