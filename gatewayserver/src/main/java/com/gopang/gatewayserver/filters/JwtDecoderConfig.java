//package com.gopang.gatewayserver.filters;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//
//@Configuration
//public class JwtDecoderConfig {
//
//    @Bean
//    public JwtDecoder jwtDecoder() {
//        // JWK Set URL을 통해 JWK를 가져와서 디코더를 생성
//        String jwkSetUri = "http://localhost:9090/oauth2/jwks";
//        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
//    }
//
//}
