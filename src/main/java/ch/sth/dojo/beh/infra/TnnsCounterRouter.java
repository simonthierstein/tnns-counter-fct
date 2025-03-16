package ch.sth.dojo.beh.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class TnnsCounterRouter {

    @Bean
    public RouterFunction<ServerResponse> route(TnnsCounterCommandApi tnnsCounterCommandApi) {

      return RouterFunctions
          .route()
          .POST("/command", tnnsCounterCommandApi::executeCommand)
          .build();
    }
}