package ru.cft.shift.crowdfundingplatformapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    ResponseEntity<String> health() {
        return ResponseEntity.ok("Работает");
    }

}
