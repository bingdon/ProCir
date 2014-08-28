package com.example.projectcircle.friend;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.adpter.FriendRequestAdapter;
import com.example.projectcircle.adpter.NewContAdapter;
import com.example.projectcircle.adpter.NewContAdapter.HandlerListener;
import com.example.projectcircle.bean.NewConstactBean;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.db.utils.NewContactsUtily;
import com.example.projectcircle.debug.AppLog;
import com.example.projectcircle.other.MessagePage;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class NewFriendActivity extends Activity implements HandlerListener {

	private static final String TAG = NewFriendActivity.class.getSimpleName();
	private List<String> telStrings = new ArrayList<>();
	private String myid = "";
	private Context context;
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

	private List<NewConstactBean> rConstactBeans = new ArrayList<>();

	/** ��ϵ�˺��� **/
	private ArrayList<String> mContactsNumber = new ArrayList<String>();

	private String now_number;

	private List<String> regist_not_friend_name = new ArrayList<String>();

	JSONArray json1, json2, json3;

	private NewContAdapter newContAdapter;

	private ListView cListView;

	private NewContactsUtily newContactsUtily;

	private static final int TYPE_CONT = 0;

	private static final int TYPE_REQUEST = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.concat_friend_list);
		context = this;
		telStrings = getIntent().getStringArrayListExtra("tels");
		myid = LoginActivity.id;
		initView();
	}

	private void initView() {
		findViewById(R.id.g_list_left).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				});
		initData();
	}

	private void initData() {
		getLastData();
		newContAdapter = new NewContAdapter(rConstactBeans, context);
		cListView = (ListView) findViewById(R.id.concat_list_listView);
		cListView.setAdapter(newContAdapter);
		getPhoneContacts();
		now_number = mContactsNumber.toString().substring(1,
				mContactsNumber.toString().length() - 1);// ȥ��ArrayList�еġ��͡�����ٿ�ͷ�ͽ�β�У�Ҫ����̨ʶ�𲻳���
		now_number = now_number.replace(" ", "");// ȥ�����пո�Ҫ����̨ʶ�𲻳���
		isRegist(now_number, myid);
		FriendRequestList(myid);
		newContAdapter.setListener(this);
		registerForContextMenu(cListView);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.msglist, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.deletemsg:

			delete(aInfo.position);

			break;

		default:
			break;
		}

		return super.onContextItemSelected(item);
	}

	private void delete(int position) {
		newContactsUtily.delete(rConstactBeans.get(position));
		rConstactBeans.remove(position);
		newContAdapter.notifyDataSetChanged();
	}

	private void getPhoneContacts() {
		ContentResolver resolver = getContentResolver();

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
				// Bitmap contactPhoto = null;
				//
				// // photoid ����0 ��ʾ��ϵ����ͷ�� ���û�и���������ͷ�������һ��Ĭ�ϵ�
				// if (photoid > 0) {
				// Uri uri = ContentUris.withAppendedId(
				// ContactsContract.Contacts.CONTENT_URI, contactid);
				// InputStream input = ContactsContract.Contacts
				// .openContactPhotoInputStream(resolver, uri);
				// contactPhoto = BitmapFactory.decodeStream(input);
				// } else {
				// contactPhoto = BitmapFactory.decodeResource(getResources(),
				// R.drawable.header);
				// }
				String mphoneNumber = phoneNumber.replace(" ", ""); // �õ��ĵ绰�����пո�ȥ����
				if (mphoneNumber.length() > 11) {
					mphoneNumber = mphoneNumber.substring(
							mphoneNumber.length() - 11, mphoneNumber.length());
				}// ��ȡ�ֻ��ŵĺ�11λ����Ϊ����е��ֻ��ſ�ͷ����+86������ȥ��
				NewConstactBean newConstactBean = new NewConstactBean();
				newConstactBean.setUsername(contactName);
				newConstactBean.setTel(mphoneNumber);
				mContactsNumber.add(mphoneNumber);
			}

			phoneCursor.close();
			// Log.i("�жϴ����̨�ĵ绰����",mContactsNumber+"");

		}
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
					AppLog.i(TAG, "��С:" + mContactsNumber.size());
					for (int i = 0; i < mContactsNumber.size(); i++) {
						AppLog.i(TAG,
								"��С:" + isIn(mContactsNumber.get(i), json3));
						if (isIn(mContactsNumber.get(i), json3)) {
							regist_not_friend_name.add(mContactsNumber.get(i));
						}

					}
					AppLog.i(TAG, "֮���С:" + regist_not_friend_name.size());
					for (int j = 0; j < regist_not_friend_name.size(); j++) {
						String num = regist_not_friend_name.get(j);
						AppLog.i(TAG, "�Ƿ����:" + isExit(num) + "num::" + num
								+ "::");
						if (!isExit(num)) {
							findUserByTel(num, j);
						}
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
		int length = source.length();
		for (int i = 0; i < length; i++) {
			String aSource = (String) source.get(i);
			AppLog.i(
					TAG,
					"����:" + substring + "::" + aSource + "::"
							+ (aSource.equals(substring)));
			if (aSource.equals(substring)) {
				return true;
			}
		}
		return false;
	}

	private void getLastData() {
		newContactsUtily = new NewContactsUtily(context);
		rConstactBeans = newContactsUtily.queryData();
		AppLog.i(TAG, "ʵ�ʴ�С:" + rConstactBeans.size());
		if (rConstactBeans == null) {
			rConstactBeans = new ArrayList<>();
		}
		AppLog.i(TAG, "ʵ�ʴ�С:" + rConstactBeans.size());
		for (int i = 0; i < rConstactBeans.size(); i++) {
			AppLog.i(TAG, "ʵ�ʴ�С:" + rConstactBeans.get(i).getUsername());
		}
	}

	private void updateList() {
		rConstactBeans = newContactsUtily.queryData();
		if (rConstactBeans == null) {
			rConstactBeans = new ArrayList<>();
		}
		newContAdapter = new NewContAdapter(rConstactBeans, context);
		cListView.setAdapter(newContAdapter);
		newContAdapter.notifyDataSetChanged();
		newContAdapter.setListener(this);
	}

	private boolean isExit(String num) {
		for (int i = 0; i < rConstactBeans.size(); i++) {
			if (num.equals(rConstactBeans.get(i))) {
				return true;
			}
		}
		return false;
	}

	private void findUserByTel(String number, final int postion) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				AppLog.i("����", "�µ�" + response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {
						JSONObject user = obj.getJSONObject("user");
						NewConstactBean newConstactBean = new Gson().fromJson(
								user.toString(), NewConstactBean.class);
						newContactsUtily.update(newConstactBean);
						updateList();
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
			JSONArray json = obj.getJSONArray("resultlist");
			int length = json.length();
			System.out.println("length==" + length);
			Log.i(TAG, "JSONArray:" + json);
			for (int i = 0; i < length; i++) {
				JSONObject objo = json.getJSONObject(i);
				Log.i(TAG, "objo:" + objo);
				NewConstactBean newConstactBean = new Gson().fromJson(
						objo.toString(), NewConstactBean.class);
				newConstactBean.setType_(1);
				newContactsUtily.update(newConstactBean);
			}
			updateList();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void addPeople(int position) {
		// TODO Auto-generated method stub
		NewConstactBean newConstactBean = rConstactBeans.get(position);
		if (newConstactBean.getType_() == TYPE_CONT) {
			applyfriend(newConstactBean.getId(), position);
		} else if (newConstactBean.getType_() == TYPE_REQUEST) {
			befriend(newConstactBean.getId(), position);
		}
	}

	private void befriend(String accept_id, final int position) {
		// TODO Auto-generated method stub
		AppLog.i("FriendRequestAdapter", "����:" + accept_id);
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("���أ�", "ͬ����" + response);
				// parseFriendRequestList(response);
				// initList();
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {
						Toast.makeText(context, "��ӳɹ����Է��Ѿ������ĺ��ѣ�",
								Toast.LENGTH_SHORT).show();
						rConstactBeans.get(position).setIsAccpet(1);
						newContactsUtily.update(rConstactBeans.get(position));
						newContAdapter.notifyDataSetChanged();
					} else {
						Toast.makeText(context, "���ʧ�ܣ�", Toast.LENGTH_SHORT)
								.show();
						onFinish();
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ToastUtils.showShort(context, getString(R.string.erro_no));
				}

			}

		};
		MyHttpClient client = new MyHttpClient();
		client.beFriends(accept_id, res);
	}

	private void applyfriend(String bid, final int position) {
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
						rConstactBeans.get(position).setIsAccpet(1);
						newContactsUtily.update(rConstactBeans.get(position));
						newContAdapter.notifyDataSetChanged();
						ToastUtils.showLong(getApplicationContext(),
								"������ϣ��ȴ�����ͨ����֤");
					} else {
						ToastUtils.showShort(getApplicationContext(),
								"��������Ϣ�ѷ��ͣ������ظ����~~");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ToastUtils.showShort(context, getString(R.string.erro_no));
				}
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.VerifyFriend(myid, bid, getString(R.string.contacts_), res);
	}

}
