package com.element.app.bean;

import lombok.Data;

@Data
public class Element {
    private String name;
    private String atomicNumber;
    private String alternativeName;
    private String group;
    private String period;
    private String appearance;
    private String[] discoverers;
    private String discoveryYear;
    private String atomicSymbol;
}
