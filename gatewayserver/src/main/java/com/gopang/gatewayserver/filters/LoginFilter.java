package com.gopang.gatewayserver.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.gopang.gatewayserver.filters.FilterUtils.CORRELATION_ID;

@Component
@Slf4j
public class LoginFilter
        extends AbstractGatewayFilterFactory<LoginFilter.Config> {

    final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);
    private final FilterUtils filterUtils;

    public LoginFilter(FilterUtils filterUtils) {
        super(Config.class);
        this.filterUtils = filterUtils;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            // /oauth2/oauth2/login 요청 확인
            if ("/login".equals(request.getPath().value())) {
                // Config의 필드를 사용하여 OAuth 2.0 인증 서버로 리다이렉트할 URL 생성
                String authorizationUrl = buildAuthorizationUrl(config);

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

            // /oauth2/token 요청 확인
            if ("/login/callback".equals(request.getPath().value()) && !request.getHeaders().containsKey(CORRELATION_ID)) {
                // Config의 필드를 사용하여 액세스 토큰 요청
                String tokenEndpoint = "http://localhost:8072/oauth2/oauth2/token";
                WebClient webClient = WebClient.builder().build();

                // 최초 요청에 대한 정보를 가져옴
                ServerHttpRequest originalRequest = exchange.getAttribute("originalRequest");

                // 액세스 토큰 요청 파라미터 설정
                MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
                formData.add("grant_type", "authorization_code");
                formData.add("code", request.getQueryParams().getFirst("code"));
                formData.add("redirect_uri", "http://localhost:8072/login/callback");
                formData.add("client_id", "demo-client");
                formData.add("client_secret", "demo-client-secret");
                formData.add("code_verifier", config.getCode_verifier());

                // 액세스 토큰 엔드포인트에 POST 요청
                return webClient.post()
                        .uri(tokenEndpoint)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .bodyValue(formData)
                        .retrieve()
                        .bodyToMono(TokenJsonData.class)
                        .flatMap(tokenResponse -> {
                            // tokenResponse에서 원하는 데이터 추출
                            String accessToken = tokenResponse.getAccessToken();
                            String refreshToken = tokenResponse.getRefreshToken();
                            String idToken = tokenResponse.getIdToken();

                            // 최초 요청에 대한 정보를 사용하여 JSON 응답 생성
                            String jsonResponse;
                            try {
                                Map<String, String> tokenMap = new HashMap<>();
                                tokenMap.put("access_token", accessToken);
                                tokenMap.put("refresh_token", refreshToken);
                                tokenMap.put("id_token", idToken);

                                jsonResponse = new ObjectMapper().writeValueAsString(tokenMap);
                                log.info(jsonResponse);

                                // JSON 응답을 설정하고 응답을 클라이언트에게 보냅니다.
                                response.setStatusCode(HttpStatus.OK);
                                response.getHeaders().set(HttpHeaders.LOCATION, "http://127.0.0.1:9191/login/oauth2/code/demo-client-oidc");
                                return response.writeWith(
                                            Mono.just(response.bufferFactory()
                                                    .wrap(jsonResponse.getBytes(StandardCharsets.UTF_8))));

                            } catch (JsonProcessingException e) {
                                // JSON 변환 중에 예외가 발생한 경우 처리
                                logger.error("Error processing JSON response: {}", e.getMessage(), e);
                                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                                return Mono.empty();
                            }
                        });
            }

            // 다른 요청은 기존의 체인으로 계속 진행
            return chain.filter(exchange);
        };
    }


    public static class Config {
        private String client_id = "demo-client";
        private String response_type = "code";
        private String redirect_uri = "http://localhost:8072/login/callback";
        private String scope = "openid";
        private String state = "state";
        private final String code_verifier = "qPsH306-ZDDaOE8DFzVn05TkN3ZZoVmI_6x4LsVglQI";
        private final String code_challenge = "QYPAZ5NU8yvtlQ9erXrUYR-T5AGCjCF47vN-KsaI2A8";
        private final String code_challenge_method = "S256";

        private String generateCodeVerifier() {
            SecureRandom secureRandom = new SecureRandom();
            byte[] codeVerifier = new byte[32];
            secureRandom.nextBytes(codeVerifier);
            return Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifier);
        }

        private static String generateCodeChallenge(String codeVerifier) {
            byte[] codeVerifierBytes = codeVerifier.getBytes();
            return Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifierBytes);
        }

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

        public String getCode_verifier() {
            return code_verifier;
        }
    }

    private String buildAuthorizationUrl(Config config) {
        return String.format(
                "http://localhost:8085/oauth2/authorize?client_id=%s&response_type=%s&redirect_uri=%s&scope=%s&state=%s&code_challenge=%s&code_challenge_method=%s",
                config.getClient_id(), config.getResponse_type(), config.getRedirect_uri(),
                config.getScope(), config.getState(), config.getCode_challenge(), config.getCode_challenge_method());
    }

}
