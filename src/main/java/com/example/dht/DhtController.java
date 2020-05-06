package com.example.dht;

import com.example.dht.model.DhtModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DhtController {
    @Autowired
    DhtModel model;

    @RequestMapping("/hello/{name}")
    String hello(@PathVariable String name) {
        return "Hello, " + name + "!\n";
    }

    @GetMapping("/get/{keyName}")
    String getValue(@PathVariable String keyName) {
        return model.get(keyName);
    }

    @PostMapping("/set/{keyName}")
    String put(@PathVariable String keyName, @RequestBody String value) {
        model.set(keyName, value);
        return "OK\n";
    }

    @GetMapping("/addNode")
    String addNode(@RequestParam String host, @RequestParam String port) {
        model.addNode(host, port);
        return "OK\n";
    }
}
