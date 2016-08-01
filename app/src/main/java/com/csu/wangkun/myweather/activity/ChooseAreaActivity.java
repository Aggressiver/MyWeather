package com.csu.wangkun.myweather.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.csu.wangkun.myweather.R;
import com.csu.wangkun.myweather.db.MyWeatherDB;
import com.csu.wangkun.myweather.model.City;
import com.csu.wangkun.myweather.model.Country;
import com.csu.wangkun.myweather.model.Province;
import com.csu.wangkun.myweather.utils.HttpCallbackListener;
import com.csu.wangkun.myweather.utils.HttpUtil;
import com.csu.wangkun.myweather.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ChooseAreaActivity extends Activity {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTRY = 2;
    @InjectView(R.id.title_text)
    TextView titleText;
    @InjectView(R.id.list_view)
    ListView listView;
    ArrayList<String> dataList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private MyWeatherDB weatherDB;
    private int currentlevel;
    private List<Province> provinceList;
    private City selectedCity;
    private Province selectedProvince;
    private List<City> cityList;
    private List<Country> countryList;
    private ProgressDialog progressDialog;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_area);
        ButterKnife.inject(this);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        weatherDB = MyWeatherDB.getInstance(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentlevel == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(i);
                    queryCities();
                } else {
                    selectedCity = cityList.get(i);
                    queryCountrise();
                }
            }
        });
        queryProvince();
    }

    private void queryProvince() {
        provinceList = weatherDB.loadProvnices();
        if (provinceList.size() > 0) {
            dataList.clear();
            for (Province province : provinceList) {

                dataList.add(province.getProviceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText("中国");
            currentlevel = LEVEL_PROVINCE;
        } else {
            queryFromServer(null, "province");
        }
    }


    private void queryCities() {
        cityList = weatherDB.loadCities(selectedProvince.getId());
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {

                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedProvince.getProviceName());
            currentlevel = LEVEL_CITY;
        } else {
            queryFromServer(selectedProvince.getProviceCode(), "city");
        }

    }

    private void queryCountrise() {
        countryList = weatherDB.loadCountry(selectedCity.getId());
        if (countryList.size() > 0) {
            dataList.clear();
            for (Country country : countryList) {

                dataList.add(country.getCountryName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedCity.getCityName());
            currentlevel = LEVEL_COUNTRY;
        } else {
            queryFromServer(selectedCity.getCityCode(), "country");
        }

    }

    private void queryFromServer(String code, final String type) {
        String address;
        if (!TextUtils.isEmpty(code)) {
            address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
        } else {
            address = "http://www.weather.com.cn/data/list3/city.xml";

        }
        showDialog();
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result = false;
                if ("province".equals(type)) {
                    result = Utility.handleProvinceResponse(weatherDB, response);

                } else if ("city".equals(type)) {
                    result = Utility.handleCityResponse(weatherDB, response, selectedProvince.getId());
                } else if ("country".equals(type)) {
                    result = Utility.handleCountryResponse(weatherDB, response, selectedCity.getId());
                }
                if (result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("pronvince".equals(type)) {
                                queryProvince();
                            } else if ("city".equals(type)) {
                                queryCities();

                            } else if ("country".equals(type)) {
                                queryCountrise();
                            }
                        }

                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ChooseAreaActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载");
            progressDialog.setCancelable(false);

        }
        progressDialog.show();

    }

    @OnClick({R.id.title_text, R.id.list_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_text:
                break;
            case R.id.list_view:

                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (currentlevel == LEVEL_COUNTRY) {
            queryCities();
        } else if (currentlevel == LEVEL_CITY) {
            queryProvince();
        } else {
            finish();
        }
    }
}
