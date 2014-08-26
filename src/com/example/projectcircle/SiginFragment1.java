package com.example.projectcircle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

/**
 * 机主 选择设备
 */
public class SiginFragment1 extends Activity implements OnCheckedChangeListener {
	/**
	 * 选择设备
	 */

	public static CheckBox btn1, btn2, btn3, btn4, btn5;
	public static EditText d_content;
	public static String equipment;
	public static String device1, device2, device3, device4, device5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_fragment);
		initDevice();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	/**
	 * 选择设备 初始化 功能实现 多选
	 */
	private void initDevice() {
		// TODO Auto-generated method stub
		d_content = (EditText) findViewById(R.id.sigin_car);

		btn1 = (CheckBox) findViewById(R.id.btn1);
		btn2 = (CheckBox) findViewById(R.id.btn2);
		btn3 = (CheckBox) findViewById(R.id.btn3);
		btn4 = (CheckBox) findViewById(R.id.btn4);
		btn5 = (CheckBox) findViewById(R.id.btn5);

		btn1.setOnCheckedChangeListener(this);
		btn2.setOnCheckedChangeListener(this);
		btn3.setOnCheckedChangeListener(this);
		btn4.setOnCheckedChangeListener(this);
		btn5.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {
		case R.id.btn1:
			if (isChecked) {
				device1 = "挖掘机";
			}
			break;
		case R.id.btn2:
			if (isChecked) {
				device2 = "自卸车";
			}
			break;
		case R.id.btn3:
			if (isChecked) {
				device3 = "装载机";
			}
			break;
		case R.id.btn4:
			if (isChecked) {
				device4 = "平板车";
			}
			break;
		case R.id.btn5:
			if (isChecked) {
				d_content.setVisibility(View.VISIBLE);
			} else {
				d_content.setVisibility(View.INVISIBLE);
			}
			break;
		default:
			break;
		}
	}
}
