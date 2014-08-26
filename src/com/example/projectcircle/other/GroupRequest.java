package com.example.projectcircle.other;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.adpter.FriendRequestAdapter;
import com.example.projectcircle.adpter.GroupRequestAdapter;
import com.example.projectcircle.bean.GroupInfo;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class GroupRequest extends Activity{
	private static final String TAG = null;
	private ListView listview;
	private GroupRequestAdapter myAdapter;
	private Button button_back;
	private ArrayList<GroupInfo> groupList;
	private String groupid;
	private String myid;
	private ArrayList<UserInfo> usersList;
	private TextView textView1;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.request_message);
		textView1 =(TextView)findViewById(R.id.textView1);
		textView1.setText("Ⱥ��������Ϣ");
		initList();
		myid = LoginActivity.id;
		//	�����ҵ�Ⱥ��
		findGroup(myid);			
		back();
	}	
	private void findGroup(String uid) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("�ҵ�Ⱥ���б�", "����:" + response);
				parseMyGroupList(response);			
			}		
		};
		MyHttpClient client = new MyHttpClient();
		client.findCreatGroup(uid,res);
	}
	private void parseMyGroupList(String response) {
		// TODO Auto-generated method stub
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("groups");
			groupList = new ArrayList<GroupInfo>();
			JSONArray json = obj.getJSONArray("resultlist");
			int length = json.length();
			 System.out.println("length==" + length);
			 Log.i("�ҵ�Ⱥ���б�", "JSONArray:"+json);
			 for (int i = 0; i < length; i++) {
				 JSONObject objo = json.getJSONObject(i);
				 Log.i(TAG, "objo:"+objo);
				 //���Ⱥ���id
				 groupid = objo.getString("id");
				 //����������������Ⱥ�����Ϣ
				 groupRequestList(groupid);
			 }
			 System.out.println("my group list==" + groupList);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void groupRequestList(String gid) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("������������Ⱥ���response", response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {
						//�����������û�
						parsegroupRequestList(response);
						//�����г�listView						
						getList();
						myAdapter.notifyDataSetChanged();
					} else {
						ToastUtils.showShort(getApplicationContext(),
								"������Ч��");					
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.verifyMember2Group(gid, res);
	}

	private void parsegroupRequestList(String response) {
		// TODO Auto-generated method stub
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("gu");
			usersList = new ArrayList<UserInfo>();
			JSONArray json = obj.getJSONArray("resultlist");
			int length = json.length();
			System.out.println("length==" + length);
			for (int i = 0; i < length; i++) {
				UserInfo user = new UserInfo();
				obj = json.getJSONObject(i);
				user.setId(obj.getString("id"));			
				user.setInfo(obj.getString("info"));
				user.setGid(obj.getString("gid"));
//				user.setAccept(obj.getString("accept"));			
				usersList.add(user);
			}
			Log.i("������������Ⱥ�����Ϣ-usersList", usersList+"");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	private void initList() {
		// TODO Auto-generated method stub
		listview = (ListView) findViewById(R.id.message_listView);
		myAdapter = new GroupRequestAdapter(listItem, this);
		listview.setAdapter(myAdapter);
	}
	
	
	ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
	private void getList() {
		// TODO Auto-generated method stub
		Log.i(TAG, "����������ҵ�Ⱥ����û�:usersList" + usersList);
		if(usersList != null){
		for (int i = 0; i < usersList.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			//map.put("friendID", friendList.get(i).getId());
			map.put("uid", usersList.get(i).getId());
			map.put("info", usersList.get(i).getInfo());
			map.put("gid", usersList.get(i).getGid());
			listItem.add(map);
		}
		}
	}
	private void back() {
		// TODO Auto-generated method stub
		button_back = (Button) findViewById(R.id.friend_request_back);
		button_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				GroupRequest.this.setResult(RESULT_OK, getIntent());
				GroupRequest.this.finish();
			}
		});
	}

}
