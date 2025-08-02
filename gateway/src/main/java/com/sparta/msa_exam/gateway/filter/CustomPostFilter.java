package com.sparta.msa_exam.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomPostFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        log.info("Post Filter : Request URI: {}", request.getURI());
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // Check if Server-Port header already exists from downstream service
            String serverPort = exchange.getResponse().getHeaders().getFirst("server-port");
            if (serverPort == null) {
                // If not, add the gateway's port
                exchange.getResponse().getHeaders().add("server-port", String.valueOf(request.getLocalAddress().getPort()));
            }
        }));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    
}
