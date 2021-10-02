package com.pixeltrice.springbootimportcsvfileapp;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class UserInvestmentRequest {

    private String email;

    private List<StockInvestmentRequest> stocks;
}
