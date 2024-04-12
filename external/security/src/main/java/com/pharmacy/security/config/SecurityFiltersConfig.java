package com.pharmacy.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityFiltersConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityFiltersConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider,
            LogoutHandler logoutHandler,
            CorsConfigurationSource corsConfigurationSource
        ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
        this.logoutHandler = logoutHandler;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
               authorizationManagerRequestMatcherRegistry
                  .requestMatchers("/api/v1/authenticate", "/api/v1/register").permitAll()
                  .anyRequest().authenticated()
            )
            .sessionManagement(sessionManagementConfigurer ->
               sessionManagementConfigurer
                  .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .logout(logoutConfigurer ->
               logoutConfigurer
                  .logoutUrl("/api/v1/logout") // if csrf.disabled() - GET /logout is allowed
                  .addLogoutHandler(logoutHandler)
                  .logoutSuccessHandler((request, response, authentication) ->
                      SecurityContextHolder.clearContext()
                  )
            )
            .cors(corsConfigurer -> {
                    // We must use global config instead of @CrossOrigin
                    // because /logout is not defined in AuthenticationController
                    corsConfigurer.configurationSource(corsConfigurationSource);
            });

        return http.build();
    }
}
