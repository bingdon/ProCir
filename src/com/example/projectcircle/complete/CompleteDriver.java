package com.example.projectcircle.complete;

import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcircle.R;
import com.example.projectcircle.SiginActivity;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class CompleteDriver extends Activity {
	/**
	 * EditText ˾����ʻ��Ϣ
	 */
	// ��ʻ��ʼ
	TextView driver_time_year, driver_time_month;
	int timeyear, timemonth;
	// �ּ�ʻ�豸
	EditText now_brand_txt, now_type_txt;
	String now_brand, now_type;
	String now_device;
	String device_age;
	String uid;
	String type;
	/**
	 * ����Button
	 */
	Button back, next;
	DatePickerDialog mDialog;

	int year_index, month_index;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.perfect_driver);
		initBtn();
		initView();
		initFilter();
	}

	private void initView() {
		// TODO Auto-generated method stub
		// ����
		driver_time_year = (TextView) findViewById(R.id.driver_time_year);
		driver_time_month = (TextView) findViewById(R.id.driver_time_month);

		driver_time_year.setOnClickListener(timelistener);
		driver_time_month.setOnClickListener(timelistener);
		// �ּ�ʻ�豸
		now_brand_txt = (EditText) findViewById(R.id.now_driver_brand);
		now_type_txt = (EditText) findViewById(R.id.now_driver_type);

	}

	private View.OnClickListener timelistener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.driver_time_year:
			case R.id.driver_time_month:
				TimeDialog();
				break;

			default:
				break;
			}
		}

	};

	private void initBtn() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.perfect_driver_left);
		next = (Button) findViewById(R.id.driver_next);
		back.setOnClickListener(listener);
		next.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.perfect_driver_left:
				// Intent intent = new Intent(CompleteDriver.this,
				// CompleteInfo.class);
				// startActivity(intent);
				finish();
				break;
			case R.id.driver_next:
				submit();
				break;
			default:
				break;
			}
		}

		private void submit() {
			// TODO Auto-generated method stub
			now_brand = now_brand_txt.getText().toString().trim();
			now_type = now_type_txt.getText().toString().trim();
			type = SiginActivity.type;
			uid = SiginActivity.id;
			// ������
			// type = "˾��";
			// uid = "25";
			now_device = now_brand + now_type;

			// ���侫ȷ���·� ʵ��ÿ�����
			Time t = new Time();
			t.setToNow();
			int currentyear = t.year;
			Log.i("currentyear", currentyear + "");
			year_index = currentyear - timeyear;
			Log.i("year_index", year_index + "");
			month_index = year_index * 12;
			Log.i("month_index", month_index + "");
			int currentmonth = t.month + month_index;
			Log.i("currentmonth", currentmonth + "");
			int month = currentmonth - timemonth;
			Log.i("month", month + "");
			device_age = month / 12 + "";

			// System.out.println(device_age);
			CompleteDriver(uid, type, device_age, now_device);
		}

		private void CompleteDriver(String uid, String type,
				String driveryears, String nequ) {
			// TODO Auto-generated method stub
			AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
				public void onSuccess(String response) {
					JSONObject obj;
					try {
						obj = new JSONObject(response);
						Log.i("response-----result", obj.getInt("result") + "");
						if (obj.getInt("result") == 1) {
							Toast.makeText(getApplicationContext(), "��ӳɹ���",
									Toast.LENGTH_LONG).show();
							Intent intent1 = new Intent(CompleteDriver.this,
									CompleteInfo.class);
							startActivity(intent1);						
						} else {
							Toast.makeText(getApplicationContext(), "���ʧ�ܣ�",
									Toast.LENGTH_LONG).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			};
			MyHttpClient client = new MyHttpClient();
			client.CompleteDriver(uid, type, driveryears, nequ, res);
		}
	};

	@SuppressWarnings("deprecation")
	private void TimeDialog() {
		// TODO Auto-generated method stub
		showDialog(0);// ���ڵ�����
		int SDKVersion = CompleteDriver.this.getSDKVersionNumber();// ��ȡϵͳ�汾
		System.out.println("SDKVersion = " + SDKVersion);
		DatePicker dp = findDatePicker((ViewGroup) mDialog.getWindow()
				.getDecorView());// ���õ���������
		if (dp != null) {
			if (SDKVersion < 11) {
				((ViewGroup) dp.getChildAt(0)).getChildAt(1).setVisibility(
						View.GONE);
			} else if (SDKVersion > 14) {
				// ����getChildAt()��������˭ 0���� 1���� 2����
				((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0))
						.getChildAt(2).setVisibility(View.GONE);
			}
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) { // ��Ӧ�����showDialog(0);//���ڵ�����
		mDialog = null;
		switch (id) {
		case 0:
			Calendar calendar = Calendar.getInstance();
			mDialog = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							driver_time_year.setText(year + "");
							driver_time_month.setText(monthOfYear + 1 + "");
							timeyear = year;
							timemonth = monthOfYear + 1;

						}
					}, calendar.get(Calendar.YEAR),
					calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH));
			break;
		}
		return mDialog;
	}

	/**
	 * �ӵ�ǰDialog�в���DatePicker�ӿؼ�
	 * 
	 * @param group
	 * @return
	 */
	private DatePicker findDatePicker(ViewGroup group) {
		if (group != null) {
			for (int i = 0, j = group.getChildCount(); i < j; i++) {
				View child = group.getChildAt(i);
				if (child instanceof DatePicker) {
					return (DatePicker) child;
				} else if (child instanceof ViewGroup) {
					DatePicker result = findDatePicker((ViewGroup) child);
					if (result != null)
						return result;
				}
			}
		}
		return null;
	}

	/**
	 * ��ȡϵͳSDK�汾
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int getSDKVersionNumber() {
		int sdkVersion;
		try {
			sdkVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
		} catch (NumberFormatException e) {
			sdkVersion = 0;
		}
		return sdkVersion;
	}
	private void initFilter() {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter();
		filter.addAction("finish.before.regist.page");		
		registerReceiver(msgReceiver, filter);
	}
	private BroadcastReceiver msgReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub			
			finish();		
		}
	};
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(msgReceiver);
	}
}
