package com.fiapchallenge.garage.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

// @Component removido — com permitAll() no SecurityConfig, não precisa de filter
public class ApiGatewayAuthFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(ApiGatewayAuthFilter.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);
                String[] parts = token.split("\\.");
                if (parts.length == 3) {
                    String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
                    @SuppressWarnings("unchecked")
                    Map<String, Object> claims = objectMapper.readValue(payload, Map.class);

                    String subject = (String) claims.get("sub");
                    String role = (String) claims.getOrDefault("role", "CUSTOMER");

                    var authority = new SimpleGrantedAuthority("ROLE_" + role);
                    var auth = new UsernamePasswordAuthenticationToken(subject, null, Collections.singletonList(authority));
                    SecurityContextHolder.getContext().setAuthentication(auth);

                    log.info("API Gateway auth: sub={}, role={}", subject, role);
                }
            } catch (Exception e) {
                log.warn("Falha ao extrair role do JWT: {}", e.getMessage());
            }
        }

        chain.doFilter(request, response);
    }
}
