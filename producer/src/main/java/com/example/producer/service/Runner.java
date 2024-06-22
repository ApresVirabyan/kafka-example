package com.example.producer.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class Runner implements CommandLineRunner {

    private final MessageService service;

    public Runner(MessageService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        service.generate();
    }
}
