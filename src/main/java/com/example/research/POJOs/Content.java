package com.example.research.POJOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * All values for an owner in the area
 */
@Setter
@Getter
public class Content {

    protected String owner;
    protected List<Double> values;
}
