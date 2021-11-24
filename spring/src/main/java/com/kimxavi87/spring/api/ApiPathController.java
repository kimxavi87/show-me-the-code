package com.kimxavi87.spring.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/path")
@RestController
public class ApiPathController {

    @GetMapping
    public ResponseEntity<Object> getPath() {
        System.out.println("getPath");
        return ResponseEntity.ok().build();
    }
}
