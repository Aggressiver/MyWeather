package com.csu.wangkun.myweather.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by WangKun on 16/4/2.
 */
public class HttpUtil {
    public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
        new Thread() {

            private URLConnection connection;

            @Override
            public void run() {
                try {
                    URL url = new URL(address);
                    connection = url.openConnection();
                    connection.setReadTimeout(1000);
                    connection.setRequestProperty("Get", null);
                    connection.setReadTimeout(8000);
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                        if (listener != null) {
                            listener.onFinish(response.toString());
                        }
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    if (listener != null
                            ) {
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                    }
                }
            }
        }.start();


    }

}
