package com.example.research.V2.pojo;

import lombok.Data;

import java.util.List;

@Data
public class RequestPojo {
    private List<List<Double>> data;
    private List<String> headerColumns;
    private List<String> headerRows;
    private Boolean autoShrink;
}
