package com.example.projectcircle.db.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.projectcircle.bean.FriendDataBean;
import com.example.projectcircle.bean.FriendDate;
import com.example.projectcircle.db.ProJectDatebase;
import com.example.projectcircle.db.interfaces.FriendDatabasePle;

public class FriendDatabaseUtils implements FriendDatabasePle {

	public FriendDatabaseUtils(Context context) {
		ProJectDatebase.getDatebase(context);
	}
	@Override
	public long update(String content, String time, int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * ɾ������
	 */
	@Override
	public Object delete(long id) {
		// TODO Auto-generated method stub

		int del = -1;
		try {
			String where = FriendDate._ID + " = ?";
			String[] whereArgs = { String.valueOf(id) };
			del = ProJectDatebase.proDatabase.delete(FriendDate.TAB_NAME,
					where, whereArgs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return del;

	}

	@Override
	public long insert(String name, String friid, String headimg) {
		// TODO Auto-generated method stub

		long id = -1;
		try {

			ContentValues values = new ContentValues();
			values.put(FriendDate.FRI_NAME, name);
			values.put(FriendDate.FRI_ID, friid);
			values.put(FriendDate.HEAD_IMG, headimg);

			id = ProJectDatebase.proDatabase.insert(FriendDate.TAB_NAME, null,
					values);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return id;
	}

	@Override
	public long insert(String content, String time) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object queryData() {
		// TODO Auto-generated method stub
		List<FriendDataBean> list = new ArrayList<FriendDataBean>();
		Cursor cursor = null;
		try {
			// ������ݿ����,������ݿⲻ�����򴴽�
			// ��ѯ��������,��ȡ�α�
			cursor = ProJectDatebase.proDatabase.query(FriendDate.TAB_NAME,
					null, null, null, null, null, "_ID desc");
			int idIndex = cursor.getColumnIndex(FriendDate._ID);
			int nameIndex = cursor.getColumnIndex(FriendDate.FRI_NAME);
			int headIndex = cursor.getColumnIndex(FriendDate.HEAD_IMG);
			int friid = cursor.getColumnIndex(FriendDate.FRI_ID);

			for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor
					.moveToNext()) {
				FriendDataBean friendDataBean = new FriendDataBean();
				friendDataBean.set_ID(cursor.getInt(idIndex));
				friendDataBean.setFriendname(cursor.getString(nameIndex));
				friendDataBean.setFriendid(cursor.getString(friid));
				friendDataBean.setFriendhead(cursor.getString(headIndex));
				list.add(friendDataBean);
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
		FriendDataBean friendDataBean = new FriendDataBean();
		Cursor cursor = null;
		try {
			String where = FriendDate._ID + " = ?";
			String[] whereValue = { String.valueOf(id) };
			cursor = ProJectDatebase.proDatabase.query(FriendDate.TAB_NAME,
					null, where, whereValue, null, null, null);
			
			 if(cursor == null){
	          	 return null;
	          }
	       	  
     	      if(cursor.moveToFirst()){
     	    	  
     	    	 int idIndex = cursor.getColumnIndex(FriendDate._ID);
     			int nameIndex = cursor.getColumnIndex(FriendDate.FRI_NAME);
     			int headIndex = cursor.getColumnIndex(FriendDate.HEAD_IMG);
     			int friid = cursor.getColumnIndex(FriendDate.FRI_ID); 
      
     			friendDataBean.set_ID(cursor.getInt(idIndex));
				friendDataBean.setFriendname(cursor.getString(nameIndex));
				friendDataBean.setFriendid(cursor.getString(friid));
				friendDataBean.setFriendhead(cursor.getString(headIndex));
		        
		    }
			
		} catch (Exception e) {
			// TODO: handle exception
		}

		return friendDataBean;
	}

	@Override
	public Object queryDataId(int frid) {
		// TODO Auto-generated method stub
		FriendDataBean friendDataBean = new FriendDataBean();
		Cursor cursor = null;
		try {
			String where = FriendDate.FRI_ID + " = ?";
			String[] whereValue = { String.valueOf(frid) };
			cursor = ProJectDatebase.proDatabase.query(FriendDate.TAB_NAME,
					null, where, whereValue, null, null, null);
			
			 if(cursor == null){
	          	 return null;
	          }
	       	  
     	      if(cursor.moveToFirst()){
     	    	  
     	    	 int idIndex = cursor.getColumnIndex(FriendDate._ID);
     			int nameIndex = cursor.getColumnIndex(FriendDate.FRI_NAME);
     			int headIndex = cursor.getColumnIndex(FriendDate.HEAD_IMG);
     			int friid = cursor.getColumnIndex(FriendDate.FRI_ID); 
      
     			friendDataBean.set_ID(cursor.getInt(idIndex));
				friendDataBean.setFriendname(cursor.getString(nameIndex));
				friendDataBean.setFriendid(cursor.getString(friid));
				friendDataBean.setFriendhead(cursor.getString(headIndex));
		        
		    }
			
		} catch (Exception e) {
			// TODO: handle exception
		}

		return friendDataBean;
	}

	@Override
	public Object deleteAll() {
		// TODO Auto-generated method stub
		long del = -1;

		try {
			del = ProJectDatebase.proDatabase.delete(FriendDate.TAB_NAME, null,
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
	public Object update(String name, String friid, String headimg, long id) {
		// TODO Auto-generated method stub
		long updatepostion=0;
    	try {
	    	//ʹ��execSQL��������в�������
	    	//db.execSQL("INSERT INTO USER(NAME) VALUES('"+name+"')");
	
	    	//ʹ��insert��������в�������
	    	//����ContentValues����洢"����-��ֵ"ӳ��
	    	ContentValues values = new ContentValues();	
	    	values.put(FriendDate.FRI_NAME, name);
			values.put(FriendDate.FRI_ID, friid);
			values.put(FriendDate.HEAD_IMG, headimg);
	    	String where =  FriendDate._ID+" = ?";
		    String[] whereValue = { String.valueOf(id) };
	    	//���÷�����������
	    	updatepostion=ProJectDatebase.proDatabase.update(FriendDate.TAB_NAME, values, where, whereValue);
    	} catch (Exception e) {
			e.printStackTrace();
		}
		return updatepostion;

	}
	
	
	
	public long update(String name, String friid, String headimg, String id) {
		// TODO Auto-generated method stub
		long updatepostion=0;
    	try {
	    	//ʹ��execSQL��������в�������
	    	//db.execSQL("INSERT INTO USER(NAME) VALUES('"+name+"')");
	
	    	//ʹ��insert��������в�������
	    	//����ContentValues����洢"����-��ֵ"ӳ��
	    	ContentValues values = new ContentValues();	
	    	values.put(FriendDate.FRI_NAME, name);
			values.put(FriendDate.FRI_ID, friid);
			values.put(FriendDate.HEAD_IMG, headimg);
	    	String where =  FriendDate.FRI_ID+" = ?";//�޸�����
		    String[] whereValue = { String.valueOf(id) };//�޸������Ĳ���
	    	//���÷�����������
		    //�����ݿ�ı��в����¼ʱ����Ҫ�Ƚ����ݰ�����һ��ContentValues�У���ö����в����ֵ�ԣ����м���������
		   // ֵ��ϣ�����뵽��һ�е�ֵ��ֵ��������ݿ⵱�е���������һ�¡�
		   // ���ŵ���Databasehelper��getWritableDatabase��������ÿ���д���Databasehelper������������insert��¼��ע�����DatabaseHelper�����insert,update����query�����Ĳ����Ĵ��ݡ�
		    
	    	updatepostion=ProJectDatebase.proDatabase.update(FriendDate.TAB_NAME, values, where, whereValue);
    	} catch (Exception e) {
			e.printStackTrace();
		}
		return updatepostion;

	}
	

	@Override
	public Object queryData(int pagersize) {
		// TODO Auto-generated method stub
		return null;
	}

}
