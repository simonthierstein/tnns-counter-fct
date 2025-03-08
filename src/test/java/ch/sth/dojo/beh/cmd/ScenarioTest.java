package ch.sth.dojo.beh.cmd;

import static ch.sth.dojo.beh.Condition.condition;
import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.RootEventHandler;
import ch.sth.dojo.beh.cgame.domain.AbgeschlossenesCGame;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.domain.GegnerPunkteBisGame;
import ch.sth.dojo.beh.cgame.domain.LaufendesCGame;
import ch.sth.dojo.beh.cgame.domain.SpielerPunkteBisGame;
import ch.sth.dojo.beh.cmatch.domain.CMatch;
import static ch.sth.dojo.beh.cmd.ScenarioTest.EventTag.eventTagToEvent;
import ch.sth.dojo.beh.csatz.domain.AbgeschlossenerCSatz;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.csatz.domain.LaufenderCSatz;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerMatchGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerMatchGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import ch.sth.dojo.beh.matchstate.GameMatchState;
import ch.sth.dojo.beh.matchstate.MatchState;
import static ch.sth.dojo.beh.matchstate.MatchState.gameMatchState;
import static ch.sth.dojo.beh.matchstate.MatchState.tiebreakMatchState;
import ch.sth.dojo.beh.tiebreak.domain.Tiebreak;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import io.vavr.Function1;
import io.vavr.Function3;
import io.vavr.Predicates;
import io.vavr.Tuple;
import io.vavr.Tuple6;
import io.vavr.Tuple8;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.Arrays;
import java.util.function.Function;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ScenarioTest {

    @ParameterizedTest
    @CsvSource(
        {
            //            "GegnerPunktet, 00-40, 6-5, 0-0, GegnerGameGewonnen, TB0-0, 6-6, 0-0",
            "SpielerPunktet, 40-00, 4-1, 0-0, SpielerGameGewonnen, 00-00, 5-1, 0-0",
            "SpielerPunktet, 30-00, 4-1, 0-0, SpielerPunktGewonnen, 40-00, 4-1, 0-0",
            "SpielerPunktet, 30-30, 4-1, 0-0, SpielerPunktGewonnen, 40-30, 4-1, 0-0",
            "SpielerPunktet, AD-DA, 4-1, 0-0, SpielerGameGewonnen, 00-00, 5-1, 0-0",
            "SpielerPunktet, 30-40, 4-1, 0-0, SpielerPunktGewonnen, DEUCE, 4-1, 0-0",
            "GegnerPunktet, 00-40, 4-1, 0-0, GegnerGameGewonnen, 00-00, 4-2, 0-0",
            "GegnerPunktet, 00-30, 4-1, 0-0, GegnerPunktGewonnen, 00-40, 4-1, 0-0",
            "GegnerPunktet, 30-30, 4-1, 0-0, GegnerPunktGewonnen, 30-40, 4-1, 0-0",
            "GegnerPunktet, DA-AD, 4-1, 0-0, GegnerGameGewonnen, 00-00, 4-2, 0-0",
            "GegnerPunktet, 40-30, 4-1, 0-0, GegnerPunktGewonnen, DEUCE, 4-1, 0-0",
            "SpielerPunktet, 40-00, 5-1, 0-0, SpielerSatzGewonnen, 00-00, 0-0, 1-0",
            "GegnerPunktet, 00-40, 5-6, 0-0, GegnerSatzGewonnen, 00-00, 0-0, 0-1",
            "SpielerPunktet, 40-00, 5-1, 1-0, SpielerMatchGewonnen, GAME, SATZ, MATCH",
            "GegnerPunktet, 00-40, 5-6, 0-1, GegnerMatchGewonnen, GAME, SATZ, MATCH",
            "SpielerPunktet, AD-DA, 5-4, 1-1, SpielerMatchGewonnen, GAME, SATZ, MATCH",
            "GegnerPunktet, DA-AD, 4-5, 1-1, GegnerMatchGewonnen, GAME, SATZ, MATCH",
        }
    )
    void scenario3_laufenderSatz(String cmd, String prevGame, String prevSatz, String prevMatch, String evt, String nextGame, String nextSatz, String nextMatch) {
        final Tuple8<String, String, String, String, String, String, String, String> tupled = Tuple.of(cmd, prevGame, prevSatz, prevMatch, evt, nextGame, nextSatz, nextMatch);
        var psc = tupled.map(parseCommand(), parseGameState(), parseSatzState(), parseMatchState(), parseEvent(), parseGameState(), parseSatzState(), parseMatchState())
            .apply((domainCommand, game, satz, match, event, game2, satz2, match2) ->
                Tuple.of(domainCommand, State.bindGame.apply(match, satz, game), event, State.bindGame.apply(match2, satz2, game2)))
            .apply(PartialScenarioConfig::PartialScenarioConfig);

        var result = applyCommand(psc);

        assertThat(result.isRight())
            .withFailMessage(result::getLeft)
            .isTrue();

    }

    @ParameterizedTest
    @CsvSource(
        {
            //            "GegnerPunktet, 00-40, 6-5, 0-0, GegnerGameGewonnen, TB0-0, 6-6, 0-0",
            "GegnerPunktet, TB0-6, 6-6, 0-0, GegnerSatzGewonnen, TB0-7, 0-0, 0-1",
            "SpielerPunktet, TB6-6, 6-6, 0-0, SpielerPunktGewonnen, TB7-6, 6-6, 0-0",
            "SpielerPunktet, TB7-7, 6-6, 0-0, SpielerPunktGewonnen, TB8-7, 6-6, 0-0",
            "SpielerPunktet, TB5-5, 6-6, 0-0, SpielerPunktGewonnen, TB6-5, 6-6, 0-0",
            "GegnerPunktet, TB6-5, 6-6, 0-0, GegnerPunktGewonnen, TB6-6, 6-6, 0-0",
            "SpielerPunktet, TB7-6, 6-6, 0-0, SpielerSatzGewonnen, TB8-6, 0-0, 1-0",
            "SpielerPunktet, TB7-6, 6-6, 1-0, SpielerMatchGewonnen, TB8-6, SATZ, MATCH",
        }
    )
    void scenario3_laufenderSatzTiebreak(String cmd, String prevGame, String prevSatz, String prevMatch, String evt, String nextGame, String nextSatz, String nextMatch) {
        final Tuple8<String, String, String, String, String, String, String, String> tupled = Tuple.of(cmd, prevGame, prevSatz, prevMatch, evt, nextGame, nextSatz, nextMatch);
        var psc = tupled.map(parseCommand(), parseTiebreakState(), parseSatzState(), parseMatchState(), parseEvent(), parseTiebreakState(), parseSatzState(), parseMatchState())
            .apply((domainCommand, game, satz, match, event, game2, satz2, match2) ->
                Tuple.of(domainCommand, State.bindTiebreak.apply(match, satz, game), event, State.bindTiebreak.apply(match2, satz2, game2)))
            .apply(PartialScenarioConfig::PartialScenarioConfig);

        var result = applyCommand(psc);

        assertThat(result.isRight())
            .withFailMessage(result::getLeft)
            .isTrue();

    }

    @ParameterizedTest
    @CsvSource(
        {
            "GegnerPunktet, 00-40, 6-5, 0-0, GegnerGameGewonnen, TB0-0, 6-6, 0-0",
            "SpielerPunktet, 40-00, 5-6, 0-0, SpielerGameGewonnen, TB0-0, 6-6, 0-0",
        }
    )
    void scenario3_laufenderSatzUebergangTiebreak(String cmd, String prevGame, String prevSatz, String prevMatch, String evt, String nextGame, String nextSatz, String nextMatch) {
        final Tuple8<String, String, String, String, String, String, String, String> tupled = Tuple.of(cmd, prevGame, prevSatz, prevMatch, evt, nextGame, nextSatz, nextMatch);
        var psc = tupled.map(parseCommand(), parseGameState(), parseSatzState(), parseMatchState(), parseEvent(), parseTiebreakState(), parseSatzState(), parseMatchState())
            .apply((domainCommand, game, satz, match, event, game2, satz2, match2) ->
                Tuple.of(domainCommand, State.bindGame.apply(match, satz, game), event, State.bindTiebreak.apply(match2, satz2, game2)))
            .apply(PartialScenarioConfig::PartialScenarioConfig);

        var result = applyCommand(psc);

        assertThat(result.isRight())
            .withFailMessage(result::getLeft)
            .isTrue();

    }

    private static CMatch match() {
        return CMatch.zero();
    }

    @ParameterizedTest
    @CsvSource(
        {
            "GegnerPunktet, 00-40, 0-0, GegnerGameGewonnen, 00-00, 0-1"
        }
    )
    void scenario2_spielerGewinntGameUndNaechsterPunkt_laufenderSatz(String cmd, String prevGame, String prevSatz, String evt, String nextGame, String nextSatz) {
        final Tuple6<String, String, String, String, String, String> tupled = Tuple.of(cmd, prevGame, prevSatz, evt, nextGame, nextSatz);
        var psc = tupled.map(parseCommand(), parseGameState(), parseSatzState(), parseEvent(), parseGameState(), parseSatzState())
            .apply((domainCommand, game, satz, event, game2, satz2) ->
                Tuple.of(domainCommand, State.bindGame.apply(match(), satz, game), event, State.bindGame.apply(match(), satz2, game2)))
            .apply(PartialScenarioConfig::PartialScenarioConfig);

        var result = applyCommand(psc)
            .map(state -> PartialScenarioConfig.PartialScenarioConfig(new GegnerPunktet(), state, new GegnerPunktGewonnen(), State.bindGame.apply(match(), CSatz.of(0, 1).get(), CGame.of(4, 3).get())))
            .flatMap(ScenarioTest::applyCommand);

        assertThat(result.isRight())
            .withFailMessage(result::getLeft)
            .isTrue();
        assertThat(result.get().game)
            .isEqualTo(CGame.of(4, 3).get());

    }

    private Function<? super String, Tiebreak> parseTiebreakState() {
        return input -> Option.some(input)
            .map(parseTennisToTiebreakCommandDomain())
            .get();
    }

    private Function<String, CGame> parseGameState() {
        return input -> Option.some(input)
            .map(parseTennisToCommandDomain())
            .get();
    }

    private Function<String, CSatz> parseSatzState() {
        return input -> scoreParsing(Option.some(input)
            .filter(Predicates.not("SATZ"::equals))
            .toEither(new AbgeschlossenerCSatz()), list -> CSatz.of(list.get(0), list.get(1)).get());
    }

    private static Function<String, CMatch> parseMatchState() {
        return input -> scoreParsing(Option.some(input)
            .filter(Predicates.not("MATCH"::equals))
            .toEither(CMatch.abgeschlossenesMatch()), list -> CMatch.of(list.get(0), list.get(1)).get());
    }

    private static <T> T scoreParsing(final Either<T, String> scoreEither, final Function<List<Integer>, T> createrFunction) {
        return scoreEither
            .map(str -> str.split("-"))
            .map(Arrays::stream)
            .map(List::ofAll)
            .map(list -> list.map(Integer::parseInt))
            .map(createrFunction)
            .fold(Function.identity(), Function.identity());
    }

    private Function<String, DomainEvent> parseEvent() {
        return input -> Option.some(input)
            .map(EventTag::valueOf)
            .map(eventTagToEvent())
            .get();
    }

    enum EventTag {
        SpielerPunktGewonnen, GegnerPunktGewonnen,
        SpielerGameGewonnen, GegnerGameGewonnen,
        SpielerSatzGewonnen, GegnerSatzGewonnen,
        SpielerMatchGewonnen, GegnerMatchGewonnen;

        static Function<EventTag, DomainEvent> eventTagToEvent() {
            return eventTag -> Match(eventTag).of(
                Case($(SpielerPunktGewonnen), new SpielerPunktGewonnen()),
                Case($(GegnerPunktGewonnen), new GegnerPunktGewonnen()),
                Case($(SpielerGameGewonnen), new SpielerGameGewonnen()),
                Case($(GegnerGameGewonnen), new GegnerGameGewonnen()),
                Case($(SpielerSatzGewonnen), new SpielerSatzGewonnen()),
                Case($(GegnerSatzGewonnen), new GegnerSatzGewonnen()),
                Case($(SpielerMatchGewonnen), new SpielerMatchGewonnen()),
                Case($(GegnerMatchGewonnen), new GegnerMatchGewonnen())
            );
        }

    }

    private static Function<String, CGame> parseTennisToCommandDomain() {
        return tennisStr -> Match(tennisStr).of(
            Case($("00-00"), Tuple.of(4, 4).apply(CGame::of)),
            Case($("15-00"), Tuple.of(3, 4).apply(CGame::of)),
            Case($("30-00"), Tuple.of(2, 4).apply(CGame::of)),
            Case($("40-00"), Tuple.of(1, 5).apply(CGame::of)),
            Case($("00-15"), Tuple.of(4, 3).apply(CGame::of)),
            Case($("15-15"), Tuple.of(3, 3).apply(CGame::of)),
            Case($("30-15"), Tuple.of(2, 3).apply(CGame::of)),
            Case($("40-15"), Tuple.of(1, 4).apply(CGame::of)),
            Case($("00-30"), Tuple.of(4, 2).apply(CGame::of)),
            Case($("15-30"), Tuple.of(3, 2).apply(CGame::of)),
            Case($("30-30"), Tuple.of(2, 2).apply(CGame::of)),
            Case($("40-30"), Tuple.of(1, 3).apply(CGame::of)),
            Case($("00-40"), Tuple.of(5, 1).apply(CGame::of)),
            Case($("15-40"), Tuple.of(4, 1).apply(CGame::of)),
            Case($("30-40"), Tuple.of(3, 1).apply(CGame::of)),
            Case($("AD-DA"), Tuple.of(1, 3).apply(CGame::of)),
            Case($("DA-AD"), Tuple.of(3, 1).apply(CGame::of)),
            Case($("DEUCE"), Tuple.of(2, 2).apply(CGame::of)),
            Case($("GAME"), Either.right(new AbgeschlossenesCGame()))
        ).get();
    }

    private static Function<String, Tiebreak> parseTennisToTiebreakCommandDomain() {
        return tennisStr -> Match(tennisStr).of(
            // Tiebreak
            Case($("TB0-0"), Tuple.of(7, 7).apply(Tiebreak::of)),
            Case($("TB0-6"), Tuple.of(8, 1).apply(Tiebreak::of)),
            Case($("TB0-7"), Tuple.of(9, 0).apply(Tiebreak::of)),
            Case($("TB6-6"), Tuple.of(2, 2).apply(Tiebreak::of)),
            Case($("TB7-6"), Tuple.of(1, 3).apply(Tiebreak::of)),
            Case($("TB8-6"), Tuple.of(0, 3).apply(Tiebreak::of)),
            Case($("TB7-7"), Tuple.of(2, 2).apply(Tiebreak::of)),
            Case($("TB5-5"), Tuple.of(2, 2).apply(Tiebreak::of)),
            Case($("TB6-5"), Tuple.of(1, 3).apply(Tiebreak::of)),
            Case($("TB8-7"), Tuple.of(1, 3).apply(Tiebreak::of))
        ).get();
    }

    private Function<String, DomainCommand> parseCommand() {
        return str -> Option.some(str)
            .map(SpielerGegner::valueOf)
            .map(spielerGegner -> condition(spielerGegner,
                x -> x.equals(SpielerGegner.SpielerPunktet),
                x -> new SpielerPunktet(),
                x -> new GegnerPunktet()))
            .get();
    }

    private static Either<String, State> applyCommand(final PartialScenarioConfig scenarioConfig) {
        final MatchState prevState = PreviousState.tuple1.apply(scenarioConfig.previousState);
        return DomainCommand.handleCommand(prevState,
                scenarioConfig.action.domainCommand)
            .peek(evt -> assertThat(evt).withFailMessage("Command failed: %s", scenarioConfig).isEqualTo(scenarioConfig.expectedEvent.event))
            .flatMap(evt -> RootEventHandler.handleEvent(prevState, evt))
            .map(State::untuple)
            .peek(state -> assertThat(state).isEqualTo(scenarioConfig.expectedState.state)).mapLeft(Object::toString);
    }

    record PartialScenarioConfig(PunkteAction action, PreviousState previousState, ExpectedEvent expectedEvent, ExpectedState expectedState) {

        static PartialScenarioConfig PartialScenarioConfig(final PunkteAction action, PreviousState previousState, final ExpectedEvent event, final ExpectedState expectedState) {
            return new PartialScenarioConfig(action, previousState, event, expectedState);
        }

        static PartialScenarioConfig PartialScenarioConfig(final DomainCommand action, State previousState, final DomainEvent event, final State expectedState) {
            return Tuple.of(action, previousState, event, expectedState)
                .map(PunkteAction.bind, PreviousState.bind, ExpectedEvent.bind, ExpectedState.bind)
                .apply(PartialScenarioConfig::new);
        }

    }

    record State(CMatch match, CSatz satz, CGame game, Tiebreak tiebreak) {// TODO sth/10.03.2025 : handle tiebreak/game

        static Function3<CMatch, CSatz, CGame, State> bindGame = (match1, satz1, game1) -> new State(match1, satz1, game1, null);
        static Function3<CMatch, CSatz, Tiebreak, State> bindTiebreak = (match1, satz1, tiebreak1) -> new State(match1, satz1, null, tiebreak1);
        static Function1<State, MatchState> toTuple = State::tuple;

        static State untuple(MatchState stateTuple) {
            return stateTuple.apply(
                state -> new State(state.nextMatch(), state.nextSatz(), state.nextGame(), null),
                state -> new State(state.nextMatch(), state.nextSatz(), null, state.tiebreak())
            );
        }

        private static MatchState tuple(State input) {
            return Match(input).of(
                Case($(x -> x.game != null), x -> gameMatchState(input.match, input.satz, input.game)),
                Case($(), x -> tiebreakMatchState(input.match, input.satz, input.tiebreak))
            );
        }
    }

    record ExpectedState(State state) {

        static Function<State, ExpectedState> bind = ExpectedState::new;

    }

    record PreviousState(State state) {

        static Function<State, PreviousState> bind = PreviousState::new;
        static Function<PreviousState, State> unbind = PreviousState::state;
        static Function<PreviousState, MatchState> tuple1 = unbind.andThen(State.toTuple);
    }

    record ExpectedEvent(DomainEvent event) {

        static Function<DomainEvent, ExpectedEvent> bind = ExpectedEvent::new;

    }

    record PunkteAction(DomainCommand domainCommand) {

        static Function<DomainCommand, PunkteAction> bind = PunkteAction::new;

    }

    enum SpielerGegner {
        SpielerPunktet, GegnerPunktet;
    }

    private static CSatz SatzWith(final int spieler, final int gegner) {
        return CSatz.of(spieler, gegner).get();
    }

    //    private static Function<MatchState, Either<DomainProblem, MatchState>> gegnerPunktetStateTransfer(final DomainCommand command) {
    //        return (MatchState prevState) ->
    //            DomainCommand.handleCommand(prevState, command).flatMap(evt -> RootEventHandler.handleEvent(prevState, evt));
    //    }

    private static CSatz LaufenderCSatzWith(final int spieler, final int gegner) {
        return CSatz.of(spieler, gegner).get();
    }

    private static CGame LaufendesCGameWith(final int spieler, final int gegner) {
        return CGame.of(spieler, gegner).get();
    }

    private static MatchState SatzGameTupleWith() {
        return gameMatchState(match(), CSatz.zero(), CGame.zero());
    }

    private static DomainCommand GegnerPunktetWith() {
        return new GegnerPunktet();
    }

    @Test
    void gegnerGewinntGame_standard() {
    }

    @Test
    void spielerGewinntGame_deuce() {
    }

    @Test
    void gegnerGewinntGame_deuce() {
    }

    @Test
    void spielerGewinntSatz_standard() {
    }

    @Test
    void gegnerGewinntSatz_standard() {
    }

    @Test
    void spielerPunktet() {
        final Either<DomainProblem, DomainEvent> domainEvents = DomainCommand.handleCommand(zeroGame(), createSpielerPunktet());

        assertThat(domainEvents.isRight()).isTrue();
        assertThat(domainEvents.get()).isInstanceOf(SpielerPunktGewonnen.class);
    }

    private static GameMatchState zeroGame() {
        return gameMatchState(CMatch.zero(), LaufenderCSatz.zero(), LaufendesCGame.zero());
    }

    @Test
    void spielerPunktetGame() {
        final Either<DomainProblem, DomainEvent> domainEvents = DomainCommand.handleCommand(laufendesGameWith(1, 5), createSpielerPunktet());

        assertThat(domainEvents.isRight()).isTrue();
        assertThat(domainEvents.get()).isInstanceOf(SpielerGameGewonnen.class);
    }

    private static MatchState laufendesGameWith(final int spielerValue, final int gegnerValue) {
        return gameMatchState(match(), LaufenderCSatz.zero(), new LaufendesCGame(new SpielerPunkteBisGame(spielerValue), new GegnerPunkteBisGame(gegnerValue)));
    }

    @Test
    void gegnerPunktet() {
        final Either<DomainProblem, DomainEvent> domainEvents = DomainCommand.handleCommand(zeroGame(), createGegnerPunktet());

        assertThat(domainEvents.isRight()).isTrue();
        assertThat(domainEvents.get()).isInstanceOf(GegnerPunktGewonnen.class);
    }

    @Test
    void gegnerPunktetGame() {
        final Either<DomainProblem, DomainEvent> domainEvents = DomainCommand.handleCommand(laufendesGameWith(3, 1), createGegnerPunktet());

        assertThat(domainEvents.isRight()).isTrue();
        assertThat(domainEvents.get()).isInstanceOf(GegnerGameGewonnen.class);
    }

    private static DomainCommand createGegnerPunktet() {
        return new GegnerPunktet();
    }

    private static DomainCommand createSpielerPunktet() {
        return new SpielerPunktet();
    }
}