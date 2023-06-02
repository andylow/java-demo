package com.example.demo.config;

import com.example.demo.config.filter.UserLoggingFilter;
import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * For demo purpose.
 *
 * @author [yun]
 */

@Profile("local")
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(withDefaults())
                .addFilterAfter(new UserLoggingFilter(), BasicAuthenticationFilter.class)
                .securityMatcher("/v1/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v1/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() throws Exception {
        final var users = User.withDefaultPasswordEncoder();
        final var userDetailsManager = new InMemoryUserDetailsManager();

        userDetailsManager.createUser(users.username("demo").password("demo-password").roles("USER").build());
        userDetailsManager.createUser(users.username("demo-admin").password("demo-password").roles("USER", "ADMIN").build());

        log.warn("\n\nWARNING: USING LOCAL DUMMY SECURITY.\nTHIS IS FOR DEMO PURPOSE.\n\n");

        return userDetailsManager;
    }

}
