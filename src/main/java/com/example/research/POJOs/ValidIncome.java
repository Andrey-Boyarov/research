package com.example.research.POJOs;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ValidIncome extends Income {

    public ValidIncome(Income income){
        area = income.area;
        qualities = income.qualities;
        values = income.values
                .stream()
                .map(ValidContent::new)
                .collect(Collectors.toList());
    }
}
