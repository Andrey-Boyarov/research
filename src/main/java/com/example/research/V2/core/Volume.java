package com.example.research.V2.core;

import lombok.*;

import java.text.DecimalFormat;
import java.util.Objects;

/**
 * Value / Property name
 * <br>
 * A (объём)
 */
@Getter
@Setter
@AllArgsConstructor
public class Volume implements Comparable<Volume>{
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volume volume = (Volume) o;
        return Objects.equals(property, volume.property) && Math.abs(value - volume.value) < 0.1d;
    }

    @Override
    public int hashCode() {
        return Objects.hash(property, value);
    }

    @Override
    public int compareTo(Volume o) {
        return property.compareTo(o.property) != 0
                ? property.compareTo(o.property)
                : value.compareTo(value);
    }

    public Volume copy() {
        return new Volume(new String(this.property), new Double(this.value));
    }
}
