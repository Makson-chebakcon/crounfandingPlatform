package ru.cft.shift.crowdfundingplatformapi.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import ru.cft.shift.crowdfundingplatformapi.security.JwtFilter;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static final String ADMIN = "ADMIN";
    private static final String MODER = "MODER";
    private final JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(configurer -> configurer.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedMethods(List.of("*"));
                    corsConfiguration.setAllowedOrigins(List.of("*"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.setMaxAge(3600L);

                    return corsConfiguration;
                }))
                .authorizeHttpRequests(
                        requests -> requests
                                .requestMatchers(HttpMethod.GET, "api/v1/persons/projects/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "api/v1/persons/projects/*/sponsorship").authenticated()
                                .requestMatchers(HttpMethod.POST, "api/v1/projects").authenticated()
                                .requestMatchers(HttpMethod.GET, "api/v1/profiles").authenticated()
                                .requestMatchers(HttpMethod.PUT, "api/v1/profiles").authenticated()
                                .requestMatchers(HttpMethod.PUT, "api/v1/persons/*/role").hasRole(ADMIN)
                                .requestMatchers(HttpMethod.POST, "api/v1/persons/search").hasRole(ADMIN)
                                .requestMatchers(HttpMethod.POST, "api/v1/promo-codes").hasRole(ADMIN)
                                .requestMatchers(HttpMethod.POST, "api/v1/promo-codes/*").authenticated()
                                .requestMatchers(HttpMethod.POST, "api/v1/project-requests/**").hasAnyRole(ADMIN, MODER)
                                .anyRequest().permitAll()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
