package com.fiapchallenge.garage.integration;

import com.fiapchallenge.garage.adapters.outbound.entities.UserEntity;
import com.fiapchallenge.garage.adapters.outbound.repositories.user.JpaUserRepository;
import com.fiapchallenge.garage.application.user.CreateUserService;
import com.fiapchallenge.garage.application.user.LoginUserService;
import com.fiapchallenge.garage.domain.user.User;
import com.fiapchallenge.garage.integration.fixtures.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final JpaUserRepository jpaUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final CreateUserService createUserService;

    @Autowired
    public UserIntegrationTest(MockMvc mockMvc, JpaUserRepository jpaUserRepository, PasswordEncoder passwordEncoder, CreateUserService createUserService, LoginUserService loginUserService) {
        super(createUserService, loginUserService);

        this.mockMvc = mockMvc;
        this.jpaUserRepository = jpaUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.createUserService = createUserService;
    }

    @Test
    @DisplayName("Criação de Usuário via API")
    void shouldCreateUserViaApi() throws Exception {
        String userJson = """
                {
                  "fullname": "João Silva",
                  "email": "joao@gmail.com",
                  "password": "senhaSegura123"
                }
        """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullname").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@gmail.com"));

        UserEntity userEntity = jpaUserRepository.findAll().getLast();
        assertThat(userEntity).isNotNull();
        assertThat(userEntity.getFullname()).isEqualTo("João Silva");
        assertThat(userEntity.getEmail()).isEqualTo("joao@gmail.com");
        assertThat(userEntity.getPassword()).isNotEqualTo("senhaSegura123");
        assertThat(passwordEncoder.matches("senhaSegura123", userEntity.getPassword()));

        assertThat(userEntity.getId()).isNotNull();
    }

    @Test
    @DisplayName("Autenticação de Usuário via API")
    void shouldAuthenticateUserViaApi() throws Exception {
        User user = UserFixture.createUser(createUserService);
        String loginJson = """
                {
                  "email": "%s",
                  "password": "%s"
                }
        """.formatted(user.getEmail(), UserFixture.PASSWORD);

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.expiration").isNotEmpty());
    }
}
