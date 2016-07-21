package com.shuai.collweather.ui;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.collweather.R;
import com.shuai.collweather.util.HttpCallBackListener;
import com.shuai.collweather.util.HttpUtil;
import com.shuai.collweather.util.LogUtil;
import com.shuai.collweather.util.Utility;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class WeatherDetailsActivity extends BaseActivity {

	private String countryCode;
	private String countryName;
	private String address;
	private String weatherCode;
	private TextView title_text;
	private TextView publishTime;
	private TextView dateToday;
	private TextView tempare;
	private TextView weather;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				try {
					JSONObject jsonObject = new JSONObject((String) msg.obj);
					JSONObject jsonObject2 = jsonObject
							.getJSONObject("weatherinfo");
					LogUtil.e("+++++++++",
							"weatherinfo==" + jsonObject2.toString());
					publishTime.setText("今天"
							+ jsonObject2.optString("ptime", "08:00") + "发布");

					tempare.setText(jsonObject2.getString("temp2") + " ~ "
							+ jsonObject2.getString("temp1"));
					weather.setText(jsonObject2.getString("weather"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;

			default:
				break;
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_detail_activity);
		loadView();
		progressDialog.show();
		doGetWeatherCode();
	}

	private void loadView() {
		title_text = (TextView) findViewById(R.id.title_text);
		publishTime = (TextView) findViewById(R.id.publishTime);
		dateToday = (TextView) findViewById(R.id.dateToday);
		tempare = (TextView) findViewById(R.id.tempare);
		weather = (TextView) findViewById(R.id.weather);

		countryCode = getIntent().getStringExtra("countryCode");
		countryName = getIntent().getStringExtra("countryName");
		title_text.setText(countryName);
		// 得到今天的日期
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		String dateStr = simpleDateFormat.format(date);
		dateToday.setText(dateStr);
	}

	/**
	 * 从服务器获取天气代号
	 */
	private void doGetWeatherCode() {
		// 获取天气代号
		address = "http://www.weather.com.cn/data/list3/city" + countryCode
				+ ".xml";

		HttpUtil.sendHttpRequest(address, new HttpCallBackListener() {
			@Override
			public void onSuccessed(String response) {
				super.onSuccessed(response);
				weatherCode = Utility.getWeatherCode(response);
				doGetWeatherDetails();
			}
		});
	}

	/**
	 * 从服务器获取天气详情
	 */
	private void doGetWeatherDetails() {
		// 获取天气代号
		address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode
				+ ".html";
		address = "http://wthrcdn.etouch.cn/weather_mini?city=北京";
		
		HttpUtil.sendHttpRequest(address, new HttpCallBackListener() {
			@Override
			public void onSuccessed(String response) {
				super.onSuccessed(response);
				LogUtil.e("++++++++", "response==" + response);
				progressDialog.dismiss();

				 Message message = new Message();
				 message.what = 0;
				 message.obj = response;
				 handler.sendMessage(message);
			}
		});
	}
}
