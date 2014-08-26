package com.example.projectcircle;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.projectcircle.adpter.HomeAdapter;
import com.example.projectcircle.app.MyApplication;
import com.example.projectcircle.bean.MyPersonBean;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.complete.CompleteCommercial;
import com.example.projectcircle.complete.CompleteDriver;
import com.example.projectcircle.complete.CompleteInfo;
import com.example.projectcircle.complete.CompleteMaster;
import com.example.projectcircle.group.GroupPage;
import com.example.projectcircle.personal.PersonalPage;
import com.example.projectcircle.util.DistentsUtil;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;


/**
 * �����渽����ѯ
 * 
 * @author Walii
 * @version 2014.3.18
 */
@SuppressLint("CutPasteId")
public class HomeActivity extends Activity {
	String id;
	/**
	 * ������Ϣ
	 * 
	 */
	LinearLayout myinfo;
	TextView uname_txt, utype_txt, ucity_txt, udevice_txt, uaddress_txt,
			ucontent_txt;
	ImageView uheadimage;
	String uname, uheadimg,  ucity, udevice, ucontent;
	public static String utype;
	/**
	 * Ⱥ����Ϣ
	 * 
	 */
	// ����Ⱥ��Ϳ�����Ⱥ��
	LinearLayout neargroup, areagroup;
	public static double latitude;
	public static double longitude;
	public static int radius;// �뾶
	public static String rplace;// ��λȫ��ַ
	// ����Ⱥ����Ϣ
	TextView ng_nearnum_txt, ng_total_txt, ng_address_txt;
	int ng_nearnum;
	String ng_total;
	// ������Ⱥ����Ϣ
	TextView area_num_txt;
	String area_num;
	/**
	 * ��������Ϣ
	 * 
	 */
	public ArrayList<UserInfo> usersList;
	/**
	 * ListView
	 * 
	 */
	private static final String TAG = "HomeActivity";
	ListView listview;
	HomeAdapter myAdapter;
	View headview;

	/**
	 * ����Button
	 * 
	 */

	/**
	 * ��λ
	 * 
	 */
	private LocationClient mLocationClient;
	private MyBDLocationListener mBDLocationListener;
	String province;
	String city;
	String district;

	// ͼƬ����
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	Context context;
	SharedPreferences sharedPreferences;
	private String hobby;
	private String info;
	private String userlocation;
	private TextView search;
//	private PopupWindow mPop;
	private CheckBox all_user,fellow_villager,owner,businesses,a_driver,all_machine,a_machine_lord,a_dump_truck,a_loader,a_scooter;
	private Button sure,cancel;;
	private CheckBox professional;
	private String addr,type,equ;
	 AlertDialog show ;
	//��������Ҫ���ҵļ���
	private String myaddr;
	
	public static String myUserhaedurl="";
	private double commercialLat;
	private double commercialLon;
	private Object distance;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// listview��ʼ�� ����ͷ��view �������� ��Ϊ�˱��� �ڸ��¶�λ��ʱ�� �����ظ�����
		listview = (ListView) findViewById(R.id.main_listview);
		headview = this.getLayoutInflater().inflate(R.layout.headview, null);
		listview.addHeaderView(headview);
		initFilter();
		initView();
		
		initLocation();
		
		id = LoginActivity.id;
		getUserInfo();
		UserDetail(id);
		select();
		// ����ͼƬ���ؼ���ʾѡ�����һЩ���������ã�����doc�ĵ��ɣ�
		options = new DisplayImageOptions.Builder().cacheInMemory(true) // ����ͼƬʱ�����ڴ��м��ػ���
				.cacheInMemory(true)// �Ƿ񾏴涼�ȴ���
				.cacheOnDisc(true)// �Ƿ񾏴浽sd����
				.build();
	}

	private void initFilter() {
		IntentFilter filter = new IntentFilter();
		filter.addAction("yi.jing.wan.shan");			
		registerReceiver(msgReceiver, filter);
	}


	private void select() {
		// TODO Auto-generated method stub
		search = (TextView) findViewById(R.id.circle_right);
		search.setOnClickListener(new OnClickListener() {		

			public void onClick(View v) {
				LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

//				ViewGroup menuView = (ViewGroup) mLayoutInflater.inflate(
//						R.layout.near_select_pop_window, null, true);
//				mPop = new PopupWindow(menuView,
//						LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT,
//						true);
//				mPop.setAnimationStyle(R.style.AnimationPreview);
//				mPop.setOutsideTouchable(true);
//				mPop.setFocusable(false);
////				mPop.setWidth(android.widget.RelativeLayout.LayoutParams.MATCH_PARENT);
////				mPop.setHeight(600);
//				Drawable dr = getResources().getDrawable(R.drawable.bg2); // ȡ��ͼƬ��Դ
//				
//				mPop.setBackgroundDrawable(dr);			
////				mPop.showAtLocation(findViewById(R.id.linearLayout1), Gravity.CENTER, 0, 0);
////				mPop.showAsDropDown(findViewById(R.id.linearLayout1), 10, 10);
//				mPop.showAtLocation(search, Gravity.CENTER, 0, 0);
////				RelativeLayout upfriend = (RelativeLayout) findViewById(R.id.add_friend_Layout1);
////				mPop.showAsDropDown(upfriend, 420, 0);// ������ʾPopupWindow��λ��λ��View�����·���x,y��ʾ����ƫ����
////				mPop.showAtLocation(findViewById(R.id.textView1), Gravity.LEFT,
////						0, 0);// ����ĳ��ViewΪ�ο���,��ʾ����������parent���Ϊ�ο���λ����࣬ƫ��-90��
				LayoutInflater inflater = getLayoutInflater();
				  View menuView = inflater.inflate(R.layout.near_select_pop_window,
				    null);
				 show = new AlertDialog.Builder(HomeActivity.this).setView(menuView).show();
//				     .setPositiveButton("ȷ��", pListener());
//				    .setNegativeButton("ȡ��", null)
						

                all_user = (CheckBox) menuView
						.findViewById(R.id.all_user);
				fellow_villager = (CheckBox) menuView
						.findViewById(R.id.fellow_villager);
				professional = (CheckBox) menuView
				        .findViewById(R.id.professional);
				owner = (CheckBox) menuView
						.findViewById(R.id.owner);
				a_driver = (CheckBox) menuView
						.findViewById(R.id.a_driver);
				businesses = (CheckBox) menuView
						.findViewById(R.id.businesses);			
				all_machine = (CheckBox) menuView
						.findViewById(R.id.all_machine);
				a_machine_lord= (CheckBox) menuView
						.findViewById(R.id.a_machine_lord);
				a_dump_truck = (CheckBox) menuView
						.findViewById(R.id.a_dump_truck);
				a_loader = (CheckBox) menuView
						.findViewById(R.id.a_loader);
				a_scooter = (CheckBox) menuView
						.findViewById(R.id.a_scooter);	
				sure = (Button)menuView.findViewById(R.id.sure);
				cancel = (Button)menuView.findViewById(R.id.cancel);
				all_user.setOnClickListener(click_listener);//ȫ����ťѡ�е�ʱ�����簴ť����ѡ
				professional.setOnClickListener(click_listener);//ȫ����ťѡ�е�ʱ�������˾�����̼Ұ�ť����ѡ
				all_machine.setOnClickListener(click_listener);//ȫ����ťѡ��ʱ���ھ������ж����װ�ػ����峵��ť����ѡ
				sure.setOnClickListener(click_listener);
				cancel.setOnClickListener(click_listener);
			}
		});

	}



	private View.OnClickListener click_listener = new OnClickListener() {


		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.all_user:
				if(all_user.isChecked()){
					fellow_villager.setEnabled(false);
				}else{
					fellow_villager.setEnabled(true);
				}
					break;
		    case R.id.professional:
		    	if(professional.isChecked()){
	                   owner.setEnabled(false);
	                   a_driver.setEnabled(false);
	                   businesses.setEnabled(false);
					}else{
						owner.setEnabled(true);
		                a_driver.setEnabled(true);
		                businesses.setEnabled(true);
					}
					break;
		    case R.id.all_machine:
		    	if(all_machine.isChecked()){
					a_machine_lord.setEnabled(false);
					a_dump_truck.setEnabled(false);
					a_loader.setEnabled(false);
					a_scooter.setEnabled(false);
				}else{
					a_machine_lord.setEnabled(true);
					a_dump_truck.setEnabled(true);
					a_loader.setEnabled(true);
					a_scooter.setEnabled(true);
				}
				break;
	        case R.id.sure:
	        	if(all_user.isChecked()) {
	        		addr= "ȫ��";
	        		}
	        	else if(fellow_villager.isChecked()){
	        			addr= ucity ;//�Ҽ��������
	        		};
	        if(professional.isChecked()){
	        	type= "����,˾��,�̼�";
	        }else{
	        	if(owner.isChecked() && (!a_driver.isChecked()) &&(!businesses.isChecked()) ){
	        		type= "����";
	        	}else if(owner.isChecked() && a_driver.isChecked()&&(!businesses.isChecked())){
	        		type= "����,˾��";
	        	}else if(owner.isChecked() && a_driver.isChecked()&&businesses.isChecked()){
	        		type= "����,˾��,�̼�";
	        	}else if((!owner.isChecked()) && a_driver.isChecked()&&businesses.isChecked()){
	        		type= "˾��,�̼�";
	        	}else if((!owner.isChecked()) && (!a_driver.isChecked())&&businesses.isChecked()){
	        		type= "�̼�";
	        	}else if((!owner.isChecked()) && a_driver.isChecked()&&(!businesses.isChecked())){
	        		type= "˾��";
	        	}else if(a_machine_lord.isChecked() && (!a_driver.isChecked())&&businesses.isChecked()){
	        		type= "����,�̼�";
	        	}
	        	
	        };
	        if(all_machine.isChecked()){
	        	equ= "�ھ��,��ж��,װ�ػ�,�峵";
	        }else{
	        	if(a_machine_lord.isChecked() && a_dump_truck.isChecked() && a_loader.isChecked() && a_scooter.isChecked()){
	        		equ= "�ھ��,��ж��,װ�ػ�,�峵";
	        	}else if((!a_machine_lord.isChecked()) && a_dump_truck.isChecked() && a_loader.isChecked() && a_scooter.isChecked()){
	        		equ= "��ж��,װ�ػ�,�峵";
		        }else if(a_machine_lord.isChecked() && (!a_dump_truck.isChecked()) && a_loader.isChecked() && a_scooter.isChecked()){
		        	equ= "�ھ��,װ�ػ�,�峵";
		        }else if(a_machine_lord.isChecked() && a_dump_truck.isChecked() && (!a_loader.isChecked()) && a_scooter.isChecked()){
		        	equ= "�ھ��,��ж��,�峵";
		        }else if(a_machine_lord.isChecked() && a_dump_truck.isChecked() && a_loader.isChecked() && (!a_scooter.isChecked())){
		        	equ= "�ھ��,��ж��,װ�ػ�";
		        }else if((!a_machine_lord.isChecked()) && (!a_dump_truck.isChecked()) && a_loader.isChecked() && (!a_scooter.isChecked())){
		        	equ= "װ�ػ�,�峵";
		        }else if(a_machine_lord.isChecked() && (!a_dump_truck.isChecked()) && (!a_loader.isChecked()) && a_scooter.isChecked()){
		        	equ= "�ھ��,�峵";
		        }else if(a_machine_lord.isChecked() && a_dump_truck.isChecked() && (!a_loader.isChecked()) && (!a_scooter.isChecked())){
		        	equ= "�ھ��,��ж��";
		        }else if((!a_machine_lord.isChecked()) && a_dump_truck.isChecked() && (!a_loader.isChecked()) && (!a_scooter.isChecked())){
		        	equ= "��ж��,�峵";
		        }else if(a_machine_lord.isChecked() && (!a_dump_truck.isChecked()) && a_loader.isChecked() && (!a_scooter.isChecked())){
		        	equ= "�ھ��,װ�ػ�";
		        }else if((!a_machine_lord.isChecked()) && a_dump_truck.isChecked() && a_loader.isChecked() && (!a_scooter.isChecked())){
		        	equ= "��ж��,װ�ػ�";
		        }else if(a_machine_lord.isChecked() && (!a_dump_truck.isChecked()) && (!a_loader.isChecked()) && (!a_scooter.isChecked())){
		        	equ= "�ھ��";
		        }else if((!a_machine_lord.isChecked()) && a_dump_truck.isChecked() && (!a_loader.isChecked()) && (!a_scooter.isChecked())){
		        	equ= "��ж��";
		        }else if((!a_machine_lord.isChecked()) && (!a_dump_truck.isChecked()) && a_loader.isChecked() && (!a_scooter.isChecked())){
		        	equ= "װ�ػ�";
		        }else if((!a_machine_lord.isChecked()) && (!a_dump_truck.isChecked()) && (!a_loader.isChecked()) && a_scooter.isChecked()){
		        	equ= "�峵";
		        }
	        };
				userFilter(addr, type, equ);
				//����һ�飬����������壬�ٴ�ɸѡ������ �ϴ�ɸѡ�ĺۼ�
				addr = "ȫ��";
				type = "ȫ��";
				equ = "ȫ��";
				show.dismiss();
				break;
	        case R.id.cancel:
	        	show.dismiss();
				break;
			default:
				break;
			}
		}
	};
	private String uid;
   //ɸѡ���뿴�����û���ְҵ���豸

	private void userFilter(String address,String type,String equ) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			public void onSuccess(String response) {
				Log.i(TAG, "ɸѡ:"+response);
				System.out.println(response);	
				try {
					JSONObject	obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {						
						parseuserFilter(response);

						Log.i(TAG, "��������:"+getList());
						myAdapter = new HomeAdapter(getList(), HomeActivity.this);
						listview.setAdapter(myAdapter);					
					} else {
						ToastUtils.showShort(getApplicationContext(),
								"������Ч��");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			@SuppressWarnings("deprecation")
			@Override
			public void onFailure(Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(error);
			}
		};
		MyHttpClient myhttpclient = new MyHttpClient();
		myhttpclient.UserFilter(address,type,equ,latitude,longitude, res);
	}
  //��ɸѡ���뿴�����û���ְҵ���豸������response�Ľ���
	private void parseuserFilter(String response) {
		// TODO Auto-generated method stub
		
		try {
			JSONObject result = new JSONObject(response);
			Log.i("parseuserFilter response",response+"");
			JSONObject obj = result.getJSONObject("users");
			usersList = new ArrayList<UserInfo>();
			JSONArray json = obj.getJSONArray("resultlist");
			int length = json.length();
			System.out.println("length==" + length);
			for (int i = 0; i < length; i++) {
				UserInfo user = new UserInfo();
				obj = json.getJSONObject(i);
				user.setId(obj.getString("id"));
				user.setTel(obj.getString("tel"));
				user.setUsername(obj.getString("username"));
				user.setType(obj.getString("type"));
				user.setAddress(obj.getString("address"));
				user.setEquipment(obj.getString("equipment"));
				user.setSign(obj.getString("sign"));
				user.setHeadimage(obj.getString("headimage"));
				user.setAccept(obj.getString("accept"));
				try {
					user.setLat(obj.getDouble("commercialLat"));
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					user.setLon(obj.getDouble("commercialLon"));
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				usersList.add(user);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Log.w(TAG, "���������쳣");
		}
	}
	/**
	 * ��ʼ����λ
	 * 
	 */
	private void initLocation() {
		// TODO Auto-generated method stub
		mLocationClient = new LocationClient(this.getApplicationContext());

		mBDLocationListener = new MyBDLocationListener();
		////ע��λ�ü�����
		mLocationClient.registerLocationListener(mBDLocationListener);

		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);//���ö�λģʽ,//Hight_Accuracy�߾��ȡ�Battery_Saving�͹��ġ�Device_Sensors���豸(GPS)
		option.setOpenGps(true);// ��gps,�����Ƿ��gps��ʹ��gpsǰ�����û�Ӳ����gps��Ĭ���ǲ���gps�ġ�
		option.setScanSpan(600000);// ��λ��ʱ��������λ��ms
		// ��Ҫ��ַ��Ϣ������Ϊ�����κ�ֵ��string���ͣ��Ҳ���Ϊnull��ʱ������ʾ�޵�ַ��Ϣ��
		option.setAddrType("all");

		// �����Ƿ񷵻�POI�ĵ绰�͵�ַ����ϸ��Ϣ��Ĭ��ֵΪfalse����������POI�ĵ绰�͵�ַ��Ϣ��
		option.setPoiExtraInfo(true);

		// ���ò�Ʒ�����ơ�ǿ�ҽ�����ʹ���Զ���Ĳ�Ʒ�����ƣ����������Ժ�Ϊ���ṩ����Ч׼ȷ�Ķ�λ����
		option.setProdName("ͨ��GPS��λ�ҵ�ǰ��λ��");

		// ��ѯ��Χ��Ĭ��ֵΪ500�����Ե�ǰ��λλ��Ϊ���ĵİ뾶��С��
		option.setPoiDistance(600);
		// �������û��涨λ����
		option.disableCache(true);
		// ����ϵ���ͣ��ٶ��ֻ���ͼ����ӿ��е�����ϵĬ����bd09ll
		option.setCoorType("bd09ll");
		// �������ɷ��ص�POI������Ĭ��ֵΪ3������POI��ѯ�ȽϺķ�������������෵�ص�POI�������Ա��ʡ������
		option.setPoiNumber(3);
		// ���ö�λ��ʽ�����ȼ���
		// ��gps���ã����һ�ȡ�˶�λ���ʱ�����ٷ�����������ֱ�ӷ��ظ��û����ꡣ���ѡ���ʺ�ϣ���õ�׼ȷ����λ�õ��û������gps�����ã��ٷ����������󣬽��ж�λ��
		option.setPriority(LocationClientOption.GpsFirst);
		mLocationClient.setLocOption(option);
		mLocationClient.start();

	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//NearUser(id, radius, latitude, longitude);
	}

	private void initView() {
		// TODO Auto-generated method stub

		
		myinfo = (LinearLayout) findViewById(R.id.myinfo_layout);
		uname_txt = (TextView) findViewById(R.id.main_my_name);
		utype_txt = (TextView) findViewById(R.id.main_my_career);
		ucity_txt = (TextView) findViewById(R.id.main_my_native);
		udevice_txt = (TextView) findViewById(R.id.main_my_device);
		//uaddress_txt = (TextView) findViewById(R.id.main_my_time);
		ucontent_txt = (TextView) findViewById(R.id.main_my_content);
		uheadimage = (ImageView) findViewById(R.id.main_my_head_img);
		
		
		neargroup = (LinearLayout) findViewById(R.id.neargroup_layout);		
		neargroup.setOnClickListener(listener);

		ng_nearnum_txt = (TextView) findViewById(R.id.ng_nearnum);
		ng_total_txt = (TextView) findViewById(R.id.ng_total);
		ng_address_txt = (TextView) findViewById(R.id.ng_address);
		
		myinfo.setOnClickListener(listener);
		
		myAdapter = new HomeAdapter(getList(), this);
		//ˢ��
		myAdapter.notifyDataSetChanged();
		listview.setAdapter(myAdapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeActivity.this,
						PersonalPage.class);
				intent.putExtra("id", usersList.get(arg2 - 1).getId());
				intent.putExtra("type", usersList.get(arg2 - 1).getType());
				intent.putExtra("time",usersList.get(arg2 - 1).getLastlogintime());
				intent.putExtra("lat",usersList.get(arg2 - 1).getLat());
				intent.putExtra("lon",usersList.get(arg2 - 1).getLon());
				startActivity(intent);
				// finish();
			}
		});
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
				Log.i(TAG, "����:" + response);
				parseUserDetail(response);
				//�ж�ͷ���ġ���ǰ���ϲ����������ƺ������ڽ��ѡ��������Ƿ���ʾ
				WhetherDisplayPrompt();
				initUser();
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.UserDetail(id, res);
	}

	public void parseUserDetail(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("user");
			uid = obj.getString("id");	
			uname = obj.getString("username");
			utype = obj.getString("type");			
			ucity = obj.getString("address");
			udevice = obj.getString("equipment");
			ucontent = obj.getString("sign");//����ǩ��
			uheadimg = obj.getString("headimage");
			rplace = obj.getString("rplace");//����û�ĵط�
			userlocation = obj.getString("place");//����û�ĵط�
			hobby = obj.getString("hobby");//��Ȥ����
		    info = obj.getString("info");//������Ϣ

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ��ȡ��Ϣ
	 */
	private void getUserInfo(){
		MyPersonBean myPersonBean=MyApplication.getMyPersonBean();
		if (myPersonBean==null) {
			return;
		}
		
		uname = myPersonBean.getUsername();
		utype = myPersonBean.getType();
		ucity = myPersonBean.getAddress();
		udevice = myPersonBean.getEquipment();
		ucontent = myPersonBean.getSign();
		uheadimg = myPersonBean.getHeadimage();
		rplace = myPersonBean.getRplace();//����û�ĵط�
		userlocation = myPersonBean.getPlace();//����û�ĵط�
		hobby = myPersonBean.getHobby();//��Ȥ����
	    info = myPersonBean.getInfo();//������Ϣ
	    WhetherDisplayPrompt();
		initUser();
		
	}
	
	/**
	 * Ⱥ����Ϣ ����ͽ���
	 * 
	 */
	private void NearGroup(int radius, double lat, double lon) {
		// TODO Auto-generated method stub

		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "����:" + response);
				parseNearGroup(response);
				initGroup();
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.NearGroup(radius, lat, lon, res);
	}

	public void parseNearGroup(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("nearGroup");
			JSONArray json = obj.getJSONArray("resultlist");
			Log.i("parseNearGroup json---",json+"");			
			ng_nearnum = json.length();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ĳ�����Ⱥ���� ����ͽ���
	 * 
	 */
	private void CountGroup(String key) {
		// TODO Auto-generated method stub
		
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "����:" + response);
				parseCountGroup(response);
				initGroup();
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.CountGroup(key, res);
	}

	public void parseCountGroup(String response) {
		try {
			JSONObject result = new JSONObject(response);
			ng_total = result.getString("groupcount");
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ������Ⱥ�� ����ͽ���
	 * 
	 */
	private void GroupList() {
		// TODO Auto-generated method stub

		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, "����:" + response);
				parseGroupList(response);
				initGroup();
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.GroupList(res);

	}

	public void parseGroupList(String response) {
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("groups");
			area_num = obj.getString("totalrecord");
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ��������Ϣ ����ͽ���
	 * 
	 */
	private void NearUser(String id, int radius, double lat, double lon) {
		// TODO Auto-generated method stub

		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("NearUser��response", "����:" + response);
				parseNearUser(response);
				
				
				
//				initView();
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.NearUser(id, radius, lat, lon, res);

	}

	public void parseNearUser(String response) {
		
		try {
			JSONObject result = new JSONObject(response);
			JSONObject obj = result.getJSONObject("nearuser");
			Log.i("nearuser��response", obj+"");
			usersList = new ArrayList<UserInfo>();
			JSONArray json = obj.getJSONArray("resultlist");
			int length = json.length();
			System.out.println("length==" + length);
			for (int i = 0; i < length; i++) {
				if(!(json.getJSONObject(i).getString("id").equals(LoginActivity.id))){
				UserInfo user = new UserInfo();
				obj = json.getJSONObject(i);
				user.setId(obj.getString("id"));
				user.setTel(obj.getString("tel"));
				user.setUsername(obj.getString("username"));
				user.setSign(obj.getString("sign"));
				user.setType(obj.getString("type"));				
				user.setAddress(obj.getString("address"));
				user.setEquipment(obj.getString("equipment"));
				user.setSign(obj.getString("sign"));
				user.setHeadimage(obj.getString("headimage"));
				user.setAccept(obj.getString("accept"));
				user.setLastlogintime(obj.getString("lastlogintime"));
				try {
					user.setLat(obj.getDouble("commercialLat"));
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					user.setLon(obj.getDouble("commercialLon"));
				} catch (Exception e) {
					// TODO: handle exception
				}				
				usersList.add(user);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			Log.w(TAG, "���������쳣");
		}

		Log.i(TAG, "��������:"+getList());
		myAdapter = new HomeAdapter(getList(), HomeActivity.this);
		listview.setAdapter(myAdapter);
		
	}

	/**
	 * ������Ϣ ��ʼ��
	 * 
	 */
	private void initUser() {
		// TODO Auto-generated method stub
		

		uname_txt.setText(uname);
		utype_txt.setText(utype);
		//��ַֻ��ȡʡ����Ϊʡ�����ɿո�ֿ��ģ����Խ�ȡ��һ���ո�ǰ�����ݼ�Ϊʡ
		if(!ucity.equals("null")){
		String arrays[] = ucity.split(" ");	
		ucity_txt.setText(arrays[0]);}
		udevice_txt.setText(udevice);
//		uaddress_txt.setText(rplace);
		ucontent_txt.setText("(" + ucontent + ")");
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(MyHttpClient.IMAGE_URL + uheadimg, uheadimage,
				options, animateFirstListener);
		
		myUserhaedurl=MyHttpClient.IMAGE_URL + uheadimg;

		
	}

	/**
	 * Ⱥ����Ϣ ��ʼ��
	 * 
	 */
	private void initGroup() {
		// TODO Auto-generated method stub
		

		ng_nearnum_txt.setText("��������" + ng_nearnum + "��Ⱥ��");
		ng_total_txt.setText("����Լ��" + ng_total + "��Ⱥ��");
		ng_address_txt.setText(rplace);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.myinfo_layout:
				Intent intent = new Intent(HomeActivity.this, PersonalPage.class);
				intent.putExtra("id", id);
				intent.putExtra("type", utype);
//				intent.putExtra("time",);
				intent.putExtra("lat",latitude);
				intent.putExtra("lon",longitude);
				startActivity(intent);
				// finish();
				break;
			case R.id.neargroup_layout:
				Intent intent2 = new Intent(HomeActivity.this, GroupPage.class);
				startActivity(intent2);
				// finish();
				break;
		
			}
		}
	};


	private ArrayList<HashMap<String, Object>> getList() {
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		// TODO Auto-generated method stub
		if(usersList != null){
		for (int i = 0; i < usersList.size(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", usersList.get(i).getId());
			map.put("tel", usersList.get(i).getTel());
			map.put("name", usersList.get(i).getUsername());
			map.put("type", usersList.get(i).getType());
			map.put("address", usersList.get(i).getAddress());
			map.put("equipment", usersList.get(i).getEquipment());
			map.put("sign", usersList.get(i).getSign());
			map.put("headimage", usersList.get(i).getHeadimage());
			map.put("lastlogintime", usersList.get(i).getLastlogintime());
			map.put("accept", usersList.get(i).getAccept());
			commercialLat = usersList.get(i).getLat();
			commercialLon = usersList.get(i).getLon();
			//�����Һͺ���֮��ľ���
			distance = DistentsUtil.GetDistance(commercialLat,commercialLon,
					latitude,longitude);
			map.put("distance", distance);
			listItem.add(map);
		}
		}
//		   Collections.sort(listItem, new Comparator<HashMap<String,Object>>(){  
//			   
//	           @Override  
//	           public int compare(HashMap<String, Object> arg0,HashMap<String, Object> arg1) {  
//	               // TODO Auto-generated method stub  
//	               return ((String) arg0.get("name")).compareTo((String) arg1.get("name"));  
//	           }  
//	       }); 
		return listItem;
	}

	/**
	 * ��дBDLocationListener�ӿڵ�ʵ���࣬�����첽���صĶ�λ������첽���ص�POI��ѯ�����
	 * 
	 */
	final class MyBDLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			province = location.getProvince(); // ��ȡʡ����Ϣ
			city = location.getCity(); // ��ȡ������Ϣ
			district = location.getDistrict(); // ��ȡ������Ϣ
			rplace = location.getAddrStr();// ȫ��ַ
			Log.i(TAG, "province = " + province);
			Log.i(TAG, "city = " + city);
			Log.i(TAG, "district = " + district);

			latitude = location.getLatitude();
			longitude = location.getLongitude();
			//radius = (int) location.getRadius();
			Log.i(TAG, "latitude = " + latitude);
			Log.i(TAG, "longitude = " + longitude);
			Log.i(TAG, "radius = " + radius);

			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nProvince : ");
			sb.append(location.getProvince());
			sb.append("\ncity : ");
			sb.append(location.getCity());
			sb.append("\nDistrict : ");
			sb.append(location.getDistrict());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
			}
			Log.i(TAG, sb + "");	
			// ��������ͷ��view ���ں���
			//UserDetail(id);
			NearUser(id, radius, latitude, longitude);// �����б� һ��Ҫ����ǰ��
			NearGroup(radius, latitude, longitude);
			CountGroup(city);
			GroupList();	
		}

		@Override
		public void onReceivePoi(BDLocation poiLocation) {

			// �����¸��汾��ȥ��poi����
			if (poiLocation == null) {
				return;
			}
			StringBuffer sb = new StringBuffer(256);
			sb.append("Poi time : ");
			sb.append(poiLocation.getTime());
			sb.append("\nerror code : ");
			sb.append(poiLocation.getLocType());
			sb.append("\nProvince : ");
			sb.append(poiLocation.getProvince());
			sb.append("\ncity : ");
			sb.append(poiLocation.getCity());
			sb.append("\nDistrict : ");
			sb.append(poiLocation.getDistrict());
			sb.append("\nlatitude : ");
			sb.append(poiLocation.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(poiLocation.getLongitude());
			sb.append("\nradius : ");
			sb.append(poiLocation.getRadius());
			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(poiLocation.getAddrStr());
			}
			if (poiLocation.hasPoi()) {
				sb.append("\nPoi:");
				sb.append(poiLocation.getPoi());
			} else {
				sb.append("noPoi information");
			}
			Log.i(TAG, sb + "");
		}
	}
	//�ж�ͷ���ġ���ǰ���ϲ����������ƺ������ڽ��ѡ��������Ƿ���ʾ
	private void WhetherDisplayPrompt() {
		// TODO Auto-generated method stub
		RelativeLayout visableOrNot = (RelativeLayout)findViewById(R.id.bujuxianshi);
		if((userlocation == null ||userlocation.equals("")) && (hobby == null|| hobby.equals(""))&& (info == null|| info.equals("")) && (ucontent == null ||ucontent.equals(""))){			
			visableOrNot.setVisibility(View.VISIBLE);//��ʾ
			visableOrNot.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (utype.equals("����")) {
						Intent intent2 = new Intent(HomeActivity.this,
								CompleteMaster.class);
						intent2.putExtra("uid", uid);
						startActivity(intent2);
//						finish();
					} else if (utype.equals("˾��")) {
						Intent intent3 = new Intent(HomeActivity.this,
								CompleteDriver.class);
						intent3.putExtra("uid", uid);
						startActivity(intent3);
//						finish();
					} else if (utype.equals("�̼�")) {
						Intent intent4 = new Intent(HomeActivity.this,
								CompleteCommercial.class);
						intent4.putExtra("uid", uid);
						startActivity(intent4);
//						finish();
					} else {
						Intent intent5 = new Intent(HomeActivity.this,
								CompleteInfo.class);
						intent5.putExtra("uid", uid);
						startActivity(intent5);
//						finish();
					}
				}
			});
		}else{
			visableOrNot.setVisibility(View.GONE);//���� �����Ҳ�ռ�ý���ռ�
		}
	}
	private BroadcastReceiver msgReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			UserDetail(id);
		}
	};

	@Override
	protected void onDestroy() {
		if (mLocationClient != null && mLocationClient.isStarted()) {
			if (mBDLocationListener != null) {
				mLocationClient.unRegisterLocationListener(mBDLocationListener);
			}

			mLocationClient.stop();
			mLocationClient = null;
		}
		unregisterReceiver(msgReceiver);
		super.onDestroy();
	}

}
