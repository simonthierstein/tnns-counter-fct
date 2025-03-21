package ch.sth.dojo.beh.infra;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class TnnsCounterClient {

    private final WebClient client;

    // Spring Boot auto-configures a `WebClient.Builder` instance with nice defaults and customizations.
    // We can use it to create a dedicated `WebClient` for our component.
    public TnnsCounterClient(WebClient.Builder builder) {
        this.client = builder.baseUrl("http://localhost:8080").build();
    }

    public Mono<String> spielerPunktet() {

        return client.post().uri("/command")
                .bodyValue(spielerPunktetCommand())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> gegnerPunktet() {
        return client.post().uri("/command")
                .bodyValue(gegnerPunktetCommand())
                .retrieve()
                .bodyToMono(String.class);
    }

    private static GenericCommand spielerPunktetCommand() {
        return asGenericCommand("SpielerPunktet");
    }

    private static GenericCommand gegnerPunktetCommand() {
        return asGenericCommand("GegnerPunktet");
    }

    private static GenericCommand asGenericCommand(String command) {
        return new GenericCommand(command);
    }
}
