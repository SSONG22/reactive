package com.songi.reacitveprogramming.toby_study.reactive05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Application {
    @RestController
    public static class MyController {

        @GetMapping("/rest")
        public String rest() {
            return "hello";
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
