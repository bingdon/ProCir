package com.example.projectcircle;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.example.projectcircle.adpter.Utils;
import com.example.projectcircle.app.MyApplication;
import com.example.projectcircle.friend.FriendPage;
import com.example.projectcircle.group.MyGroup;
import com.example.projectcircle.util.UserInfoUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class WelcomeActivity extends Activity {
	boolean isFirstIn = true;
	private static final int GO_HOME = 1000;
	private static final int GO_LOGIN = 1001;
	// �ӳ�3��
	private static final long SPLASH_DELAY_MILLIS = 3000;
	/**
	 * Handler:��ת����ͬ����
	 * �����Ϣ����ʹ��ͬһ��handler��
                 ͨ��what��ͬ���ֲ�ͬ����Ϣ��Դ�� �Ӷ���ȡ��Ϣ����
	 */

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				goHome();
				break;
			case GO_LOGIN:
				goLogin();
				break;
			}
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
//		if (!Utils.hasBind(getApplicationContext())) {
			PushManager.startWork(getApplicationContext(),
					PushConstants.LOGIN_TYPE_API_KEY,
					Utils.getMetaValue(WelcomeActivity.this, "api_key"));
			// Push: �������ڵ���λ�����ͣ����Դ�֧�ֵ���λ�õ����͵Ŀ���
			PushManager.enableLbs(getApplicationContext());
		
//		}//�趨���Լ���id
//		List<String> tags = new ArrayList();
//		tags.add("guojianchao");
//		PushManager.setTags(getApplicationContext(), tags);
		initView();
		
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		// ��ȡSharedPreferences����Ҫ������
		// ʹ��SharedPreferences����¼�����ʹ�ô���

		// ȡ����Ӧ��ֵ�����û�и�ֵ��˵����δд�룬��true��ΪĬ��ֵ

		SharedPreferences sharedPreferences = getSharedPreferences("Login",
				Context.MODE_PRIVATE);
		isFirstIn = sharedPreferences.getBoolean("isFirstIn", true);
		// LoginActivity.id = sharedPreferences.getString("id", "");
		// LoginActivity.phone = sharedPreferences.getString("tel", "");
		// LoginActivity.pwd = sharedPreferences.getString("password", "");
		// System.out.println("tag" + sharedPreferences.getString("tel", "")
		// + sharedPreferences.getString("password", "") + "id"
		// + sharedPreferences.getString("id", ""));

		// �жϳ�����ڼ������У�����ǵ�һ����������ת���������棬������ת��������
		if (!isFirstIn) {
			// ʹ��Handler��postDelayed������3���ִ����ת��MainActivity
			mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
			Log.i("enter", "home");
		} else {
			mHandler.sendEmptyMessageDelayed(GO_LOGIN, SPLASH_DELAY_MILLIS);
			Log.i("enter", "Login");
		}
	}


	private void goHome() {
		// TODO Auto-generated method stub		
		 Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
		 startActivity(intent);
		 finish();
	}

	private void goLogin() {
		
		try {
			FriendPage.getFriInfo(this);
			MyGroup.getGroupInfo(this);
			UserInfoUtils.getPersonInfo(WelcomeActivity.this);
			if (MyApplication.getMyPersonBean()!=null) {
				LoginActivity.id=MyApplication.getMyPersonBean().getId();
				Log.i("�û�ID:", ""+LoginActivity.id);
				startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
				finish();
			}else {
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
				finish();
			}
		} catch (Exception e) {
			// TODO: handle exception
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
		}
		
		
	}
}
