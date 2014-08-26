package com.example.projectcircle.job;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.projectcircle.HomeActivity;
import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * ��ӹ�����ҵ��˾������
 */
public class AddJob extends Activity {
	public static final String TAG = null;
	EditText address_txt, title_txt, detail_txt, contact_txt, tel_txt;
	String address, title, detail, contact, tel;
	String type;
	String device1, device2, device3, device4, device5;
	Button back, submit;
	TextView text_title;
	String phone, name;
	String uid;
	//��λ
	private LocationClient mLocationClient;
//	private MyBDLocationListener mBDLocationListener;
	String province;
	String city;
	String district;
    String rplace = HomeActivity.rplace;
	private Double jlat = HomeActivity.latitude;
	private Double jlon = HomeActivity.longitude;
	private TextView main_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_job_lay);
		Intent intent = getIntent();
		type = intent.getStringExtra("type");
		phone = LoginActivity.phone;
		name = LoginActivity.name;
		uid = LoginActivity.id;
		//initLocation();
		initView();
		initBtn();
	}

	private void initView() {
		// TODO Auto-generated method stub
		
		main_title = (TextView) findViewById(R.id.textView1);
		address_txt = (EditText) findViewById(R.id.add_job_address);
		title_txt = (EditText) findViewById(R.id.add_job_title);
		detail_txt = (EditText) findViewById(R.id.add_job_detail);
		contact_txt = (EditText) findViewById(R.id.add_job_contact);
		tel_txt = (EditText) findViewById(R.id.add_job_tel);
		text_title = (TextView) findViewById(R.id.text_title);
		if (type.equals("˾������")) {
			main_title.setText("˾��������Ϣ");
			text_title.setText("ְλ����");
			title_txt.setHint("�����д��ڼ�ʻԱ");
			detail_txt.setHint("����������Ҫ��");
		}
		tel_txt.setText(phone);
		contact_txt.setText(name);
		address_txt.setText(rplace+"");
	}

	private void initBtn() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.add_job_left);
		submit = (Button) findViewById(R.id.add_job_submit);

		back.setOnClickListener(listener);
		submit.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.add_job_left:
				// Intent intent = new Intent(AddJob.this, ProjectJob.class);
				// startActivity(intent);
				finish();
				break;
			case R.id.add_job_submit:
				postInfo();
				break;
			default:
				break;
			}
		}
	};


	private void postInfo() {
		// TODO Auto-generated method stub
		address = address_txt.getText().toString();
		title = title_txt.getText().toString();
		detail = detail_txt.getText().toString();
		contact = contact_txt.getText().toString();
		tel = tel_txt.getText().toString();
		if (TextUtils.isEmpty(address)) {
			ToastUtils.showShort(getApplicationContext(), "��ַ����Ϊ��!");
			return;
		}

		if (TextUtils.isEmpty(title)) {
			ToastUtils.showShort(getApplicationContext(), "���ⲻ��Ϊ��!");
			return;
		}

		if (TextUtils.isEmpty(detail)) {
			ToastUtils.showShort(getApplicationContext(), "���鲻��Ϊ��!");
			return;
		}
		if (TextUtils.isEmpty(contact)) {
			ToastUtils.showShort(getApplicationContext(), "��ϵ�˲���Ϊ��!");
			return;
		}
		if (TextUtils.isEmpty(tel)) {
			ToastUtils.showShort(getApplicationContext(), "��ϵ�绰����Ϊ��!");
			return;
		}
		if (tel.length() != 11) {
			ToastUtils.showShort(getApplicationContext(), "��ϵ�绰�ĺ����ʽ����!");
			return;
		}
		PostJob(uid, title, address, type, detail, contact, tel);
	
	}

	private void PostJob(String uid, String title, String address, String type,
			String detail, String contact, String tel) {

		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			public void onSuccess(String response) {
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					Log.i("response-----result", obj.getInt("result") + "");
					if (obj.getInt("result") == 1) {
						Toast.makeText(getApplicationContext(), "�����ɹ���",
								Toast.LENGTH_LONG).show();
						// Intent intent = new Intent(AddJob.this,
						// ProjectJob.class);
						// startActivity(intent);
						finish();
					} else {
						Toast.makeText(getApplicationContext(), "����ʧ��",
								Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@SuppressWarnings("deprecation")
			@Override
			public void onFailure(Throwable error, String response) {
				// TODO Auto-generated method stub
				super.onFailure(error, response);
			}

			@SuppressWarnings("deprecation")
			@Override
			public void onFailure(Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(error);
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.PostJob(uid, title, address, type, detail, contact, tel, jlat, jlon, res);
	}

//	/**
//	 * ��ʼ����λ
//	 * 
//	 */
//	private void initLocation() {
//		// TODO Auto-generated method stub
//		mLocationClient = new LocationClient(this.getApplicationContext());
//
//		mBDLocationListener = new MyBDLocationListener();
//		////ע��λ�ü�����
//		mLocationClient.registerLocationListener(mBDLocationListener);
//
//		LocationClientOption option = new LocationClientOption();
//		option.setOpenGps(true);// ��gps
//		option.setScanSpan(60000);// ��λ��ʱ��������λ��ms
//		// ��Ҫ��ַ��Ϣ������Ϊ�����κ�ֵ��string���ͣ��Ҳ���Ϊnull��ʱ������ʾ�޵�ַ��Ϣ��
//		option.setAddrType("all");
//
//		// �����Ƿ񷵻�POI�ĵ绰�͵�ַ����ϸ��Ϣ��Ĭ��ֵΪfalse����������POI�ĵ绰�͵�ַ��Ϣ��
//		option.setPoiExtraInfo(true);
//
//		// ���ò�Ʒ�����ơ�ǿ�ҽ�����ʹ���Զ���Ĳ�Ʒ�����ƣ����������Ժ�Ϊ���ṩ����Ч׼ȷ�Ķ�λ����
//		option.setProdName("ͨ��GPS��λ�ҵ�ǰ��λ��");
//
//		// ��ѯ��Χ��Ĭ��ֵΪ500�����Ե�ǰ��λλ��Ϊ���ĵİ뾶��С��
//		option.setPoiDistance(600);
//		// �������û��涨λ����
//		option.disableCache(true);
//		// ����ϵ���ͣ��ٶ��ֻ���ͼ����ӿ��е�����ϵĬ����bd09ll
//		option.setCoorType("bd09ll");
//		// �������ɷ��ص�POI������Ĭ��ֵΪ3������POI��ѯ�ȽϺķ�������������෵�ص�POI�������Ա��ʡ������
//		option.setPoiNumber(3);
//		// ���ö�λ��ʽ�����ȼ���
//		// ��gps���ã����һ�ȡ�˶�λ���ʱ�����ٷ�����������ֱ�ӷ��ظ��û����ꡣ���ѡ���ʺ�ϣ���õ�׼ȷ����λ�õ��û������gps�����ã��ٷ����������󣬽��ж�λ��
//		option.setPriority(LocationClientOption.GpsFirst);
//		mLocationClient.setLocOption(option);
//		mLocationClient.start();
//
//	}
//
//	/**
//	 * ��дBDLocationListener�ӿڵ�ʵ���࣬�����첽���صĶ�λ������첽���ص�POI��ѯ�����
//	 * 
//	 */
//	final class MyBDLocationListener implements BDLocationListener {
//
//		@Override
//		public void onReceiveLocation(BDLocation location) {
//			if (location == null)
//				return;
//			province = location.getProvince(); // ��ȡʡ����Ϣ
//			city = location.getCity(); // ��ȡ������Ϣ
//			district = location.getDistrict(); // ��ȡ������Ϣ
//			rplace = location.getAddrStr();// ȫ��ַ
//			Log.i(TAG, "province = " + province);
//			Log.i(TAG, "city = " + city);
//			Log.i(TAG, "district = " + district);
//			latitude = location.getLatitude();
//			longitude = location.getLongitude();
//			radius = (int) location.getRadius();
//			Log.i(TAG, "latitude = " + latitude);
//			Log.i(TAG, "longitude = " + longitude);
//			Log.i(TAG, "radius = " + radius);
//
//
//			StringBuffer sb = new StringBuffer(256);
//			sb.append("time : ");
//			sb.append(location.getTime());
//			sb.append("\nerror code : ");
//			sb.append(location.getLocType());
//			sb.append("\nProvince : ");
//			sb.append(location.getProvince());
//			sb.append("\ncity : ");
//			sb.append(location.getCity());
//			sb.append("\nDistrict : ");
//			sb.append(location.getDistrict());
//			sb.append("\nlatitude : ");
//			sb.append(location.getLatitude());
//			sb.append("\nlontitude : ");
//			sb.append(location.getLongitude());
//			sb.append("\nradius : ");
//			sb.append(location.getRadius());
//			if (location.getLocType() == BDLocation.TypeGpsLocation) {
//				sb.append("\nspeed : ");
//				sb.append(location.getSpeed());
//				sb.append("\nsatellite : ");
//				sb.append(location.getSatelliteNumber());
//			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
//				sb.append("\naddr : ");
//				sb.append(location.getAddrStr());
//			}
//			Log.i(TAG, sb + "");
//
//			jobAddress(rplace);	
//		}
//
//		@Override
//		public void onReceivePoi(BDLocation poiLocation) {
//
//			// �����¸��汾��ȥ��poi����
//			if (poiLocation == null) {
//				return;
//			}
//			StringBuffer sb = new StringBuffer(256);
//			sb.append("Poi time : ");
//			sb.append(poiLocation.getTime());
//			sb.append("\nerror code : ");
//			sb.append(poiLocation.getLocType());
//			sb.append("\nProvince : ");
//			sb.append(poiLocation.getProvince());
//			sb.append("\ncity : ");
//			sb.append(poiLocation.getCity());
//			sb.append("\nDistrict : ");
//			sb.append(poiLocation.getDistrict());
//			sb.append("\nlatitude : ");
//			sb.append(poiLocation.getLatitude());
//			sb.append("\nlontitude : ");
//			sb.append(poiLocation.getLongitude());
//			sb.append("\nradius : ");
//			sb.append(poiLocation.getRadius());
//			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
//				sb.append("\naddr : ");
//				sb.append(poiLocation.getAddrStr());
//			}
//			if (poiLocation.hasPoi()) {
//				sb.append("\nPoi:");
//				sb.append(poiLocation.getPoi());
//			} else {
//				sb.append("noPoi information");
//			}
//			Log.i(TAG, sb + "");
//		}
//	}

}
