/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.infra.endpoint;

import static ch.sth.dojo.beh.infra.endpoint.CommandHandler.handleCommand;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.matchstate.MatchState;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Either;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
class TnnsCounterCommandApi {

    private final ObjectMapper objectMapper;

    TnnsCounterCommandApi(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    Mono<ServerResponse> executeCommand(ServerRequest request) {
        final MatchState zero = MatchState.zero();
        return request
            .body(BodyExtractors.toMono(new ParameterizedTypeReference<Map<String, Object>>() {
            }))
            .map(ToCommandConverter.buildCommand(objectMapper))
            .map(genericCommand -> handleCommand(genericCommand, zero))
            .flatMap(TnnsCounterCommandApi::serverResponse);
    }

    private static Mono<ServerResponse> serverResponse(Either<DomainProblem, DomainEvent> domainEvents) {
        return domainEvents.fold(err -> ServerResponse.badRequest().bodyValue(err.toString()),
            succ -> ServerResponse.accepted().bodyValue(succ));
    }

}

