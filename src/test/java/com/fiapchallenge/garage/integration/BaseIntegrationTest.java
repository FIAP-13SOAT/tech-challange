package com.fiapchallenge.garage.integration;

import com.fiapchallenge.garage.domain.user.UserRole;
import com.fiapchallenge.garage.integration.config.VehicleTestUseCaseConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
@Import(VehicleTestUseCaseConfig.class)
@Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public abstract class BaseIntegrationTest {

    private static final Key TEST_KEY = Keys.hmacShaKeyFor("test-secret-key-for-integration-tests-only-32bytes".getBytes());

    protected String getAuthToken() {
        return getAuthTokenForRole(UserRole.CLERK);
    }

    protected String getAuthTokenForRole(UserRole role) {
        String token = Jwts.builder()
                .setSubject(UUID.randomUUID().toString())
                .claim("role", role.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(TEST_KEY, SignatureAlgorithm.HS256)
                .compact();
        return "Bearer " + token;
    }
}
