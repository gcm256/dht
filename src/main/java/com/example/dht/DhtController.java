package com.example.dht;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DhtController {
    @RequestMapping("/hello/{name}")
    String hello(@PathVariable String name) {
        return "Hello, " + name + "!\n";
    }

    @GetMapping("/get")
    String getValue(@RequestParam String key) {
        return key + "1\n";
    }

    @PostMapping("/put")
    String put(@RequestBody String body) {
        return "OK\n";
    }
}
