/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.infra;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cmd.DomainCommand;
import ch.sth.dojo.beh.cmd.GegnerPunktet;
import ch.sth.dojo.beh.cmd.SpielerPunktet;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.matchstate.MatchState;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import io.vavr.control.Either;
import java.util.function.Function;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
class TnnsCounterCommandApi {

    Mono<ServerResponse> executeCommand(ServerRequest request) {
        return request
            .body(BodyExtractors.toMono(GenericCommand.class))
            .map(cmd -> routeCommand(cmd))
            .map(cmd -> DomainCommand.handleCommand(MatchState.zero(), cmd))
            .flatMap(toServerResponse());
    }

    private static Function<Either<DomainProblem, DomainEvent>, Mono<ServerResponse>> toServerResponse() {
        return eith -> ServerResponse.ok().bodyValue(eith.get());
    }

    private DomainCommand routeCommand(final GenericCommand cmd) {
        return Match(cmd.command()).of(
            Case($("SpielerPunktet"), toSpielerPunktet(cmd)),
            Case($("GegnerPunktet"), toGegnerPunktet(cmd))
        );
    }

    private static DomainCommand toGegnerPunktet(final GenericCommand cmd) {
        return new GegnerPunktet();
    }

    private static DomainCommand toSpielerPunktet(final GenericCommand cmd) {
        return new SpielerPunktet();
    }
}


