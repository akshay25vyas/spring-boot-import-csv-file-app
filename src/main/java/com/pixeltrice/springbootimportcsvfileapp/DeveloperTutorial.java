package com.pixeltrice.springbootimportcsvfileapp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "developer_tutorial")
public class DeveloperTutorial {

    @Id
    @Column(name = "userid")
    private int userid;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "incentive")
    private int incentive;

    public int getUserid() {
        return userid;
    }

    public DeveloperTutorial() {
    }

    public DeveloperTutorial(int userid, String name, String email, int incentive) {
        this.userid = userid;
        this.name = name;
        this.email = email;
        this.incentive = incentive;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIncentive() {
        return incentive;
    }

    public void setIncentive(int incentive) {
        this.incentive = incentive;
    }
}
