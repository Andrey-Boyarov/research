package com.example.research.Content.Validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class Quality {
    @Getter
    protected String name;
    protected List<Double> values;
}
