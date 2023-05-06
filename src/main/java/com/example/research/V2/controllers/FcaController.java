package com.example.research.V2.controllers;

import com.example.research.V2.math.clustering.ClusteringUtils;
import com.example.research.V2.math.core.AlgorithmUtils;
import com.example.research.V2.math.core.Concept;
import com.example.research.V2.math.core.context.K;
import com.example.research.V2.math.core.context.Matrix;
import com.example.research.V2.pojo.RequestPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/fca")
@RequiredArgsConstructor
public class FcaController {

    private final AlgorithmUtils algorithmUtils;
    private final ClusteringUtils clusteringUtils;

    @PostMapping("/getConcepts")
    public ResponseEntity<List<Concept>> launch(@RequestBody RequestPojo pojo) {
        K k = new K(
                pojo.getHeaderRows(),
                pojo.getHeaderColumns(),
                new Matrix(pojo.getData()).transpose()
        ).validate();

        if (pojo.getAutoShrink()) k.autoShrink();

        List<Concept> concepts = algorithmUtils.getConcepts(k);

        return new ResponseEntity<>(concepts, HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test() {
        K k = new K(
                Arrays.asList("Титаник", "Мстители", "Джентельмены", "Большой куш", "Джон Уик", "Тайна Коко", "Интерстеллар", "Начало"),
                Arrays.asList("Бюджет", "Сборы", "Кинопоиск", "IMDB"),
                new Matrix(new double[][]{
                        {200, 2.22, 8.4, 7.9},
                        {220, 1.52, 7.9, 8.0},
                        {22, 0.1, 8.5, 7.8},
                        {10, 0.1, 8.5, 8.2},
                        {20, 0.1, 7.0, 7.4},
                        {175, 0.8, 8.7, 8.4},
                        {165, 0.7, 8.6, 8.6},
                        {160, 0.8, 8.7, 8.8}})
        );
        String kString = k.toString();

        String vString = k.validate().toString();

//        k.mergeObjects(1, 3, 4);

        String shString = k.autoShrink().toString();

        String cString = algorithmUtils.getConcepts(k).toString();

        return String.format("%S\n%S\n%S\n%d\n%d\n%s", kString, vString, shString, k.getI().getColNum(), k.getI().getRowNum(), cString);


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


