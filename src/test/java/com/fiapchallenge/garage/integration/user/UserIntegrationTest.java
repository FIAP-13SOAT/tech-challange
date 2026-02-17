package com.fiapchallenge.garage.integration.user;

import com.fiapchallenge.garage.adapters.outbound.entities.UserEntity;
import com.fiapchallenge.garage.adapters.outbound.gateways.user.JpaUserRepository;
import com.fiapchallenge.garage.application.user.CreateUserService;
import com.fiapchallenge.garage.application.user.LoginUserService;
import com.fiapchallenge.garage.domain.user.User;
import com.fiapchallenge.garage.integration.BaseIntegrationTest;
import com.fiapchallenge.garage.integration.fixtures.UserFixture;
import com.fiapchallenge.garage.domain.user.UserRole;
import com.fiapchallenge.garage.application.user.command.CreateUserCommand;
import org.apache.juli.logging.Log;
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
class UserIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final JpaUserRepository jpaUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final CreateUserService createUserService;
    private final LoginUserService loginUserService;

    @Autowired
    public UserIntegrationTest(MockMvc mockMvc, JpaUserRepository jpaUserRepository, PasswordEncoder passwordEncoder, CreateUserService createUserService, LoginUserService loginUserService) {
        super(createUserService, loginUserService);

        this.mockMvc = mockMvc;
        this.jpaUserRepository = jpaUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.createUserService = createUserService;
        this.loginUserService = loginUserService;
    }

    @Test
    @DisplayName("Criação de Usuário via API")
    void shouldCreateUserViaApi() throws Exception {
        CreateUserCommand adminCommand = new CreateUserCommand(
                "Admin User",
                "admin" + System.currentTimeMillis() + "@test.com",
                "admin123",
                UserRole.ADMIN
        );

        User adminUser = createUserService.handle(adminCommand);
        String adminToken = UserFixture.login(adminUser.getEmail(), "admin123", loginUserService);

        String userJson = """
                {
                  "fullname": "João Silva",
                  "email": "joao@gmail.com",
                  "password": "senhaSegura123",
                  "role": "MECHANIC"
                }
        """;

        mockMvc.perform(post("/users")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullname").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@gmail.com"))
                .andExpect(jsonPath("$.role").value("MECHANIC"));

        UserEntity userEntity = jpaUserRepository.findAll().getLast();
        assertThat(userEntity).isNotNull();
        assertThat(userEntity.getFullname()).isEqualTo("João Silva");
        assertThat(userEntity.getEmail()).isEqualTo("joao@gmail.com");
        assertThat(userEntity.getPassword()).isNotEqualTo("senhaSegura123");
        assertThat(passwordEncoder.matches("senhaSegura123", userEntity.getPassword())).isTrue();

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
