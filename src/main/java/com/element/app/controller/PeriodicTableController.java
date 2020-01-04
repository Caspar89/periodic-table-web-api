package com.element.app.controller;

import com.element.app.bean.Element;
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
        List<Element> elements = periodicTableService.getAllElements();
        if (elements.size() == 0) {
//            throw new ElementNotFoundException("Could not find any elements.");
            return new ResponseEntity<>(elements, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(elements, HttpStatus.OK);
    }

    @GetMapping("/elements/group/{group}")
    public ResponseEntity<List<Element>> getElementsByGroup(
        @PathVariable String group
    ) {
        List<Element> elements = periodicTableService.getElementsByGroup(group);
        if (elements.size() == 0) {
//            throw new ElementNotFoundException("Could not find any elements in group " + group + ". Groups range from 1 to 18.");
            return new ResponseEntity<>(elements, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(elements, HttpStatus.OK);
    }

    @GetMapping("/elements/period/{period}")
    public ResponseEntity<List<Element>> getElementsByPeriod(
        @PathVariable String period
    ) {
        List<Element> elements = periodicTableService.getElementsByPeriod(period);
        if (elements.size() == 0) {
//            throw new ElementNotFoundException("Could not find any elements in period " + period + ". Periods range from 1 to 8.");
            return new ResponseEntity<>(elements, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(elements, HttpStatus.OK);
    }
}
