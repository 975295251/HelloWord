package com.shuai.collweather.util;

import android.R.array;
import android.text.TextUtils;

import com.shuai.collweather.bean.City;
import com.shuai.collweather.bean.Country;
import com.shuai.collweather.bean.Province;
import com.shuai.collweather.db.CoolWeatherDB;

/**
 * 解析服务端返回过来的数据，并将数据存储到对应的数据库中
 * 
 * @author XuShuai
 *
 */
public class Utility {

	/**
	 * 解析和存储服务器返回的省级数据
	 * 
	 * @param coolWeatherDB
	 * @param response
	 * @return
	 */
	public synchronized static boolean handleProvincesRespose(
			CoolWeatherDB coolWeatherDB, String response) {
		if (!TextUtils.isEmpty(response)) {
			String[] allProvince = response.split(",");
			if (allProvince != null && allProvince.length > 0) {
				for (String pro : allProvince) {
					// "."和"|"都是转义字符必须加上"\\"
					String[] array = pro.split("\\|");
					Province province = new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					// 将解析出来的数据存储在Province表里
					coolWeatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 解析和存储服务器返回的市级数据
	 * 
	 * @param coolWeatherDB
	 * @param response
	 * @return
	 */
	public synchronized static boolean handleCityRespose(
			CoolWeatherDB coolWeatherDB, String response, int provinceId) {
		if (!TextUtils.isEmpty(response)) {
			String[] allCity= response.split(",");
			if (allCity != null && allCity.length > 0) {
				for (String c : allCity) {
					// "."和"|"都是转义字符必须加上"\\"
					String[] array = c.split("\\|");
					City city = new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					city.setProvinceId(provinceId);
					// 将解析出来的数据存储在Province表里
					coolWeatherDB.saveCity(city);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 解析和存储服务器返回的县级数据
	 * 
	 * @param coolWeatherDB
	 * @param response
	 * @return
	 */
	public synchronized static boolean handleCountryRespose(
			CoolWeatherDB coolWeatherDB, String response, int cityId) {
		if (!TextUtils.isEmpty(response)) {
			String[] allProvince = response.split(",");
			if (allProvince != null && allProvince.length > 0) {
				for (String pro : allProvince) {
					// "."和"|"都是转义字符必须加上"\\"
					String[] array = pro.split("\\|");
					Country country = new Country();
					country.setCountryCode(array[0]);
					country.setCountryName(array[1]);
					country.setCityId(cityId);
					// 将解析出来的数据存储在Province表里
					coolWeatherDB.saveCountry(country);
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public static String getWeatherCode(String data){
		if (!TextUtils.isEmpty(data)) {
			String [] array = data.split("\\|");
			return array[1];
		}
		return null;
	}
}
