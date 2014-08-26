package com.example.projectcircle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * �̼� ѡ���豸
 */
public class SiginFragment3 extends Activity implements OnCheckedChangeListener {
	/**
	 * ѡ���豸
	 */
	public static CheckBox btn1, btn2, btn3, btn4, btn5;
	public static EditText d_content;
	public static String device1, device2, device3, device4, device5;
	/**
	 * ѡ��ҵ��Χ
	 */
	public static CheckBox btn_1, btn_2, btn_3, btn_4;
	public static String busi1, busi2, busi3, busi4;
	int index = 0;
	int index5 = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_fragment2);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub

		d_content = (EditText) findViewById(R.id.sigin_car);

		btn1 = (CheckBox) findViewById(R.id.car_btn1);
		btn2 = (CheckBox) findViewById(R.id.car_btn2);
		btn3 = (CheckBox) findViewById(R.id.car_btn3);
		btn4 = (CheckBox) findViewById(R.id.car_btn4);
		btn5 = (CheckBox) findViewById(R.id.car_btn5);

		btn_1 = (CheckBox) findViewById(R.id.busi_btn_1);
		btn_2 = (CheckBox) findViewById(R.id.busi_btn_2);
		btn_3 = (CheckBox) findViewById(R.id.busi_btn_3);
		btn_4 = (CheckBox) findViewById(R.id.busi_btn_4);

		btn1.setOnCheckedChangeListener(this);
		btn2.setOnCheckedChangeListener(this);
		btn3.setOnCheckedChangeListener(this);
		btn4.setOnCheckedChangeListener(this);
		btn5.setOnCheckedChangeListener(this);

		btn_1.setOnCheckedChangeListener(listener);
		btn_2.setOnCheckedChangeListener(listener);
		btn_3.setOnCheckedChangeListener(listener);
		btn_4.setOnCheckedChangeListener(listener);
	}

	/**
	 * ѡ���豸
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {
		case R.id.car_btn1:
			if (isChecked) {
				device1 = "�ھ��";
			}
			break;
		case R.id.car_btn2:
			if (isChecked) {
				device2 = "��ж��";
			}
			break;
		case R.id.car_btn3:
			if (isChecked) {
				device3 = "װ�ػ�";
			}
			break;
		case R.id.car_btn4:
			if (isChecked) {
				device4 = "ƽ�峵";
			}
			break;
		case R.id.car_btn5:
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

	/**
	 * ѡ��ҵ��Χ
	 */
	private CompoundButton.OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			switch (buttonView.getId()) {
			case R.id.busi_btn_1:
				if (isChecked) {
					busi1 = "����";
				}
				break;
			case R.id.busi_btn_2:
				if (isChecked) {
					busi2 = "�����";
				}
				break;
			case R.id.busi_btn_3:
				if (isChecked) {
					busi3 = "ά��";
				}
				break;
			case R.id.busi_btn_4:
				if (isChecked) {
					busi4 = "����";
				}
				break;

			default:
				break;
			}
		}
	};
}
