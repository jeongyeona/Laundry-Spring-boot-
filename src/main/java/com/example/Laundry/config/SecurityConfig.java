package com.example.Laundry.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 1) 정적 리소스
                        .requestMatchers(
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/webjars/**",
                                "/favicon.ico"
                        ).permitAll()

                        // 2) 뷰를 반환하는 페이지들
                        .requestMatchers(
                                "/", "/LoginInfo/home", "/LoginInfo/Login",
                                "/LoginInfo/FindId", "/LoginInfo/FindPwd", "/LoginInfo/Signup"
                        ).permitAll()

                        // 3) AJAX/API
                        .requestMatchers(HttpMethod.GET,  "/CheckId", "/LoginInfo/CheckId")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/SignupPost", "/LoginInfo/SignupPost")
                        .permitAll()
                        .requestMatchers("/error").permitAll()

                        // 4) 위에 매칭되지 않은 모든 요청은 인증 요구
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());

        return http.build();
    }
}
