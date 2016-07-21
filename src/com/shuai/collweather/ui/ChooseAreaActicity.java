package com.shuai.collweather.ui;

import java.util.ArrayList;
import java.util.List;

import com.example.collweather.R;
import com.shuai.collweather.adapter.MyListViewAdapter;
import com.shuai.collweather.bean.City;
import com.shuai.collweather.bean.Country;
import com.shuai.collweather.bean.Province;
import com.shuai.collweather.db.CoolWeatherDB;
import com.shuai.collweather.util.ShuaiApplication;
import com.shuai.collweather.util.HttpCallBackListener;
import com.shuai.collweather.util.HttpUtil;
import com.shuai.collweather.util.Utility;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ChooseAreaActicity extends BaseActivity {

	private Context context;
	
	public static final int LEVEL_PROVINCE = 0;
	public static final int LEVEL_CITY = 1;
	public static final int LEVEL_COUNTY = 2;

	private CoolWeatherDB coolWeatherDB;
	private List<String> dataList;
	private MyListViewAdapter listAdapter;
	private String address;

	private TextView title;
	private ListView listView;

	// 省列表
	private List<Province> proviceList;
	// 市列表
	private List<City> cityList;
	// 县列表
	private List<Country> countryList;
	// 当前选中的省
	private Province selectedProvice;
	// 当前选中的市
	private City selectedCity;
	// 当前选择的级别
	private int currentLevel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_area_activity);
		context = ChooseAreaActicity.this;
		loadView();
	}

	private void loadView() {
		title = (TextView) findViewById(R.id.title_text);
		listView = (ListView) findViewById(R.id.listView);
		dataList = new ArrayList<String>();
		listAdapter = new MyListViewAdapter(context, dataList);
		listView.setAdapter(listAdapter);
		coolWeatherDB = ShuaiApplication.getCoolWeatherDB();
		queryProvinces();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (currentLevel == LEVEL_PROVINCE) {
					selectedProvice = proviceList.get(position);
					queryCities();
				} else if (currentLevel == LEVEL_CITY) {
					selectedCity = cityList.get(position);
					queryCounties();
				} else if (currentLevel == LEVEL_COUNTY) {
					Intent intent = new Intent(ChooseAreaActicity.this,
							WeatherDetailsActivity.class);
					intent.putExtra("countryCode", countryList.get(position)
							.getCountryCode());
					intent.putExtra("countryName", countryList.get(position)
							.getCountryName());
					startActivity(intent);
					finish();
				}
			}
		});
	}

	/**
	 * 查询省级数据
	 */
	private void queryProvinces() {
		proviceList = coolWeatherDB.loadProvince();
		if (proviceList.size() > 0) {
			dataList.clear();
			for (Province p : proviceList) {
				dataList.add(p.getProvinceName());
			}
			listAdapter.notifyDataSetChanged();
			listView.setSelection(0);
			title.setText("中国");
			currentLevel = LEVEL_PROVINCE;
		} else {
			doQueryDataFromServer(null, "province");
		}

	}

	/**
	 * 查询市级数据
	 * 
	 * @param p
	 *            .getProvinceName()
	 */
	private void queryCities() {
		cityList = coolWeatherDB.loadCity(selectedProvice.getId());
		if (cityList.size() > 0) {
			dataList.clear();
			for (City c : cityList) {
				dataList.add(c.getCityName());
			}
			listAdapter.notifyDataSetChanged();
			currentLevel = LEVEL_CITY;
			title.setText(selectedProvice.getProvinceName());
			listView.setSelection(0);
		} else {
			doQueryDataFromServer(selectedProvice.getProvinceCode(), "city");
		}
	}

	/**
	 * 查询县级数据
	 */
	private void queryCounties() {
		countryList = coolWeatherDB.loadCountry(selectedCity.getId());
		if (countryList.size() > 0) {
			dataList.clear();
			for (Country c : countryList) {
				dataList.add(c.getCountryName());
			}
			listAdapter.notifyDataSetChanged();
			listView.setSelection(0);
			title.setText(selectedCity.getCityName());
			currentLevel = LEVEL_COUNTY;
		} else {
			doQueryDataFromServer(selectedCity.getCityCode(), "country");
		}
	}

	/**
	 * 从服务端获取数据
	 */
	private void doQueryDataFromServer(String code, final String type) {
		if (!TextUtils.isEmpty(code)) {
			address = "http://www.weather.com.cn/data/list3/city" + code
					+ ".xml";
		} else {
			address = "http://www.weather.com.cn/data/list3/city.xml";
		}
		progressDialog.show();
		HttpUtil.sendHttpRequest(address, new HttpCallBackListener() {

			@Override
			public void onSuccessed(final String response) {
				dismissDialog();
				boolean result = false;
				if ("province".equals(type)) {
					result = Utility.handleProvincesRespose(coolWeatherDB,
							response);
				} else if ("city".equals(type)) {
					result = Utility.handleCityRespose(coolWeatherDB, response,
							selectedProvice.getId());

				} else if ("country".equals(type)) {
					result = Utility.handleCountryRespose(coolWeatherDB,
							response, selectedCity.getId());
				}
				if (result) {
					// 通过runOnUiThread方法返回主线程处理逻辑
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if ("province".equals(type)) {
								queryProvinces();
							} else if ("city".equals(type)) {
								queryCities();
							} else if ("country".equals(type)) {
								queryCounties();
							}
						}
					});
				}
			}

			@Override
			public void onErrored(String msg) {
				super.onErrored(msg);
				dismissDialog();
			}
		});
	}

	@Override
	public void onBackPressed() {
		if (currentLevel == LEVEL_COUNTY) {
			queryCities();
		} else if (currentLevel == LEVEL_CITY) {
			queryProvinces();
		} else {
			finish();
		}
	}
}
