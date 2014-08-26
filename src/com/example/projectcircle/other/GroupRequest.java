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
		textView1.setText("群组请求信息");
		initList();
		myid = LoginActivity.id;
		//	查找我的群组
		findGroup(myid);			
		back();
	}	
	private void findGroup(String uid) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("我的群组列表", "返回:" + response);
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
			 Log.i("我的群组列表", "JSONArray:"+json);
			 for (int i = 0; i < length; i++) {
				 JSONObject objo = json.getJSONObject(i);
				 Log.i(TAG, "objo:"+objo);
				 //获得群组的id
				 groupid = objo.getString("id");
				 //解析请求加入我这个群组的消息
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
				Log.i("请求加入我这个群组的response", response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {
						//解析附近的用户
						parsegroupRequestList(response);
						//重新列出listView						
						getList();
						myAdapter.notifyDataSetChanged();
					} else {
						ToastUtils.showShort(getApplicationContext(),
								"搜索无效！");					
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
			Log.i("请求加入我这个群组的消息-usersList", usersList+"");
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
		Log.i(TAG, "返回想加入我的群组的用户:usersList" + usersList);
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
