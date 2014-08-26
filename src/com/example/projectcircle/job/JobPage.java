package com.example.projectcircle.job;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.projectcircle.R;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * ������ҳ
 */
public class JobPage extends Activity {
	private static final String TAG = "JobPage";
	/**
	 * �б�Button
	 */
	RelativeLayout shop, setting, driver,circle;
	String type1 = "������ҵ";
	String type2 = "˾������";
	String total1, total2, total3;
	TextView total_txt1, total_txt2, total_txt3;
	String type = "�̼�";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.job);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JobList(type1);
		JobList1(type2);
		AllBusiness(type);
	}

	private void AllBusiness(String type) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "����:" + response);
				parseAllBusiness(response);
				initView();
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.AllBusiness(type, res);
	}

	private void JobList1(String type) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "����:" + response);
				parseJobList1(response);
				initView();
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.JobList1(type, res);
	}

	private void JobList(String type) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "����:" + response);
				parseJobList(response);
				initView();
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.JobList(type, res);
	}

	private void parseAllBusiness(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("users");
			total1 = obj.getString("totalrecord");
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void parseJobList(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("jtypes");
			total2 = obj.getString("totalrecord");
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void parseJobList1(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("jtypes");
			total3 = obj.getString("totalrecord");
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void initView() {
		// TODO Auto-generated method stub
		circle = (RelativeLayout) findViewById(R.id.circle_Layout);
		shop = (RelativeLayout) findViewById(R.id.job_Layout2);
		setting = (RelativeLayout) findViewById(R.id.job_Layout3);
		driver = (RelativeLayout) findViewById(R.id.job_Layout4);
		total_txt1 = (TextView) findViewById(R.id.shop_total);
		total_txt2 = (TextView) findViewById(R.id.project_total);
		total_txt3 = (TextView) findViewById(R.id.requirement_total);

		total_txt1.setText("����" + total1 + "��������λ����ѡ��");
		total_txt2.setText("����" + total2 + "��������λ����ѡ��");
		total_txt3.setText("����" + total3 + "��������λ����ѡ��");
		circle.setOnClickListener(listener);
		shop.setOnClickListener(listener);
		setting.setOnClickListener(listener);
		driver.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.circle_Layout:
				Intent intent1 = new Intent(JobPage.this, projectCircle.class);
				startActivity(intent1);

				break;
			case R.id.job_Layout2:
				Intent intent2 = new Intent(JobPage.this, MoveShop.class);
				startActivity(intent2);

				break;
			case R.id.job_Layout3:
				Intent intent3 = new Intent(JobPage.this, ProjectJob.class);
				intent3.putExtra("type", type1);
				startActivity(intent3);

				break;
			case R.id.job_Layout4:
				Intent intent4 = new Intent(JobPage.this, Requirement.class);
				intent4.putExtra("type", type2);
				startActivity(intent4);

				break;
			default:
				break;
			}
		}
	};

}
