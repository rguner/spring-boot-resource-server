package com.guner.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class JWTSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                authorize -> authorize
                        .requestMatchers("/foos/*").hasAuthority("SCOPE_read")
                        .requestMatchers("/foos").hasAuthority("SCOPE_write")
                        .requestMatchers("/authorized/**").permitAll()
                        .anyRequest().authenticated())
                //.csrf(AbstractHttpConfigurer::disable)
                // .oauth2Login auth server login ekranına yönlendirme yapar, pom da spring-security-oauth2-client tanımı gerektirir
                .oauth2Login(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }
}