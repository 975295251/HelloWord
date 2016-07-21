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
 * 从服务端获取数据
 * @author XuShuai
 *
 */
public class HttpUtil {

	/**
	 * 判断是否有网络链接
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
			listener.onErrored("网络无连接，请检查网络···");
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
							// 回调onSuccessed方法
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
