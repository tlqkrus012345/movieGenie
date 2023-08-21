package com.project.moviegenie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MovieGenieApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieGenieApplication.class, args);
    }

}
