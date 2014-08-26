package com.example.projectcircle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ˾�� ѡ���豸
 */
public class SiginFragment2 extends Activity implements OnClickListener {
	/**
	 * ѡ���豸
	 */
	LinearLayout d_radio1, d_radio2, d_radio3, d_radio4, d_radio5;
	CheckBox btn1, btn2, btn3, btn4, btn5;
	EditText d_content;
	TextView device_name;
	String device_content;
	public static String equipment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_fragment1);
		initDevice();
	}

	/**
	 * ѡ���豸 ��ʼ�� ����ʵ�� ��ѡ
	 */
	private void initDevice() {
		device_name = (TextView) findViewById(R.id.device_name1);
		device_name.setText("���ּ�ʻ�豸");
		d_content = (EditText) findViewById(R.id.device1_sigin_car);
//		// TODO Auto-generated method stub
//		d_radio1 = (LinearLayout) findViewById(R.id.car_radiobtn1);
//		d_radio2 = (LinearLayout) findViewById(R.id.car_radiobtn2);
//		d_radio3 = (LinearLayout) findViewById(R.id.car_radiobtn3);
//		d_radio4 = (LinearLayout) findViewById(R.id.car_radiobtn4);
//		d_radio5 = (LinearLayout) findViewById(R.id.car_radiobtn5);

		btn1 = (CheckBox) findViewById(R.id.device1_btn1);
		btn2 = (CheckBox) findViewById(R.id.device1_btn2);
		btn3 = (CheckBox) findViewById(R.id.device1_btn3);
		btn4 = (CheckBox) findViewById(R.id.device1_btn4);
		btn5 = (CheckBox) findViewById(R.id.device1_btn5);	
		btn1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
     
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				 if(btn1.isChecked()){
						btn2.setChecked(false);
						btn3.setChecked(false);
						btn4.setChecked(false);
						btn5.setChecked(false);
						equipment = "�ھ��";
						d_content.setVisibility(View.GONE);
					}
			}
    });
		btn2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		     
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
			 if(btn2.isChecked()){
					btn1.setChecked(false);
					btn3.setChecked(false);
					btn4.setChecked(false);
					btn5.setChecked(false);
					equipment = "��ж��";
					d_content.setVisibility(View.GONE);
				}
			}
	});
		btn3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		     
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				 if(btn3.isChecked()){
						btn1.setChecked(false);
						btn2.setChecked(false);
						btn4.setChecked(false);
						btn5.setChecked(false);
						equipment = "װ�ػ�";
						d_content.setVisibility(View.GONE);
					}
			}
    });
		btn4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		     
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
			 if(btn4.isChecked()){
					btn1.setChecked(false);
					btn2.setChecked(false);
					btn3.setChecked(false);
					btn5.setChecked(false);
					equipment = "ƽ�峵";
					d_content.setVisibility(View.GONE);
				}
			}
	});
		btn5.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		     
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
			 if(btn5.isChecked()){
					btn1.setChecked(false);
					btn2.setChecked(false);
					btn3.setChecked(false);
					btn4.setChecked(false);;
					d_content.setVisibility(View.VISIBLE);
					equipment = d_content.getText().toString();
				}
			}
	});
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		
	}
	
}
