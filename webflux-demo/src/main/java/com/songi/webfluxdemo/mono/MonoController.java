package com.songi.webfluxdemo.mono;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class MonoController {
    // 9강 - Mono
    @GetMapping("/")
    public Mono<String> helloMono() {
        // Publisher -> (Publisher) -> (Publisher) -> Subscriber :: reactive flow
        log.info("pos1");
        String msg = generateHello();
        Mono<String> m = Mono.just(msg).doOnNext(c -> log.info(c)).log();
        String msg2 = m.block(); // 값을 넘겨주는 역할 내부에서 subscribe
        log.info("pos2: " + msg2);

        return Mono.just(msg2);
    }

    @GetMapping("/supplier")
    public Mono<String> fromSupplier() {
        log.info("pos1");
        Mono<String> m = Mono.fromSupplier(() -> generateHello()) // cold
                .doOnNext(c -> log.info(c))
                .log();

        m.subscribe(); // 미리 subscribe 하면?
        log.info("pos2");
        return m;
    }

    private String generateHello() {
        log.info("method generateHello");
        return "HELLO";
    }

}
