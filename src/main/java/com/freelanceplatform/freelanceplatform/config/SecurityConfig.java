package com.freelanceplatform.freelanceplatform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions().sameOrigin())// CSRF uitschakelen om POST-aanvragen te kunnen verwerken
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui/**", "/webjars/**").permitAll()
                        .requestMatchers("/api/**").permitAll()  // Sta POST en GET naar deze endpoints toe
                        .anyRequest().permitAll()  // Sta alle andere verzoeken toe
                );
        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:5173")  // Vervang door de correcte frontend URL
                        .allowedMethods("GET", "POST","PUT","DELETE")  // Sta GET en POST toe
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
