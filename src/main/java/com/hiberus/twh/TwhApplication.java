package com.hiberus.twh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
@EnableReactiveMongoRepositories("com.hiberus.twh")
public class TwhApplication {
    public static void main(String[] args) {
        SpringApplication.run(TwhApplication.class, args);
    }
}
