package com.ucv.codetech.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/ping")
public class Ping {

    @GetMapping
    public ResponseEntity<Integer> checkHealth() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(HttpStatus.OK.value());
    }
}
