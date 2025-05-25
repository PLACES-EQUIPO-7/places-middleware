package com.example.me.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    @Primary
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .cors(cors -> cors.configurationSource(getConfigurationSource()))
                .authorizeHttpRequests(request -> {
                    request
                            .requestMatchers(HttpMethod.POST, "/api/places/login/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/places/login/google").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/places/login/google/callback").permitAll()
                            .anyRequest().authenticated();
                })
                .oauth2ResourceServer(auth2 ->
                        auth2.jwt(Customizer.withDefaults()))
                .csrf(AbstractHttpConfigurer::disable)

                .build();
    }

    private CorsConfigurationSource getConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of("http://localhost:*", "http://127.0.0.1:*", "http://3.128.221.93:*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Content-Type", "Authorization"));
        config.setExposedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
