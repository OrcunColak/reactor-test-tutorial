package mono.consumenextwith;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class StepVerifierConsumeNextWithTest {

    private Mono<List<String>> uppercaseList(List<String> names) {
        return Flux.fromIterable(names)
                .map(String::toUpperCase)
                .collectList();
    }

    @Test
    void testUppercaseListReactive() {
        List<String> names = List.of("John", "Doe");
        Mono<List<String>> mono = uppercaseList(names);

        StepVerifier.create(mono)
                .consumeNextWith(uppercaseNames -> {
                    assertNotNull(uppercaseNames);
                    assertEquals(2, uppercaseNames.size());
                    assertEquals("JOHN", uppercaseNames.getFirst());
                })
                .verifyComplete();
    }
}
