package com.element.app.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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
