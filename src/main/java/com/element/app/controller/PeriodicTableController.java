package com.element.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PeriodicTableController {
    @RequestMapping("/ping")
    public String ping() {
        return "Periodic table web api is alive and kicking!";
    }
}
