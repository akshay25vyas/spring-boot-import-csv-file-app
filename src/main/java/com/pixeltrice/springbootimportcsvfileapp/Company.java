package com.pixeltrice.springbootimportcsvfileapp;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class Company {

    private String name;

    private Double esgScore;

    private Double predictedStockReturn;

    public Company(String name, Double esgScore, Double predictedStockReturn) {
        this.name = name;
        this.esgScore = esgScore;
        this.predictedStockReturn = predictedStockReturn;
    }
}