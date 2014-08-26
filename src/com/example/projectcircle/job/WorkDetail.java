package com.example.projectcircle.job;

import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.projectcircle.R;
import com.example.projectcircle.adpter.WorkAdapter;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class WorkDetail extends Activity implements OnClickListener {
	private static final String TAG = "WorkDetail";
	String jid;
	String address, title, date, tel, contact, detail, year, month, day;
	Button left;
	TextView address_txt, title_txt, date_txt, tel_txt, contact_txt,
			detail_txt,distance_txt;
	private String date1;
	private String distance;
	private String job;
	private TextView titleText;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.work_detail_lay);
		Intent intent = getIntent();
		jid = intent.getStringExtra("jid");
		distance = intent.getStringExtra("distance");
		job = intent.getStringExtra("job");
		titleText = (TextView)findViewById(R.id.textView1);
		if(job.equals("1")){
			titleText.setText("������ҵ��Ϣ����");
		}else if(job.equals("2")){
			titleText.setText("˾��������Ϣ����");
		}
		JobDetail(jid);
		left = (Button) findViewById(R.id.work_detail_left);
		left.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
	}

	private void JobDetail(String id) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "����:" + response);
				parseJobDetail(response);
				initView();
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.JobDetail(id, res);
	}

	private void parseJobDetail(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("job");
			address = obj.getString("location");
			title = obj.getString("title");
			tel = obj.getString("tel");
			contact = obj.getString("name");
			detail = obj.getString("detail");
			date1= obj.getString("date");
			// Log.i("read", uname);
			// Log.i("read", utype);
			// Log.i("read", ucity);
			// Log.i("read", udevice);
			// Log.i("read", uheadimg);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void initView() {
		// TODO Auto-generated method stub
		address_txt = (TextView) findViewById(R.id.work_detail_address);
		title_txt = (TextView) findViewById(R.id.work_detail_title);
		date_txt = (TextView) findViewById(R.id.work_detail_date);
		tel_txt = (TextView) findViewById(R.id.work_detail_phone);
		contact_txt = (TextView) findViewById(R.id.work_detail_contact);
		detail_txt = (TextView) findViewById(R.id.work_detail_info);
		distance_txt = (TextView) findViewById(R.id.work_detail_distance);
		address_txt.setText("�ص㣺" + address);
		title_txt.setText(title);
		date1 = date1.substring(0,10);
		date_txt.setText("ʱ�䣺"+date1);
		tel_txt.setText("��ϵ�绰��" + tel);
		contact_txt.setText("��ϵ�ˣ�" + contact);
		detail_txt.setText(detail);
		distance_txt.setText("����:"+"  "+distance+"km");
	}
}
