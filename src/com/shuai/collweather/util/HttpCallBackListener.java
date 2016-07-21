package com.shuai.collweather.util;

import android.widget.Toast;

/**
 * 回调服务端返回的接口
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
