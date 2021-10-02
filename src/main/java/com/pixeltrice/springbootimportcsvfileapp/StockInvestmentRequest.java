package com.pixeltrice.springbootimportcsvfileapp;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StockInvestmentRequest {

    private int stockId;

    private int esg;

    private int incentive;

    private double price;

    private int quantity;
}
