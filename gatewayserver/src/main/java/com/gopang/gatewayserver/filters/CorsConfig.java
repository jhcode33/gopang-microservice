package com.gopang.gatewayserver.filters;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class CorsConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowCredentials(false)
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
//                .allowedHeaders("header1", "header2", "header3") // 요청에 허용할 헤더
//                .exposedHeaders("header1", "header2")            // 응답에 허용할 헤더
//                .allowCredentials(true).maxAge(3600);            // 인증 정보 유무

    }
}
