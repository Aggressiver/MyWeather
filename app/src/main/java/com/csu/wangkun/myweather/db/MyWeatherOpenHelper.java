package com.csu.wangkun.myweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by WangKun on 16/4/2.
 */
public class MyWeatherOpenHelper extends SQLiteOpenHelper {
    public static final String CREATE_PROVINCE = "create table Province(" + "id integer primary key autoincrement" + "province_name text" + "province_code text";
    public static final String CREATE_CITY = "create table City(" + "id integer primary key autoincrement" + "city_name text " + "city_code text" + "province_id integer";
    public static final String CREATE_COUNTTRY = "create table Countru(" + "id integer primary key autoincrement" + "country_name text" + "country_code text" + "city_id integer";

    public MyWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PROVINCE);
        sqLiteDatabase.execSQL(CREATE_CITY);
        sqLiteDatabase.execSQL(CREATE_COUNTTRY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
