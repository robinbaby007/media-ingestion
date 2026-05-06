package com.mi.geteway.routing;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Configuration
public class GatewayRoutingConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("event-api-service", r -> r
                        // Matches both /api/events and /api/events/anything
                        .path("/api/events", "/api/events/**")
                        .filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(apiEventsRateLimiter())
                                .setKeyResolver(ipKeyResolver())))
                        // Forwards to the load balancer
                        .uri("lb://EVENT-API-SERVICE"))
                .route("event-ingestion-service", r -> r
                        .path("/api/ingestion", "/api/ingestion/**")
                        .filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(apiEventsRateLimiter())
                                .setKeyResolver(ipKeyResolver())))
                        .uri("lb://EVENT-INGESTION-SERVICE")
                )
                .route("user-service", r -> r
                        .path("/api/v1/users/**")
                        .uri("lb://USER-SERVICE")
                )
                .build();
    }

    // Define the rate limits for this specific route
    @Bean
    public RedisRateLimiter apiEventsRateLimiter() {
        // replenishRates - number of tokens added to the bucket every second
        // burstCapacity - maximum number of tokens the bucket can hold (the burst capacity)
        // requestedTokens - number of tokens required for each request (default is 1)
        return new RedisRateLimiter(1, 2, 1);
    }

    // Rate limits based on the client's IP address
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(
                Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress()
        );
    }


}
