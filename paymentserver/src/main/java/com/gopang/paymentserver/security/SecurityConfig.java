package com.gopang.paymentserver.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            // HTTP 기본 인증 비활성화
            .httpBasic(basic -> basic.disable())
            // CORS(Cross-Origin Resource Sharing) 설정을 기본값으로 설정
            .cors(Customizer.withDefaults())
            // CSRF(Cross-Site Request Forgery) 보호 기능 비활성화
            .csrf(csrf -> csrf.disable())
            // 요청에 대한 인증 및 권한 설정
            .authorizeRequests(
                    request ->
                    {
                      // OPTIONS 메서드에 대한 요청은 모두 허용
                      request.requestMatchers(HttpMethod.OPTIONS).permitAll()
                              // PATCH 메서드에 대한 요청은 루트 경로에서만 허용
                              .requestMatchers(HttpMethod.PATCH, "/").permitAll()
                              // "/api/payment/pay/**" 패턴에 대한 요청은 모두 허용
                              .requestMatchers("/api/payment/pay/**").permitAll()
                              // "/api/payment/**" 패턴에 대한 요청은 모두 허용
                              .requestMatchers("/api/payment/**").permitAll();
                    });
    // 보안 필터 체인 설정 완료 후 반환
    return httpSecurity.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    // 허용된 오리진(Origin) 설정 (여기서는 "http://localhost:8080"만 허용)
    configuration.setAllowedOrigins(Arrays.asList("*"));
    // 허용된 HTTP 메서드 설정 (GET, POST, PUT, DELETE 허용)
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    // 모든 헤더(Header) 허용
    configuration.setAllowedHeaders(Arrays.asList("*"));
    // 노출할 헤더(Exposed Headers) 설정 (여기서는 모든 헤더 노출)
    configuration.setExposedHeaders(Arrays.asList("*"));
    // CORS 구성을 URL 기반으로 설정
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    // CORS 구성 반환
    return source;
  }
}
