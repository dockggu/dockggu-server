package com.DXsprint.dockggu.config;

import com.DXsprint.dockggu.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // cors 정책 (현재는 Application에서 작업을 해둬서 기본 설정 사용
                .cors().and()
                // csrf (cross site reques force?) 대책 (CSRF에 대한 대책 비활성화)
                .csrf().disable()
                // Basic 인증 (현재 Bearer Token 인증 방법을 사용해서 비활성화)
                .httpBasic().disable()
                // Session 사용여부 (현재는 Session 기반 인증 사용하지 않아서 비활성화)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // '/', '/api/auth' 모듈에 대해서는 모두 허용 (인증 안해도 사용가능하게 함)
                .authorizeHttpRequests().requestMatchers("/", "/api/auth/**").permitAll()
                // 나머지 Request에 대해서는 모두 인증된 사용자만 사용가능하게 함
                .anyRequest().authenticated();

        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
