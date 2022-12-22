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
@ToString
public class Volume {
    private String property;
    private Double value;

    public boolean same(Volume v) {
        return this.property.equals(v.property);
    }

    @Override
    public String toString() {
        DecimalFormat formatter = new DecimalFormat("0.0");
        return property + ":" + formatter.format(value);
    }
}
