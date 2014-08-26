package com.example.projectcircle.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import com.example.projectcircle.app.MyApplication;
import com.example.projectcircle.bean.MyPersonBean;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

public class UserInfoUtils {

	/**
	 * ������Ϣ
	 * 
	 * @param info
	 * @param context
	 */
	public static void setPersonInfo(MyPersonBean info, Activity context) {
		SharedPreferences preferences = context.getSharedPreferences("Login",
				Context.MODE_PRIVATE);

		Editor editor = preferences.edit();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream infoStream = new ObjectOutputStream(baos);
			infoStream.writeObject(info);
			String infobase64 = Base64.encodeToString(baos.toByteArray(),
					Base64.DEFAULT);
			editor.putString("personinfo", infobase64);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// �ύ�޸�
		editor.commit();
	}

	/**
	 * ��ȡ��Ϣ
	 */
	public static void getPersonInfo(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"Login", Context.MODE_PRIVATE);
		String personstr = sharedPreferences.getString("personinfo", "");////getString()�ڶ�������Ϊȱʡֵ�����preference�в����ڸ�key��������ȱʡֵ
		if (!TextUtils.isEmpty(personstr)) {
			byte[] bytepersonbase64 = Base64.decode(personstr.getBytes(),
					Base64.DEFAULT);
			ByteArrayInputStream bis = new ByteArrayInputStream(
					bytepersonbase64);
			ObjectInputStream ois;
			try {
				ois = new ObjectInputStream(bis);
				try {
					MyPersonBean myPersonBean = (MyPersonBean) ois.readObject();
					MyApplication.setMyPersonBean(myPersonBean);

				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (StreamCorruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * ע����Ϣ
	 * 
	 * @param context
	 */
	public static void delPersonInfo(Context context) {
		
		boolean a=context.getSharedPreferences("Login", Context.MODE_PRIVATE).edit()
				.clear().commit();//SharedPreferences��һ���ӿڣ��������޷�����SharedPreferencesʵ���ģ�����ͨ��Context.getSharedPreferences(String name,int mode)���õ�һ��SharedPreferencesʵ��
		//name����ָ�ļ����ƣ�����Ҫ�Ӻ�׺.xml��ϵͳ���Զ�Ϊ��������ϡ�һ������ļ��洢��/data/data/<package name>/shared_prefs��(������Գ��ʵ�)
//		boolean b=context.getSharedPreferences("LoginActivity",
//				Context.MODE_PRIVATE).edit().clear().commit();
		
//		Log.i("ɾ��", "ɾ��A:"+a);
	}

}
