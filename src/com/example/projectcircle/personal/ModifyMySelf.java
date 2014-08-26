package com.example.projectcircle.personal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.SiginFragment1;
import com.example.projectcircle.SiginFragment2;
import com.example.projectcircle.SiginFragment3;
import com.example.projectcircle.SiginFragment4;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.home.HomeSecActivity;
import com.example.projectcircle.util.ImageUtil;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

/**
 * �޸ĸ�����Ϣ����
 * 
 * @author Walii
 * @version 2014.3.18
 */
@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class ModifyMySelf extends TabActivity {
	private static final String TAG = "ModifyMySelf";
	UserInfo info;
	String equipment;
	String business;
	/**
	 * ������Ϣ
	 */
	EditText realname_edit, age_edit, sign_edit, intro_edit;
	TextView hometown_edit;
	ImageView myhead;

	String realname, hometown, age, sign, intro;
	// �ϴ�ͷ������Ҫ��
	private static final int REQUEST_CAMERA = 1;
	int current_index;// ��ǵ�ǰ���ڴ洢�ĸ�ͷ�� �豸�����ˣ�
	private Bitmap myBitmap;
	private byte[] mContent;
	String returnString;
	String headimage;
	/**
	 * ѡ��ְҵ
	 */
	private TabHost tabhost;
	EditText c_content;
	String crr_content;
	// ְҵ��ѡ��
	public static String type = "����";
	public static String type_content;
	/**
	 * ���ܵ绰
	 */
	RadioGroup call_group;
	RadioButton call_radio1, call_radio2;
	String accept = "0";
	/**
	 * ����Button
	 */
	Button back, submit;
	/**
	 * ѡ�����
	 */
	private ArrayAdapter<String> adapter1;
	private ArrayAdapter<String> adapter2;
	private ArrayAdapter<String> adapter3;
	private static final String[] m = { "����", "�ӱ�", "���", "�Ϻ�", "�Ĵ�" };
	String ht_1, ht_2, ht_3;
	private String id;

	private ProgressDialog progressDialog;
	private String uname;
	private String utype;
	private String uinfo;
	private String ucity;
	private String uheadimg;
	private String uaccept;
	private String uage;
	private String persign;
	// ͼƬ����
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private String address;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.modify_myself);		
		// ����ͼƬ���ؼ���ʾѡ�����һЩ���������ã�����doc�ĵ��ɣ�
		options = new DisplayImageOptions.Builder()
				.cacheInMemory(true) // ����ͼƬʱ�����ڴ��м��ػ���
				.cacheInMemory(true)// �Ƿ񾏴涼�ȴ���
				.cacheOnDisc(true)// �Ƿ񾏴浽sd����
				.build();
		id = LoginActivity.id;
		Intent intent =getIntent();
		uname = intent.getStringExtra("uname");
		utype = intent.getStringExtra("utype");
		ucity = intent.getStringExtra("ucity");
		uinfo = intent.getStringExtra("info");
		address = intent.getStringExtra("address");
		uheadimg = intent.getStringExtra("uheadimg");
		uage = intent.getStringExtra("age");
		uaccept = intent.getStringExtra("accept");
		persign = intent.getStringExtra("persign");
		Log.i("ModifyMyself address", address);
		progressDialog=new ProgressDialog(this);
		initInfo();
		initCareer();
		initCall();
		initBtn();
	}

	private void initInfo() {
		// TODO Auto-generated method stub

		myhead = (ImageView) findViewById(R.id.modify_head_headimg);
		realname_edit = (EditText) findViewById(R.id.modify_realname);
		age_edit = (EditText) findViewById(R.id.modify_age);
		sign_edit = (EditText) findViewById(R.id.modify_qianming);
		intro_edit = (EditText) findViewById(R.id.modify_content);
		hometown_edit = (TextView) findViewById(R.id.modify_hometown);
		realname_edit.setText(uname);
		age_edit.setText(uage);
		sign_edit.setText(persign);	
		intro_edit.setText(uinfo);
		hometown_edit.setText(address);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(MyHttpClient.IMAGE_URL + uheadimg, myhead,
				options, animateFirstListener);
		myhead.setOnClickListener(listener);
		hometown_edit.setOnClickListener(listener);
		hometown_edit.setText(hometown);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.modify_hometown:
				Intent intent =new Intent();
				intent.setClass(ModifyMySelf.this, HomeSecActivity.class);
				startActivityForResult(intent, 10);
//				HometownDialog();
				break;
			case R.id.modify_head_headimg:
				MyDialog();
				break;
			default:
				break;
			}
		}

	};

	private void initCareer() {
		// TODO Auto-generated method stub
		c_content = (EditText) findViewById(R.id.sigin_carrer);
		tabhost = this.getTabHost();

		TabSpec tabSpec1 = tabhost.newTabSpec("tab1").setIndicator("tab1")
				.setContent(new Intent(this, SiginFragment1.class));
		TabSpec tabSpec2 = tabhost.newTabSpec("tab2").setIndicator("tab2")
				.setContent(new Intent(this, SiginFragment2.class));
		TabSpec tabSpec3 = tabhost.newTabSpec("tab3").setIndicator("tab3")
				.setContent(new Intent(this, SiginFragment3.class));
		TabSpec tabSpec4 = tabhost.newTabSpec("tab4").setIndicator("tab4")
				.setContent(new Intent(this, SiginFragment4.class));

		tabhost.addTab(tabSpec1);
		tabhost.addTab(tabSpec2);
		tabhost.addTab(tabSpec3);
		tabhost.addTab(tabSpec4);

		RadioGroup rg = (RadioGroup) this.findViewById(R.id.sigin_radiogroup);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.sigin_radiobtn1:
					type = "����";
					c_content.setVisibility(View.INVISIBLE);
					tabhost.setCurrentTabByTag("tab1");
					break;
				case R.id.sigin_radiobtn2:
					type = "˾��";
					c_content.setVisibility(View.INVISIBLE);
					tabhost.setCurrentTabByTag("tab2");
					break;
				case R.id.sigin_radiobtn3:
					type = "�̼�";
					c_content.setVisibility(View.INVISIBLE);
					tabhost.setCurrentTabByTag("tab3");
					break;
				case R.id.sigin_radiobtn4:
					type = "����";
					c_content.setVisibility(View.VISIBLE);
					type_content = c_content.getText().toString().trim();
					tabhost.setCurrentTabByTag("tab4");
					break;

				}
			}
		});

	}

	private void initCall() {
		// TODO Auto-generated method stub
		call_group = (RadioGroup) findViewById(R.id.call_radiogroup);
		call_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int radioButtonid = group.getCheckedRadioButtonId();
				switch (radioButtonid) {
				case R.id.call_radiobtn1:
					accept = "0";
					break;
				case R.id.call_radiobtn2:
					accept = "1";
					break;
				default:
					break;
				}
			}
		});
	}

	private void initBtn() {

		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.modify_head_left);
		submit = (Button) findViewById(R.id.modify_head_submit);
		back.setOnClickListener(Btnlistener);
		submit.setOnClickListener(Btnlistener);
	}

	private View.OnClickListener Btnlistener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.modify_head_left:
				finish();
				break;
			case R.id.modify_head_submit:
				// Intent intent2 = new Intent(SiginActivity.this,
				// SiginSuccess.class);
				// startActivity(intent2);
				// finish();
				submit();
				submitHead();
				break;
			default:
				break;
			}
		}


	};

	/**
	 * �ύ״̬
	 */
	private void submit() {
		// TODO Auto-generated method stub
		initData();
		realname = realname_edit.getText().toString().trim();
		age = age_edit.getText().toString().trim();
		sign = sign_edit.getText().toString().trim();
		intro = intro_edit.getText().toString().trim();
		hometown = hometown_edit.getText().toString().trim();
		if (TextUtils.isEmpty(age)) {
			ToastUtils.showShort(getApplicationContext(), "���䲻��Ϊ��!");
			return;
		}
		if (TextUtils.isEmpty(realname)) {
			ToastUtils.showShort(getApplicationContext(), "��ʵ��������Ϊ��!");
			return;
		}
		if (realname_edit.length() < 2 && realname_edit.length() > 6) {
			ToastUtils.showShort(getApplicationContext(), "��ʵ����������2-6λ����!");
			return;
		}
		if (TextUtils.isEmpty(hometown)) {
			ToastUtils.showShort(getApplicationContext(), "���粻��Ϊ��!");
			return;
		}
//		if (!TextUtils.isEmpty(age) && !TextUtils.isEmpty(realname)
//				&& !TextUtils.isEmpty(hometown) &&realname_edit.length() >= 2 && realname_edit.length() <= 6) {
			doSubMit(id,realname,age,sign,intro,type,equipment,accept,hometown);
//		}
	}

	//�ύͷ��
	private void submitHead() {
		// TODO Auto-generated method stub
		
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				headimage = "";
				if (myBitmap != null) {
					headimage = URLEncoder.encode(ImageUtil.bitmaptoString(myBitmap));
					headimage = ImageUtil.bitmaptoString(myBitmap);
					postHeadImage(id, headimage);
				}
			}
		}.start();
		
		
	}
	private void postHeadImage(String id2, String headimage2) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			
			
			
			public void onSuccess(String response) {
				// System.out.println(response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					Log.i("response-----result", obj.getInt("result") + "");
					if (obj.getInt("result") == 1) {
						//myhead.setImageBitmap(myBitmap);
					} else {

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.postHeadImage(id, headimage, res);
	}

	private void doSubMit(String id, String username,String age,String sign,
			String info,String type,String equipment,String accept, String address) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler resj = new AsyncHttpResponseHandler() {
			
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				Log.i("on start", "start");
				progressDialog.setMessage(getString(R.string.submiting));
				progressDialog.show();
			}
			
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				progressDialog.dismiss();
			}
			
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("���أ�Submit", response);			
				Toast.makeText(getApplicationContext(), "������Ϣ���³ɹ���",
					     Toast.LENGTH_SHORT).show();					
				finish();
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.updateMyInfo(id, username, age, sign, info, type, equipment, accept, address, resj);
	}

	/**
	 * �����豸����
	 */
	private void initData() {
		// TODO Auto-generated method stub
		if (type.equals("����")) {
			String s = "";
			if (SiginFragment1.btn1.isChecked()) {
				if (s.isEmpty()) {
					s = s + SiginFragment1.device1;
				} else {
					s = s + "," + SiginFragment1.device1;
				}
			}
			if (SiginFragment1.btn2.isChecked()) {
				if (s.isEmpty()) {
					s = s + SiginFragment1.device2;
				} else {
					s = s + "," + SiginFragment1.device2;
				}
			}
			if (SiginFragment1.btn3.isChecked()) {
				if (s.isEmpty()) {
					s = s + SiginFragment1.device3;
				} else {
					s = s + "," + SiginFragment1.device3;
				}
			}
			if (SiginFragment1.btn4.isChecked()) {
				if (s.isEmpty()) {
					s = s + SiginFragment1.device4;
				} else {
					s = s + "," + SiginFragment1.device4;
				}
			}
			if (SiginFragment1.btn5.isChecked()) {
				SiginFragment1.device5 = SiginFragment1.d_content.getText()
						.toString().trim();
				if (s.isEmpty()) {
					s = s + SiginFragment1.device5;
				} else {
					s = s + "," + SiginFragment1.device5;
				}
			}
			equipment = s;
			Log.i("equipment", equipment);

		} else if (type.equals("˾��")) {
			equipment = SiginFragment2.equipment;

		} else if (type.equals("�̼�")) {
			String b = "";
			if (SiginFragment3.btn1.isChecked()) {
				if (b.isEmpty()) {
					b = b + SiginFragment3.device1;
				} else {
					b = b + "," + SiginFragment3.device1;
				}
			}
			if (SiginFragment3.btn2.isChecked()) {
				if (b.isEmpty()) {
					b = b + SiginFragment3.device2;
				} else {
					b = b + "," + SiginFragment3.device2;
				}
			}
			if (SiginFragment3.btn3.isChecked()) {
				if (b.isEmpty()) {
					b = b + SiginFragment3.device3;
				} else {
					b = b + "," + SiginFragment3.device3;
				}
			}
			if (SiginFragment3.btn4.isChecked()) {
				if (b.isEmpty()) {
					b = b + SiginFragment3.device4;
				} else {
					b = b + "," + SiginFragment3.device4;
				}
			}
			if (SiginFragment3.btn5.isChecked()) {
				SiginFragment3.device5 = SiginFragment3.d_content.getText()
						.toString().trim();
				if (b.isEmpty()) {
					b = b + SiginFragment3.device5;
				} else {
					b = b + "," + SiginFragment3.device5;
				}
			}
			equipment = b;
			Log.i("equipment", equipment);

			String c = "";
			if (SiginFragment3.btn_1.isChecked()) {
				if (c.isEmpty()) {
					c = c + SiginFragment3.busi1;
				} else {
					c = c + "," + SiginFragment3.busi1;
				}
			}
			if (SiginFragment3.btn_2.isChecked()) {
				if (c.isEmpty()) {
					c = c + SiginFragment3.busi2;
				} else {
					c = c + "," + SiginFragment3.busi2;
				}
			}
			if (SiginFragment3.btn_3.isChecked()) {
				if (c.isEmpty()) {
					c = c + SiginFragment3.busi3;
				} else {
					c = c + "," + SiginFragment3.busi3;
				}
			}
			if (SiginFragment3.btn_4.isChecked()) {
				if (c.isEmpty()) {
					c = c + SiginFragment3.busi4;
				} else {
					b = b + "," + SiginFragment3.busi4;
				}
			}
			business = c;
			Log.i("business", business);
		} else {
			business = SiginFragment4.yewu.getText().toString();
			Log.i("business", business);
		}
	}

	/**
	 * ����Dialog
	 */
	private void HometownDialog() {
		// TODO Auto-generated method stub
		// �����ļ�ת��Ϊview����
		LayoutInflater inflaterDl = LayoutInflater.from(this);
		LinearLayout layout = (LinearLayout) inflaterDl.inflate(
				R.layout.hometown, null);

		// �Ի���
		final Dialog dialog = new AlertDialog.Builder(ModifyMySelf.this)
				.create();
		dialog.setTitle("ѡ�����");
		dialog.show();
		dialog.getWindow().setContentView(layout);

		Spinner spinner1 = (Spinner) layout
				.findViewById(R.id.hometown_spinner_1);
		Spinner spinner2 = (Spinner) layout
				.findViewById(R.id.hometown_spinner_2);
		Spinner spinner3 = (Spinner) layout
				.findViewById(R.id.hometown_spinner_3);

		adapter1 = new ArrayAdapter<String>(ModifyMySelf.this,
				android.R.layout.simple_spinner_item, m);
		adapter2 = new ArrayAdapter<String>(ModifyMySelf.this,
				android.R.layout.simple_spinner_item, m);
		adapter3 = new ArrayAdapter<String>(ModifyMySelf.this,
				android.R.layout.simple_spinner_item, m);

		spinner1.setAdapter(adapter1);
		spinner2.setAdapter(adapter2);
		spinner3.setAdapter(adapter3);

		spinner1.setOnItemSelectedListener(spinnerlistener);
		spinner2.setOnItemSelectedListener(spinnerlistener);
		spinner3.setOnItemSelectedListener(spinnerlistener);

		// ȷ����ť
		Button btnOK = (Button) layout.findViewById(R.id.button_ok);
		btnOK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hometown = ht_1 + ht_2 + ht_3;
				hometown_edit.setText(hometown);
				dialog.dismiss();
				ToastUtils.showShort(getApplicationContext(), "ok");
			}
		});

	}

	private OnItemSelectedListener spinnerlistener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.hometown_spinner_1:
				ht_1 = arg0.getItemAtPosition(arg2).toString();
				break;
			case R.id.hometown_spinner_2:
				ht_2 = arg0.getItemAtPosition(arg2).toString();
				break;
			case R.id.hometown_spinner_3:
				ht_3 = arg0.getItemAtPosition(arg2).toString();
				break;
			default:
				break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	};

	/**
	 * �ϴ�ͷ��Dialog
	 */
	// ͼƬ�ϴ�ѡ��;��
	private void MyDialog() {
		final CharSequence[] items = { "���", "����" };
		AlertDialog dlg = new AlertDialog.Builder(ModifyMySelf.this)
				.setTitle("ѡ��ͼƬ")
				.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						// ����item�Ǹ���ѡ��ķ�ʽ��
						// ��items�������涨�������ַ�ʽ�����յ��±�Ϊ1���Ծ͵������շ���
						if (item == 1) {
							Intent getImageByCamera = new Intent(
									"android.media.action.IMAGE_CAPTURE");
							startActivityForResult(getImageByCamera,
									REQUEST_CAMERA);
						} else {
							Intent getImage = new Intent(
									Intent.ACTION_GET_CONTENT);
							getImage.addCategory(Intent.CATEGORY_OPENABLE);
							getImage.setType("image/jpeg");
							startActivityForResult(getImage, 0);
						}
					}
				}).create();
		dlg.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		ContentResolver resolver = getContentResolver();
		/**
		 * ��Ϊ���ַ�ʽ���õ���startActivityForResult������ �������ִ����󶼻�ִ��onActivityResult������
		 * ����Ϊ�����𵽵�ѡ�����Ǹ���ʽ��ȡͼƬҪ�����жϣ�
		 * �����requestCode��startActivityForResult����ڶ���������Ӧ
		 */
		if (requestCode == 0) {
			try {
				// ���ͼƬ��uri
				Uri originalUri = data.getData();
				// ��ͼƬ���ݽ������ֽ�����
				mContent = ImageUtil.readStream(resolver.openInputStream(Uri
						.parse(originalUri.toString())));
				// ���ֽ�����ת��ΪImageView�ɵ��õ�Bitmap����
				myBitmap = ImageUtil.getPicFromBytes(mContent, null);
				myBitmap = comp(myBitmap);
				myBitmap = ImageUtil.toRoundCorner(myBitmap, 20);
				// //�ѵõ���ͼƬ���ڿؼ�����ʾ
				if (current_index == 0) {
					myhead.setImageBitmap(myBitmap);
				} else {

				}

			} catch (Exception e) {
				// System.out.println(e.getMessage());
			}

		} else if (requestCode == REQUEST_CAMERA) {
			try {
				super.onActivityResult(requestCode, resultCode, data);
				Bundle extras = data.getExtras();
				myBitmap = (Bitmap) extras.get("data");
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				mContent = baos.toByteArray();
				myBitmap = ImageUtil.toRoundCorner(myBitmap, 20);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// ���������Ƭת��Բ����ʾ��Ԥ���ؼ���
			if (current_index == 0) {
				myhead.setImageBitmap(myBitmap);
			} else {

			}
		}else if (requestCode==10) {
			try{
			Bundle extras = data.getExtras();
			String home=extras.getString("home", "");
			hometown=home;
			hometown_edit.setText(hometown);
			}
			catch (Exception e) {  	          
	            e.printStackTrace(System.err);  
	        }

		}
		if (myBitmap != null) {
			ImageUtil.bitmaptoString(myBitmap);
		}
		// submit();
	}

	// ѹ��ͼƬ����
	private Bitmap comp(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// �ж����ͼƬ����1M,����ѹ������������ͼƬ��BitmapFactory.decodeStream��ʱ���
			baos.reset();// ����baos�����baos
			image.compress(Bitmap.CompressFormat.JPEG, 20, baos);// ����ѹ��50%����ѹ��������ݴ�ŵ�baos��
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// ��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// ���������ֻ��Ƚ϶���800*480�ֱ��ʣ����ԸߺͿ���������Ϊ
		float hh = 800f;// �������ø߶�Ϊ800f
		float ww = 480f;// �������ÿ��Ϊ480f
		// ���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��
		int be = 1;// be=1��ʾ������
		if (w > h && w > ww) {// �����ȴ�Ļ����ݿ�ȹ̶���С����
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// ����߶ȸߵĻ����ݿ�ȹ̶���С����
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// �������ű���
		// ���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return bitmap;// ѹ���ñ�����С���ٽ�������ѹ��
	}
}
