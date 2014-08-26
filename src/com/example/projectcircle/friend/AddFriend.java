package com.example.projectcircle.friend;

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

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.MainActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.SiginActivity;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class AddFriend extends Activity {
	Button button;
	private Button search_friend;
	protected String context;
	private EditText search_editText;
	 String id;
	String name,address, equipment, username, headimage, place, type, info;
	String []a = new String [6];	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_friend);			
		searchFriends();
		back();	
	}
	private void searchFriends() {
		// TODO Auto-generated method stub
		//���Һ���
		search_friend =  (Button)findViewById(R.id.search_friend);
		search_friend.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				search_editText = (EditText) findViewById(R.id.search_editText);
				context = search_editText.getText().toString();				
				if (TextUtils.isEmpty(context)) {
					ToastUtils.showShort(getApplicationContext(), "�����빤�̺Ż����ֻ���");
					return;
				}
				
				if (!TextUtils.isEmpty(context)) {
					
					dosearchfriend(context);
					}
			}
		});	
	}
	protected void dosearchfriend(String context) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			public void onSuccess(String response) {
				System.out.println(response);
				parsefriend(response);
				JSONObject obj;				
				try {
					obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {				
					Intent intent = new Intent(AddFriend.this,
								DetailInformation.class);
					intent.putExtra("id",
							id); 
					intent.putExtra("address",
							address); 
					intent.putExtra("equipment",
							equipment); 
					intent.putExtra("headimage",
							headimage); 
					intent.putExtra("username",
							username); 
					intent.putExtra("place",
							place);
					intent.putExtra("type",
							type); 
					intent.putExtra("info",
							info); 
					startActivity(intent);
					finish();
					} else {
						ToastUtils.showShort(getApplicationContext(),
								"δ��������Ӧ�ĺ��ѣ�");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			}
			@SuppressWarnings("deprecation")
			@Override
			public void onFailure(Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(error);
			}
		};
		MyHttpClient myhttpclient = new MyHttpClient();
		myhttpclient.searchfriend(context, res);
	}
	private void parsefriend(String response) {
		// TODO Auto-generated method stub
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("user");
			id = obj.getString("id");//���̺�
			address = obj.getString("address");//����
			equipment = obj.getString("equipment");//�豸
			username = obj.getString("username");//����
			headimage = obj.getString("headimage");//ͷ��
			place = obj.getString("place");//����û�ĵط�
			type = obj.getString("type");//����			
			info = obj.getString("info");
			Log.i("response  username==", username);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	
	private void back() {
		// TODO Auto-generated method stub
		button = (Button) findViewById(R.id.n_group_left);
		button.setOnClickListener(new  OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AddFriend.this.setResult(RESULT_OK, getIntent());
				AddFriend.this.finish();
			}});
	}

}
