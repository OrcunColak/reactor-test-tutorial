package flux.expectnext;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Slf4j
class StepVerifierExpectTest {

    @Test
    public void testFlux() {
        Flux<String> flux = Flux.just("A", "B", "C");

        StepVerifier.create(flux)
                .expectNext("A")
                .expectNext("B")
                .expectNext("C")
                .expectComplete()
                .verify();
    }

    @Test
    void testExpectNext() {
        Flux<String> fluxStream = Flux.just("Athos", "Porthos", "Aramis");
        StepVerifier.create(fluxStream)
                .expectNext("Athos", "Porthos", "Aramis")
                .verifyComplete();

    }

}
