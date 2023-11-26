package com.gopang.gatewayserver.filters.login;

import com.gopang.gatewayserver.filters.FilterUtils;
import com.gopang.gatewayserver.filters.ResponseFilter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.gopang.gatewayserver.filters.FilterUtils.CORRELATION_ID;

@Component
@Slf4j
public class LoginFilter
        extends AbstractGatewayFilterFactory<LoginConfig> {

    final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);
    private final FilterUtils filterUtils;

    public LoginFilter(FilterUtils filterUtils) {
        super(LoginConfig.class);
        this.filterUtils = filterUtils;
    }

    @Override
    public GatewayFilter apply(LoginConfig config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            // /oauth2/oauth2/login 요청 확인
            if ("/login".equals(request.getPath().value())) {
                // Config의 필드를 사용하여 OAuth 2.0 인증 서버로 리다이렉트할 URL 생성
                String authorizationUrl = buildAuthorizationUrl(config);
                log.info("URL: {}", authorizationUrl);

                // 생성한 URL로 리다이렉트
                return Mono.fromRunnable(() -> {
                    HttpHeaders requestHeaders = request.getHeaders();

                    String correlationId = filterUtils.getCorrelationId(requestHeaders);
                    logger.debug("Adding the correlation id to the outbound headers. {}", correlationId);

                    // Header에 상관관계 ID를 넣습니다.
                    exchange.getResponse().getHeaders().add(CORRELATION_ID, correlationId);
                    logger.debug("Completing outgoing request for {}.", exchange.getRequest().getURI());

                    // 최초 요청에 대한 정보를 저장
                    exchange.getAttributes().put("originalRequest", request);

                    response.setStatusCode(HttpStatus.SEE_OTHER);
                    response.getHeaders().set(HttpHeaders.LOCATION, authorizationUrl);
                });
            }
            // 다른 요청은 기존의 체인으로 계속 진행
            return chain.filter(exchange);
        };
    }

    private String buildAuthorizationUrl(LoginConfig config) {
        return String.format(
                "http://localhost:8085/oauth2/authorize?client_id=%s&response_type=%s&redirect_uri=%s&scope=%s&state=%s&code_challenge=%s&code_challenge_method=%s",
                config.getClient_id(), config.getResponse_type(), config.getRedirect_uri(),
                config.getScope(), config.getState(), config.getCode_challenge(), config.getCode_challenge_method());
    }
}
