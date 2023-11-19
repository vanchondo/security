package com.vanchondo.security.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.vanchondo.security.handler.SecurityHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class SecurityRouter {

  @Bean
  public RouterFunction<ServerResponse> securityRoute(SecurityHandler securityHandler) {
    return route(GET("/version"), securityHandler::handleVersion);
  }
}
