package com.csu.wangkun.myweather.model;

/**
 * Created by WangKun on 16/4/2.
 */
public class City {
    private int id;
    private String cityName;
    private String cityCode;
    private int provniceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvniceId() {
        return provniceId;
    }

    public void setProvniceId(int provniceId) {
        this.provniceId = provniceId;
    }


}
