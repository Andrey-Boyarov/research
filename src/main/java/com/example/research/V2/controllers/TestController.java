package com.example.research.V2.controllers;

import com.example.research.V2.math.clustering.ClusteringUtils;
import com.example.research.V2.math.core.AlgorithmUtils;
import com.example.research.V2.math.core.context.K;
import com.example.research.V2.math.core.context.Matrix;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final AlgorithmUtils algorithmUtils;
    private final ClusteringUtils clusteringUtils;

    @GetMapping("/launch")
    public String test() {
        double [][] healthyValues = {
                {1, 0.4, 0.2, 0},
                {1, 0.4, 0.6, 0},
                {1, 0.8, 0.8, 0},
                {0, 0.8, 0.8, 1},
                {0.2, 0.2, 0.6, 1},
                {1, 0.2, 0, 0.8},
                {1, 0.5, 0.4, 0},
                {0.2, 0.6, 0.2, 1}
        };

//        double [][] unHealthyValues = {
//                {10, 0.2, -4, 0.8},
//                {70, 0.5, -6, 0},
//                {100, 0.6, 2, 1}
//        };
//
//        String result = "";
//
//        K k = new K(
//                Arrays.asList("a", "b", "c"),
//                Arrays.asList("strenght", "agile", "health", "mind"),
//                new Matrix(unHealthyValues));
//
//        result += k.getI().toString();
//        result += "\n";
//        result += k.validate().getI().toString();
//
//        return result;

        K k = new K(
                Arrays.asList("AP", "DN", "IM", "SR", /*"KB",*/ /*"ZG",*/ /*"TZ",*/ "ZP"),
                Arrays.asList("S", "A", "I", "Sp"),
                new Matrix(new double[][]{
                        {1, 0.4, 0.3, 0},
                        {1, 0.4, 0.5, 0},
                        {1, 0.8, 0.8, 0},
                        {0, 0.8, 0.8, 1},
//                        {0.2, 0.2, 0.6, 1},
//                        {1, 0.2, 0, 0.8},
//                        {1, 0.5, 0.4, 0},
                        {0.2, 0.6, 0.5, 1}})
        );
        String kString = k.toString();

//        String vString = k.validate().toString();

//        k.mergeObjects(1, 3, 4);

        String cString = k.autoShrink().toString();

//        String cString = algorithmUtils.getConcepts(k).toString();

        return String.format("%S\n%S", kString, cString);


//        Matrix a = new Matrix(new double[][]{
//                {1, 0.4, 0.2, 0},
//                {1, 0.4, 0.6, 0},
//                {1, 0.8, 0.8, 0},
//                {0, 0.8, 0.8, 1},
//                        {0.2, 0.2, 0.6, 1},
//                        {1, 0.2, 0, 0.8},
//                        {1, 0.5, 0.4, 0},
//                {0.2, 0.6, 0.2, 1}});
//
//        Matrix b = a.copy();
//        b.mergeRows(1, 3);
//        return String.format("%s\n\n%s", a, b);
    }


}


