package com.pixeltrice.springbootimportcsvfileapp;

public class PairClass {

    String name;
    Double loss;
    Double esgScore;
    Double stockPredictedPrice;

    public PairClass(String name, Double loss, Double esgScore, Double stockPredictedPrice) {
        this.name = name;
        this.loss = loss;
        this.esgScore = esgScore;
        this.stockPredictedPrice = stockPredictedPrice;
    }
}
