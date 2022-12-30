package com.example.research.V2.math.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Bijection of volume list (A) and content list (B)
 */
@Getter
@Setter
@AllArgsConstructor
public class Concept {
    /**
     * Objects - content
     *
     * B (содержание)
     */
    private List<Content> content;

    /**
     * (value, name of property) - volume
     *
     * A (объём)
     */
    private List<Volume> volume;

    @Override
    public String toString() {
        return "\nConcept{" + content + ", " +  volume + "}\n";
    }

    public Concept copy() {
        List<Content> content = this.content.stream().map(Content::copy).collect(Collectors.toList());
        List<Volume> volume = this.volume.stream().map(Volume::copy).collect(Collectors.toList());
        return new Concept(content, volume);
    }
}
