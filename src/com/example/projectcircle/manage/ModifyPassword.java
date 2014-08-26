package com.example.projectcircle.manage;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * �޸�����
 * 
 */
public class ModifyPassword extends Activity implements OnClickListener {
	Button back, submit;
	EditText pwd_past_txt, pwd_new_txt, pwd_again_txt;
	String pwd_past, pwd_new, pwd_again;
	String id;
	String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_password);
		initView();
		id = LoginActivity.id;
		UserDetail(id);
		
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
				Log.i("�޸�����", "����:" + response);
				parseUserDetail(response);
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.UserDetail(id, res);
	}

	public void parseUserDetail(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("user");
			password = obj.getString("password");
			// password�Ӻ�̨��ȡ������ Ҳ����ԭ����
			// password��ȡ֮���ӡ���� �������뱻�����ˣ�����������ν��ܣ�����˵��Ҫԭ����ֱ�Ӹ��� �����ӿ���ô����
			Log.i("password", "password=" + password);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void initView() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.modify_pwd_left);
		submit = (Button) findViewById(R.id.modify_pwd_submit);
		pwd_past_txt = (EditText) findViewById(R.id.modify_pwd_past);
		pwd_new_txt = (EditText) findViewById(R.id.modify_pwd_new);
		pwd_again_txt = (EditText) findViewById(R.id.modify_pwd_again);

		back.setOnClickListener(this);
		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.modify_pwd_left:
			finish();
			break;
		case R.id.modify_pwd_submit:
			submit();
			break;
		default:
			break;
		}
	}

	private void submit() {
		// TODO Auto-generated method stub
		pwd_past = pwd_past_txt.getText().toString();
		pwd_new = pwd_new_txt.getText().toString();
		pwd_again = pwd_again_txt.getText().toString();

//		if (!pwd_past.equals(password)) {
//			ToastUtils.showShort(getApplicationContext(), "ԭ�����������������룡");
//			return;
//		}
		if (pwd_new.length() != 6) {
			ToastUtils.showShort(getApplicationContext(), "�������6λ���ֻ���ĸ��");
			return;
		}
		if (!pwd_again.equals(pwd_new)) {
			ToastUtils.showShort(getApplicationContext(), "�����벻һ�£����������룡");
			return;
		}
		if (!TextUtils.isEmpty(pwd_past) && !TextUtils.isEmpty(pwd_new)
				&& !TextUtils.isEmpty(pwd_again)) {
			// ��������ýӿ� �޸ĳɹ����������Ž�ȥ �����޸�ʧ��
			//�ж��û�����������Ƿ���ȷ
			PwdIsTrue(id,pwd_past,pwd_new);
		
			finish();
		}
	}
	//�ж��û�����������Ƿ���ȷ
	private void PwdIsTrue(String id, String pwd_past,String pwd_new) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("pwd is true?", "����:" + response);
			try {
				JSONObject obj= new JSONObject(response);
				if(obj.getInt("result") == 1){
					Log.i("ԭ����ƥ��", "ԭ����ƥ��");
					LoginActivity.savePwd(pwd_new_txt.getText().toString(), ModifyPassword.this);
				}else{
				ToastUtils.showShort(getApplicationContext(), "ԭ����������������룡");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			}
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1, arg2, arg3);
				Log.i("failuer ", "failuer");
	
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.doPwdIsTrue(id,pwd_past,pwd_new, res);
	}
}
