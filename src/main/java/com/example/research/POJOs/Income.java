package com.example.research.POJOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Income {

    protected String area;
    protected List<String> qualities;
    protected List<Content> values;
}
