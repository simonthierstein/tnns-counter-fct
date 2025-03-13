/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.infra;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class SpielerHandler {

    static Mono<ServerResponse> spielerPunktet(ServerRequest request) {
        return ServerResponse.ok().bodyValue("Spieler");
    }

    static Mono<ServerResponse> gegnerPunktet(ServerRequest request) {
        return ServerResponse.ok().bodyValue("Gegner");
    }
}
