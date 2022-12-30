package com.example.research.V2.math.core;

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
public class Content implements Comparable<Content> {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content = (Content) o;
        return Objects.equals(name, content.name) && Math.abs(value - content.value) < 0.1d;
    }

    @Override
    public int compareTo(Content o) {
        return name.compareTo(o.name) != 0
                ? name.compareTo(o.name)
                : value.compareTo(value);
    }


    public Content copy() {
        return new Content(new String(this.name), new Double(this.value));
    }
}
