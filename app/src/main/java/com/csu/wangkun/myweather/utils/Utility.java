package com.csu.wangkun.myweather.utils;

import android.text.TextUtils;

import com.csu.wangkun.myweather.db.MyWeatherDB;
import com.csu.wangkun.myweather.model.City;
import com.csu.wangkun.myweather.model.Country;
import com.csu.wangkun.myweather.model.Province;

/**
 * Created by WangKun on 16/4/3.
 */
public class Utility {

    public synchronized static boolean handleProvinceResponse(MyWeatherDB weatherDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProviceCode(array[0]);
                    province.setProviceName(array[1]);
                    weatherDB.saveProvince(province);

                }
                return true;
            }

        }
        return false;
    }

    public synchronized static boolean handleCountryResponse(MyWeatherDB weatherDB, String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCountries = response.split(",");
            if (allCountries != null && allCountries.length > 0) {
                for (String p : allCountries) {
                    String[] array = p.split("\\|");
                    Country country = new Country();
                    country.setCountryCode(array[0]);
                    country.setCountryName(array[1]);
                    weatherDB.saveCountry(country);

                }
                return true;
            }

        }
        return false;
    }

    public synchronized static boolean handleCityResponse(MyWeatherDB weatherDB, String response, int provnice) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCities = response.split(",");
            if (allCities != null && allCities.length > 0) {
                for (String p : allCities) {
                    String[] array = p.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    weatherDB.saveCity(city);

                }
                return true;
            }

        }
        return false;
    }
}
