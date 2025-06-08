package com.ibardos.motoShop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        System.out.println("Health check received: " + LocalDateTime.now());
        return ResponseEntity.ok("pong");
    }
}
