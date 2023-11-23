package com.gopang.gatewayserver.filters;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(1)
@Component
@RequiredArgsConstructor
public class TrackingFilter implements GlobalFilter {

	private static final Logger logger = LoggerFactory.getLogger(TrackingFilter.class);

	private final FilterUtils filterUtils;
//	private final JwtDecoder jwtDecoder;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
		if (isCorrelationIdPresent(requestHeaders)) {
			logger.debug("tmx-correlation-id found in tracking filter: {}. ", 
					filterUtils.getCorrelationId(requestHeaders));
		} else {
			String correlationID = generateCorrelationId();
			exchange = filterUtils.setCorrelationId(exchange, correlationID);
			logger.debug("tmx-correlation-id generated in tracking filter: {}.", correlationID);
		}
		
		System.out.println("The authentication name from the token is : " + getUsername(requestHeaders));
		
		return chain.filter(exchange);
	}

	// 현재 요청 Header에 상관관계 ID가 있는지 확인하는 메소드
	private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
		if (filterUtils.getCorrelationId(requestHeaders) != null) {
			return true;
		} else {
			return false;
		}
	}

	// 상관관계 ID를 생성하는 메소드
	private String generateCorrelationId() {
		return java.util.UUID.randomUUID().toString();
	}

	private String getUsername(HttpHeaders requestHeaders){
		String username = "";
		if (filterUtils.getAuthToken(requestHeaders)!=null) {
			String authToken = filterUtils.getAuthToken(requestHeaders).replace("Bearer ","");
	        try {
	        	JSONObject jsonObj = decodeJWT(authToken);
	        	username = jsonObj.getString("sub");
	        } catch (Exception e) {
				logger.debug(e.getMessage());
			}
		}
		return username;
	}

	private JSONObject decodeJWT(String JWTToken) throws Exception {
//		Jwt jwt = jwtDecoder.decode(JWTToken);
//		// Jwt 객체에서 클레임(Map<String, Object>)을 가져오기
//		Map<String, Object> claims = jwt.getClaims();
//
//		// 클레임을 사용하여 JSONObject 만들기
//		JSONObject jsonObj = new JSONObject(claims);
//		return jsonObj;

		String[] split_string = JWTToken.split("\\.");
		String base64EncodedBody = split_string[1];
		Base64 base64Url = new Base64(true);
		String body = new String(base64Url.decode(base64EncodedBody));
		JSONObject jsonObj = new JSONObject(body);
		return jsonObj;
	}
}