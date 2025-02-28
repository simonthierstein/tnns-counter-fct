package ch.sth.dojo.beh.cmd;

import static ch.sth.dojo.beh.Condition.condition;
import static ch.sth.dojo.beh.cmd.ScenarioTest.EventTag.eventTagToEvent;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.RootEventHandler;
import ch.sth.dojo.beh.cgame.domain.AbgeschlossenesCGame;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.domain.GegnerPunkteBisGame;
import ch.sth.dojo.beh.cgame.domain.LaufendesCGame;
import ch.sth.dojo.beh.cgame.domain.SpielerPunkteBisGame;
import ch.sth.dojo.beh.cgame.domain.Tiebreak;
import ch.sth.dojo.beh.cmatch.domain.CMatch;
import ch.sth.dojo.beh.csatz.domain.AbgeschlossenerCSatz;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.csatz.domain.LaufenderCSatz;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Predicates;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.Tuple6;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.Arrays;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class ScenarioTest {

    @ParameterizedTest
    @CsvSource(
        {
            "GegnerPunktet, 00-40, 6-5, GegnerGameGewonnen, TB0-0, 6-6",
            "GegnerPunktet, TB0-6, 6-6, GegnerSatzGewonnen, TB0-7, SATZ",
            "SpielerPunktet, TB6-6, 6-6, SpielerPunktGewonnen, TB7-6, 6-6",
            "SpielerPunktet, TB7-7, 6-6, SpielerPunktGewonnen, TB8-7, 6-6",
            "SpielerPunktet, TB5-5, 6-6, SpielerPunktGewonnen, TB6-5, 6-6",
            "GegnerPunktet, TB6-5, 6-6, GegnerPunktGewonnen, TB6-6, 6-6",
            "SpielerPunktet, TB7-6, 6-6, SpielerSatzGewonnen, TB8-6, SATZ"
        }
    )
    void scenario3_punktZumTiebreak_laufenderSatz(String cmd, String prevGame, String prevSatz, String evt, String nextGame, String nextSatz) {
        final Tuple6<String, String, String, String, String, String> tupled = Tuple.of(cmd, prevGame, prevSatz, evt, nextGame, nextSatz);
        var psc = tupled.map(parseCommand(), parseState(), parseSatzState(), parseEvent(), parseState(), parseSatzState())
            .apply((domainCommand, game, satz, event, game2, satz2) ->
                Tuple.of(domainCommand, State.bind.apply(satz, game), event, State.bind.apply(satz2, game2)))
            .apply(PartialScenarioConfig::PartialScenarioConfig);

        var result = applyCommand(psc);

        assertThat(result.isRight())
            .withFailMessage(result::getLeft)
            .isTrue();

    }

    @ParameterizedTest(name = "Spieler gewinnt game mit gegner score {0}🥸")
    @ValueSource(strings = {"00", "15", "30"})
    void spielerGewinntGame_standard(String other) {

        // TODO sth/22.02.2025 : deklarativer mit command abfolge

        final Tuple2<CSatz, CGame> prev = SatzGameTupleWith();
        final DomainCommand gegnerPunktetCommand = GegnerPunktetWith();

        final Either<DomainProblem, Tuple2<CSatz, CGame>> gegnerPointsApplied = List.of(other)
            .flatMap(str -> Match(str).of(
                Case($("00"), List.empty()),
                Case($("15"), List.range(0, 1)),
                Case($("30"), List.range(0, 2))
            ))
            .map(x -> gegnerPunktetStateTransfer(gegnerPunktetCommand))
            .foldLeft(Either.right(prev), Either::flatMap);

        final DomainCommand spielerPunkteCommand = SpielerPunktetWith();

        final Either<DomainProblem, Tuple2<CSatz, CGame>> result = List.range(0, 4)
            .foldLeft(gegnerPointsApplied,
                (acc, it) ->
                    acc.flatMap(prevState ->
                        DomainCommand.handleCommand(prevState, spielerPunkteCommand).flatMap(evt -> RootEventHandler.handleEvent(prevState, evt))));

        assertThat(result.isRight()).isTrue();
        result.forEach(t -> assertAll(
            () -> assertThat(t._1).isEqualTo(SatzWith(1, 0)),
            () -> assertThat(t._2).isEqualTo(LaufendesCGame.zero())
        ));

        System.out.println(other);

    }

    @ParameterizedTest
    @CsvSource(
        {
            "SpielerPunktet, 40-00, 4-1, SpielerGameGewonnen, 00-00, 5-1",
            "SpielerPunktet, 30-00, 4-1, SpielerPunktGewonnen, 40-00, 4-1",
            "SpielerPunktet, 30-30, 4-1, SpielerPunktGewonnen, 40-30, 4-1",
            "SpielerPunktet, AD-DA, 4-1, SpielerGameGewonnen, 00-00, 5-1",
            "SpielerPunktet, 30-40, 4-1, SpielerPunktGewonnen, DEUCE, 4-1",
            "GegnerPunktet, 00-40, 4-1, GegnerGameGewonnen, 00-00, 4-2",
            "GegnerPunktet, 00-30, 4-1, GegnerPunktGewonnen, 00-40, 4-1",
            "GegnerPunktet, 30-30, 4-1, GegnerPunktGewonnen, 30-40, 4-1",
            "GegnerPunktet, DA-AD, 4-1, GegnerGameGewonnen, 00-00, 4-2",
            "GegnerPunktet, 40-30, 4-1, GegnerPunktGewonnen, DEUCE, 4-1",
            "SpielerPunktet, 40-00, 5-1, SpielerSatzGewonnen, GAME, SATZ",
            "GegnerPunktet, 00-40, 5-6, GegnerSatzGewonnen, GAME, SATZ"
        }
    )
    void scenario1_spielerGewinntGame_laufenderSatz(String cmd, String prevGame, String prevSatz, String evt, String nextGame, String nextSatz) {
        final Tuple6<String, String, String, String, String, String> tupled = Tuple.of(cmd, prevGame, prevSatz, evt, nextGame, nextSatz);
        var psc = tupled.map(parseCommand(), parseState(), parseSatzState(), parseEvent(), parseState(), parseSatzState())
            .apply((domainCommand, game, satz, event, game2, satz2) ->
                Tuple.of(domainCommand, State.bind.apply(satz, game), event, State.bind.apply(satz2, game2)))
            .apply(PartialScenarioConfig::PartialScenarioConfig);

        final Either<String, State> states = applyCommand(psc);

        assertThat(states.isRight())
            .withFailMessage(states::getLeft)
            .isTrue();

    }

    @ParameterizedTest
    @CsvSource(
        {
            "GegnerPunktet, 00-40, 0-0, GegnerGameGewonnen, 00-00, 0-1"
        }
    )
    void scenario2_spielerGewinntGameUndNaechsterPunkt_laufenderSatz(String cmd, String prevGame, String prevSatz, String evt, String nextGame, String nextSatz) {
        final Tuple6<String, String, String, String, String, String> tupled = Tuple.of(cmd, prevGame, prevSatz, evt, nextGame, nextSatz);
        var psc = tupled.map(parseCommand(), parseState(), parseSatzState(), parseEvent(), parseState(), parseSatzState())
            .apply((domainCommand, game, satz, event, game2, satz2) ->
                Tuple.of(domainCommand, State.bind.apply(satz, game), event, State.bind.apply(satz2, game2)))
            .apply(PartialScenarioConfig::PartialScenarioConfig);

        var result = applyCommand(psc)
            .map(state -> PartialScenarioConfig.PartialScenarioConfig(new GegnerPunktet(), state, new GegnerPunktGewonnen(), State.bind.apply(CSatz.of(0, 1).get(), CGame.of(4, 3).get())))
            .flatMap(ScenarioTest::applyCommand);

        assertThat(result.isRight())
            .withFailMessage(result::getLeft)
            .isTrue();
        assertThat(result.get().game)
            .isEqualTo(CGame.of(4, 3).get());

    }

    private Function<String, CSatz> parseSatzState() {
        return input -> Option.some(input)
            .filter(Predicates.not("SATZ"::equals))
            .toEither(new AbgeschlossenerCSatz())
            .map(str -> str.split("-"))
            .map(Arrays::stream)
            .map(List::ofAll)
            .map(list -> list.map(Integer::parseInt))
            .map(list -> CSatz.of(list.get(0), list.get(1)).get())
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
        SpielerSatzGewonnen, GegnerSatzGewonnen;

        static Function<EventTag, DomainEvent> eventTagToEvent() {
            return eventTag -> Match(eventTag).of(
                Case($(SpielerPunktGewonnen), new SpielerPunktGewonnen()),
                Case($(GegnerPunktGewonnen), new GegnerPunktGewonnen()),
                Case($(SpielerGameGewonnen), new SpielerGameGewonnen()),
                Case($(GegnerGameGewonnen), new GegnerGameGewonnen()),
                Case($(SpielerSatzGewonnen), new SpielerSatzGewonnen()),
                Case($(GegnerSatzGewonnen), new GegnerSatzGewonnen())
            );
        }
    }

    private Function<String, CGame> parseState() {
        return input -> Option.some(input)
            .map(parseTennisToCommandDomain())
            .get();
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
            Case($("GAME"), Either.right(new AbgeschlossenesCGame())),
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
        final Tuple2<CSatz, CGame> prevState = PreviousState.tuple1.apply(scenarioConfig.previousState);
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

    record State(CSatz satz, CGame game) {

        static Function2<CSatz, CGame, State> bind = State::new;
        static Function1<State, Tuple2<CSatz, CGame>> toTuple = State::tuple;

        static State untuple(Tuple2<CSatz, CGame> stateTuple) {
            return stateTuple.apply(State::new);
        }

        private static Tuple2<CSatz, CGame> tuple(State input) {
            return Tuple.of(input.satz, input.game);
        }
    }

    record ExpectedState(State state) {

        static Function<State, ExpectedState> bind = ExpectedState::new;

    }

    record PreviousState(State state) {

        static Function<State, PreviousState> bind = PreviousState::new;
        static Function<PreviousState, State> unbind = PreviousState::state;
        static Function<PreviousState, Tuple2<CSatz, CGame>> tuple1 = unbind.andThen(State.toTuple);
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

    private static Function<Tuple3<CMatch, CSatz, CGame>, Either<DomainProblem, Tuple3<CMatch, CSatz, CGame>>> gegnerPunktetStateTransfer(final DomainCommand command) {
        return (Tuple3<CMatch, CSatz, CGame> prevState) ->
            DomainCommand.handleCommand(prevState, command).flatMap(evt -> RootEventHandler.handleEvent(prevState, evt));
    }

    private static CSatz LaufenderCSatzWith(final int spieler, final int gegner) {
        return CSatz.of(spieler, gegner).get();
    }

    private static CGame LaufendesCGameWith(final int spieler, final int gegner) {
        return CGame.of(spieler, gegner).get();
    }

    private static Tuple2<CSatz, CGame> SatzGameTupleWith() {
        return Tuple.of(CSatz.zero(), CGame.zero());
    }

    private static DomainCommand GegnerPunktetWith() {
        return new GegnerPunktet();
    }

    private static DomainCommand SpielerPunktetWith() {
        return new SpielerPunktet();
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

    private static Tuple2<CSatz, CGame> zeroGame() {
        return Tuple.of(LaufenderCSatz.zero(), LaufendesCGame.zero());
    }

    @Test
    void spielerPunktetGame() {
        final Either<DomainProblem, DomainEvent> domainEvents = DomainCommand.handleCommand(laufendesGameWith(1, 5), createSpielerPunktet());

        assertThat(domainEvents.isRight()).isTrue();
        assertThat(domainEvents.get()).isInstanceOf(SpielerGameGewonnen.class);
    }

    private static Tuple2<CSatz, CGame> laufendesGameWith(final int spielerValue, final int gegnerValue) {
        return Tuple.of(LaufenderCSatz.zero(), new LaufendesCGame(new SpielerPunkteBisGame(spielerValue), new GegnerPunkteBisGame(gegnerValue)));
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