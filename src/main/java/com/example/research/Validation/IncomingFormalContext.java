package com.example.research.Validation;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Formal context
 */
@Setter
@Getter
public class IncomingFormalContext {
    private String areaName;
    private List<Quality> qualities;
    private List<String> owners;
}
