package com.fiapchallenge.garage.shared.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Base64;
import java.util.UUID;

public class JwtUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static UUID extractUserIdFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        
        String token = authHeader.substring(7);
        return extractUserIdFromToken(token);
    }

    private static UUID extractUserIdFromToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new RuntimeException("Invalid JWT token format");
            }
            
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            JsonNode payloadNode = objectMapper.readTree(payload);
            
            String userIdStr = payloadNode.get("user_id").asText();
            return UUID.fromString(userIdStr);
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract user ID from token", e);
        }
    }
}