package com.shuai.collweather.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * �ӷ���˻�ȡ����
 * @author XuShuai
 *
 */
public class HttpUtil {

	/**
	 * �ж��Ƿ�����������
	 * @param context
	 * @return
	 */
    public static boolean isNetAvailable(Context context) {
        boolean isNetAvailable = false;
        ConnectivityManager nManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = nManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            isNetAvailable = networkInfo.isAvailable();
        }
        return isNetAvailable;
    }
	
	public static void sendHttpRequest(final String address,
			final HttpCallBackListener listener) {
		
		if (!isNetAvailable(ShuaiApplication.getContext())) {
			listener.onErrored("���������ӣ��������硤����");
		}else {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					HttpURLConnection connection = null;
					try {
						
						URL url = new URL(address);
						connection = (HttpURLConnection) url.openConnection();
						connection.setRequestMethod("GET");
						connection.setConnectTimeout(8000);
						connection.setReadTimeout(8000);
						InputStream inputStream = connection.getInputStream();
						BufferedReader bufferedReader = new BufferedReader(
								new InputStreamReader(inputStream));
						StringBuilder builder = new StringBuilder();
						String line = null;
						while ((line = bufferedReader.readLine()) != null) {
							builder.append(line);
						}
						if (listener != null) {
							// �ص�onSuccessed����
							listener.onSuccessed(builder.toString());
							LogUtil.i("++++++++++++++++", "respose=="+builder.toString());
						}
					} catch (MalformedURLException e) {
						if (listener != null) {
						}
					} catch (IOException e) {
						if (listener != null) {
						}
					} finally {
						if (connection != null) {
							connection.disconnect();
						}
					}
				}
			}).start();
		}
	}
}
