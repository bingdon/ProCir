package com.example.projectcircle.db.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.projectcircle.bean.FriendChat;
import com.example.projectcircle.bean.FriendChatBean;
import com.example.projectcircle.bean.FriendDate;
import com.example.projectcircle.db.ProJectDatebase;
import com.example.projectcircle.db.interfaces.FriendChatAbs;

public class FriendChatUtils implements FriendChatAbs {

	 private String friendid = "";
	
	public  FriendChatUtils(Context context,String friendid ) {
		this.friendid=friendid;
		ProJectDatebase.getDatebase(context);
	}
	
	@Override
	public Object update(String content, String time, String showtime, long id) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public long update(String content, String time, int id) {
		// TODO Auto-generated method stub
		long updatepostion=0;
    	try {
	    	ContentValues values = new ContentValues();	
	    	values.put(FriendChat.CONTENT, content);
			values.put(FriendChat.TIME, time);
	    	String where =  FriendChat._ID+" = ?";
		    String[] whereValue = { String.valueOf(id) };
	    	//���÷�����������
	    	updatepostion=ProJectDatebase.proDatabase.update(FriendChat.TABLE_NAME+friendid, values, where, whereValue);
    	} catch (Exception e) {
			e.printStackTrace();
		}
		return updatepostion;
	}

	@Override
	public Object delete(long id) {
		// TODO Auto-generated method stub
		int del = -1;
		try {
			String where = FriendDate._ID + " = ?";
			String[] whereArgs = { String.valueOf(id) };
			del = ProJectDatebase.proDatabase.delete(FriendChat.TABLE_NAME+friendid,
					where, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return del;
	}

	@Override
	public long insert(String content, String time, String showtime) {
		// TODO Auto-generated method stub
		
		long id = -1;
		try {

			ContentValues values = new ContentValues();
			values.put(FriendChat.CONTENT, content);
			values.put(FriendChat.TIME, time);
			values.put(FriendChat.SHOW_TIME, showtime);
			
			id = ProJectDatebase.proDatabase.insert(FriendChat.TABLE_NAME+friendid, null,
					values);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return id;
		
	}

	@Override
	public long insert(String content, String time) {
		// TODO Auto-generated method stub
		long id = -1;
		try {

			ContentValues values = new ContentValues();
			values.put(FriendChat.CONTENT, content);
			values.put(FriendChat.TIME, time);
			
			id = ProJectDatebase.proDatabase.insert(FriendChat.TABLE_NAME+friendid, null,
					values);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return id;
	}

	@Override
	public Object queryData() {
		// TODO Auto-generated method stub
		List<FriendChatBean> list = new ArrayList<FriendChatBean>();
		Cursor cursor = null;
		try {
			// ������ݿ����,������ݿⲻ�����򴴽�
			// ��ѯ��������,��ȡ�α�
			cursor = ProJectDatebase.proDatabase.query(FriendChat.TABLE_NAME+friendid,
					null, null, null, null, null, "_ID desc");
			int idIndex = cursor.getColumnIndex(FriendChat._ID);
			int contentIndex = cursor.getColumnIndex(FriendChat.CONTENT);
			int timeIndex = cursor.getColumnIndex(FriendChat.TIME);
			int showtIndex=cursor.getColumnIndex(FriendChat.SHOW_TIME);
			int iscomIndex=cursor.getColumnIndex(FriendChat.IS_COM);

			for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor
					.moveToNext()) {
				FriendChatBean friendChatBean = new FriendChatBean();
				friendChatBean.set_id(cursor.getInt(idIndex));
				friendChatBean.setContent(cursor.getString(contentIndex));
				friendChatBean.setRealtime(cursor.getString(timeIndex));
				friendChatBean.setShowTime(cursor.getString(showtIndex));
				friendChatBean.setIscom(cursor.getInt(iscomIndex));
				list.add(friendChatBean);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				// �ر��α�
				cursor.close();
			}

		}

		return list;
	}

	@Override
	public Object queryDataId(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object queryDataId(int frid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object deleteAll() {
		// TODO Auto-generated method stub
		long del = -1;

		try {
			del = ProJectDatebase.proDatabase.delete(FriendChat.TABLE_NAME+friendid, null,
					null);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return del;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		if (ProJectDatebase.proDatabase != null
				&& ProJectDatebase.proDatabase.isOpen()) {
			// �ر����ݿ����
			ProJectDatebase.proDatabase.close();
			ProJectDatebase.proDatabase = null;
		}
	}

	@Override
	public Object queryData(int pagersize) {
		// TODO Auto-generated method stub
		List<FriendChatBean> list = new ArrayList<FriendChatBean>();
		Cursor cursor = null;
		try {
			// ������ݿ����,������ݿⲻ�����򴴽�
			// ��ѯ��������,��ȡ�α�
			cursor = ProJectDatebase.proDatabase.query(FriendChat.TABLE_NAME+friendid,
					null, null, null, null, null, "_ID desc");
			int idIndex = cursor.getColumnIndex(FriendChat._ID);
			int contentIndex = cursor.getColumnIndex(FriendChat.CONTENT);
			int timeIndex = cursor.getColumnIndex(FriendChat.TIME);
			int showtIndex=cursor.getColumnIndex(FriendChat.SHOW_TIME);
			int iscomIndex=cursor.getColumnIndex(FriendChat.IS_COM);

			for (cursor.moveToFirst(); !(cursor.isAfterLast())&&cursor.getPosition()<pagersize; cursor
					.moveToNext()) {
				FriendChatBean friendChatBean = new FriendChatBean();
				friendChatBean.set_id(cursor.getInt(idIndex));
				friendChatBean.setContent(cursor.getString(contentIndex));
				friendChatBean.setRealtime(cursor.getString(timeIndex));
				friendChatBean.setShowTime(cursor.getString(showtIndex));
				friendChatBean.setIscom(cursor.getInt(iscomIndex));
				list.add(friendChatBean);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				// �ر��α�
				cursor.close();
			}

		}

		return list;
	}

	@Override
	public Object update( String content, String time,
			String showtime, int iscom, long id) {
		// TODO Auto-generated method stub
		long updatepostion=0;
    	try {
	    	ContentValues values = new ContentValues();	
	    	values.put(FriendChat.CONTENT, content);
			values.put(FriendChat.TIME, time);
			values.put(FriendChat.SHOW_TIME, showtime);
			values.put(FriendChat.IS_COM, iscom);
	    	String where =  FriendChat._ID+" = ?";
		    String[] whereValue = { String.valueOf(id) };
	    	//���÷�����������
	    	updatepostion=ProJectDatebase.proDatabase.update(FriendChat.TABLE_NAME+friendid, values, where, whereValue);
    	} catch (Exception e) {
			e.printStackTrace();
		}
		return updatepostion;
	}

	@Override
	public long insert( String content, String time,
			String showtime, int iscom) {
		// TODO Auto-generated method stub
		long id = -1;
		try {

			ContentValues values = new ContentValues();
			values.put(FriendChat.CONTENT, content);
			values.put(FriendChat.TIME, time);
			values.put(FriendChat.SHOW_TIME, showtime);
			values.put(FriendChat.IS_COM, iscom);
			
			id = ProJectDatebase.proDatabase.insert(FriendChat.TABLE_NAME+friendid, null,
					values);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return id;
	}


}
