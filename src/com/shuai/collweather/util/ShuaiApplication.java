package com.shuai.collweather.util;

import com.shuai.collweather.db.CoolWeatherDB;
import com.shuai.collweather.ui.ChooseAreaActicity;

import android.app.Application;
import android.content.Context;

/**
 * 初始化应用程序中的一些数据
 * 
 * @author XuShuai
 *
 */
public class ShuaiApplication extends Application {

	private static Context context;
	private static CoolWeatherDB coolWeatherDB;
	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		getCoolWeatherDB();
	}

	/**
	 * 创建数据库表
	 */
	public static CoolWeatherDB getCoolWeatherDB() {
		return coolWeatherDB = CoolWeatherDB.getInstance(context);
	}

	/**
	 * 通过此方法获取程序的context
	 * @return
	 */
	public static Context getContext() {
		
		return context;
	}

}
