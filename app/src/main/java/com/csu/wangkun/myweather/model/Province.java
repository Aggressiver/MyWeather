package com.csu.wangkun.myweather.model;

/**
 * Created by WangKun on 16/4/2.
 */
public class Province {
    private int id;
    private String proviceName;
    private String proviceCode;

    public String getProviceName() {
        return proviceName;
    }

    public void setProviceName(String proviceName) {
        this.proviceName = proviceName;
    }

    public String getProviceCode() {
        return proviceCode;
    }

    public void setProviceCode(String proviceCode) {
        this.proviceCode = proviceCode;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
