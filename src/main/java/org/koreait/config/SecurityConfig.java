package org.koreait.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.koreait.models.user.LoginFailureHandler;
import org.koreait.models.user.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage("/user/login") // 로그인 페이지경로
                //.defaultSuccessUrl("/") // 로그인 성공시 이동할 경로
                .successHandler(new LoginSuccessHandler()) // 로그인 성공 시 여러 상황처리.....
                .usernameParameter("userId")
                .passwordParameter("userPw")
                //.failureUrl("/user/login") // 로그인 실패시 이동할 경로
                .failureHandler(new LoginFailureHandler()) // 로그인 실패 시 여러 상황처리.....
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/user/login"); //로그아웃 성공 시 이동할 경로

        http.authorizeHttpRequests()
                .requestMatchers("/mypage/**").authenticated() // 로그인 한 회원만 가능한 URL
                .requestMatchers("/admin/**").hasAuthority("ADMIN") // 관리자만 접근 가능한 URL
                .anyRequest().permitAll();

        // 관리자 접근 권한 없을 시 -> 접근권한이 없습니다. 401 - Unauthorized
        // 비회원 접근 권한이 없는 경우 -> 로그인 페이지
        http.exceptionHandling()
                .authenticationEntryPoint((req, res, e) -> {
                    String redirectUrl = "/user/login";
                    String URI = req.getRequestURI();
                    if(URI.indexOf("/admin") != -1) { // 관리자 페이지
                        redirectUrl = "/error/401";
                    }

                    res.sendRedirect(redirectUrl);
                });
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return w -> w.ignoring()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/api/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
