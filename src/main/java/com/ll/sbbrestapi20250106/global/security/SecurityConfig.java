package com.ll.sbbrestapi20250106.global.security;

import com.ll.sbbrestapi20250106.global.rsData.RsData;
import com.ll.sbbrestapi20250106.standard.util.Ut;
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
                                .requestMatchers("/api/*/user/login", "/api/*/user/sign-up", "/api/v1/user/logout").permitAll()
                                .requestMatchers("/api/v1/question_list/statistics").hasAuthority("ROLE_ADMIN")
                                .anyRequest()
                                .authenticated()
                )
                .headers(headers ->
                        headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> {
                                    response.setContentType("application/json; charset=utf-8");

                                    response.setStatus(401);
                                    response.getWriter().write(
                                            Ut.json.toString(
                                                    new RsData("401-1", "사용자의 인증정보가 올바르지 않습니다.")
                                            )
                                    );
                                }
                        )
                        .accessDeniedHandler(
                                ((request, response, accessDeniedException) -> {
                                    response.setContentType("application/json; charset=utf-8");

                                    response.setStatus(403);
                                    response.getWriter().write(
                                            Ut.json.toString(
                                                    new RsData("403-1", "접근 권한이 없습니다.")
                                            )
                                    );
                                }
                                )
                        )
                )
        ;

        return http.build();
    }

}
