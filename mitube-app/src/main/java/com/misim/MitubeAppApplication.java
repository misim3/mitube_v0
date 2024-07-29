package com.misim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MitubeAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MitubeAppApplication.class, args);
    }
}