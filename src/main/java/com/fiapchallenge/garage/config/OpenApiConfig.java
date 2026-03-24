package com.fiapchallenge.garage.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Value("${app.api-gateway-url:}")
    private String apiGatewayUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .name(SECURITY_SCHEME_NAME)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .description("Token JWT para autenticação. Use POST /login (CPF) ou POST /admin/login (email/password) para obter o token.");

        OpenAPI openAPI = new OpenAPI()
                .info(new Info()
                        .title("Garage API")
                        .version("1.0.0")
                        .description("API de gerenciamento de oficina mecânica — Projeto Garage (FIAP Pós-Graduação)"))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, securityScheme))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME));

        if (apiGatewayUrl != null && !apiGatewayUrl.isEmpty()) {
            openAPI.servers(List.of(
                new Server().url(apiGatewayUrl).description("API Gateway (produção)")
            ));
        }

        return openAPI;
    }
}
