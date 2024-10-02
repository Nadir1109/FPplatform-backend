package com.freelanceplatform.freelanceplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class FreelancePlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreelancePlatformApplication.class, args);
    }



    // Optioneel: Voeg een welkomstpagina toe
    @GetMapping("/")
    public String welcome() {
        return "Welkom op het Freelance Platform! Hier kunnen freelancers en opdrachtgevers elkaar vinden.";
    }
}
