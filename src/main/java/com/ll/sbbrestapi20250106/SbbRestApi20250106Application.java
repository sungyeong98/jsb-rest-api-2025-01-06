package com.ll.sbbrestapi20250106;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SbbRestApi20250106Application {

    public static void main(String[] args) {
        SpringApplication.run(SbbRestApi20250106Application.class, args);
    }

}
