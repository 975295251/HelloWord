package com.shuai.collweather.util;

import android.R.array;
import android.text.TextUtils;

import com.shuai.collweather.bean.City;
import com.shuai.collweather.bean.Country;
import com.shuai.collweather.bean.Province;
import com.shuai.collweather.db.CoolWeatherDB;

/**
 * ��������˷��ع��������ݣ��������ݴ洢����Ӧ�����ݿ���
 * 
 * @author XuShuai
 *
 */
public class Utility {

	/**
	 * �����ʹ洢���������ص�ʡ������
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
					// "."��"|"����ת���ַ��������"\\"
					String[] array = pro.split("\\|");
					Province province = new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					// ���������������ݴ洢��Province����
					coolWeatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * �����ʹ洢���������ص��м�����
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
					// "."��"|"����ת���ַ��������"\\"
					String[] array = c.split("\\|");
					City city = new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					city.setProvinceId(provinceId);
					// ���������������ݴ洢��Province����
					coolWeatherDB.saveCity(city);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * �����ʹ洢���������ص��ؼ�����
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
					// "."��"|"����ת���ַ��������"\\"
					String[] array = pro.split("\\|");
					Country country = new Country();
					country.setCountryCode(array[0]);
					country.setCountryName(array[1]);
					country.setCityId(cityId);
					// ���������������ݴ洢��Province����
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
