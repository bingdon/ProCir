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

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.adpter.FriendRequestAdapter;
import com.example.projectcircle.adpter.MsgAdapter;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.friend.GroupChat;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class FriendRequest extends Activity{
	private static final String TAG = null;
	private String bid;
	private ArrayList<UserInfo> friendRequestList;
	private ListView listview;
	private FriendRequestAdapter myAdapter;
	private Button button_back;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.request_message);
		bid = LoginActivity.id;
		FriendRequestList(bid);
		back();
	}
	private void FriendRequestList(String bid) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("返回：", response);
				parseFriendRequestList(response);
				initList();				
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.FriendRequestMessage(bid, res);
	}
	private void parseFriendRequestList(String response) {
		// TODO Auto-generated method stub
		 try {
			 Log.i("解析好友请求的response", response+"");
			 JSONObject result = new JSONObject(response);
			 JSONObject obj = result.getJSONObject("friends");
			 friendRequestList = new ArrayList<UserInfo>();
			 JSONArray json = obj.getJSONArray("resultlist");
			 int length = json.length();
			 System.out.println("length==" + length);
			 Log.i(TAG, "JSONArray:"+json);
			 for (int i = 0; i < length; i++) {
			 UserInfo user = new UserInfo();
			 JSONObject objo = json.getJSONObject(i);
			 Log.i(TAG, "objo:"+objo);
			 user.setId(objo.getString("cid"));
			 user.setUsername(objo.getString("username"));
		     user.setInfo(objo.getString("info"));
		     friendRequestList.add(user);
			 }
			 } catch (JSONException e) {
			 e.printStackTrace();
			 }

	}

	private void initList() {
		// TODO Auto-generated method stub
		listview = (ListView) findViewById(R.id.message_listView);
		myAdapter = new FriendRequestAdapter(getList(), this);
		listview.setAdapter(myAdapter);
	}
	private ArrayList<HashMap<String, Object>> getList() {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		Log.i(TAG, "返回:friendList" + friendRequestList);
		if(friendRequestList != null){
		for (int i = 0; i < friendRequestList.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			//map.put("friendID", friendList.get(i).getId());
			map.put("cid", friendRequestList.get(i).getId());
			map.put("info", friendRequestList.get(i).getInfo());
//			map.put("headimage", friendList.get(i).getHeadimage());
			map.put("username", friendRequestList.get(i).getUsername());
//			map.put("tel", friendList.get(i).getTel());			
//			map.put("accept", friendList.get(i).getAccept());
			listItem.add(map);
		}
		}
		return listItem;
	}
	private void back() {
		// TODO Auto-generated method stub
		button_back = (Button) findViewById(R.id.friend_request_back);
		button_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				FriendRequest.this.setResult(RESULT_OK, getIntent());
				FriendRequest.this.finish();
			}
		});
	}

}
