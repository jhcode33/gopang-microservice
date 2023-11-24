package com.gopang.gatewayserver.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class CustomFilterGatewayFilterFactory
        extends AbstractGatewayFilterFactory<CustomFilterGatewayFilterFactory.Config> {

    public CustomFilterGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            // 로그인 요청만 처리
            if ("/login".equals(request.getPath().value())) {
                Flux<DataBuffer> requestBody = request.getBody();

                String requestBodyString = requestBody.map(buffer -> {
                    byte[] bytes = new byte[buffer.readableByteCount()];
                    buffer.read(bytes);
                    DataBufferUtils.release(buffer);
                    return new String(bytes, StandardCharsets.UTF_8);
                }).reduce(String::concat).block();

                // Config 필드를 포함한 JSON 문자열 생성
                String configJson = String.format("{\"client_id\":\"%s\",\"response_type\":\"%s\",\"redirect_uri\":\"%s\",\"scope\":\"%s\",\"state\":\"%s\",\"code_challenge\":\"%s\",\"code_challenge_method\":\"%s\"}",
                        config.client_id(), config.getResponse_type(), config.getRedirect_uri(),
                        config.getScope(), config.getState(), config.getCode_challenge(), config.getCode_challenge_method());

                // 요청 바디 수정하여 Config 필드 추가
                String modifiedBody = mergeJsonStrings(requestBodyString, configJson);

                // 수정된 바디를 요청에 설정
                ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                        .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(modifiedBody.length()))
                        .body(modifiedBody.getBytes(StandardCharsets.UTF_8)).build();

                // 수정된 요청으로 필터 체인 계속 진행
                return chain.filter(exchange.mutate().request(modifiedRequest).build());
            }

            // 로그인이 아닌 요청에 대해 필터 체인 계속 진행
            return chain.filter(exchange);
        });
    }

    private String mergeJsonStrings(String originalJson, String additionalJson) {
        // 원래의 JSON과 추가 JSON을 병합
        // 이 간단한 예제에서는 originalJson이 유효한 JSON 객체라고 가정합니다.
        return originalJson.substring(0, originalJson.length() - 1) + "," + additionalJson.substring(1);
    }

    public static class Config {
        private String client_id = "demo-client";
        private String response_type = "code";
        private String redirect_uri = "http://127.0.0.1:9191/login/oauth2/code/demo-client-oidc";
        private String scope = "openid";
        private String state = "state";
        private String code_challenge;
        private String code_challenge_method;

        public Object client_id() {
            return client_id;
        }

        public Object getResponse_type() {
            return response_type;
        }

        public Object getRedirect_uri() {
            return getRedirect_uri();
        }

        public Object getScope() {
            return getScope();
        }

        public Object getState() {
            return getState();
        }

        public Object getCode_challenge() {
            return getCode_challenge();
        }

        public Object getCode_challenge_method() {
            return getCode_challenge_method();
        }
    }
}
