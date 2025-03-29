/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.infra;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cmd.DomainCommand;
import ch.sth.dojo.beh.cmd.GegnerPunktet;
import ch.sth.dojo.beh.cmd.SpielerPunktet;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.matchstate.MatchState;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.function.Function;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
class TnnsCounterCommandApi {

    Mono<ServerResponse> executeCommand(ServerRequest request) {
        final MatchState zero = MatchState.zero();
        return request
            .body(BodyExtractors.toMono(GenericCommand.class))
            .map(genericCommand -> executeCommand(genericCommand, zero))
            .flatMap(TnnsCounterCommandApi::serverResponse);
    }

    private static Either<DomainProblem, DomainEvent> executeCommand(GenericCommand genericCommand, final MatchState state) {
        return routeCommand(genericCommand)
            .toEither(DomainProblem.valueNotValid)
            .flatMap(delegateCommand(state));
    }

    private static Mono<ServerResponse> serverResponse(Either<DomainProblem, DomainEvent> domainEvents) {
        return domainEvents.fold(err -> ServerResponse.badRequest().bodyValue(err.toString()),
            succ -> ServerResponse.accepted().bodyValue(succ));
    }

    private static Function<DomainCommand, Either<DomainProblem, ? extends DomainEvent>> delegateCommand(final MatchState zero) {
        return x -> DomainCommand.handleCommand(zero, x);
    }

    private static Option<DomainCommand> routeCommand(final GenericCommand cmd) {
        return Option.narrow(Match(cmd.command()).of(
            Case($("SpielerPunktet"), toSpielerPunktet(cmd)),
            Case($("GegnerPunktet"), toGegnerPunktet(cmd))
        ));
    }

    private static Option<GegnerPunktet> toGegnerPunktet(final GenericCommand cmd) {
        return GegnerPunktet.gegnerPunktet(cmd.commandId());
    }

    private static Option<SpielerPunktet> toSpielerPunktet(final GenericCommand cmd) {
        return SpielerPunktet.spielerPunktet(cmd);
    }
}


