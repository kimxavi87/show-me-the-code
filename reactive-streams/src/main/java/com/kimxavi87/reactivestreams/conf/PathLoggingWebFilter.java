package com.kimxavi87.reactivestreams.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.RequestPath;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class PathLoggingWebFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        RequestPath path = exchange.getRequest().getPath();
        log.info("Request Path : {}", path.value());
        return chain.filter(exchange);
    }
}
