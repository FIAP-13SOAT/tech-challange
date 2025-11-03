package com.fiapchallenge.garage.infra;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class UserSecurityFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private final UserDetailsService userDetailsService;
    private final JwtHelper jwtHelper;
    private final Logger log = LoggerFactory.getLogger(UserSecurityFilter.class);

    public UserSecurityFilter(UserDetailsService userDetailsService, JwtHelper jwtHelper) {
        this.userDetailsService = userDetailsService;
        this.jwtHelper = jwtHelper;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/swagger-ui") || 
               path.startsWith("/v3/api-docs") || 
               path.equals("/swagger-ui.html") ||
               path.startsWith("/swagger-resources") ||
               path.startsWith("/actuator/health") ||
               path.equals("/users/login") ||
               (path.equals("/users") && "POST".equals(request.getMethod()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(AUTHORIZATION);

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            jwt = authorizationHeader.substring(BEARER.length());
            try {
                username = jwtHelper.extractUsername(jwt);
                log.info("Token processado para usuário: {}", username);
            } catch (ExpiredJwtException e) {
                log.error("Token JWT expirado!");
            } catch (Exception e) {
                log.error("Não é possível analisar o token JWT");
            }
        } else {
            log.debug("Header Authorization não encontrado ou inválido para: {}", request.getRequestURI());
        }

        if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtHelper.validateToken(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
