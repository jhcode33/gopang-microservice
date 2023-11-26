package com.gopang.gatewayserver.filters;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class ResponseFilter {
 
    final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);
    
	private final FilterUtils filterUtils;
 
    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

                  // 현재 요청에 Header을 얻어옴
            	  HttpHeaders requestHeaders = exchange.getRequest().getHeaders();

                  // Header에서 상관관계 Id를 가져온다
            	  String correlationId = filterUtils.getCorrelationId(requestHeaders);
            	  logger.debug("Adding the correlation id to the outbound headers. {}", correlationId);

                  // Header에 상관관계 ID를 넣는다
                  exchange.getResponse().getHeaders().add(FilterUtils.CORRELATION_ID, correlationId);
                  logger.debug("Completing outgoing request for {}.", exchange.getRequest().getURI());
              }));
        };
    }
}
