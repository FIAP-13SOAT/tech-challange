package com.fiapchallenge.garage.shared.jwt;

import java.time.Instant;

public record JwtTokenVO(
        String token,
        Instant expiration
) {
}
