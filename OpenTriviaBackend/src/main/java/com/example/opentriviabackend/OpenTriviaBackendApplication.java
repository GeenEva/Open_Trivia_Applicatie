package com.example.opentriviabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class OpenTriviaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenTriviaBackendApplication.class, args);
    }

}
