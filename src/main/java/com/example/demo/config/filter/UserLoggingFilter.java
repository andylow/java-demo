package com.example.demo.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

public class UserLoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final var strategy = SecurityContextHolder.getContextHolderStrategy();

        if (Objects.nonNull(strategy.getContext().getAuthentication())
                && strategy.getContext().getAuthentication().isAuthenticated()) {
            MDC.put("user", ((User) strategy.getContext().getAuthentication().getPrincipal()).getUsername());
        }

        filterChain.doFilter(request, response);
    }
}
