package com.pixeltrice.springbootimportcsvfileapp;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PythonLPRequest {
    private Double esgvalue;

    public PythonLPRequest(Double esgvalue, Double stockpricepredicted) {
        this.esgvalue = esgvalue;
        this.stockpricepredicted = stockpricepredicted;
    }

    private Double stockpricepredicted;


}
