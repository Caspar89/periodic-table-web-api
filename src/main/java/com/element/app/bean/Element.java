package com.element.app.bean;

import lombok.Data;

@Data
public class Element {
    private String name;
    private Short atomicNumber;
    private String alternativeName;
    // Could easily be a Short
    private Integer group;
    private Short period;
    private String appearance;
    private String[] discoverers;
    private String discoveryYear;
}
