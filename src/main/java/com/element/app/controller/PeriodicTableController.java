package com.element.app.controller;

import com.element.app.bean.Element;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PeriodicTableController {
    @RequestMapping("/ping")
    public String ping() {
        return "Periodic table web api is alive and kicking!";
    }

    @RequestMapping("/error")
    public String error() {
        return "Please try one of the following paths:" +
                "\n /elements" +
                "\n/elements/group/{group}" +
                "\n /elements/period/{period}";
    }

    @GetMapping("/elements")
    public ResponseEntity<Element> getAllElements() {
        return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }

    @GetMapping("/elements/group/{group}")
    public ResponseEntity<Element> getElementsByGroup(
        @PathVariable String group
    ) {
        return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

    }

    @GetMapping("/elements/period/{period}")
    public ResponseEntity<Element> getElementsByPeriod(
        @PathVariable String period
    ) {
        return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }
}
