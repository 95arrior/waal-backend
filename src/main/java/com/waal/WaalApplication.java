package com.waal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WaalApplication {

    public static void main(String[] args) {
        SpringApplication.run(WaalApplication.class, args);
    }
}
