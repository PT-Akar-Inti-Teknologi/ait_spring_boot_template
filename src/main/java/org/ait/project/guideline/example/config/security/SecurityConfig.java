package org.ait.project.guideline.example.config.security;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.config.security.filter.JwtFilter;
import org.ait.project.guideline.example.modules.permission.service.core.UserSessionService;
import org.ait.project.guideline.example.shared.utils.response.security.JwtUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableAutoConfiguration(exclude = UserDetailsServiceAutoConfiguration.class)
@EnableMethodSecurity
public class SecurityConfig {

  private final JwtUtils jwtUtils;
  private final HandlerExceptionResolver handlerExceptionResolver;
  private final UserSessionService userSessionService;


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(
            registry -> registry.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR)
                .permitAll()
                .requestMatchers(
                    "/auth/login",
                    "/auth/refresh-token",
                    "/swagger-ui/**",
                    "/v3/api-docs/**"
                )
                .permitAll().anyRequest().authenticated())
        .sessionManagement(
            manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(new JwtFilter(jwtUtils, handlerExceptionResolver, userSessionService),
            UsernamePasswordAuthenticationFilter.class);


    return http.build();
  }
}
