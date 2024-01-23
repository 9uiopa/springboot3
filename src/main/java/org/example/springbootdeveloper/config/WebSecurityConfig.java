//package org.example.springbootdeveloper.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

//@RequiredArgsConstructor
//@Configuration
//public class WebSecurityConfig {
//    private final UserDetailsService userService;
//
//    // H2 데이터베이스 console과 정적 리소스(js)에 대한 요청 : 보안 검사 생략
//    @Bean
//    public WebSecurityCustomizer configure(){ //WebSecurityCustomizer가 interface
//        return (web) -> web.ignoring() // 구현체.. 람다식으로 간결히 표현
//                .requestMatchers(toH2Console())
//                .requestMatchers("/static/**");
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests() //인증과 인가 설정
//                .requestMatchers("/login", "/signup", "/user").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin() // 폼 기반 로그인 설정
//                .loginPage("/login")
//                .defaultSuccessUrl("/articles")
//                .and()
//                .logout()
//                .logoutSuccessUrl("/login")
//                .invalidateHttpSession(true) // 로그아웃 시 세션무효화하여 세션공격 방지.
//                .and()
//                .csrf().disable() // csrf 공격 방어용이지만 실습 편의상 비활성화
//                .build();
//    }
//    // AuthenticationManager 관련 설정
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService) throws Exception{
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userService)
//                .passwordEncoder(bCryptPasswordEncoder)
//                .and()
//                .build();
//    }
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//

//}
