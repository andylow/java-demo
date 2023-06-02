package com.example.demo.config.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final var req = (HttpServletRequest) servletRequest;
        MDC.put("request_uri", req.getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
