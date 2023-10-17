package org.roniel.stream_demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StreamController {


    @GetMapping(value = "/temperatures", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> getTemperature() {
        AtomicReference<Random> r = new AtomicReference<>(new Random());
        return Flux.fromStream(Stream.generate(() -> r.get().nextInt(50))
                        .map(String::valueOf)
                        .peek(log::info))
                .map(Integer::valueOf)
                .delayElements(Duration.ofMillis(100));
    }
}
