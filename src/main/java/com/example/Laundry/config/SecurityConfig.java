package com.example.Laundry.config;

import static org.springframework.security.config.Customizer.withDefaults;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        // SimpleUrlAuthenticationSuccessHandler를 상속해서 세션에 값 저장
        return new SimpleUrlAuthenticationSuccessHandler("/") {
            @Override
            public void onAuthenticationSuccess(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    Authentication authentication
            ) throws IOException, ServletException {
                // 로그인 성공 시 세션에 LOGIN_USER 저장
                request.getSession().setAttribute("LOGIN_USER", authentication.getName());
                super.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .logout(logout -> logout
                        .logoutUrl("/Logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/Logout","GET"))
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                    .frameOptions(frame -> frame.sameOrigin())
                    .addHeaderWriter(new StaticHeadersWriter("Permissions-Policy", "unload=(self)"))
                )
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
                                "/LoginInfo/FindId", "/LoginInfo/FindPwd", "/LoginInfo/Signup",
                                "/LoginInfo/LoginPost", "/LoginInfo/Logout", "/Corporation/Brand",
                                "/Corporation/History", "/Corporation/Startup","Guide/PriceGuide",
                                "/Guide/AreaGuide", "/Notice/List", "/Notice/NoticeDetail"
                        ).permitAll()

                        // 3) AJAX/API
                        .requestMatchers(HttpMethod.GET,  "/CheckId", "/LoginInfo/CheckId")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET,  "/PriceGuide", "/PriceGuideFragment")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/SignupPost", "/LoginInfo/SignupPost")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/LoginInfo/LoginPost").permitAll()
                        .requestMatchers("/error").permitAll()

                        // 4) 위에 매칭되지 않은 모든 요청은 인증 요구
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/LoginInfo/Login")            // GET: 로그인 폼
                        .loginProcessingUrl("/LoginInfo/LoginPost") // POST: 인증 처리
                        .usernameParameter("id")                   // <input name="id">
                        .passwordParameter("pwd")                  // <input name="pwd">
                        .successHandler(successHandler())          // 세션에 LOGIN_USER 저장
                        .failureUrl("/LoginInfo/Login?error=true")      // 인증 실패 시
                        .permitAll()
                );

        return http.build();
    }
}
