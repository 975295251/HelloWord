package com.shuai.collweather.weight;

import android.app.ProgressDialog;
import android.content.Context;

public class MyProgressDialog extends ProgressDialog {

	
	public MyProgressDialog(Context context) {
		super(context);
	}
	
	@Override
	public void setMessage(CharSequence message) {
		super.setMessage("���ڼ����У����Ժ󡤡���");
	}
	
	@Override
	public void setCanceledOnTouchOutside(boolean cancel) {
		super.setCanceledOnTouchOutside(false);
	}
}
