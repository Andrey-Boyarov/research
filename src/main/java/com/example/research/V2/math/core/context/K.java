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
        this.i.mergeCols(indexes);
        this.mergeM(indexes);
    }

    private void mergeColumns(List<Integer> indexes) {
        mergeProps(indexes.stream().mapToInt(i->i).toArray());
    }

    public K autoMergeColumns() {
        List<List<Integer>> indexesLists = getSimilarColumns();
        clearFromRedundant(indexesLists);
        setStraightBorders(indexesLists);
        for (List<Integer> indexes : indexesLists) {
            mergeColumns(indexes);
        }
        return this;
    }

    public List<List<Integer>> getSimilarColumns() {
        List<List<Integer>> result = new ArrayList<>();
        double rangeAllowed = getRangeAllowed();
        for (int i = 0; i < this.getI().getColNum() - 1; i++) {
            List<Integer> list = new ArrayList<>();
            list.add(i);
            for (int j = i + 1; j < this.getI().getColNum(); j++) {
                if (Matrix.areNearEnough(this.getI().getValues().get(i), this.getI().getValues().get(j), rangeAllowed))
                    list.add(j);
            }
            if (list.size() > 1) result.add(list);
        }
        return result;
    }

    /**
     * Make indexes unique amongst all lists
     */
    private List<List<Integer>> setStraightBorders(List<List<Integer>> list) {
        for (List<Integer> upperList : list) {
            for (Integer val : upperList) {
                deleteValueCopies(list, upperList, val);
            }
        }
        return list;
    }

    private void deleteValueCopies(List<List<Integer>> list, List<Integer> listToSkip, Integer value) {
        for (List<Integer> upperList : list) {
            if (upperList != listToSkip)
            upperList.remove(value);
        }
    }

    /**
     * Clears from redundant arrays which are contained in others
     */
    private List<List<Integer>> clearFromRedundant(List<List<Integer>> list) {
        for (int i = 0; i < list.size(); i++){
            Set<Integer> list1 = new HashSet<>(list.get(i));
            list.removeIf(list2 -> !new HashSet<>(list2).equals(list1) && list1.containsAll(list2));
        }
        return list;
    }

    /**
     * Range in which dots are considered as being close to each other
     */
    private double getRangeAllowed() {
        return 1D - 1D / Math.sqrt(getI().getRowNum());
    }


    private void mergeG(int... indexes) {
        Arrays.sort(indexes);
        List<String> strings = new ArrayList<>();
        for (int i : indexes) {
            strings.add(g.get(i));
        }
        Collections.sort(strings);
        String result = String.join(" | ", strings);
        g.set(indexes[0], result);
        List<String> newList = new ArrayList<>(g);
        newList.removeAll(strings);
        g = newList;
    }

    private void mergeM(int... indexes) {
        Arrays.sort(indexes);
        List<String> strings = new ArrayList<>();
        for (int i : indexes) {
            strings.add(m.get(i));
        }
        Collections.sort(strings);
        String result = String.join(" | ", strings);
        m.set(indexes[0], result);
        List<String> newList = new ArrayList<>(m);
        newList.removeAll(strings);
        m = newList;
    }

    private void mergeStringsArrays(List<String> list, int... indexes) {

    }
}
