package com.example.projectcircle.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.projectcircle.bean.MyPersonBean;
import com.example.projectcircle.db.ProJectDatebase;
import com.example.projectcircle.db.utils.FriendChatUtils;
import com.example.projectcircle.db.utils.GroupChatUtils;
import com.example.projectcircle.other.Chat;
import com.example.projectcircle.other.ChatMsgEntity;
import com.google.gson.Gson;

public class SaveChattoDb {

	/**
	 * �����������
	 * 
	 * @param message
	 *            ��Ϣ
	 * @param context
	 *            ������
	 */
	public static void saveChat2db(String message, Context context) {

		String msg = getMsg(message);
		String id = getId(message);
		if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(msg)) {
			SaveMsg2View(msg, context, id);
		}

	}

	/**
	 * ����Ⱥ������
	 * 
	 * @param message
	 *            ��Ϣ
	 * @param context
	 *            ������
	 */
	public static void saveGroupChat2db(String message, Context context) {

		String msg = getMsg(message);
		String id = getgId(message);
		if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(msg)) {
			
			MyPersonBean myPersonBean=getPersonInfo(message);
			
			SaveMsg2View(msg, context,id, myPersonBean.getHeadimage(), myPersonBean.getId());
		}

	}

	private static String getMsg(String message) {
		String msg = "";
		try {
			JSONObject jsonObject = new JSONObject(message);
			JSONObject content = jsonObject.getJSONObject("content");
			msg = content.getString("content");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return msg;

	}

	public static String getId(String message) {
		String id = "";
		try {
			JSONObject jsonObject = new JSONObject(message);
			JSONObject content = jsonObject.getJSONObject("content");
			id = content.getString("senduid");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id;

	}

	public static String getgId(String message) {
		String id = "";
		try {
			JSONObject jsonObject = new JSONObject(message);
			JSONObject content = jsonObject.getJSONObject("content");
			id = content.getString("gid");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return id;

	}

	public static MyPersonBean getPersonInfo(String message) {
		MyPersonBean myPersonBean = new MyPersonBean();
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(message);
			JSONObject jsonObject2 = jsonObject.getJSONObject("user");
			Gson gson = new Gson();
			myPersonBean = gson.fromJson(jsonObject2.toString(),
					MyPersonBean.class);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			Log.e(MyPersonBean.class.getSimpleName(), "���ݽ�������");
			
		}

		return myPersonBean;

	}

	private static void SaveMsg2View(String msg, Context context, String id) {
		ChatMsgEntity chatMsgEntity = new ChatMsgEntity();
		chatMsgEntity.setDate(Chat.getDate());
		chatMsgEntity.setMsgType(true);
		chatMsgEntity.setText(msg);
		save(chatMsgEntity, context, id);
	}

	/**
	 * ����Ⱥ����Ϣ
	 * @param msg
	 * @param context
	 * @param gid
	 * @param headimage
	 * @param uid
	 */
	public static void SaveMsg2View(String msg, Context context, String gid,
			String headimage, String uid) {
		ChatMsgEntity chatMsgEntity = new ChatMsgEntity();
		chatMsgEntity.setDate(Chat.getDate());
		chatMsgEntity.setHeadimgString(headimage);
		chatMsgEntity.setTime(System.currentTimeMillis());
		chatMsgEntity.setUid(uid);
		chatMsgEntity.setMsgType(true);
		chatMsgEntity.setText(msg);
		saveGroupChat(chatMsgEntity, context, gid);
	}

	private static void save(ChatMsgEntity chatMsgEntity, Context context,
			String id) {
		ProJectDatebase.createFriTab(id, context);
		FriendChatUtils friendChatUtils = new FriendChatUtils(context, id);
		int iscom = 0;
		if (chatMsgEntity.getMsgType()) {
			iscom = 1;
		}
		friendChatUtils.insert(chatMsgEntity.getText(),
				chatMsgEntity.getDate(), chatMsgEntity.getDate(), iscom);

	}

	/**
	 * ����Ⱥ����������
	 * @param chatMsgEntity ����
	 * @param context ������
	 * @param gid Ⱥ��ID
	 */
	private static void saveGroupChat(ChatMsgEntity chatMsgEntity,
			Context context, String gid) {
		ProJectDatebase.createGrouTable(gid, context);
		GroupChatUtils groupChatUtils = new GroupChatUtils(context, gid);
		int iscom = 0;
		if (chatMsgEntity.getMsgType()) {
			iscom = 1;
		}
		groupChatUtils.insert(chatMsgEntity.getUid(), chatMsgEntity.getUid(),
				chatMsgEntity.getHeadimgString(), chatMsgEntity.getText(),
				chatMsgEntity.getDate(), chatMsgEntity.getDate(), iscom);

	}

}
