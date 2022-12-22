package com.example.research.V2.core.context;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.List;

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

    public Double get(String g, String m) {
        return i.get(this.g.indexOf(g), this.m.indexOf(m));
    }

    /**
     * Validate so all values are between 0 and 1 inclusive
     */
    public K validate() {
        i.validate();
        return this;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        DecimalFormat formatter = new DecimalFormat("0.00");

//        result.append("\t");
        for (String s : m) {
            result.append(String.format("\t%s\t", s));
        }
        result.append("\n");

        for (int i = 0; i < this.i.getRowNum(); i++) {
            result.append(g.get(i));
            for (int j = 0; j < this.i.getColNum(); j++) {
                result.append(String.format("\t%s", formatter.format(this.i.get(i, j))));
            }
            result.append("\n");
        }

        return result.toString();
    }
}
