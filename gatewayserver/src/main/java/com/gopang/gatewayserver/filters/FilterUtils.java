package com.gopang.gatewayserver.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

/**
 * HTTP Header 작업 유틸리티
 */
@Component
public class FilterUtils {

	public static final String CORRELATION_ID = "tmx-correlation-id";
	public static final String AUTH_TOKEN     = "Authorization";
	public static final String PRE_FILTER_TYPE = "pre";
	public static final String POST_FILTER_TYPE = "post";
	public static final String ROUTE_FILTER_TYPE = "route";

	//== 상관관계 ID를 찾는 메소드 ==//
	public String getCorrelationId(HttpHeaders requestHeaders){
		if (requestHeaders.get(CORRELATION_ID) != null) {
			List<String> header = requestHeaders.get(CORRELATION_ID);
			return header.stream().findFirst().get();
		}
		else{
			return null;
		}
	}

	//== Header에서 Authorization을 찾고 token 값을 반환하는 메소드 ==//
	public String getAuthToken(HttpHeaders requestHeaders){
		if (requestHeaders.get(AUTH_TOKEN) != null) {
			List<String> header = requestHeaders.get(AUTH_TOKEN);
			return header.stream().findFirst().get();
		}
		else{
			return null;
		}
	}

	public ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
		return exchange.mutate().request(					 // ServerWebExchange을 수정 가능한 상태로 변경
							exchange.getRequest().mutate()	 // Request를 수정 가능한 상태로 변경
							.header(name, value)			 // Request Header 추가 및 수정
							.build())
						.build();	
	}

	// 상관관계 ID를 Header에 추가
	public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
		return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
	}

}
