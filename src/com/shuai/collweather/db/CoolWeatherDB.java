package com.shuai.collweather.db;

import java.util.ArrayList;
import java.util.List;

import com.shuai.collweather.bean.City;
import com.shuai.collweather.bean.Country;
import com.shuai.collweather.bean.Province;
import com.shuai.collweather.util.ShuaiApplication;
import com.shuai.collweather.util.LogUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * ��װһЩ���õ����ݿ�
 * 
 * @author XuShuai
 *
 */
public class CoolWeatherDB {

	// ���ݿ���
	public static final String DB_NAME = "CoolWeather.db";
	// ���ݿް汾
	public static final int VERSION = 1;

	private static CoolWeatherDB coolWeatherDB;

	private SQLiteDatabase db;

	/**
	 * �����췽��˽�л�
	 * 
	 * @param context
	 */
	private CoolWeatherDB(Context context) {
		CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(
				ShuaiApplication.getContext(), DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}

	/**
	 * ��ȡCoolWeaherDBʵ�� synchronized
	 * ���ֶζԴ����������ͬһʱ��ֻ��һ���߳�ִ�иô��룬�����߳�����Ҫ�ȴ���һ�߳�ִ�������ִ��
	 * 
	 * @param context
	 * @return
	 */
	public synchronized static CoolWeatherDB getInstance(Context context) {
		if (coolWeatherDB == null) {
			coolWeatherDB = new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}

	/**
	 * ��Provinceʵ���洢�����ݿ�
	 * 
	 * @param province
	 */
	public void saveProvince(Province province) {
		if (province != null) {
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}

	/**
	 * �����ݿ���ȡ��Provinceʵ��
	 * 
	 * @return
	 */
	public List<Province> loadProvince() {
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db
				.query("Province", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor
						.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor
						.getColumnIndex("province_code")));
				list.add(province);
			} while (cursor.moveToNext());
		}
		return list;
	}

	/**
	 * ��Cityʵ���洢�����ݿ���
	 * 
	 * @param city
	 */
	public void saveCity(City city) {
		if (city != null) {
			ContentValues values = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}

	/**
	 * �����ݿ���ȥ��Cityʵ��
	 * 
	 * @return
	 */
	public List<City> loadCity(int provinceId) {
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_id = ?",
				new String[] { String.valueOf(provinceId) }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor
						.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor
						.getColumnIndex("city_code")));
				city.setProvinceId(cursor.getInt(cursor
						.getColumnIndex("province_id")));
				list.add(city);
			} while (cursor.moveToNext());
		}
		return list;
	}

	/**
	 * ��Countryʵ���洢�����ݿ���
	 * ���ݿ��������ֶβ��ö��丳ֵ
	 * @param county
	 */
	public void saveCountry(Country county) {
		if (county != null) {
			ContentValues values = new ContentValues();
//			values.put("id", county.getId());
			values.put("country_name", county.getCountryName());
			values.put("country_code", county.getCountryCode());
			values.put("city_id", county.getCityId());
			db.insert("Country", null, values);
		}
	}

	/**
	 * �����ݿ���ȡ��Countryʵ��
	 * 
	 * @return
	 */
	public List<Country> loadCountry(int cityId) {
		List<Country> list = new ArrayList<Country>();
		Cursor cursor = db.query("Country", null, "city_id = ?",
				new String[] { String.valueOf(cityId) }, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Country country = new Country();
				country.setId(cursor.getInt(cursor.getColumnIndex("id")));
				country.setCountryName(cursor.getString(cursor
						.getColumnIndex("country_name")));
				country.setCountryCode(cursor.getString(cursor
						.getColumnIndex("country_code")));
				country.setCityId(cursor.getInt(cursor
						.getColumnIndex("city_id")));
				list.add(country);
			} while (cursor.moveToNext());
		}
		return list;
	}
}
