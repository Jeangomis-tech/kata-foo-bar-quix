package com.jcgomis.kata_foo_bar_quix.controllers;

import com.jcgomis.kata_foo_bar_quix.services.ConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/foobarquix")
public class FooBarQuixController {

    private final ConvertService convertService;
    @Autowired
    public FooBarQuixController(ConvertService convertService) {
        this.convertService = convertService;
    }
    @GetMapping("/{number}")
    public ResponseEntity<String> convertNum(@PathVariable int number){
        try{
            String result = convertService.convertNumber(number);
            return ResponseEntity.ok("\"" + result + "\"");
        }catch(IllegalArgumentException err){
            return ResponseEntity.badRequest().body(err.getMessage());
        }
    }
}
