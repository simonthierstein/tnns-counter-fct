/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.infra;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
class TnnsCounterHandler {

    Mono<ServerResponse> spielerPunktet(ServerRequest request) {
        return request
            .body(BodyExtractors.toMono(String.class))
            .map(MyResponse::new)
            .flatMap(myResponse -> ServerResponse.ok()
                .body(BodyInserters.fromValue(myResponse)));
    }

    Mono<ServerResponse> gegnerPunktet(ServerRequest request) {
        return ServerResponse.ok().bodyValue("Gegner");
    }
}


