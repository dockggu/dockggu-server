package com.DXsprint.dockggu.config;

import com.DXsprint.dockggu.filter.JwtAuthenticationFilter;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

import static com.google.gson.internal.$Gson$Types.arrayOf;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 예외하고싶은 url
        return (web) -> web.ignoring().requestMatchers("/image/**", "/status");
    }
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
                .authorizeHttpRequests().requestMatchers("/", "/api/auth/**","/api/oauth/**","/error").permitAll()
                // 나머지 Request에 대해서는 모두 인증된 사용자만 사용가능하게 함
                .anyRequest().authenticated();


//        httpSecurity
//                .csrf().disable().cors().disable()
//                .authorizeHttpRequests(request -> request
//                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
//                        .requestMatchers("/status", "/images/**", "/view/join","/api/auth/**","/").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(login -> login
//                        .loginPage("/view/login")
//                        .loginProcessingUrl("/login-process")
//                        .usernameParameter("userid")
//                        .passwordParameter("password")
//                        .defaultSuccessUrl("/view/dashboard", true)
//                        .permitAll()
//                )
//                .logout(withDefaults());

        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//        response.sendRedirect(request.getContextPath() + "/");
//    }
}
