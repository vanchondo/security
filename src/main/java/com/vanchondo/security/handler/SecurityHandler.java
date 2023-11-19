package com.vanchondo.security.handler;

import com.vanchondo.security.util.LogUtil;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Log4j2
public class SecurityHandler {
  private ApplicationContext context;

  public Mono<ServerResponse> handleVersion(ServerRequest request) {
    String methodName = LogUtil.getMethodName(new Object(){});
    log.info("{}Entering method", methodName);
    String version = Optional.of(context.getBean(BuildProperties.class))
      .map(BuildProperties::getVersion)
      .orElse("UNKNOWN");
    return ServerResponse.ok().bodyValue(version);
  }
}
