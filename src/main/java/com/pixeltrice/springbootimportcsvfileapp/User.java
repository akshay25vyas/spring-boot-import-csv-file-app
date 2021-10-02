package com.pixeltrice.springbootimportcsvfileapp;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {

    private int userId;

    private String name;

    private String email;

    private int incentive;
}
