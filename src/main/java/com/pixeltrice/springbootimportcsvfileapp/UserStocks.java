package com.pixeltrice.springbootimportcsvfileapp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_stocks")
public class UserStocks {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "userid")
    private int userid;

    @Column(name = "stockid")
    private int stockid;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total_purchase_price")
    private double totalPurchasePrice;

    @Column(name = "incentive")
    private int incentive;

    public UserStocks() {
    }

    public UserStocks(int id, int userid, int stockid, int quantity, double totalPurchasePrice, int incentive) {
        this.id = id;
        this.userid = userid;
        this.stockid = stockid;
        this.quantity = quantity;
        this.totalPurchasePrice = totalPurchasePrice;
        this.incentive = incentive;
    }

    public int getId() {
        return id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getStockid() {
        return stockid;
    }

    public void setStockid(int stockid) {
        this.stockid = stockid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPurchasePrice() {
        return totalPurchasePrice;
    }

    public void setTotalPurchasePrice(double totalPurchasePrice) {
        this.totalPurchasePrice = totalPurchasePrice;
    }

    public int getIncentive() {
        return incentive;
    }

    public void setIncentive(int incentive) {
        this.incentive = incentive;
    }
}
