package com.shuai.collweather.util;

import com.shuai.collweather.db.CoolWeatherDB;
import com.shuai.collweather.ui.ChooseAreaActicity;

import android.app.Application;
import android.content.Context;

/**
 * ��ʼ��Ӧ�ó����е�һЩ����
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
	 * �������ݿ��
	 */
	public static CoolWeatherDB getCoolWeatherDB() {
		return coolWeatherDB = CoolWeatherDB.getInstance(context);
	}

	/**
	 * ͨ���˷�����ȡ�����context
	 * @return
	 */
	public static Context getContext() {
		
		return context;
	}

}
