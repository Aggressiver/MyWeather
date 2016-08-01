package com.csu.wangkun.myweather.utils;

/**
 * Created by WangKun on 16/4/2.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);

}
