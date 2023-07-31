package com.example.bookStore.configure;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomJwtFilter customJwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer -> {
                    httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                });
        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry.requestMatchers("/api/v1/login").permitAll()
                    .requestMatchers("/api/v1/register").permitAll()
                    .anyRequest().authenticated();
        });
        http.addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors(httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(request -> {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
                corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                return corsConfiguration;
            });
        });
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
