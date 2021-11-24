package com.kimxavi87.spring.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class Api2Controller {
    @GetMapping("/something2")
    public ResponseEntity<Object> get() {
        System.out.println("/api/something2");
        return ResponseEntity.ok().build();
    }
}
