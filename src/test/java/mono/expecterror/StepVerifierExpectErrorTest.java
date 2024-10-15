package mono.expecterror;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@Slf4j
class StepVerifierExpectErrorTest {

    @Test
    public void testMonoWithError() {
        Mono<String> mono = Mono.error(new RuntimeException("Error"));

        StepVerifier.create(mono)
                .expectError(RuntimeException.class)
                .verify();
    }

}
