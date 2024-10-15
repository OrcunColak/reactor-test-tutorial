package flux.expectnextcount;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;


// See https://medium.com/@kshitij.13srivastav/mastering-back-pressure-and-reactive-programming-with-spring-webclient-ed152f3cd8de

@Slf4j
class StepVerifierExpectNextCountTest {

    @Test
    void testExpectNextCount() {
        Flux<String> fluxStream = Flux.just("Athos", "Porthos", "Aramis");
        StepVerifier
                .create(fluxStream)
                .expectNext("Athos")
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void testExpectNextCount2() {
        Flux<String> fluxStream = Flux.just("Athos", "Porthos", "Aramis");
        StepVerifier
                .create(fluxStream)
                .expectNextCount(3)
                .verifyComplete();
    }


    @Test
    void testExpectNextCount3() {
        int count = 100;
        Flux<Integer> flux = Flux.range(1, count)
                .delayElements(Duration.ofMillis(1))
                .doOnNext(data -> {
                    log.info("Received data: {}", data);
                })
                .onBackpressureLatest();

        // Verify that the total emitted elements match the expected count
        StepVerifier.create(flux)
                .expectSubscription()
                .thenRequest(Long.MAX_VALUE)
                .expectNextCount(count)  // Assuming the service generates count numbers
                .verifyComplete();

        // Verify that the stream can emit for the second time
        StepVerifier.create(flux)
                .expectSubscription()
                .thenRequest(Long.MAX_VALUE)
                .expectNextCount(count)  // Assuming the service generates count numbers
                .verifyComplete();
    }


}
