package com.example.research.V2.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Bijection of volume list (A) and content list (B)
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
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


}
