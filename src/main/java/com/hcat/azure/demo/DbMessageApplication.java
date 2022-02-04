package com.hcat.azure.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DbMessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbMessageApplication.class, args);
    }

    private class HelloMessage {
        private final String message;

        private HelloMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    @RestController
    public class HelloController {

        @GetMapping("/hello")
        public HelloMessage hello() {
            return new HelloMessage("Hello from Spring Boot!");
        }

    }

}
