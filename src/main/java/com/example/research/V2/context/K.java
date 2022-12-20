package com.example.research.V2.context;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Formal context
 *
 * K(G, M, I)
 */
@Getter
@Setter
@AllArgsConstructor
public class K {

    /**
     * List of objects (mean names of researched objects)
     */
    private List<String> g;

    /**
     * List of properties (their names)
     */
    private List<String> m;

    /**
     * Bijection (g, m) -> [0, 1]
     *
     * Отношение инцидентности, определяющее степень принадлежности
     *  * признака m объекту g
     */
    private Matrix i;

    /**
     * Validate so all values are between 0 and 1 inclusive
     */
    public K validate() {
        i.validate();
        return this;
    }
}
