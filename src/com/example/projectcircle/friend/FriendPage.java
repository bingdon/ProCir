package com.example.projectcircle.friend;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcircle.HomeActivity;
import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.adpter.FriendAdapter;
import com.example.projectcircle.bean.FriendDataBean;
import com.example.projectcircle.bean.GroupChatBean;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.db.utils.FriendDatabaseUtils;
import com.example.projectcircle.db.utils.GroupChatUtils;
import com.example.projectcircle.group.GroupPage;
import com.example.projectcircle.group.MyGroup;
import com.example.projectcircle.personal.PersonalPage;
import com.example.projectcircle.util.DistentsUtil;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class FriendPage extends Activity {
	private static final String TAG = null;
	// private static final String TAG = "FriendPage";
	ListView listview, listview2;
	FriendAdapter myAdapter;
	FriendAdapter myAdapter2;

	/** ��ȡ��Phon���ֶ� **/
	private static final String[] PHONES_PROJECTION = new String[] {
			Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID };

	/** ��ϵ����ʾ���� **/
	private static final int PHONES_DISPLAY_NAME_INDEX = 0;

	/** �绰���� **/
	private static final int PHONES_NUMBER_INDEX = 1;

	/** ͷ��ID **/
	private static final int PHONES_PHOTO_ID_INDEX = 2;

	/** ��ϵ�˵�ID **/
	private static final int PHONES_CONTACT_ID_INDEX = 3;

	/** ��ϵ������ **/
	private ArrayList<String> mContactsName = new ArrayList<String>();

	/** ��ϵ�˺��� **/
	private ArrayList<String> mContactsNumber = new ArrayList<String>();

	/** ��ϵ��ͷ�� **/
	private ArrayList<Bitmap> mContactsPhonto = new ArrayList<Bitmap>();
	/**
	 * ����Button
	 */
	Button add, new_, maybe, group;
	/**
	 * ����Button
	 */
	Button search;
	LinearLayout groupchat, addfriend, tellfriend;

	boolean isRefreshing = false;
	View headview2;
	LinearLayout group2;
	LinearLayout friend;
	protected Activity context;
	protected LayoutInflater anchor;
	public ArrayList<UserInfo> friendList;
	LayoutInflater inflater = null;
	PopupWindow mPop;
	String id;

	public ArrayList<UserInfo> usersList;
	// �Ի������
	Context self = FriendPage.this;
	private friendBroadcastReceiver receiver;
	ArrayList<HashMap<String, Object>> listItem;
	private Object deny_id;
	private Object cid;
	
	private ArrayList<String> tel;
	String msg_id ;//��ע��ɹ�ʱ�������¼���棬�㲥�յ���id
	private String now_number;
	public static List<FriendDataBean> friendDataBeans=new ArrayList<FriendDataBean>();
	private JSONArray json1;
	private JSONArray json2;
	private JSONArray json3;
	private TextView tipNotice;
	private int   new_friend_tip_count = 0;//ͨѶ¼�и�ע�Ტ���Ǻ��ѵĸ���
	private String mphoneNumber;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_page);
		//ɾ�����ѡ��Ӻ��ѵļ����㲥
		initFilter();
		id =  LoginActivity.id;	
		initList();		
		initPopWindow();	
	//��ȡͨѶ¼��ϵ�ˣ����ж��Ƿ�Ϊ���ѣ��Ա���΢����������������ʾ
		getContactInNumber();
		if (null!=myAdapter) {
			findfriend(id);
		}
	}

	private void getContactInNumber() {
		// TODO Auto-generated method stub
		ContentResolver resolver = FriendPage.this.getContentResolver();

		// ��ȡ�ֻ���ϵ��
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,
				PHONES_PROJECTION, null, null, null);

		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {

				// �õ��ֻ�����
				String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
				// ���ֻ�����Ϊ�յĻ���Ϊ���ֶ� ������ǰѭ��
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				// �õ���ϵ������
				String contactName = phoneCursor
						.getString(PHONES_DISPLAY_NAME_INDEX);
				// �õ���ϵ��ID
				Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);
				// �õ���ϵ��ͷ��ID
				Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);
				// �õ���ϵ��ͷ��Bitamp
				Bitmap contactPhoto = null;
				// photoid ����0 ��ʾ��ϵ����ͷ�� ���û�и���������ͷ�������һ��Ĭ�ϵ�
				if (photoid > 0) {
					Uri uri = ContentUris.withAppendedId(
							ContactsContract.Contacts.CONTENT_URI, contactid);
					InputStream input = ContactsContract.Contacts
							.openContactPhotoInputStream(resolver, uri);
					contactPhoto = BitmapFactory.decodeStream(input);
				} else {
					contactPhoto = BitmapFactory.decodeResource(getResources(),
							R.drawable.driver);
				}
				mphoneNumber = phoneNumber.replace(" ", ""); // �õ��ĵ绰�����пո�ȥ����
				if (mphoneNumber.length() > 11) {
					mphoneNumber = mphoneNumber.substring(
							mphoneNumber.length() - 11, mphoneNumber.length());
				}// ��ȡ�ֻ��ŵĺ�11λ����Ϊ����е��ֻ��ſ�ͷ����+86������ȥ��
				mContactsName.add(contactName);
				mContactsNumber.add(mphoneNumber);
				mContactsPhonto.add(contactPhoto);
			}

			phoneCursor.close();
		}
		now_number = mContactsNumber.toString().substring(1,
				mContactsNumber.toString().length() - 1);// ȥ��ArrayList�еġ��͡�����ٿ�ͷ�ͽ�β�У�Ҫ����̨ʶ�𲻳���
		now_number = now_number.replace(" ", "");//ȥ�����пո�Ҫ����̨ʶ�𲻳���
		isRegist(now_number, id);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();	
		
	}
//�ж�ͨѶ¼����ϵ���Ƿ�ע��
	private void isRegist(String now_number, String id) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				// Log.i("�绰�����Ƿ�ע��response---------", response);
				try {
					JSONObject obj = new JSONObject(response);
					json1 = obj.getJSONArray("1");// ��ֵ��1�ģ�û��ע���
					json2 = obj.getJSONArray("2");// ��ֵ��2�ģ�ע�Ტ���Ǻ���
					json3 = obj.getJSONArray("3");// ��ֵ��3�ģ�ע�ᵫ���Ǻ���
					Log.i(TAG, "��С:" + mContactsNumber.size());
					for (int i = 0; i < mContactsNumber.size(); i++) {
//						Log.i(TAG, "��ֵ��1�ģ�û��ע���"+isIn(mContactsNumber.get(i), json1));
//						Log.i(TAG, "��ֵ��2�ģ�ע�Ტ���Ǻ���"+isIn(mContactsNumber.get(i), json2));
//						Log.i(TAG, "��ֵ��3�ģ�ע�ᵫ���Ǻ���"+isIn(mContactsNumber.get(i), json3));
                       if (isIn(mContactsNumber.get(i), json3)) {		
					     new_friend_tip_count++;
						}                       
					}
					if( new_friend_tip_count > 0){
				 tipNotice.setText(new_friend_tip_count+"");//�м���
				 tipNotice.setVisibility(View.VISIBLE);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.contactIsRegiest(now_number, id, res);

	}

	// �ж��ַ����������Ƿ���ĳһ�ַ���
	private static boolean isIn(String substring, JSONArray source)
			throws JSONException {
		if (source == null || source.length() == 0) {
			return false;
		}
		int length=source.length();		
		for (int i = 0; i < length; i++) {
			String aSource = (String) source.get(i);
			Log.i(TAG, "����:"+substring+"::"+aSource+"::"+(aSource.equals(substring)));
			if (aSource.equals(substring)) {
//				Log.i(TAG, "asource:" + aSource);
				return true;
			}
		}
		return false;
	}
	
	
	private void findfriend(String id) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("�ҵĺ����б�response", response);
				parsefindfriend(response);
				listItem.clear();
				listItem=getList_friend();
			
				myAdapter=new FriendAdapter(listItem, FriendPage.this);
				myAdapter.notifyDataSetChanged();
				listview.setAdapter(myAdapter);
				Log.i(TAG, "����:"+myAdapter.getCount());
				getFriInfo(FriendPage.this);
			}
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1, arg2, arg3);
				Log.i("failuer ", "failuer");
				getFriInfo(FriendPage.this);
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.findfriend(id, res);
	}

	private void parsefindfriend(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("friends");
			friendList = new ArrayList<UserInfo>();
			JSONArray json = obj.getJSONArray("resultlist");
			int length = json.length();
			System.out.println("length==" + length);
			Log.i(TAG, "JSONArray:" + json);
			for (int i = 0; i < length; i++) {
				UserInfo user = new UserInfo();
				JSONObject objo = json.getJSONObject(i);
				Log.i("�����б�", "objo:" + objo);
				user.setId(objo.getString("id"));
				user.setTel(objo.getString("tel"));
				user.setUsername(objo.getString("username"));
				user.setType(objo.getString("type"));
				user.setAddress(objo.getString("address"));
				user.setCcid(objo.getString("ccid"));
				user.setEquipment(objo.getString("equipment"));
				user.setSign(objo.getString("sign"));
				user.setHeadimage(objo.getString("headimage"));
				user.setAccept(objo.getString("accept"));
				user.setLat(Double.valueOf(objo.getString("commercialLat")));
				user.setLon(Double.valueOf(objo.getString("commercialLon")));
				user.setLastlogintime(objo.getString("lastlogintime"));
				friendList.add(user);
				
				saveFriendinfo(user.getUsername(), user.getId(), user.getHeadimage());
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	private void initPopWindow() {
		// TODO Auto-generated method stub
		search = (Button) findViewById(R.id.f_page_right);
		search.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

				ViewGroup menuView = (ViewGroup) mLayoutInflater.inflate(
						R.layout.popwindow, null, true);
				mPop = new PopupWindow(menuView, LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT, true);
				mPop.setAnimationStyle(R.style.AnimationPreview);
				mPop.setOutsideTouchable(true);
				mPop.setFocusable(true);
				// mPop.setWidth(200);
				// mPop.setHeight(300);
				ColorDrawable dr=new ColorDrawable(getResources().getColor(R.color.transparent));
				mPop.setBackgroundDrawable(dr);
				RelativeLayout upfriend = (RelativeLayout) findViewById(R.id.add_friend_Layout1);
				mPop.showAsDropDown(upfriend, 420, 0);// ������ʾPopupWindow��λ��λ��View�����·���x,y��ʾ����ƫ����
				mPop.showAtLocation(findViewById(R.id.textView1), Gravity.LEFT,
						0, 0);// ����ĳ��ViewΪ�ο���,��ʾ����������parent���Ϊ�ο���λ����࣬ƫ��-90��
				groupchat = (LinearLayout) menuView
						.findViewById(R.id.groupchat);
				addfriend = (LinearLayout) menuView
						.findViewById(R.id.addfriend);
				tellfriend = (LinearLayout) menuView
						.findViewById(R.id.tellfriend);
				groupchat.setVisibility(View.GONE);
				//groupchat.setOnClickListener(listener3);
				addfriend.setOnClickListener(listener3);
				tellfriend.setOnClickListener(listener3);
			}
		});

	}

	private View.OnClickListener listener3 = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
//			case R.id.groupchat:
//				Intent intent = new Intent(FriendPage.this, GroupChat.class);
//				startActivity(intent);
//				mPop.dismiss();
//				break;
			case R.id.addfriend:
				Intent intent1 = new Intent(FriendPage.this, AddFriend.class);
				startActivity(intent1);
				mPop.dismiss();
				break;
			case R.id.tellfriend:
				Intent intent11 = new Intent(Intent.ACTION_SEND);
				intent11.setType("text/plain");
				intent11.putExtra(Intent.EXTRA_SUBJECT, "����");
				intent11.putExtra(
						Intent.EXTRA_TEXT,
						"����ʹ�á�����Ȧ���ύȫ��ͬ�к��ѣ�������Ȧ��רΪ���̻�е��ҵ������˾�����̼���Ƶ��ֻ��罻������ҵĹ��̺���XXX���ǵ�ע������Ϊ���ѡ����ص�ַ��www.gcquan.cn");
				startActivity(Intent.createChooser(intent11, getTitle()));
				mPop.dismiss();
				break;
			}
		}
	};
	// λ�ü���

	// private void locationCal() {
	// // TODO Auto-generated method stub
	// Double distance = DistentsUtil.DistanceOfTwoPoints(friendlat, friendlon,
	// mylat, mylon) / 1000;
	// }
	private void initList() {
		// TODO Auto-generated method stub
		getList_friend();		
		listview = (ListView) findViewById(R.id.f_page_listView);		
		headview2 = this.getLayoutInflater().inflate(R.layout.headview2, null);
		listview.addHeaderView(headview2);
		tipNotice = (TextView)findViewById(R.id.new_friend_notice);
		friend = (LinearLayout) findViewById(R.id.newfriend_layout);
		friend.setOnClickListener(listener1);
		group2 = (LinearLayout) findViewById(R.id.neargroup_layout);
		group2.setOnClickListener(listener2);
	    
		myAdapter = new FriendAdapter(listItem, this);
		listview.setAdapter(myAdapter);
		// �̰�listView
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(FriendPage.this, PersonalPage.class);
				intent.putExtra("id", friendList.get(arg2 - 1).getId());
				intent.putExtra("type", friendList.get(arg2 - 1).getType());
				intent.putExtra("ccid", friendList.get(arg2 - 1).getCcid());
				intent.putExtra("time",friendList.get(arg2 - 1).getLastlogintime());
				intent.putExtra("lat",friendList.get(arg2 - 1).getLat());
				intent.putExtra("lon",friendList.get(arg2 - 1).getLon());
				startActivity(intent);
			}
		});
		// ����listview
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(self)
						.setTitle("ȷ��Ҫɾ���˺�����")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										// ��ȷ����ɾ��
										cid = listItem.get(position - 1).get(
												"ccid");
										denyfriend(cid, position);
									}
								}).setNegativeButton("ȡ��", null).show();
				return true;
			}

		});
	}

	// ����ɾ�����ѵĽӿڣ��ͺ��������б���ߵľܾ�������һ���ӿ�
	private void denyfriend(Object cid, final int position) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("���أ�", response);
				// parseFriendRequestList(response);
				// initList();
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {
						Toast.makeText(FriendPage.this, "ɾ���ɹ���",
								Toast.LENGTH_SHORT).show();
						// //ɾ���ɹ���Ͱ���Ӧ��listItemɾ��
						if (listItem != null && listItem.size() > 0) {
							listItem.remove(position - 1);
						}
						myAdapter.notifyDataSetChanged();
					} else {
						Toast.makeText(context, "ɾ��ʧ�ܣ�", Toast.LENGTH_SHORT)
								.show();

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};
		MyHttpClient client = new MyHttpClient();
		client.denyFriends(cid, res);
	}

	private View.OnClickListener listener1 = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			Intent intent = new Intent(FriendPage.this, NewFriendActivity.class);
			getTel();
			intent.putStringArrayListExtra("tels", tel);
			startActivity(intent);
			// finish();

		}
	};
	private View.OnClickListener listener2 = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			Intent intent = new Intent(FriendPage.this, MyGroup.class);
			startActivity(intent);
			// finish();

		}
	};

	private ArrayList<HashMap<String, Object>> getList_friend() {
		listItem = new ArrayList<HashMap<String, Object>>();

		// TODO Auto-generated method stub
		Log.i("����:friendList", "����:friendList" + friendList);
		if (friendList != null) {
			for (int i = 0; i < friendList.size(); i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				// map.put("friendID", friendList.get(i).getId());
				map.put("address", friendList.get(i).getAddress());
				map.put("type", friendList.get(i).getType());
				map.put("headimage", friendList.get(i).getHeadimage());
				map.put("username", friendList.get(i).getUsername());
				map.put("tel", friendList.get(i).getTel());
				map.put("accept", friendList.get(i).getAccept());
				map.put("equipment", friendList.get(i).getEquipment());
				map.put("ccid", friendList.get(i).getCcid());
				map.put("sign", friendList.get(i).getSign());
				map.put("lastlogintime", friendList.get(i).getLastlogintime());
				map.put("commercialLat", friendList.get(i).getLat());
				map.put("commercialLon", friendList.get(i).getLon());
				listItem.add(map);
			}
		}
		return listItem;
	}

	// �㲥
	private class friendBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			msg_id = arg1.getExtras().getString("id");
			Log.i("�����㲥��û�гɹ���", "msg:" + msg_id);
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
		unregisterReceiver(msgReceiver);
		if (null!=friendDatabaseUtils) {
			friendDatabaseUtils.close();
		}

	}

	private void getTel(){
		if (friendList != null) {
			tel=new ArrayList<String>();
			for (int i = 0; i < friendList.size(); i++) {
				tel.add(friendList.get(i).getTel());
			}
		}
	}
	
	

	
	
	FriendDatabaseUtils friendDatabaseUtils=null;
	
	private void saveFriendinfo(String name,String friid,String headimg){
		if (friendDatabaseUtils==null) {
			friendDatabaseUtils=new FriendDatabaseUtils(this);
		}
		
		long m=friendDatabaseUtils.update(name, friid, headimg, friid);
		Log.i(TAG, "����:"+m);
		if (m<1) {
			m=friendDatabaseUtils.insert(name, friid, headimg);
			Log.i(TAG, "����:"+m);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static void getFriInfo(Context context){
		friendDataBeans=(List<FriendDataBean>) new FriendDatabaseUtils(context).queryData();
		Log.i("friendDataBeans","" + friendDataBeans);
	}
	
	/**
	 * �ж��Ƿ�Ϊ����
	 * @param id
	 * @return
	 */
	public static boolean isFriend(String id){
		boolean a=false;
		if (null==friendDataBeans) {
			return a;
		}
		for (int i = 0; i < friendDataBeans.size(); i++) {
			if (friendDataBeans.get(i).getFriendid().equals(id)) {
				a=true;
				break;
			}
		}
		
		return a;
	}
	
	public static String getUsername(String friid){
		String name="����Ȧ";
		if (null==friendDataBeans) {
			return name;
		}
		for (int i = 0; i < friendDataBeans.size(); i++) {
			if (friendDataBeans.get(i).getFriendid().equals(friid)) {
				name=friendDataBeans.get(i).getFriendname();
				break;
			}
		}
		
		return name;
		
	}
	
	public static FriendDataBean getUserdata(String friid){
		FriendDataBean friendDataBean=new FriendDataBean();
		if (null==friendDataBeans) {
			return null;
		}
		for (int i = 0; i < friendDataBeans.size(); i++) {
			if (friendDataBeans.get(i).getFriendid().equals(friid)) {
				friendDataBean=friendDataBeans.get(i);
				break;
			}
		}
		
		return friendDataBean;
		
	}
	private void initFilter() {
		IntentFilter filter = new IntentFilter();
		filter.addAction("shan.chu.hao.you");
		filter.addAction("add.a.new.friend");
		registerReceiver(msgReceiver, filter);
		Log.i("initFilter", "initFilter()");
	}
	private BroadcastReceiver msgReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.i("�㲥��ʼ", "msgReceiver");				
			findfriend(id);
			Log.i("listItem", listItem+"");
			myAdapter.notifyDataSetChanged();
		}
	};
	
}
