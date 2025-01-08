package com.ll.sbbrestapi20250106.global.security;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationFilter customAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/*/question_list/{id:\\d+}", "/api/*/question_list", "/api/*/question_list/{id:\\d+}/answer_list")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .headers(headers ->
                        headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }

}
