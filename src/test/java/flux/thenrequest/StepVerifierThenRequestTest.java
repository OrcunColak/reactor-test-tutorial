package flux.thenrequest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

@Slf4j
class StepVerifierThenRequestTest {

    @Test
    public void testFluxWithBackpressure() {
        Flux<Integer> flux = Flux.range(1, 10);

        StepVerifier.create(flux, 1)
                .expectNext(1)
                .thenRequest(1)
                .expectNext(2)
                .thenRequest(8)
                .expectNext(3, 4, 5, 6, 7, 8, 9, 10)
                .expectComplete()
                .verify();
    }

    @Test
    void testFluxWithBackpressure2() {
        int count = 100;
        Flux<Integer> flux = Flux.range(1, count)
                .delayElements(Duration.ofMillis(1))
                .doOnNext(data -> {
                    log.info("Received data: {}", data);
                })
                .onBackpressureLatest();

        StepVerifier.create(flux)
                .expectSubscription()
                .thenRequest(1)  // Request only one item initially
                .expectNext(1)
                .thenRequest(2)  // Request two more items
                .expectNext(2, 3)
                .thenRequest(3)  // Request three more items
                .expectNext(4, 5, 6)
                .thenCancel()    // Cancel the subscription
                .verify(Duration.ofSeconds(5));  // Verify within a specified time
    }


}
