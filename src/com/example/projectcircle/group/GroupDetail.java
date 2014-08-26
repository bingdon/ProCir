package com.example.projectcircle.group;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.adpter.MemberAdapter;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.other.Chat;
import com.example.projectcircle.other.GroupChatOther;
import com.example.projectcircle.personal.PersonalPage;
import com.example.projectcircle.util.MsgUtils;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.example.projectcircle.view.WiperSwitch;
import com.example.projectcircle.view.WiperSwitch.OnChangedListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class GroupDetail extends Activity {
	private static final String TAG = "GroupDetail";
	/**
	 * ����Button
	 */
	Button send, back;

	/**
	 * ͷ����
	 */
	ImageView group_headbg, group_headimg;
	TextView group_name, group_number;

	/**
	 * Ⱥ����Ϣ
	 */
	LinearLayout name_lay;
	TextView master_name;

	/**
	 * �ص�
	 */
	TextView group_place;

	/**
	 * Ⱥ���
	 */
	TextView group_intro;
	/**
	 * Ⱥ��Ա
	 */
	LinearLayout friend_lay;
	GridView gridview;
	MemberAdapter imageAdapter;
	public ArrayList<UserInfo> groupList;
	/**
	 * ����
	 */
	WiperSwitch wiperSwitch;

	public static String gid;
	String id;
	String name;
	String address;
	String content;
	String headimage;
	String uname;
	// ͼƬ����
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	Context context;
	private Button apply;
    private String uid = LoginActivity.id;
	private Button send2;
	private Button apply_or_send;
	private String utype;
	private String ucity;
	private String ucontent;
	private String udevice;
	private String rplace;
	private String uheadimg;
	private String userlocation;
	private String hobby;
	private String info;
	private String utime;
	private String lat;
	private String lon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_detail);
		Intent intent = getIntent();
		gid = intent.getStringExtra("id");
		initBtn();
		initVoice();
		GroupDetails(gid);

		// ����ͼƬ���ؼ���ʾѡ�����һЩ���������ã�����doc�ĵ��ɣ�
		options = new DisplayImageOptions.Builder().cacheInMemory(true) // ����ͼƬʱ�����ڴ��м��ػ���
				.cacheInMemory(true)// �Ƿ񾏴涼�ȴ���
				.cacheOnDisc(true)// �Ƿ񾏴浽sd����
				.build();
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		findMember(gid);
		//�ж������ǲ���Ⱥ��Ա
		isMember(gid,uid);;
	}
	//�ж������ǲ���Ⱥ��Ա
	private void isMember(final String gid, String uid) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("�ж��Ƿ���Ⱥ��Ա��response", "����:" + response);
				try {
					JSONObject result = new JSONObject(response);
					if(result.getInt("result") ==1 ){
						Resources resources =  getBaseContext().getResources();   
						Drawable btnDrawable = resources.getDrawable(R.drawable.apply_join_group); 
						apply_or_send.setBackgroundDrawable(btnDrawable);
						apply_or_send.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(GroupDetail.this, ApplyGroup.class);
								intent.putExtra("gid", gid);
								startActivity(intent);
							}
						});

 
					}else if(result.getInt("result") == 0){
						Resources resources =  getBaseContext().getResources();   
						Drawable btnDrawable =  resources.getDrawable(R.drawable.send_message_selector); 
						apply_or_send.setBackgroundDrawable(btnDrawable);
						apply_or_send.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								go2GroupChat();
							}
						});
					
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
		MyHttpClient client = new MyHttpClient();
		client.isMember(gid,uid,res);
	}

	private void GroupDetails(String id) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "����:" + response);
				parseGroupDetail(response);
				initView();
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.GroupDetails(id, res);
	}

	private void parseGroupDetail(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("group");
			JSONArray json = obj.getJSONArray("resultlist");
			int length = json.length();
			System.out.println("length==" + length);
			for (int i = 0; i < length; i++) {
				obj = json.getJSONObject(i);
				id = obj.getString("uid");
				headimage = obj.getString("headimage");
				name = obj.getString("gname");
				address = obj.getString("gaddress");
				content = obj.getString("content");
				UserDetail(id);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ����Ⱥ��Ա ����ͽ���
	 * 
	 */
	private void findMember(String id) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "����:" + response);
				parsefindMember(response);
				initGrid();
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.findMember(id, res);
	}
	private void parsefindMember(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("members");
			groupList = new ArrayList<UserInfo>();
			JSONArray json = obj.getJSONArray("resultlist");
			int length = json.length();
			System.out.println("length==" + length);
			for (int i = 0; i < length; i++) {
				UserInfo group = new UserInfo();
				obj = json.getJSONObject(i);
				group.setHeadimage(obj.getString("headimage"));
				groupList.add(group);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ������Ϣ ����ͽ���
	 * 
	 */
	private void UserDetail(String id) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "����:" + response);
				parseUserDetail(response);
				initView();
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.UserDetail(id, res);
	}

	public void parseUserDetail(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("user");		
			uid = obj.getString("id");	
			uname = obj.getString("username");
			utype = obj.getString("type");			
			ucity = obj.getString("address");
			udevice = obj.getString("equipment");
			ucontent = obj.getString("sign");//����ǩ��
			uheadimg = obj.getString("headimage");
			rplace = obj.getString("rplace");//����û�ĵط�		
		    utime = obj.getString("lastlogintime");
		    lat = obj.getString("commercialLat");
		    lon = obj.getString("commercialLon");
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void initGrid() {
		// TODO Auto-generated method stub
		// Ⱥ��Ա
		gridview = (GridView) findViewById(R.id.g_detail_friend_member);
		imageAdapter = new MemberAdapter(getList(), this);
		gridview.setAdapter(imageAdapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent(GroupDetail.this, GroupNum.class);
				intent2.putExtra("gid", gid);
				startActivity(intent2);
			}	
			
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		// ͷ����
		group_headbg = (ImageView) findViewById(R.id.g_detail_background);
		group_headimg = (ImageView) findViewById(R.id.g_detail_headimg);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(MyHttpClient.IMAGE_URL + headimage,
				group_headbg, options, animateFirstListener);
		imageLoader.displayImage(MyHttpClient.IMAGE_URL + headimage,
				group_headimg, options, animateFirstListener);
		group_name = (TextView) findViewById(R.id.g_detail_name);
		group_name.setText(name);
		group_number = (TextView) findViewById(R.id.g_detail_number);
		group_number.setText(gid);
		// Ⱥ������
		master_name = (TextView) findViewById(R.id.g_detail_master_name);
		master_name.setText("Ⱥ����"+uname);
		// �ص�
		group_place = (TextView) findViewById(R.id.g_detail_distance);
		group_place.setText(address);
		// Ⱥ���
		group_intro = (TextView) findViewById(R.id.g_detail_introduce);
		group_intro.setText(content);

	}

	private void initVoice() {
		// TODO Auto-generated method stub
		wiperSwitch = (WiperSwitch) findViewById(R.id.wiperSwitch1);
		wiperSwitch.SetOnChangedListener(new OnChangedListener() {

			@Override
			public void OnChanged(boolean checkState) {
				// TODO Auto-generated method stub
				if (checkState) {
					ToastUtils.showShort(getApplicationContext(), "��������");
				} else {
					ToastUtils.showShort(getApplicationContext(), "�ر�����");
				}
			}
		});
	}

	private void initBtn() {
		// TODO Auto-generated method stub
		name_lay = (LinearLayout) findViewById(R.id.g_detail_name_lay);
//		friend_lay = (LinearLayout) findViewById(R.id.g_detail_friend_lay);
		apply_or_send = (Button) findViewById(R.id.g_detail_apply_or_send);		
		back = (Button) findViewById(R.id.g_detail_left);

		name_lay.setOnClickListener(listener);
//		friend_lay.setOnClickListener(listener);
//		apply.setOnClickListener(listener);
//		send2.setOnClickListener(listener);
		back.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.g_detail_name_lay:
				Intent intent = new Intent(GroupDetail.this, PersonalPage.class);
				intent.putExtra("id", id);
				intent.putExtra("type", utype);
				intent.putExtra("time",utime);
				intent.putExtra("lat",lat);
				intent.putExtra("lon",lon);
				startActivity(intent);
				break;
//			case R.id.g_detail_friend_lay:
//				Intent intent2 = new Intent(GroupDetail.this, GroupNum.class);
//				intent2.putExtra("gid", gid);
//				startActivity(intent2);
//				break;
//			case R.id.g_detail_apply:
//				Intent intent4 = new Intent(GroupDetail.this, ApplyGroup.class);
//				intent4.putExtra("gid", gid);
//				startActivity(intent4);
//				break;
			case R.id.g_detail_left:
				// Intent intent5 = new Intent(GroupDetail.this,
				// GroupPage.class);
				// startActivity(intent5);
				finish();
				break;
				
//			case R.id.g_detail_send:
//				
//				go2GroupChat();
//				
//				break;
				
			default:
				break;
			}
		}
	};

	private ArrayList<HashMap<String, Object>> getList() {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		if(groupList!=null && groupList.size()>0){
		for (int i = 0; i < groupList.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("headimage", groupList.get(i).getHeadimage());
			listItem.add(map);
		}
		}
		return listItem;
	}
	
	
	private void go2GroupChat(){
		if (TextUtils.isEmpty(gid)) {
			return;
		}
		
		if (!MsgUtils.isExitMsgList(gid, GroupDetail.this)) {
			MsgUtils.saveMsgList(MyGroup.getGroupname(gid), " ", "" + Chat.getDate(), gid,
					headimage, 1,0, GroupDetail.this);
		}
		
		Intent intent=new Intent();
		intent.setClass(GroupDetail.this, GroupChatOther.class);
		intent.putExtra("gid", gid);
		intent.putExtra("gname", name);
		startActivity(intent);
		
	}
	
}
