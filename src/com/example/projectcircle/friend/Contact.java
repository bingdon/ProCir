package com.example.projectcircle.friend;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.bean.MyPersonBean;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.friend.Contact.MyListAdapter;
import com.example.projectcircle.friend.Contact.ViewHolder;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
@Deprecated
public class Contact extends Activity {

	Context mContext = Contact.this;

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
	// ע�ᵫ���Ǻ��ѵ�arraylist�ĵ绰����
	private ArrayList<String> regist_not_friend_number = new ArrayList<String>();
	// ע�ᵫ���Ǻ��ѵ�arraylist������
	private ArrayList<String> regist_not_friend_name = new ArrayList<String>();
	
	private List<MyPersonBean> liPersonBeans=new ArrayList<>();
	
	ListView mListView = null;
	MyListAdapter myAdapter = null;
	private ArrayList<String> telStrings;
	protected int sure;

	private String myid;

	public int zhi;
	ViewHolder holder;
	// ȥ���ո�֮��ĵ绰����
	private String mphoneNumber;

	private String now_number;

	JSONArray json1, json2, json3;
	String aid = LoginActivity.id;
	String bid;// ��ע�����ͨѶ¼����ϵ�˵�id
	HashMap<String, Object> contactmap = new HashMap<String, Object>();
	private ArrayList<String> regist_and_friend_number = new ArrayList<String>();
	private ArrayList<String> regist_and_friend_name = new ArrayList<String>();

	private ListView listView;

	private Button button_back;

	private ArrayList<UserInfo> friendRequestList;
	private static final String TAG = Contact.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.concat_friend_list);
		telStrings = getIntent().getStringArrayListExtra("tels");
		myid = LoginActivity.id;// ��ȡ�ҵĵ�¼id
		FriendRequestList(myid);
		/** �õ��ֻ�ͨѶ¼��ϵ����Ϣ **/
		getPhoneContacts();
		// �ж��Ƿ�ע��
		// mContactsNumber=mContactsNumber.replace("[","");
		// //ȥ���ַ����еġ��͡�����ٿ�ͷ�ͽ�β�У�Ҫ����̨ʶ�𲻳���
		// mContactsNumber=mContactsNumber.replace("]","");
		now_number = mContactsNumber.toString().substring(1,
				mContactsNumber.toString().length() - 1);// ȥ��ArrayList�еġ��͡�����ٿ�ͷ�ͽ�β�У�Ҫ����̨ʶ�𲻳���
		now_number = now_number.replace(" ", "");// ȥ�����пո�Ҫ����̨ʶ�𲻳���
		isRegist(now_number, myid);
		back();
	}

	// ����
	private void back() {
		// TODO Auto-generated method stub
		button_back = (Button) findViewById(R.id.g_list_left);
		button_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Contact.this.setResult(RESULT_OK, getIntent());
				Contact.this.finish();
			}
		});
	}

	/**
	 * �õ��ֻ�ͨѶ¼��ϵ����Ϣ
	 * 
	 * @return
	 **/
	public ArrayList<String> getPhoneContacts() {
		ContentResolver resolver = mContext.getContentResolver();

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
							R.drawable.header);
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
			// Log.i("�жϴ����̨�ĵ绰����",mContactsNumber+"");

		}
		return mContactsNumber;
	}

	class MyListAdapter extends BaseAdapter {

		public MyListAdapter(Context context) {
			mContext = context;
		}

		public int getCount() {
			// ���û�������
			return regist_not_friend_name.size();
		}

		@Override
		public boolean areAllItemsEnabled() {
			return false;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		@SuppressWarnings("deprecation")
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final String number;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.contact, null);
				holder = new ViewHolder();
				holder.headimg = (ImageView) convertView
						.findViewById(R.id.newfriend_header);
				holder.image = (Button) convertView
						.findViewById(R.id.invitation);
				holder.username = (TextView) convertView
						.findViewById(R.id.username);
				holder.message = (TextView) convertView
						.findViewById(R.id.message);
				convertView.setTag(holder);// ��ViewHolder����
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// ������ϵ������
			holder.username.setText(regist_not_friend_name.get(position));
			// holder.image.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View arg0) {
			// // TODO Auto-generated method stub
			// Uri uri = Uri.parse("smsto:" + mContactsName.get(position));
			// Intent it = new Intent(Intent.ACTION_SENDTO, uri);
			// it.putExtra("sms_body", "�����ù���Ȧ��������ǳ����ã���Ҳע����һ�°ɣ�");
			// startActivity(it);
			// }
			// });
			// ��ȡ�˵绰����,number���붨���final String���͵ģ�Ҫ���position������
			number = regist_not_friend_number.get(position) + "";
			// �ж��Ƿ��Ѿ�ע��
			// if (contactmap.get(number) == "1") {
			// holder.image.setText("����");
			// holder.image.setBackgroundColor(R.color.transparent);
			// } else
			if (contactmap.get(number) == "2") {
				holder.image.setText("�����");

			} else if (contactmap.get(number) == "3") {
				// holder.image.setText("���");
				Resources resources = getBaseContext().getResources();
				Drawable btnDrawable = resources
						.getDrawable(R.drawable.add_new_contact_selector);
				holder.image.setBackgroundDrawable(btnDrawable);
				holder.image.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						// ���ݵ绰��������û���Ϣ���Ի����id
						findUserByTel(number);
					}
				});
			}
			return convertView;
		}

	}

	/* ��ſؼ� */
	public final class ViewHolder {

		public TextView message;
		public TextView username;// �û���
		public ImageView headimg;// ͷ��
		public Button image;// ͬ�⻹����ӵ�ͼƬ

	}

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
						if (isIn(mContactsNumber.get(i), json1)) {
							contactmap.put(mContactsNumber.get(i), "1");

						} else if (isIn(mContactsNumber.get(i), json2)) {
							contactmap.put(mContactsNumber.get(i), "2");

						} else if (isIn(mContactsNumber.get(i), json3)) {
							contactmap.put(mContactsNumber.get(i), "3");

						}
					}
					// ע��������Ǻ��ѵ��ᵽǰ����
					Log.i("contactmap��regist֮��", contactmap + "");
					for (int i = 0, len = mContactsNumber.size(); i < len; ++i) {
						if (contactmap.get(mContactsNumber.get(i)) == "3") {
							regist_not_friend_number
									.add(mContactsNumber.get(i));
							regist_not_friend_name.add(mContactsName.get(i));
							// mContactsNumber.remove(i);
							// mContactsName.remove(i);
							// --len;//����һ��
							// --i;//��лdeny_guoshouָ����������ӻ��������1¥��˵�������

						} else if (contactmap.get(mContactsNumber.get(i)) == "2") {
							regist_and_friend_number
									.add(mContactsNumber.get(i));
							regist_and_friend_name.add(mContactsName.get(i));
							// mContactsNumber.remove(i);
							// mContactsName.remove(i);
							// --len;//����һ��
							// --i;//��лdeny_guoshouָ����������ӻ��������1¥��˵�������

						}
					}
					Log.i("regist_not_friend_number----ǰ",
							regist_not_friend_number + "");
					Log.i("regist_not_friend_name----ǰ", regist_not_friend_name
							+ "");
					regist_not_friend_number.addAll(regist_and_friend_number);// ��������ƴ��
					regist_not_friend_name.addAll(regist_and_friend_name);// ��������ƴ��
					Log.i("regist_not_friend_number----��",
							regist_not_friend_number + "");
					Log.i("regist_not_friend_name----��", regist_not_friend_name
							+ "");
					InitList();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.contactIsRegiest(now_number, id, res);

	}

	private void InitList() {
		// TODO Auto-generated method stub
		listView = (ListView) findViewById(R.id.concat_list_listView);
		myAdapter = new MyListAdapter(this);
		listView.setAdapter(myAdapter);
	}

	// �ж��ַ����������Ƿ���ĳһ�ַ���
	private static boolean isIn(String substring, JSONArray source)
			throws JSONException {
		if (source == null || source.length() == 0) {
			return false;
		}
		int length = source.length();
		for (int i = 0; i < length; i++) {
			String aSource = (String) source.get(i);
			// Log.i(TAG,
			// "����:"+substring+"::"+aSource+"::"+(aSource.equals(substring)));
			if (aSource.equals(substring)) {
				// Log.i(TAG, "asource:" + aSource);
				return true;
			}
		}
		return false;
	}

	private void applyfriend(String aid, String bid, String info) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("����", response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {
						ToastUtils.showLong(getApplicationContext(),
								"������ϣ��ȴ�����ͨ����֤");
					} else {
						ToastUtils.showShort(getApplicationContext(),
								"��������Ϣ�ѷ��ͣ������ظ����~~");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.VerifyFriend(aid, bid, info, res);
	}

	private void findUserByTel(String number) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("����", response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {
						JSONObject user = obj.getJSONObject("user");
						bid = user.getString("id");
						// ������ѣ��ϱ�findUserByTel������bid���첽�ģ����ж�һ��
						String info = "ͨѶ¼��ϵ��";
						// �õ�����绰�����Ӧ��Id�󣬿�ʼ�������
						applyfriend(aid, bid, info);// ��д�������

					} else {
						ToastUtils.showShort(getApplicationContext(), "û�ҵ���");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.findUserByTel(number, res);

	}

	private void FriendRequestList(String bid) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("���أ�", response);
				parseFriendRequestList(response);
				// initList();
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.FriendRequestMessage(bid, res);
	}

	private void parseFriendRequestList(String response) {
		// TODO Auto-generated method stub
		try {
			Log.i("�������������response", response + "");
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("friends");
			friendRequestList = new ArrayList<UserInfo>();
			JSONArray json = obj.getJSONArray("resultlist");
			int length = json.length();
			System.out.println("length==" + length);
			Log.i(TAG, "JSONArray:" + json);
			for (int i = 0; i < length; i++) {
				UserInfo user = new UserInfo();
				JSONObject objo = json.getJSONObject(i);
				Log.i(TAG, "objo:" + objo);
				user.setId(objo.getString("cid"));
				user.setUsername(objo.getString("username"));
				user.setInfo(objo.getString("info"));
				friendRequestList.add(user);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}