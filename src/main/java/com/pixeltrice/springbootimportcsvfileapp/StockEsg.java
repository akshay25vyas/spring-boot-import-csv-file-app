package com.pixeltrice.springbootimportcsvfileapp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stock_esg_data")
public class StockEsg {

    @Id
    @Column(name = "stockname")
    private String stockname;

    @Column(name = "esgvalue")
    private double esgvalue;

    public String getStockname() {
        return stockname;
    }

    public void setStockname(String stockname) {
        this.stockname = stockname;
    }

    public double getEsgvalue() {
        return esgvalue;
    }

    public void setEsgvalue(double esgvalue) {
        this.esgvalue = esgvalue;
    }

    public StockEsg(String stockname, double esgvalue) {
        this.stockname = stockname;
        this.esgvalue = esgvalue;
    }

    public StockEsg() {
    }
}
