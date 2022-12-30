package com.example.research.V2.math.core.context;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.*;
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

    public K copy() {
        return new K(
                this.g.stream().map(String::new).collect(Collectors.toList()),
                this.m.stream().map(String::new).collect(Collectors.toList()),
                this.i.copy()
        );
    }

    /**
     * Merge objects
     */
    public void mergeObjects(int... indexes) {
        this.i.mergeRows(indexes);
        this.mergeG(indexes);
    }

    /**
     * Merge properties
     */
    public void mergeProps(int... indexes) {
        this.i.mergeRows(indexes);
        this.mergeG(indexes);
    }

    private void mergeG(int... indexes) {
        mergeStringsArrays(g, indexes);
    }

    private void mergeM(int... indexes) {
        mergeStringsArrays(m, indexes);
    }

    private void mergeStringsArrays(List<String> list, int... indexes) {
        Arrays.sort(indexes);
        List<String> strings = new ArrayList<>();
        for (int i : indexes) {
            strings.add(list.get(i));
        }
        Collections.sort(strings);
        String result = String.join(" | ", strings);
        list.set(indexes[0], result);
        list = list.stream().filter(item -> item.equals(strings.get(0)) || strings.stream().noneMatch(item::equals)).collect(Collectors.toList());
    }
}
