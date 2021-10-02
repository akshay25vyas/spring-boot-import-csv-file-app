package com.pixeltrice.springbootimportcsvfileapp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stock_price")
public class StockPrice {

    public String getStockname() {
        return stockname;
    }

    public void setStockname(String stockname) {
        this.stockname = stockname;
    }

    public double getStockPricePredicted() {
        return stockPricePredicted;
    }

    public void setStockPricePredicted(double stockPricePredicted) {
        this.stockPricePredicted = stockPricePredicted;
    }

    public StockPrice() {
    }

    public StockPrice(String stockname, double stockPricePredicted) {
        this.stockname = stockname;
        this.stockPricePredicted = stockPricePredicted;
    }

    @Id
    @Column(name = "stockname")
    private String stockname;

    @Column(name = "stockpricepredicted")
    private double stockPricePredicted;

}
