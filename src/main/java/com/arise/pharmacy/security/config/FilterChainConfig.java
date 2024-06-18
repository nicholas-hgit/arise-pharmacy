package com.arise.pharmacy.security.config;

import com.arise.pharmacy.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.arise.pharmacy.security.roles.Role.SHOPPER;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class FilterChainConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req -> req
                                .requestMatchers(HttpMethod.POST,"/api/v1/auth","/api/v1/users/register")
                                .permitAll()
                                .requestMatchers("/api/v1/info/user/**")
                                .hasRole(SHOPPER.name())
                                .anyRequest()
                                .authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(c -> c.sessionCreationPolicy(STATELESS));

        return http.build();
    }
}
