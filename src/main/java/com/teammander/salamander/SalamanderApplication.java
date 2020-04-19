package com.teammander.salamander;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SalamanderApplication {

    public static void main(String[] args) {SpringApplication.run(SalamanderApplication.class, args); }

}
