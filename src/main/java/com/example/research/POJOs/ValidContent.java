package com.example.research.POJOs;

import lombok.Getter;

import java.util.Collections;
import java.util.stream.Collectors;

public class ValidContent extends Content{

    @Getter
    private Double startMinimum;
    @Getter
    private Double startMaximum;

    public ValidContent(Content content){
        owner = content.owner;
        startMinimum = Collections.min(values);
        startMaximum = Collections.max(values);

        double range = startMaximum - startMinimum;

        values = content.values
                .stream()
                .map(val -> (val - startMinimum)/range)
                .collect(Collectors.toList());
    }
}
