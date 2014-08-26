package com.example.projectcircle.db.interfaces;

public interface FriendDatabasePle {

	public Object update(String name, String friid,String headimg,long id);
	
	
	
	public long update(String content,String time ,int id);
	
	/**
	 * ɾ��
	 * @param id 
	 * @return
	 */
	public Object delete(long id);
	/**
	 * 
	 * @param name ��������
	 * @param friid ����ID
	 * @param headimg ����ͷ��
	 * @return
	 */
	public long insert(String name, String friid,String headimg);
	/**
	 * 
	 * @param content ��������
	 * @param time ʱ��
	 * @return 
	 */
	public long insert(String content,String time);
	/**
	 * ��������
	 * @return
	 */
	public Object queryData();
	
	public Object queryData(int pagersize);
	
	public Object queryDataId(long id);
	
	public Object queryDataId(int frid);
	
	public Object deleteAll();
	
	public void close();
	
}
