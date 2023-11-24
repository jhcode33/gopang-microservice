package com.gopang.gatewayserver.filters;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class CustomFilter
        extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    public CustomFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            // /oauth2/oauth2/login 요청 확인
            if ("/oauth2/oauth2/login".equals(request.getPath().value())) {
                // Config의 필드를 사용하여 URL 생성
                String redirectUrl = String.format(
                        "http://localhost:8085/oauth2/authorize?client_id=%s&response_type=%s&redirect_uri=%s&scope=%s&state=%s&code_challenge=%s&code_challenge_method=%s",
                        config.getClient_id(), config.getResponse_type(), config.getRedirect_uri(),
                        config.getScope(), config.getState(), config.getCode_challenge(), config.getCode_challenge_method());

                // 생성한 URL로 리다이렉트
                return Mono.fromRunnable(() -> {
                    response.setStatusCode(HttpStatus.SEE_OTHER);
                    response.getHeaders().set(HttpHeaders.LOCATION, redirectUrl);
                });
            }

            // 다른 요청은 기존의 체인으로 계속 진행
            return chain.filter(exchange);
        };
    }

    public static class Config {
        private String client_id = "demo-client";
        private String response_type = "code";
        private String redirect_uri = "http://127.0.0.1:9191/login/oauth2/code/demo-client-oidc";
        private String scope = "openid";
        private String state = "state";
        private String code_challenge;
        private String code_challenge_method;

        public Object getClient_id() {
            return client_id;
        }

        public Object getResponse_type() {
            return response_type;
        }

        public Object getRedirect_uri() {
            return redirect_uri;
        }

        public Object getScope() {
            return scope;
        }

        public Object getState() {
            return state;
        }

        public Object getCode_challenge() {
            return code_challenge;
        }

        public Object getCode_challenge_method() {
            return code_challenge_method;
        }
    }
}
