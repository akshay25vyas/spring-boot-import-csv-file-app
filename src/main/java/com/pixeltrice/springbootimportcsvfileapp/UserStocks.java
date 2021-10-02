package com.pixeltrice.springbootimportcsvfileapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserStocks {

    private int id;

    private int userId;

    private int stockId;

    private int quantity;

    private double totalPurchasePrice;

    private int incentive;
}
