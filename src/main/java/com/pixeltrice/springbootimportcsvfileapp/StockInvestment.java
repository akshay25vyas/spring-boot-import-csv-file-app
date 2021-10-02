package com.pixeltrice.springbootimportcsvfileapp;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StockInvestment {

    private int id;

    private String name;

    private int esg;

    private double stockReturn;

    private int incentive;

    private double price;
}
