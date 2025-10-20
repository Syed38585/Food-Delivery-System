package com.example.ApiGateway.Config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                // 1️⃣ Restaurant main endpoints
                .route("restaurant-service", r -> r
                        .path("/restaurant/**")
                        .filters(f -> f.stripPrefix(1)) // Remove /restaurant prefix
                        .uri("lb://RESTAURANT")
                )

                // 2️⃣ Menu endpoints
                .route("menu-service", r -> r
                        .path("/menu/**")
                        .filters(f -> f.stripPrefix(1)) // Remove /menu prefix
                        .uri("lb://RESTAURANT")
                )

                // 3️⃣ Menu search endpoints under /restaurant/menu/search
                .route("menu-search-service", r -> r
                        .path("/restaurant/menu/**")
                        .filters(f -> f.stripPrefix(2)) // remove /restaurant/menu prefix
                        .uri("lb://RESTAURANT")
                )

                // 4️⃣ Order endpoints
                .route("order-service", r -> r
                        .path("/order/**")
                        .filters(f -> f.stripPrefix(1)) // remove /order prefix if needed
                        .uri("lb://RESTAURANT")
                )

                // 5️⃣ Security endpoints
                .route("security-service", r -> r
                        .path("/security/**")
                        .uri("lb://SECURITY") // Keep /security in path
                )

                .build();
    }
}
