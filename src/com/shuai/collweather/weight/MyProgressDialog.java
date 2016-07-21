package com.shuai.collweather.weight;

import android.app.ProgressDialog;
import android.content.Context;

public class MyProgressDialog extends ProgressDialog {

	
	public MyProgressDialog(Context context) {
		super(context);
	}
	
	@Override
	public void setMessage(CharSequence message) {
		super.setMessage("正在加载中，请稍后···");
	}
	
	@Override
	public void setCanceledOnTouchOutside(boolean cancel) {
		super.setCanceledOnTouchOutside(false);
	}
}
