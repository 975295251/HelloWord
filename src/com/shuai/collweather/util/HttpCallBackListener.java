package com.shuai.collweather.util;

import android.widget.Toast;

/**
 * �ص�����˷��صĽӿ�
 * 
 * @author XuShuai
 *
 */
public class HttpCallBackListener {

	public void onSuccessed(String response) {

	}

	public void onErrored(String msg) {
		Toast.makeText(ShuaiApplication.getContext(), msg, Toast.LENGTH_LONG)
				.show();
	}
}
