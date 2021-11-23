package com.kimxavi87.spring.player;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ApiController {
    @GetMapping("/something")
    public ResponseEntity<Object> get() {
        System.out.println("/api/something");
        return ResponseEntity.ok().build();
    }
}
