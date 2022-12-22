package com.example.research.V2.core.context;

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

        for (double[] value : values) {
            for (int j = 0; j < values[0].length; j++) {
                list.get(j).add(value[j]);
            }
        }

        rowNum = list.get(0).size();
        for (List<Double> col : list) {
            if (col.size() != rowNum) {
                new RuntimeException("Incorrect list inserted. Unable to initialize matrix.").printStackTrace();
            }
        }
        this.values = list;
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

    /**
     * Validate one column so all values are between 0 and 1 inclusive
     */
    private List<Double> validatedColumn(List<Double> list) {
        double max = Collections.max(list);
        double min = Collections.min(list);
        double range = max - min;
        return list.stream().map(v -> v = (v - min) / range).collect(Collectors.toList());
    };
}
