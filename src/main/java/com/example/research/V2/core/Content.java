package com.example.research.V2.core;

import lombok.*;

import java.text.DecimalFormat;

/**
 * Value / Property name
 * <br>
 * A (объём)
 */
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class Content {
    private String name;
    private Double value;

    public boolean same(Content v) {
        return this.name.equals(v.name);
    }

    @Override
    public String toString() {
        DecimalFormat formatter = new DecimalFormat("0.0");
        return name + ":" + formatter.format(value);
    }
}
