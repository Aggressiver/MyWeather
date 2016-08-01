package com.csu.wangkun.myweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.csu.wangkun.myweather.model.City;
import com.csu.wangkun.myweather.model.Country;
import com.csu.wangkun.myweather.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangKun on 16/4/2.
 * //
 */
public class MyWeatherDB {
    public static final String DB_NAME = "my_weather";
    public static final int VERSION = 1;
    public static MyWeatherDB mWeatherDB;
    private SQLiteDatabase db;
    private Context mContext;

    private MyWeatherDB(Context context) {
        MyWeatherOpenHelper myWeatherOpenHelper = new MyWeatherOpenHelper(context, DB_NAME, null, VERSION);
    }

    public static MyWeatherDB getInstance(Context context) {
        if (mWeatherDB == null) {
            synchronized (MyWeatherDB.class) {
                if (mWeatherDB == null) {
                    mWeatherDB = new MyWeatherDB(context);
                }
            }
        }
        return mWeatherDB;
    }

    public void saveProvince(Province provice) {
        if (provice == null) {
            ContentValues values = new ContentValues();
            values.put("province_name", provice.getProviceName());
            values.put("province_code", provice.getProviceCode());
            db.insert("Province", null, values);
        }
    }

    public List<Province> loadProvnices() {
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProviceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public void saveCity(City city) {
        if (city != null) {
            ContentValues values = new ContentValues();
            values.put("city_name", city.getCityName());
            values.put("city_code", city.getCityCode());
            values.put("province_id", city.getProvniceId());
            db.insert("City", null, null);
        }
    }

    public List<City> loadCities(int provinceId) {
        List<City> list = new ArrayList<>();
        Cursor cursor = db.query("City", null, "Province_id=?", new String[]{String.valueOf(provinceId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvniceId(provinceId);
                list.add(city);

            } while (cursor.moveToNext());
        }
        return list;
    }

    public void saveCountry(Country country) {
        if (country != null) {
            ContentValues values = new ContentValues();
            values.put("country_name", country.getCountryName());
            values.put("country_code", country.getCountryCode());
            values.put("city_id", country.getCityId());
            db.insert("Country", null, values);
        }
    }

    public List<Country> loadCountry(int cityId) {
        ArrayList<Country> list = new ArrayList<>();
        Cursor cursor = db.query("Country", null, "city_id=?", new String[]{String.valueOf(cityId)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Country country = new Country();
                country.setId(cursor.getInt(cursor.getColumnIndex("id")));
                country.setCountryName(cursor.getString(cursor.getColumnIndex("country_name")));
                country.setCountryCode(cursor.getString(cursor.getColumnIndex("country_code")));
                country.setCityId(cityId);
                list.add(country);

            } while (cursor.moveToNext());
        }
        return list;
    }

}

