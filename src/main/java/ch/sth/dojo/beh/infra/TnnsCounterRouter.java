package ch.sth.dojo.beh.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class TnnsCounterRouter {

    @Bean
    public RouterFunction<ServerResponse> route(TnnsCounterHandler tnnsCounterHandler) {

      return RouterFunctions
          .route()
          .POST("/spielerpunktet", tnnsCounterHandler::spielerPunktet)
          .POST("/gegnerpunktet", tnnsCounterHandler::gegnerPunktet)
          .build();
    }
}