package com.example.projectcircle.personal;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcircle.HomeActivity;
import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.adpter.GroupGallaryAdapter;
import com.example.projectcircle.adpter.HisCreateFriendGridAdapter;
import com.example.projectcircle.adpter.HisCreateGroupGridAdapter;
import com.example.projectcircle.adpter.HisDeviceGridAdapter;
import com.example.projectcircle.bean.EquInfo;
import com.example.projectcircle.bean.FriendDataBean;
import com.example.projectcircle.bean.GroupInfo;
import com.example.projectcircle.bean.MoodInfo;
import com.example.projectcircle.bean.StatusInfo;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.db.utils.FriendDatabaseUtils;
import com.example.projectcircle.friend.FriendPage;
import com.example.projectcircle.friend.MaybeFriend;
import com.example.projectcircle.group.GroupDetail;
import com.example.projectcircle.other.Chat;
import com.example.projectcircle.util.DistentsUtil;
import com.example.projectcircle.util.MsgUtils;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class PersonalPage extends Activity {

	private static final String TAG = "PersonalPage";

	private static List<FriendDataBean> friendDataBeans;

	/**
	 * ����Button
	 */
	Button back, send;
	/**
	 * ͷ���� ����TextView
	 */
	ImageView headbg, headimg;

	TextView name, career, content;
	// ����
	TextView project_num;
	// �漮
	TextView mycity;
	// ����
	TextView age_txt;
	// ���ҽ���
	TextView intro;
	// ��ϵ
	TextView relation;
	/**
	 * Call-Phone
	 */
	RelativeLayout call_phone;

	/**
	 * �豸��ϸ
	 */
	LinearLayout device_detail_lay;
	// �豸����
	TextView equipment_detail_txt;

	/**
	 * ����Ⱥ��
	 */
	LinearLayout group_lay;
	// ����Ⱥ��ͷ��
	ImageView group_headimg;
	// ����Ⱥ�����ƺ�Ⱥ����
	TextView group_name, group_content;

	/**
	 * ��ͬ����
	 */
	LinearLayout common_friend;
	String id;
	String uname, utype, ucity, udevice, ucontent, uheadimg, accept, info, uid,
			age, group, gname, singn, rplace, udriverYear;

	private LinearLayout qian_ming_lay;
	private LinearLayout he_friend_lay;// ���ĺ��ѵ�layout
	// ͼƬ����
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	// ���Ѷ�̬
	String status;
	private ArrayList<GroupInfo> groupList;
	private String gcontent;
	private String gheadImage;
	private HisCreateGroupGridAdapter myAdapter;
	private GridView his_group_gridview;
	private ArrayList<HashMap<String, Object>> listItem;
	private ArrayList<HashMap<String, Object>> listItem2;
	private HisCreateGroupGridAdapter gridAdapter;
	private HisDeviceGridAdapter devgridAdapter;
	private HisCreateFriendGridAdapter friendgridAdapter;
	private TextView persingnTextView;
	private TextView recentplaceTextView;

	private ImageLoader imageLoader = ImageLoader.getInstance();
	private Gallery mGallery;
	GroupGallaryAdapter gallaryAdapter;
	private List<String> device_picList = new ArrayList<String>();

	private Button addfriend;
	private GridView his_friend_gridview;

	private String hobby;

	private String type;

	private TextView driver_txt;

	private LinearLayout driver_layout;

	private String companyname;

	private LinearLayout company_lay;

	private TextView company_txt;

	private String business;

	private LinearLayout business_scope_lay;

	ArrayList<String> ename = new ArrayList<String>();// �豸����
	ArrayList<String> ebrand = new ArrayList<String>();
	ArrayList<String> emodel = new ArrayList<String>();// �豸�ͺ�
	ArrayList<String> eweight = new ArrayList<String>();
	ArrayList<String> enumber = new ArrayList<String>();// �豸����

	private String driveryear;

	private String brand_model;

	private LinearLayout equipment_details;

	private TextView equipment_lay;
	StringBuffer strb = new StringBuffer();

	private String ch;

	private String driveryearO;

	private String nequ;

	private TextView my_hobby;
	List<UserInfo> friendList;

	private String usualplace;

	private String businessinfo;

	private TextView brief_introduction_of_business;

	private LinearLayout ye_wu_jian_jie_lay;

	private GridView his_device_gridview;
	private ArrayList<GroupInfo> deviceList;
	private ArrayList<StatusInfo> mymoodList;
	private GridView his_photo_gridview;
	public static ArrayList<HashMap<String, Object>> commfrihead = new ArrayList<HashMap<String, Object>>();
	private ArrayList<HashMap<String, Object>> mood_listItem = new ArrayList<HashMap<String, Object>>();
	private LinearLayout chu_mo_di_dian_lay;
	private GridView common_friend_gridView;
	private ArrayList<MoodInfo> usermoodsList = new ArrayList<MoodInfo>();
	private HisCreateFriendGridAdapter commonfrigridAdapter;

	private String ccid;
	private double mylat = HomeActivity.latitude;
	private double mylon = HomeActivity.longitude;
	private double lat;// �����˵�γ��
	private double lon;// �����˵ľ���
	private LinearLayout relationship_lay;
	private TextView distance_txt;// �Һͺ���֮��ľ����TextView

	private String time;// �û����ϴε�¼ʱ��

	private TextView time_txt;// ʱ���textView

	private double distance;

	private String lastlogintime_new;

	private String timecompute;

	private TextView business_scope_txt;

	private LinearLayout she_bei_tu_pian_lay;

	private LinearLayout he_group_lay;

	private LinearLayout common_friend_lay;// ��ͬ���ѵ�layout

	private LinearLayout self_introduction_lay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_home_page);
		// ����ͼƬ���ؼ���ʾѡ�����һЩ���������ã�����doc�ĵ��ɣ�
		options = new DisplayImageOptions.Builder().cacheInMemory(true) // ����ͼƬʱ�����ڴ��м��ػ���
				.cacheInMemory(true)// �Ƿ񾏴涼�ȴ���
				.cacheOnDisc(true)// �Ƿ񾏴浽sd����
				.build();
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		type = intent.getStringExtra("type");
		time = intent.getStringExtra("time");
		ccid = intent.getStringExtra("ccid");
		lat = intent.getDoubleExtra("lat", 0);
		lon = intent.getDoubleExtra("lon", 0);
		getFriInfo(PersonalPage.this);
		initBtn();
		dectRelationship();
		// �豸������Ƭ
		EquDetail(id);
		UserDetail(id, type);
		// �ж��Ƿ�Ϊ����
		if (isFriend(id)) {
			addfriend.setText("ȡ������");
			addfriend.setVisibility(View.VISIBLE);
		} else {
			addfriend.setText("��Ϊ����");
			addfriend.setVisibility(View.VISIBLE);
		}
		;

		findfriend(id);
		findCreatGroup(id);
		// Ϊ�˵õ����˶�̬��ͷ��
		// doListMyMood();
		initHisPhoto();

	}

	// �õ���ͬ����
	private void commonFriend() {
		// TODO Auto-generated method stub
		// ��ͬ���ѵ�layout
		common_friend_lay = (LinearLayout) findViewById(R.id.common_friend_lay);
		Log.i("friendList", friendList + "");
		commfrihead.clear();
		for (int i = 0; i < friendList.size(); i++) {
			String friid = friendList.get(i).getId();
			if (isFriend(friid)) {
				// commfrihead.add(friendList.get(i).getHeadimage());
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("id", friendList.get(i).getId());
				map.put("address", friendList.get(i).getAddress());
				map.put("type", friendList.get(i).getType());
				map.put("headimage", friendList.get(i).getHeadimage());
				map.put("username", friendList.get(i).getUsername());
				map.put("tel", friendList.get(i).getTel());
				map.put("accept", friendList.get(i).getAccept());
				map.put("ccid", friendList.get(i).getCcid());
				map.put("sign", friendList.get(i).getSign());
				map.put("lastlogintime", friendList.get(i).getLastlogintime());
				map.put("commercialLat", friendList.get(i).getLat());
				map.put("commercialLon", friendList.get(i).getLon());
				commfrihead.add(map);
			}
		}
		if (commfrihead.size() == 0) {
			common_friend_lay.setVisibility(View.GONE);
		}
		common_friend_gridView = (GridView) findViewById(R.id.common_friend);
		commonfrigridAdapter = new HisCreateFriendGridAdapter(commfrihead, this);
		common_friend_gridView.setAdapter(commonfrigridAdapter);
		// �������������ϵ���б�
		common_friend_gridView
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(PersonalPage.this,
								HisContact.class);
						intent.putExtra("id", id);
						intent.putExtra("biaoji", "1");// ��ͬ���ѵı��
						startActivity(intent);
					}
				});
	}

	// �豸��Ƭ
	private void EquDetail(String uid) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "������Ϣ����:" + response);
				try {
					deviceList = new ArrayList<GroupInfo>();
					JSONObject result = new JSONObject(response);
					JSONObject equ = result.getJSONObject("equ");
					JSONArray array = equ.getJSONArray("resultlist");
					for (int i = 0; i < array.length(); i++) {
						GroupInfo device = new GroupInfo();
						JSONArray array1 = array.getJSONArray(i);
						JSONObject obj = array1.getJSONObject(0);
						device.setHeadimage(obj.getString("headimage"));
						deviceList.add(device);
					}
					initHisDevice();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.EquDetail(uid, res);
	}

	/**
	 * �����������״̬ ����Է�ͨ���� ������Ϣ �ؼ���ʧ
	 * 
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@SuppressWarnings("unchecked")
	public static void getFriInfo(Context context) {
		friendDataBeans = (List<FriendDataBean>) new FriendDatabaseUtils(
				context).queryData();
	}

	/**
	 * �ж��Ƿ�Ϊ����
	 * 
	 * @param id
	 * @return
	 */
	private static boolean isFriend(String id) {
		boolean a = false;
		if (null == friendDataBeans) {
			return a;
		}
		for (int i = 0; i < friendDataBeans.size(); i++) {
			if (friendDataBeans.get(i).getFriendid().equals(id)) {
				a = true;
				break;
			}
		}
		return a;
	}

	/**
	 * ����Ⱥ�� ����ͽ���
	 * 
	 */
	private void findCreatGroup(String id) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "����:" + response);
				parsefindCreateGroup(response);
				// ����Ⱥ��ͷ���г���
				initHisGroup();
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.findCreatGroup(id, res);
	}

	// ����Ⱥ��ͷ��
	private void initHisGroup() {
		// TODO Auto-generated method stub
		he_group_lay = (LinearLayout) findViewById(R.id.he_group_lay);
		if (groupList.size() == 0) {
			he_group_lay.setVisibility(View.GONE);
		}
		his_group_gridview = (GridView) findViewById(R.id.his_group_gridview);
		gridAdapter = new HisCreateGroupGridAdapter(get_list_group_headimage(),
				this);
		his_group_gridview.setAdapter(gridAdapter);
		// �������������ϵ���б�
		his_group_gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PersonalPage.this, HisGroup.class);
				intent.putExtra("id", id);
				startActivity(intent);
				// finish();
			}
		});

	}

	// �����豸ͼƬ
	private void initHisDevice() {
		// TODO Auto-generated method stub
		// �豸ͼƬ��layout
		she_bei_tu_pian_lay = (LinearLayout) findViewById(R.id.she_bei_tu_pian_lay);

		his_device_gridview = (GridView) findViewById(R.id.his_device_gridview);
		devgridAdapter = new HisDeviceGridAdapter(get_list_device_image(), this);
		his_device_gridview.setAdapter(devgridAdapter);
		if (deviceList.size() == 0 || listItem2.size() == 0) {
			she_bei_tu_pian_lay.setVisibility(View.GONE);
		} else {
			she_bei_tu_pian_lay.setVisibility(View.VISIBLE);
		}
		// �������������ϵ���б�
		his_device_gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PersonalPage.this, PictureShow.class);
				intent.putExtra("image", deviceList.get(arg2).getHeadimage());
				startActivity(intent);
			}
		});

	}

	private void parsefindCreateGroup(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("groups");
			groupList = new ArrayList<GroupInfo>();
			JSONArray json = obj.getJSONArray("resultlist");
			int length = json.length();
			System.out.println("length==" + length);
			for (int i = 0; i < length; i++) {
				GroupInfo group = new GroupInfo();
				obj = json.getJSONObject(i);
				group.setId(obj.getString("id"));
				group.setGname(obj.getString("gname"));
				group.setContent(obj.getString("content"));
				group.setHeadimage(obj.getString("headimage"));
				group.setGaddress(obj.getString("gaddress"));
				groupList.add(group);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ������Ϣ ����ͽ���
	 * 
	 */
	private void UserDetail(String id, final String type) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "������Ϣ����:" + response);
				if (type.equals("˾��")) {
					parseUserDetail1(response);
				} else if (type.equals("����")) {
					parseUserDetail2(response);
				} else if (type.equals("�̼�")) {
					parseUserDetail3(response);
				} else {
					parseUserDetail4(response);
				}
				// initCall();
				initView();
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.UserDetail2(id, type, res);
	}

	// �����Ľ���OK
	public void parseUserDetail2(String response) {
		try {
			JSONObject result = new JSONObject(response);
			Log.i("������ response", response + "");
			JSONObject json = result.getJSONObject("equ");
			if (json.has("resultlist")) {
				JSONArray obja = json.getJSONArray("resultlist");
				int length = obja.length();
				for (int i = 0; i < length; i++) {
					JSONArray objo = obja.getJSONArray(i);
					if (objo.length() > 1) {
						for (int j = 0; j < objo.length(); j++) {
							JSONObject obj = objo.getJSONObject(j);
							if (j == 0) {
								ename.add(obj.getString("ename"));
								ebrand.add(obj.getString("ebrand"));
								emodel.add(obj.getString("emodel"));
								eweight.add(obj.getString("eweight"));
								enumber.add(obj.getString("enumber"));
							} else {
								uid = obj.getString("id");
								uname = obj.getString("username");
								utype = obj.getString("type");
								ucity = obj.getString("address");
								udevice = obj.getString("equipment");
								ucontent = obj.getString("sign");
								uheadimg = obj.getString("headimage");
								accept = obj.getString("accept");
								hobby = obj.getString("hobby");// ��Ȥ����
								// Log.i("accept", accept);
								info = obj.getString("info");// ���˼�顢���ҽ���
								age = obj.getString("age");
								singn = obj.getString("sign");
								usualplace = obj.getString("place");// ����û�ص�
							}

						}
					}
				}
			} else {
				uid = json.getString("id");
				uname = json.getString("username");
				utype = json.getString("type");
				ucity = json.getString("address");
				udevice = json.getString("equipment");
				ucontent = json.getString("sign");
				uheadimg = json.getString("headimage");
				accept = json.getString("accept");
				hobby = json.getString("hobby");// ��Ȥ����
				// Log.i("accept", accept);
				info = json.getString("info");// ���˼�顢���ҽ���
				age = json.getString("age");
				singn = json.getString("sign");
				usualplace = json.getString("place");// ����û�ص�
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	// �̼ҵĽ���
	public void parseUserDetail3(String response) {
		try {
			JSONObject result = new JSONObject(response);
			Log.i("user detail response", response + "");
			JSONObject obj = result.getJSONObject("user");
			uid = obj.getString("id");
			uname = obj.getString("username");
			utype = obj.getString("type");
			ucity = obj.getString("address");
			udevice = obj.getString("equipment");
			ucontent = obj.getString("sign");
			uheadimg = obj.getString("headimage");
			accept = obj.getString("accept");
			hobby = obj.getString("hobby");// ��Ȥ����
			companyname = obj.getString("companyname");//
			info = obj.getString("info");// ���˼�飬���ҽ���
			// Log.i("accept", accept)��
			usualplace = obj.getString("place");// ����û�ص�
			business = obj.getString("business");// �̼ҵ�ҵ��Χ
			businessinfo = obj.getString("businessinfo");// �̼ҵ�ҵ����
			age = obj.getString("age");
			singn = obj.getString("sign");

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	// �����Ľ���
	public void parseUserDetail4(String response) {
		try {
			JSONObject result = new JSONObject(response);
			Log.i("������response", response + "");
			JSONObject obj = result.getJSONObject("userO");
			uid = obj.getString("id");
			uname = obj.getString("username");
			utype = obj.getString("type");
			ucity = obj.getString("address");
			udevice = obj.getString("equipment");
			ucontent = obj.getString("sign");
			uheadimg = obj.getString("headimage");
			business = obj.getString("business");
			accept = obj.getString("accept");
			hobby = obj.getString("hobby");// ��Ȥ����
			// Log.i("accept", accept);
			info = obj.getString("info");
			age = obj.getString("age");
			singn = obj.getString("sign");
			usualplace = obj.getString("place");// ����û�ص�

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	// ������˾��ʱ��Ľ���
	private void parseUserDetail1(String response) {
		// TODO Auto-generated method stub
		try {
			JSONObject result = new JSONObject(response);
			Log.i("user detail response", response + "");
			if (result.getInt("result") == 1) {
				if (result.getInt("type") == 1) {
					JSONArray arr = result.getJSONArray("driver");
					JSONArray arr1 = arr.getJSONArray(0);
					JSONObject equobj = arr1.getJSONObject(0);
					driveryearO = equobj.getString("driveryear");
					nequ = equobj.getString("nequ");
					JSONObject personal = arr1.getJSONObject(1);
					uid = personal.getString("id");
					uname = personal.getString("username");
					utype = personal.getString("type");
					ucity = personal.getString("address");
					udevice = personal.getString("equipment");
					ucontent = personal.getString("sign");
					uheadimg = personal.getString("headimage");
					accept = personal.getString("accept");
					hobby = personal.getString("hobby");// ��Ȥ����
					// Log.i("accept", accept);
					info = personal.getString("info");// ���˼�顢���ҽ���
					age = personal.getString("age");
					singn = personal.getString("sign");
					usualplace = personal.getString("place");// ����û�ص�
				} else if (result.getInt("type") == 2) {
					JSONObject obj = result.getJSONObject("driver");
					uid = obj.getString("id");
					uname = obj.getString("username");
					utype = obj.getString("type");
					ucity = obj.getString("address");
					udevice = obj.getString("equipment");
					ucontent = obj.getString("sign");
					uheadimg = obj.getString("headimage");
					accept = obj.getString("accept");
					hobby = obj.getString("hobby");// ��Ȥ����
					// Log.i("accept", accept);
					info = obj.getString("info");
					age = obj.getString("age");
					singn = obj.getString("sign");
					usualplace = obj.getString("place");// ����û�ص�
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ���ĸ�����Ϣ
	 */
	private void initView() {
		// TODO Auto-generated method stub
		// ͷ����
		headbg = (ImageView) findViewById(R.id.p_home_page_background);
		headimg = (ImageView) findViewById(R.id.p_home_page_headimg);
		name = (TextView) findViewById(R.id.main_name);
		career = (TextView) findViewById(R.id.main_career);
		content = (TextView) findViewById(R.id.main_content);
		content.setVisibility(View.GONE);// ��������ظ���Ҫ��ȥ��
		// ����
		project_num = (TextView) findViewById(R.id.project_number);
		// �漮
		mycity = (TextView) findViewById(R.id.p_home_city);
		// ����
		age_txt = (TextView) findViewById(R.id.p_home_age);
		// ˾��ʱ����ʾ����
		driver_txt = (TextView) findViewById(R.id.p_driver_year);
		self_introduction_lay = (LinearLayout) findViewById(R.id.self_introduction_lay);
		// ���ҽ���
		intro = (TextView) findViewById(R.id.p_home_introduce);// ���ҽ��ܵ�����
		// ҵ����
		ye_wu_jian_jie_lay = (LinearLayout) findViewById(R.id.ye_wu_jian_jie_lay);
		// ��˾�����̼���ʾ
		company_lay = (LinearLayout) findViewById(R.id.p_company);
		company_txt = (TextView) findViewById(R.id.p_company_name);
		// ҵ��Χ���̼ҡ�������ʾ
		business_scope_lay = (LinearLayout) findViewById(R.id.business_scope_lay);
		business_scope_txt = (TextView) findViewById(R.id.business_scope);
		equipment_details = (LinearLayout) findViewById(R.id.equipment_details);
		brief_introduction_of_business = (TextView) findViewById(R.id.brief_introduction_of_business);
		// �豸����
		equipment_detail_txt = (TextView) findViewById(R.id.equipment_lay);
		// ��Ȥ���õ�lay
		he_hobby_lay = (LinearLayout) findViewById(R.id.he_hobby_lay);
		my_hobby = (TextView) findViewById(R.id.my_hobby);
		// my_hobby.setText(hobby);
		// ������Ϣ��ȡ
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(MyHttpClient.IMAGE_URL + uheadimg, headimg,
				options, animateFirstListener);
		name.setText(uname);
		career.setText(utype);
		content.setText(ucontent);
		project_num.setText("���̺�:   " + uid);

		mycity.setText(ucity);
		age_txt.setText(age + "��");
		intro.setText(info);
		if (!TextUtils.isEmpty(singn)) {
			qian_ming_lay.setVisibility(View.VISIBLE);
			persingnTextView.setText("" + singn);
		} else {
			qian_ming_lay.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(usualplace)) {
			recentplaceTextView.setText("" + usualplace);
			chu_mo_di_dian_lay.setVisibility(View.VISIBLE);
		} else {
			chu_mo_di_dian_lay.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(hobby)) {
			he_hobby_lay.setVisibility(View.VISIBLE);
			my_hobby.setText("" + hobby);

		} else {
			he_hobby_lay.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(info)) {
			intro.setText("" + info);
			self_introduction_lay.setVisibility(View.VISIBLE);
		} else {
			self_introduction_lay.setVisibility(View.GONE);
		}

		// type��˾����ʱ����ʾ����
		driver_layout = (LinearLayout) findViewById(R.id.p_driver_age);
		if (type.equals("˾��")) {
			company_lay.setVisibility(View.GONE);
			ye_wu_jian_jie_lay.setVisibility(View.GONE);
			business_scope_lay.setVisibility(View.GONE);
			if (!TextUtils.isEmpty(driveryearO)) {
				// int jialing = 2014 -
				// Integer.parseInt(driveryearO);//2014��ȥ������㣬���Ǽ��伸��
				driver_layout.setVisibility(View.VISIBLE);
				driver_txt.setText(driveryearO);
			} else {
				driver_layout.setVisibility(View.GONE);
			}
			if (!TextUtils.isEmpty(udevice) || !TextUtils.isEmpty(nequ)) {
				equipment_details.setVisibility(View.VISIBLE);
				equipment_detail_txt.setText(udevice + "      " + nequ);
			} else {
				equipment_details.setVisibility(View.GONE);
			}
		} else if (type.equals("����")) {
			driver_layout.setVisibility(View.GONE);
			company_lay.setVisibility(View.GONE);
			ye_wu_jian_jie_lay.setVisibility(View.GONE);
			business_scope_lay.setVisibility(View.GONE);
			for (int i = 0; i < ename.size(); i++) {
				if (!enumber.get(i).equals("0")) {
					if (eweight.get(i).equals("�Ĵ�")) {
						ch = ename.get(i) + "    " + ebrand.get(i) + "    "
								+ emodel.get(i) + "    " + enumber.get(i) + "̨"
								+ "\n";
						strb.append(ch);
					} else {
						ch = ename.get(i) + "    " + ebrand.get(i) + "    "
								+ emodel.get(i) + "    " + eweight.get(i)
								+ "    " + enumber.get(i) + "̨" + "\n";
						strb.append(ch);
					}
				}
			}
			if (!TextUtils.isEmpty(strb)) {
				equipment_detail_txt.setText(strb);
				equipment_details.setVisibility(View.VISIBLE);
			} else {
				equipment_details.setVisibility(View.GONE);
			}

		} else if (type.equals("�̼�")) {
			equipment_details.setVisibility(View.GONE);
			driver_layout.setVisibility(View.GONE);
			if (!TextUtils.isEmpty(companyname)) {
				company_txt.setText(companyname);
				company_lay.setVisibility(View.VISIBLE);
			} else {
				company_lay.setVisibility(View.GONE);
			}

			business_scope_lay.setVisibility(View.VISIBLE);
			if (!TextUtils.isEmpty(business) && !TextUtils.isEmpty(udevice)) {
				business_scope_txt.setText(udevice + " " + business);
				business_scope_lay.setVisibility(View.VISIBLE);
			} else {
				business_scope_lay.setVisibility(View.GONE);
			}
			if (!TextUtils.isEmpty(businessinfo)) {
				brief_introduction_of_business.setText(businessinfo);
				ye_wu_jian_jie_lay.setVisibility(View.VISIBLE);
			} else {
				ye_wu_jian_jie_lay.setVisibility(View.GONE);
			}
		} else {
			if (!TextUtils.isEmpty(business)) {
				business_scope_txt.setText(business);
				business_scope_lay.setVisibility(View.VISIBLE);
			} else {
				business_scope_lay.setVisibility(View.GONE);
			}
			ye_wu_jian_jie_lay.setVisibility(View.GONE);
			company_lay.setVisibility(View.GONE);
			driver_layout.setVisibility(View.GONE);
			equipment_details.setVisibility(View.GONE);
		}

	}

	private ArrayList<HashMap<String, Object>> get_list_group_headimage() {
		// TODO Auto-generated method stub
		listItem = new ArrayList<HashMap<String, Object>>();
		// TODO Auto-generated method stub
		Log.i(TAG, "����:groupList" + groupList);
		if (groupList != null) {
			for (int i = 0; i < groupList.size(); i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				// map.put("friendID", friendList.get(i).getId());
				map.put("id", groupList.get(i).getId());
				map.put("gname", groupList.get(i).getGname());
				map.put("content", groupList.get(i).getContent());
				map.put("headimage", groupList.get(i).getHeadimage());
				map.put("gaddress", groupList.get(i).getGaddress());
				listItem.add(map);
			}
		}
		Log.i("groupGridAdapter�ķ���listItem", listItem + "");
		return listItem;
	}

	private ArrayList<HashMap<String, Object>> get_list_device_image() {
		// TODO Auto-generated method stub
		listItem2 = new ArrayList<HashMap<String, Object>>();
		Log.i("����:deviceList", "����:deviceList" + deviceList);
		// TODO Auto-generated method stub
		if (deviceList != null) {
			if (deviceList.size() <= 3) {
				for (int i = 0; i < deviceList.size(); i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					// map.put("friendID", friendList.get(i).getId());
					if (!TextUtils.isEmpty(deviceList.get(i).getHeadimage())
							&& !(deviceList.get(i).getHeadimage().equals(""))
							&& !(deviceList.get(i).getHeadimage()
									.equals("null"))) {
						map.put("headimage", deviceList.get(i).getHeadimage());
						listItem2.add(map);
					}
				}
			} else if (deviceList.size() > 3) {
				for (int i = 0; i < 3; i++) {
					HashMap<String, Object> map = new HashMap<String, Object>();
					// map.put("friendID", friendList.get(i).getId());
					if (!TextUtils.isEmpty(deviceList.get(i).getHeadimage())
							&& !(deviceList.get(i).getHeadimage().equals(""))
							&& !(deviceList.get(i).getHeadimage()
									.equals("null"))) {
						map.put("headimage", deviceList.get(i).getHeadimage());
						listItem2.add(map);
					}
				}
			}
		}
		Log.i("deviceGridAdapter�ķ���listItem", listItem2 + "");
		return listItem2;
	}

	// ��ø���״̬��ͷ��
	// private ArrayList<HashMap<String, Object>> get_list_mood_image() {
	// // TODO Auto-generated method stub
	// Log.i("����:mymoodList", "����:mymoodList" + mymoodList);
	// // TODO Auto-generated method stub
	// HashMap<String, Object> map = new HashMap<String, Object>();
	// if (mymoodList != null ){
	// for (int i = 0; i < usermoodsList.size(); i++) {
	// // map.put("friendID", friendList.get(i).getId());
	// if(!TextUtils.isEmpty(usermoodsList.get(i).getMheadimage()) &&
	// (mymoodList.get(i).getMheadimage())!=null){
	// map.put("uheadimage", usermoodsList.get(i).getUheadimage());
	// map.put("headimage", usermoodsList.get(i).getMheadimage());
	// mood_listItem.add(map);
	// }
	// }
	// if(mood_listItem.size() == 0){
	// // map.put("headimage", mymoodList.get(0).getUheadimage());
	// }else if(mood_listItem.size() > 3){
	// mood_listItem.subList(0, 3);
	// }
	// }
	// Log.i("mood_listItem", mood_listItem+"");
	// return mood_listItem;
	// }
	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.ye_wu_jian_jie_lay:
				// Intent intent1 = new Intent(PersonalPage.this,
				// CompleteMaster.class);
				// startActivity(intent1);
				break;
			case R.id.he_group_lay:
				Intent intent2 = new Intent(PersonalPage.this,
						GroupDetail.class);
				startActivity(intent2);
				// finish();
				break;
			// case R.id.common_friend_lay:
			// Intent intent3 = new Intent(PersonalPage.this,
			// MaybeFriend.class);
			// startActivity(intent3);
			// finish();
			// break;
			default:
				break;
			}
		}
	};

	private LinearLayout he_hobby_lay;

	/**
	 * ����绰����ʵ��
	 */
	private void initCall() {
		// TODO Auto-generated method stub
		call_phone = (RelativeLayout) findViewById(R.id.call_phone_lay);
		call_phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * ����Button��ʼ�� ����ʵ��
	 */
	private void initBtn() {
		// TODO Auto-generated method stub

		addfriend = (Button) findViewById(R.id.addfriden_btn);

		relationship_lay = (LinearLayout) findViewById(R.id.relationship_lay);
		// ��ϵ
		relation = (TextView) findViewById(R.id.p_home_relation);//

		qian_ming_lay = (LinearLayout) findViewById(R.id.qian_ming_lay);// ����ǩ���Ĳ���
		persingnTextView = (TextView) findViewById(R.id.per_sign);
		chu_mo_di_dian_lay = (LinearLayout) findViewById(R.id.chu_mo_di_dian_lay);
		recentplaceTextView = (TextView) findViewById(R.id.per_rplace);
		distance_txt = (TextView) findViewById(R.id.distance);
		time_txt = (TextView) findViewById(R.id.time);
		// �����ʱ�����
		// �����Һͺ���֮��ľ���
		distance = DistentsUtil.GetDistance(lat, lon, mylat, mylon);
		distance_txt.setText(distance + "km");
		// ��ȡ��ǰʱ��
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy��MM��dd��   HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
		String now = formatter.format(curDate);
		if (time != null && !time.equals("null")) {
			try { // ʱ��ת���ɸ�������ʱ��
				lastlogintime_new = time.substring(0, 4) + "��"
						+ time.substring(5, 7) + "��" + time.substring(8, 10)
						+ "��   " + time.substring(11, time.length());
				Log.i("lastlogintime_new", lastlogintime_new);
				java.util.Date d1 = (formatter).parse(now);
				java.util.Date d2 = (formatter).parse(lastlogintime_new);
				long diff = d1.getTime() - d2.getTime();// �����õ��Ĳ�ֵ��΢�뼶��
				long days = diff / (1000 * 60 * 60 * 24);
				long hours = (diff - days * (1000 * 60 * 60 * 24))
						/ (1000 * 60 * 60);
				long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
						* (1000 * 60 * 60))
						/ (1000 * 60);
				System.out.println("" + days + "��" + hours + "Сʱ" + minutes
						+ "��");
				if (days >= 1) {
					timecompute = "1��ǰ";
				} else if (hours >= 1) {
					timecompute = hours + "Сʱǰ";
				} else {
					timecompute = minutes + "����ǰ";
				}

			} catch (Exception e) {
			}
			time_txt.setText(timecompute + "");
		} else {
			// ������ϴε�¼��ʱ�䣬��ʾ���ؼ���
			time_txt.setText("��");
		}
		back = (Button) findViewById(R.id.p_home_page_back);
		send = (Button) findViewById(R.id.send_message);
		back.setOnClickListener(btnlistener);
		send.setOnClickListener(btnlistener);
		addfriend.setOnClickListener(btnlistener);
	}

	private View.OnClickListener btnlistener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.p_home_page_back:
				// Intent intent1 = new Intent(PersonalPage.this,
				// MainActivity.class);
				// startActivity(intent1);
				finish();
				break;
			case R.id.send_message:

				if (TextUtils.isEmpty(uid)) {
					return;
				}

				if (!MsgUtils.isExitMsgList(uid, PersonalPage.this)) {
					MsgUtils.saveMsgList(uname, " ", "" + Chat.getDate(), uid,
							uheadimg, 0, 0, PersonalPage.this);
				}
				Intent intent2 = new Intent(PersonalPage.this, Chat.class);
				intent2.putExtra("id", uid);
				intent2.putExtra("username", "" + uname);
				intent2.putExtra("headimg", "" + uheadimg);
				startActivity(intent2);
				// finish();
				break;

			case R.id.addfriden_btn:
				if (addfriend.getText().toString().equals("��Ϊ����")) {
					Intent intent3 = new Intent(PersonalPage.this,
							ApplyFriend.class);
					intent3.putExtra("id", uid);
					startActivity(intent3);
				} else {
					deleteDialog();
				}
				break;

			default:
				break;
			}
		}
	};

	private void findfriend(String id) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("���أ�", response);
				parsefindfriend(response);
				initHisFriend();
				// �õ���ͬ����
				commonFriend();
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.findfriend(id, res);
	}

	// ɾ������
	protected void deleteDialog() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(PersonalPage.this).setTitle("ȷ��Ҫɾ���˺�����")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// ��ȷ����ɾ��
						denyfriend(ccid);
					}

				}).setNegativeButton("ȡ��", null).show();

	}

	private void denyfriend(String ccid) {
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
						Toast.makeText(PersonalPage.this, "ɾ���ɹ���",
								Toast.LENGTH_SHORT).show();
						// //ɾ���ɹ���Ͱ���Ӧ��listItemɾ��
						//
						Intent intent2 = new Intent();
						intent2.setAction("shan.chu.hao.you");
						PersonalPage.this.sendBroadcast(intent2);
						finish();
					} else {
						Toast.makeText(PersonalPage.this, "ɾ��ʧ�ܣ�",
								Toast.LENGTH_SHORT).show();

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};
		MyHttpClient client = new MyHttpClient();
		client.denyFriends(ccid, res);
	}

	protected void initHisFriend() {
		// TODO Auto-generated method stub
		he_friend_lay = (LinearLayout) findViewById(R.id.he_friend_lay);
		if (friendList.size() == 0) {
			he_friend_lay.setVisibility(View.GONE);
		}
		his_friend_gridview = (GridView) findViewById(R.id.his_friend_gridview);
		friendgridAdapter = new HisCreateFriendGridAdapter(
				get_list_friend_headimage(), this);
		his_friend_gridview.setAdapter(friendgridAdapter);
		// �������������ϵ���б�
		his_friend_gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PersonalPage.this, HisContact.class);
				intent.putExtra("id", id);
				intent.putExtra("biaoji", "0");// ������ϵ�˱��
				startActivity(intent);
				// finish();
			}
		});

	}

	private ArrayList<HashMap<String, Object>> get_list_friend_headimage() {
		// TODO Auto-generated method stub
		listItem = new ArrayList<HashMap<String, Object>>();
		// TODO Auto-generated method stub
		Log.i("����:friendList", "����:friendList" + friendList);
		if (friendList != null) {
			for (int i = 0; i < friendList.size(); i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				// map.put("friendID", friendList.get(i).getId());
				map.put("id", friendList.get(i).getId());
				map.put("username", friendList.get(i).getUsername());
				map.put("headimage", friendList.get(i).getHeadimage());
				listItem.add(map);
			}
		}
		Log.i("friendList�ķ���listItem", listItem + "");
		return listItem;
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
				Log.i(TAG, "objo:" + objo);
				user.setId(objo.getString("id"));
				user.setTel(objo.getString("tel"));
				user.setUsername(objo.getString("username"));
				user.setType(objo.getString("type"));
				user.setAddress(objo.getString("address"));
				user.setCcid(objo.getString("ccid"));
				// user.setEquipment(obj.getString("equipment"));
				user.setSign(objo.getString("sign"));
				user.setHeadimage(objo.getString("headimage"));
				user.setAccept(objo.getString("accept"));
				user.setLat(Double.valueOf(objo.getString("commercialLat")));
				user.setLon(Double.valueOf(objo.getString("commercialLon")));
				user.setLastlogintime(objo.getString("lastlogintime"));
				if (isexit(user.getId())) {
					friendList.add(user);
				}

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private boolean isexit(String id) {
		if (TextUtils.isEmpty(id)) {
			return false;
		}
		if (null != FriendPage.friendDataBeans) {
			int length = FriendPage.friendDataBeans.size();
			for (int i = 0; i < length; i++) {
				if (id.equals(FriendPage.friendDataBeans.get(i).getFriendid()
						.toString())) {
					return true;
				}
			}
		}

		return false;

	}

	private void findEquienment(String id) {
		MyHttpClient.findUserEqu4User(id, new AsyncHttpResponseHandler() {
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				Log.i(TAG, "�豸���:" + content);
				// ParseEq(content);
			}

			@Override
			public void onFailure(Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(error);
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
			}

		});
	}

	private List<EquInfo> equInfos = new ArrayList<EquInfo>();

	private HisDeviceGridAdapter phogridAdapter;

	private RelativeLayout ge_ren_dong_tai;

	private String pingluncontent;

	private void ParseEq(String json) {
		try {
			JSONObject jsonObject = new JSONObject(json);

			String result = jsonObject.getString("result");
			Log.i(TAG, "�豸�б�:" + result);
			// if (result!="1") {
			// return ;
			// }
			JSONObject jsonObject2 = jsonObject.getJSONObject("equ");
			Log.i(TAG, "�豸�б�����:" + jsonObject2);
			JSONArray resultlist = jsonObject2.getJSONArray("resultlist");
			Log.i(TAG, "�豸�б�:" + resultlist);
			for (int i = 0; i < resultlist.length(); i++) {

				EquInfo equInfo = null;
				try {
					Gson gson = new Gson();
					equInfo = gson.fromJson(resultlist.get(i).toString(),
							EquInfo.class);
					equInfos.add(equInfo);
					Log.i(TAG, "�豸���:" + equInfo);
				} catch (Exception e) {
					// TODO: handle exception
					Log.e(TAG, "�豸��������");
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < equInfos.size(); i++) {
			if (i != 0) {
				udevice = "\n" + udevice + equInfos.get(i).getEname() + ":"
						+ "   Ʒ��:" + equInfos.get(i).getEbrand() + "  " + "����:"
						+ equInfos.get(i).getEnumber();
			} else {
				udevice = equInfos.get(i).getEname() + ":" + "   Ʒ��:"
						+ equInfos.get(i).getEbrand() + "   ����:"
						+ equInfos.get(i).getEnumber();
			}

			device_picList.add("" + equInfos.get(i).getHeadimage());

		}
		gallaryAdapter.notifyDataSetChanged();
		equipment_detail_txt.setText("" + udevice);

	}

	private void dectRelationship() {
		if (TextUtils.isEmpty(id)) {
			return;
		}

		if (!id.equals(LoginActivity.id)) {
			if (isexit(id)) {
				relation.setText(R.string.isfriend);
				addfriend.setVisibility(View.GONE);
			} else {
				relation.setText(R.string.nofriend);
				addfriend.setVisibility(View.VISIBLE);
			}
		} else {
			relationship_lay.setVisibility(View.GONE);
		}
	}

	protected void initHisPhoto() {
		// TODO Auto-generated method stub
		// his_photo_gridview = (GridView)
		// findViewById(R.id.his_photo_gridview);
		// mood_listItem.clear();
		// mood_listItem =get_list_mood_image();
		// phogridAdapter = new HisDeviceGridAdapter(mood_listItem,
		// this);
		// his_photo_gridview.setAdapter(phogridAdapter);
		ge_ren_dong_tai = (RelativeLayout) findViewById(R.id.ge_ren_dong_tai);
		// �������������ϵ���б�
		ge_ren_dong_tai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent4 = new Intent(PersonalPage.this, HisDynamic.class);
				intent4.putExtra("id", id);
				intent4.putExtra("username", "" + uname);
				startActivity(intent4);
			}
		});
	}

}
