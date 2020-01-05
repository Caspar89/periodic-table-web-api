package com.element.app.controller;

import com.element.app.bean.Element;
import com.element.app.exception.ElementNotFoundException;
import com.element.app.service.PeriodicTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
// TODO:
// - Throw in service for server error, and element not found
// - Catch in controller for 500 and 404
// - Add tests for these cases
// - Do a readme update for git push heroku master etc.
// - Done

@RestController
public class PeriodicTableController {
    @Autowired
    PeriodicTableService periodicTableService;

    @RequestMapping("/ping")
    public String ping() {
        return "Periodic table web api is alive and kicking!";
    }

    @GetMapping("/elements")
    public ResponseEntity<List<Element>> getAllElements() {
        try {
            return new ResponseEntity<>(periodicTableService.getAllElements(), HttpStatus.OK);
        } catch (ElementNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/elements/group/{group}")
    public ResponseEntity<List<Element>> getElementsByGroup(
        @PathVariable String group
    ) {
        try {
            return new ResponseEntity<>(periodicTableService.getElementsByGroup(group), HttpStatus.OK);
        } catch (ElementNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/elements/period/{period}")
    public ResponseEntity<List<Element>> getElementsByPeriod(
        @PathVariable String period
    ) {
        try {
            return new ResponseEntity<>(periodicTableService.getElementsByPeriod(period), HttpStatus.OK);
        } catch (ElementNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/element/{atomicNumber}")
    public ResponseEntity<Element> getElement(
            @PathVariable String atomicNumber
    ) {
        try {
            return new ResponseEntity<>(periodicTableService.getElement(atomicNumber), HttpStatus.OK);
        } catch (ElementNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
