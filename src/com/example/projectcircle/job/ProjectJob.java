package com.example.projectcircle.job;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.projectcircle.HomeActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.adpter.WorkAdapter;
import com.example.projectcircle.bean.JobInfo;
import com.example.projectcircle.util.DistentsUtil;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * ������ҵ�б�
 */
public class ProjectJob extends Activity implements OnClickListener {
	private static final String TAG = "ProjectJob";
	ListView listview;
	WorkAdapter myAdapter;
	/**
	 * ����Button
	 */
	Button back, add_job;

	String type;
	public ArrayList<JobInfo> jobsList;
	Double mylat = HomeActivity.latitude;
	Double mylon = HomeActivity.longitude;
	double distance ;
	ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_job);
		Intent intent = getIntent();
		type = intent.getStringExtra("type");
		initView();
	
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();	
		JobList(type);
	}

	private void JobList(String type) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "����:" + response);
				parseJobList(response);
				initList();
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.JobList(type, res);
	}

	private void parseJobList(String response) {
		// TODO Auto-generated method stub
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("jtypes");
			jobsList = new ArrayList<JobInfo>();
			JSONArray json = obj.getJSONArray("resultlist");
			Log.i("������ҵ�б�json", json+"");			
			int length = json.length();
			System.out.println("length==" + length);
			for (int i = 0; i < length; i++) {
				JobInfo job = new JobInfo();
				obj = json.getJSONObject(i);				
				job.setTitle(obj.getString("title"));
				job.setDate(obj.getString("date"));	
				job.setId(obj.getString("id"));	
				try {
					job.setLat(obj.getDouble("jlat"));
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					job.setLon(obj.getDouble("jlon"));
				} catch (Exception e) {
					// TODO: handle exception
				}			
				jobsList.add(job);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void initView() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.project_job_left);
		add_job = (Button) findViewById(R.id.project_job_right);
		back.setOnClickListener(this);
		add_job.setOnClickListener(this);
	}

	private void initList() {
		// TODO Auto-generated method stub
		listview = (ListView) findViewById(R.id.project_job_listView);
		listItem.clear();
		listItem = getList();
		myAdapter = new WorkAdapter(listItem, this);
		listview.setAdapter(myAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.i("arg2", arg2+"");
				Log.i("jid", jobsList.get(arg2).getId()+"");
				Intent intent = new Intent(ProjectJob.this, WorkDetail.class);
				intent.putExtra("jid", jobsList.get(arg2).getId());
				intent.putExtra("distance", listItem.get(arg2).get("distance")+"");
				intent.putExtra("job", "1");//1��������ҵ��Title��ʾ������ҵ��Ϣ������棻2.����˾������Title��ʾ˾��������Ϣ����
				startActivity(intent);

			}
		});
	}

	private ArrayList<HashMap<String, Object>> getList() {
		// TODO Auto-generated method stub
		Log.i("jobsList",jobsList+"");
		for (int i = 0; i < jobsList.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("title", jobsList.get(i).getTitle());		
			map.put("date", jobsList.get(i).getDate());
//			map.put("commercialLat", jobsList.get(i).getLat());
//			map.put("commercialLon", jobsList.get(i).getLon());
			Double commercialLat = jobsList.get(i).getLat();
			Double commercialLon = jobsList.get(i).getLon();
			//�����Һͺ���֮��ľ���
			distance = DistentsUtil.GetDistance(commercialLat,commercialLon,
					mylat,mylon);
			//distance = DistentsUtil.changep2(distance);
			map.put("distance", distance);
			listItem.add(map);
		}
		//���������150��ȥ����ֻ��ʾ150Km���ڵ���Ϣ
		for(int i = 0,len = listItem.size();i<len; i++){
			if((Double)(listItem.get(i).get("distance")) > 150){
			listItem.remove(i);
			  --len;//����һ��  
		       --i;//��лdeny_guoshouָ����������ӻ��������1¥��˵�������  
			}
		Log.i("listItem������ҵ�б�", listItem+"");		
		}
		return listItem;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.project_job_left:

			finish();
			break;
		case R.id.project_job_right:
			Intent intent2 = new Intent(ProjectJob.this, AddJob.class);
			intent2.putExtra("type", type);
			startActivity(intent2);
			break;
		default:
			break;
		}
	}
}
