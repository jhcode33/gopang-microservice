package com.gopang.gatewayserver.filters.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gopang.gatewayserver.filters.ResponseFilter;
import com.gopang.gatewayserver.filters.TokenJsonData;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.gopang.gatewayserver.filters.FilterUtils.CORRELATION_ID;

@Component
@Slf4j
public class GeneratorTokenFilter extends AbstractGatewayFilterFactory<LoginConfig> {

    final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);

    public GeneratorTokenFilter() {
        super(LoginConfig.class);
    }

    @Override
    public GatewayFilter apply(LoginConfig config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            // /login/callback 요청 확인
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
                formData.add("client_secret", config.getClient_secret());
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
}
