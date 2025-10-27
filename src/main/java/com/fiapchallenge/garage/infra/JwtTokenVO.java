package com.fiapchallenge.garage.infra;

import java.time.Instant;

public record JwtTokenVO(
        String token,
        Instant expiration
) {
}
