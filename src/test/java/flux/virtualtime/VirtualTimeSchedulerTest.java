package flux.virtualtime;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;

@Slf4j
public class VirtualTimeSchedulerTest {

    @Test
    public void testFluxWithVirtualTime() {
        VirtualTimeScheduler.getOrSet();

        Flux<Long> delayFlux = Flux.interval(Duration.ofSeconds(1)).take(3);

        StepVerifier.withVirtualTime(() -> delayFlux)
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(3))
                .expectNext(0L, 1L, 2L)
                .expectComplete()
                .verify();
    }
}
