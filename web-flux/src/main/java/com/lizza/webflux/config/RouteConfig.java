package com.lizza.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.*;

/**
 * @Desc:
 * @author: lizza.liu
 * @date: 2021-02-19
 */
@Configuration
public class RouteConfig {

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/flux", t -> ServerResponse.ok().body("Hello Web Flux!"))
                .build();
    }
}
