package ch.sth.dojo.beh;

import ch.sth.dojo.beh.infra.TnnsCounterClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ReactiveWebServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ReactiveWebServiceApplication.class, args);
        TnnsCounterClient tnnsCounterClient = context.getBean(TnnsCounterClient.class);
        // We need to block for the content here or the JVM might exit before the message is logged
        System.out.println(">> message = " + tnnsCounterClient.spielerPunktet().block());
        System.out.println(">> message = " + tnnsCounterClient.gegnerPunktet().block());
    }
}