package com.example.projectcircle.group;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.util.ImageUtil;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class CreateGroup extends Activity {
	private static final String TAG = "CreateGroup";
	/**
	 * ����Button
	 */
	Button back, next;

	/**
	 * ͷ��ͻ�����Ϣ
	 */
	ImageView group_head;
	EditText group_name, group_intro;
	TextView group_place;
	String name, intro;
	String place;
	// �ϴ�ͷ������Ҫ��
	private static final int REQUEST_CAMERA = 1;
	private Bitmap myBitmap;
	private byte[] mContent;
	String returnString;
	String uid;
	String headimage;
	String limit = "10";
	/**
	 * ��λ
	 * 
	 */
	double latitude;
	double longitude;
	private LocationClient mLocationClient;
	private MyBDLocationListener mBDLocationListener;
	private friendBroadcastReceiver receiver;

	private String gid;
//��ȡʡ�ݡ����ء�������Ϣ
	private String province;
	private String city;
	private String district;

	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_group);
		initView();
		initBtn();
		initLoc();
		progressDialog=new ProgressDialog(this);
		//ע��㲥,���������Ϣ��һ���ͬ�⣬����ߺ����б����������Ӧ�ĺ���
		  IntentFilter filter = new IntentFilter();
	      filter.addAction("cn.abel.action.broadcast");
	      receiver = new friendBroadcastReceiver();
	      registerReceiver(receiver, filter);
	}

	/**
	 * ��ʼ����λ ���ö�λ
	 * 
	 */
	private void initLoc() {
		// TODO Auto-generated method stub
		mLocationClient = new LocationClient(this.getApplicationContext());

		mBDLocationListener = new MyBDLocationListener();
		mLocationClient.registerLocationListener(mBDLocationListener);

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// ��gps

		option.setAddrType("all");
		// �����Ƿ񷵻�POI�ĵ绰�͵�ַ����ϸ��Ϣ��Ĭ��ֵΪfalse����������POI�ĵ绰�͵�ַ��Ϣ��
		option.setPoiExtraInfo(true);
		// ���ò�Ʒ�����ơ�ǿ�ҽ�����ʹ���Զ���Ĳ�Ʒ�����ƣ����������Ժ�Ϊ���ṩ����Ч׼ȷ�Ķ�λ����
		option.setProdName("ͨ��GPS��λ�ҵ�ǰ��λ��");
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
		    group_place.setText(province+city+district+"");
		    Log.i("����Ⱥ�����ڵ�ʡ", province);
		    Log.i("����Ⱥ�����ڵ���", city);
		    Log.i("����Ⱥ�����ڵ���", district);
			latitude = location.getLatitude();
			longitude = location.getLongitude();

			Log.i(TAG, "latitude = " + latitude);
			Log.i(TAG, "longitude = " + longitude);

			StringBuffer sb = new StringBuffer(256);
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
			}
			Log.i("Ⱥ���ַ", sb + "");
		}

		@Override
		public void onReceivePoi(BDLocation poiLocation) {

			// �����¸��汾��ȥ��poi����
			if (poiLocation == null) {
				return;
			}
			StringBuffer sb = new StringBuffer(256);
			sb.append("\nerror code : ");
			sb.append(poiLocation.getLocType());
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
			Log.i("Ⱥ���ַ", sb + "");
			
		}
	}

	/**
	 * ��ʼ���ؼ�
	 * 
	 */
	private void initView() {
		// TODO Auto-generated method stub
		group_head = (ImageView) findViewById(R.id.c_group_camera);
		group_name = (EditText) findViewById(R.id.c_group_name);
		group_intro = (EditText) findViewById(R.id.c_group_jianjie);		
        group_place = (TextView)findViewById(R.id.group_add_name);    
		group_head.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyDialog();
			}
		});
	}

	private void initBtn() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.c_group_left);
		next = (Button) findViewById(R.id.c_group_next);
		back.setOnClickListener(listener);
		next.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.c_group_left:
//				Intent intent = new Intent(CreateGroup.this, GroupPage.class);
//				startActivity(intent);
				finish();
				break;
			case R.id.c_group_next:
				next();		
				break;
			default:
				break;
			}
		}
	};

	/**
	 * �ύ״̬
	 * 
	 */
	@SuppressWarnings("deprecation")
	private void next() {
		// TODO Auto-generated method stub
		uid = LoginActivity.id;
		name = group_name.getText().toString().trim();
		intro = group_intro.getText().toString().trim();
		place = group_place.getText().toString().trim();

		headimage = "";	
		if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(intro)
				&& !TextUtils.isEmpty(place)) {
			postGroup(uid, name, place, intro, limit, latitude, longitude);
		}

	}

	/**
	 * �ϴ�
	 * 
	 */
	private void postGroupHeadImage(String uid, String headimage) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				progressDialog.dismiss();
			}
			
			public void onSuccess(String response) {
				JSONObject obj;
				try {
					obj = new JSONObject(response);		
					Log.i("���Ǵ���Ⱥ��ģ�����ͷ���ϴ��ɹ�û��", response+ "");
					if (obj.getInt("result") == 1) {				
							postfinish();
					} else {

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.postGroupHeadImage(uid, headimage, res);
	}


	private void postGroup(String uid, String name, String place, String intro,
			String limit, double latitude, double longitude) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				
				progressDialog.setMessage(getString(R.string.submiting));
				progressDialog.show();
				
			}
			
			@Override
			public void onFailure(Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(error);
				progressDialog.dismiss();
			}
			
			public void onSuccess(String response) {
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					Log.i("response-----result", obj.getInt("result") + "");
					if (obj.getInt("result") == 1) {
						//��ʼ�ϴ�ͷ�񣬲�����group��id
					     JSONObject result = obj.getJSONObject("group");			
						
					    gid = result.getString("id");
						postHeadStart(gid);						
					} else {

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		MyHttpClient client = new MyHttpClient();
		client.postGroup(uid, name, place, intro, limit, latitude, longitude,
				res);
	}

	@SuppressWarnings("deprecation")
	protected void postHeadStart(String gid) {
		// TODO Auto-generated method stub
		if (myBitmap != null) {
			headimage =ImageUtil.bitmaptoString(myBitmap);		
			//System.out.println(headimage);
			postGroupHeadImage(gid, headimage);
		}
	}

	private void postfinish() {
		// TODO Auto-generated method stub
//		Toast.makeText(getApplicationContext(), "��ӳɹ���", Toast.LENGTH_LONG)
//				.show();
//		group_head.setImageBitmap(myBitmap);
		Intent intent1 = new Intent(CreateGroup.this, CreateGroupSuccess.class);		
		startActivity(intent1);		
	}

	// ͼƬ�ϴ�ѡ��;��
	private void MyDialog() {
		final CharSequence[] items = { "���", "����" };
		AlertDialog dlg = new AlertDialog.Builder(CreateGroup.this)
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
				// //�ѵõ���ͼƬ���ڿؼ�����ʾ
				group_head.setImageBitmap(myBitmap);

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
			} catch (Exception e) {
				e.printStackTrace();
			}
			// �ѵõ���ͼƬ���ڿؼ�����ʾ
			group_head.setImageBitmap(myBitmap);// ���������Ƭת��Բ����ʾ��Ԥ���ؼ���
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
	//�㲥
		private class friendBroadcastReceiver extends BroadcastReceiver{

			@Override
			public void onReceive(Context arg0, Intent arg1) {
				// TODO Auto-generated method stub				
				 //���·�һ�����󣬺ø���friend �б�			
		    finish();
			}


		}



	@Override
	protected void onDestroy() {
		if (mLocationClient != null && mLocationClient.isStarted()) {
			if (mBDLocationListener != null) {
				mLocationClient.unRegisterLocationListener(mBDLocationListener);
			}

			mLocationClient.stop();
			mLocationClient = null;
		}
		unregisterReceiver(receiver);
		super.onDestroy();
	}
}
