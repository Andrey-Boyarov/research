package com.example.research.V2.math.core.context;

import lombok.Getter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class Matrix {
    private List<List<Double>> values;
    @Getter
    private int rowNum;
    @Getter
    private int colNum;

    /**
     * Matrix in format:
     *
     *  [[column], [column], ..., [column]]
     */
    public Matrix(List<List<Double>> values) {
        colNum = values.size();
        rowNum = values.get(0).size();
        for (List<Double> col : values) {
            if (col.size() != rowNum) {
                new RuntimeException("Incorrect list inserted. Unable to initialize matrix.").printStackTrace();
            }
        }
        this.values = values;
    }

    public Matrix(double[][] values) {
        colNum = values[0].length;
        if (Arrays.stream(values).anyMatch(t -> t.length != colNum)) {
            new RuntimeException("Incorrect list inserted. Unable to initialize matrix.").printStackTrace();
        }

        List<List<Double>> list = new ArrayList<>();
        for (int i = 0; i < values[0].length; i++) list.add(new ArrayList<>());

        for (double[] value : values) for (int j = 0; j < values[0].length; j++) list.get(j).add(value[j]);

        rowNum = list.get(0).size();
        for (List<Double> col : list) {
            if (col.size() != rowNum) {
                new RuntimeException("Incorrect list inserted. Unable to initialize matrix.").printStackTrace();
            }
        }
        this.values = list;
    }

    public Matrix transpose() {
        Matrix ct = this.copyTransposed();
        this.values = ct.values;
        this.colNum = ct.colNum;
        this.rowNum = ct.rowNum;
        return this;
    }

    public Matrix copyTransposed() {
        List<List<Double>> newValues = new ArrayList<>();
        for (int i = 0; i < rowNum; i++) {
            newValues.add(new ArrayList<>());
        }
        for (int i = 0; i < colNum; i++) {
            for (int j = 0; j < rowNum; j++) {
                newValues.get(j).add(values.get(i).get(j));
            }
        }
        return new Matrix(newValues).copy();
    }

    public List<List<Double>> getValues() {
        return values;
    }

    public Double get(int row, int col) {
        return values.get(col).get(row);
    }

    /**
     * Validate so all values are between 0 and 1 inclusive
     */
    public Matrix validate() {
        values = values.stream().map(this::validatedColumn).collect(Collectors.toList());
        return this;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        DecimalFormat formatter = new DecimalFormat("0.00");

        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                result.append(String.format("%s\t", formatter.format(get(i, j))));
            }
            result.append("\n");
        }

        return result.toString();
    }

    public Matrix copy() {
        List<List<Double>> values = this.values.stream().map(
                list -> list.stream().map(
                        Double::new
                ).collect(Collectors.toList())
        ).collect(Collectors.toList());
        return new Matrix(values);
    }


    public void mergeCols(List<Double> list1, List<Double> list2) {
        for (int i = 0; i < list1.size(); i++) {
            list1.set(i, (list1.get(i) + list2.get(i)) / 2);
        }
        values.remove(list2);
        colNum--;
    }

    public void mergeRows(int... indexes) {
        Arrays.sort(indexes);
        for (List<Double> col : values) {
            for (int i = indexes.length - 1; i > 0; i--) {
                col.set(indexes[0], col.get(indexes[0]) + col.remove(indexes[i]));
            }
            col.set(indexes[0], col.get(indexes[0]) / indexes.length);
        }
        rowNum -= indexes.length - 1;
    }


    public void mergeCols(int... indexes) {
        if (indexes.length < 2) return;
        Arrays.sort(indexes);
        for (int i = 0; i < rowNum; i++) {
            for (int j = 1; j < indexes.length; j++) {
                this.values.get(indexes[0]).set(i, this.values.get(indexes[0]).get(i) + this.values.get(indexes[j]).get(i));
            }
            this.values.get(indexes[0]).set(i, this.values.get(indexes[0]).get(i) / indexes.length);
        }
        for (int i = indexes.length - 1; i > 0; i--) {
            this.values.remove(indexes[i]);
            this.colNum--;
        }
    }

    /**
     * Validate one column so all values are between 0 and 1 inclusive
     */
    private List<Double> validatedColumn(List<Double> list) {
        double max = Collections.max(list);
        double min = Collections.min(list);
        double range = max - min;
        if (range == 0.0) return list;
        return list.stream().map(v -> v = (v - min) / range).collect(Collectors.toList());
    };

    /**
     * Decide if dots are close or not
     */
    public static boolean areNearEnough(List<Double> point1, List<Double> point2, double rangeAllowed) {
        if (point1.size() != point2.size())
            new RuntimeException("Incompatible points (their dimentions are unequal").printStackTrace();
        return range(point1, point2) < rangeAllowed;
    }

    private static Double range(List<Double> point1, List<Double> point2) {
        if (point1.size() != point2.size())
            new RuntimeException("Incompatible points (their dimentions are unequal").printStackTrace();
        Double total = 0D;
        for (int i = 0; i < point1.size(); i++) {
            total += Math.pow(point1.get(i) - point2.get(i), 2);
        }
        total = Math.sqrt(total);
        return total;
    }
}
