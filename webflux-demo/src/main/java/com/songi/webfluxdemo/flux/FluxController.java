package com.songi.webfluxdemo.flux;

import com.songi.webfluxdemo.WebfluxDemoApplication;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RestController
public class FluxController {

    // 10강 - FLux
    @GetMapping("/event/{id}")
    Mono<Event> hello(@PathVariable long id) {
        return Mono.just(new Event(id, "event" + id));
    }

    @GetMapping("/events/{id}")
    Mono<List<Event>> monoList(@PathVariable long id) {
        List<Event> list = Arrays.asList(new Event(1L, "event1"), new Event(2L, "event2"));
        return Mono.just(list);
    }

    @GetMapping("/events")
    Flux<Event> fluxEvents() {
        return Flux.just(new Event(1L, "event1"), new Event(2L, "event2"));
    }

    @GetMapping(value = "/event2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Event> fluxEvents2() {
        List<Event> list = Arrays.asList(new Event(1L, "event1"), new Event(2L, "event2"));
        return Flux.fromIterable(list); // http stream 을 지원하는 것은 Flux 를 쓰면 편리하다.
    }

    @GetMapping(value = "/events3", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Event> fluxEvents3() {
        // rule 에 따라 generation
        Stream<Event> s = Stream.generate(() -> new Event(System.currentTimeMillis(), "value"));
        return Flux.fromStream(s).take(10);
    }

    @Data
    @AllArgsConstructor
    public static class Event {
        long id;
        String value;
    }
}
