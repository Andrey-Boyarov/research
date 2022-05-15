package com.example.research.Validation;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Quality of range [0, 1] from income quality
 *
 * Completed validation
 */
public class ValidQuality extends Quality{
    public ValidQuality(Quality q){
        name = q.name;
        double max = Collections.max(q.values);
        double min = Collections.min(q.values);
        double range = max - min;
        values = q.values
                .stream()
                .map(v ->
                        (v - min) / range
                ).collect(Collectors.toList());
    }
}
