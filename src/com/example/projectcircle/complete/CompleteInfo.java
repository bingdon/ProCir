package com.example.projectcircle.complete;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectcircle.R;
import com.example.projectcircle.SiginActivity;
import com.example.projectcircle.adpter.ImageAdapter;
import com.example.projectcircle.constants.ContantS;
import com.example.projectcircle.util.ImageUtil;
import com.example.projectcircle.util.MyHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class CompleteInfo extends Activity implements OnClickListener {
	/**
	 * ͷ��
	 */
	ImageView myhead;
	GridView gridview;
	ImageAdapter imageAdapter;
	// private static final int[] images = new int[2];
	// �ϴ�ͷ������Ҫ��
	private static final int REQUEST_CAMERA = 1;
	int current_index;// ��ǵ�ǰ���ڴ洢�ĸ�ͷ�� �豸�����ˣ�
	private Bitmap myBitmap;
	private byte[] mContent;
	String returnString;
	String headimage;
	// ����յ��ǵڼ���
	int index = 0;
	/**
	 * EditText ����ǩ�� ����û�ĵط� ��Ȥ���� ���˼��
	 */
	//�豸����ͼƬ��Bitmap
	private Bitmap myBitmap1;
	private Bitmap myBitmap2;
	private Bitmap myBitmap3;
	private String wajue_equid = FootActivity1.equid;//�ھ�����豸id
	private String zixie_equid = FootActivity2.equid;//��ж�����豸id
	private String pingban_equid = FootActivity3.equid;//ƽ�峵���豸id
	private String zhuangzai_equid = FootActivity4.equid;//װ�ص��豸id
	private String other_equid = FootActivity5.equid;//�������豸id
	EditText my_sign, my_intro, my_address, my_hobby;
	String sign, intro, address, hobby;

	/**
	 * ����Button
	 */
	Button back, next;

	String id;
	private ProgressDialog progressDialog;
	private ImageView mydevice2;
	private ImageView mydevice3;
	private ImageView mydevice1;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.perfect_info);
		progressDialog=new ProgressDialog(this);
		initFilter();
		initBtn();
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		myhead = (ImageView) findViewById(R.id.perfect_info_camera);
//		gridview = (GridView) findViewById(R.id.gridview);
//		imageAdapter = new ImageAdapter(getList(), this);
//		gridview.setAdapter(imageAdapter);
		mydevice1 = (ImageView) findViewById(R.id.perfect_info_camera1);
		mydevice2 = (ImageView) findViewById(R.id.perfect_info_camera2);
		mydevice3 = (ImageView) findViewById(R.id.perfect_info_camera3);
		my_sign = (EditText) findViewById(R.id.personalized_signature);
		my_intro = (EditText) findViewById(R.id.personalized_jianjie);
		my_address = (EditText) findViewById(R.id.personalized_address);
		my_hobby = (EditText) findViewById(R.id.personalized_hobby);

		myhead.setOnClickListener(this);
		mydevice1.setOnClickListener(this);
		mydevice2.setOnClickListener(this);
		mydevice3.setOnClickListener(this);
//		gridview.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				current_index = 1;
//				MyDialog();
//			}
//		});
	}

//	private ArrayList<HashMap<String, Object>> getList() {
//		// TODO Auto-generated method stub
//		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
//		for (int i = 0; i < 3; i++) {
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("headimage", R.drawable.camera);
//
//			listItem.add(map);
//		}
//		return listItem;
//	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.perfect_info_camera:
			current_index = 0;
			MyDialog();
			break;
		case R.id.perfect_info_camera1:
			current_index = 1;
			MyDialog();
			break;
		case R.id.perfect_info_camera2:
			current_index = 2;
			MyDialog();
			break;
		case R.id.perfect_info_camera3:
			current_index =3;
			MyDialog();
			break;
		default:
			break;
		}
	}

	private void initBtn() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.perfect_info_left);
		next = (Button) findViewById(R.id.perfect_info_next);
		back.setOnClickListener(listener);
		next.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.perfect_info_left:
				finish();
				break;
			case R.id.perfect_info_next:

				submit();
				break;
			default:
				break;
			}
		}
	};
	private String type;
	
	/**
	 * �ϴ�����
	 */
	@SuppressWarnings("deprecation")
	private void submit() {
		// TODO Auto-generated method stub
		id = SiginActivity.id;
		type = SiginActivity.type;		
		// id = "25";
		sign = my_sign.getText().toString();
		intro = my_intro.getText().toString();
		address = my_address.getText().toString();
		hobby = my_hobby.getText().toString();

		headimage = "";
		if (myBitmap != null) {
			headimage = URLEncoder.encode(ImageUtil.bitmaptoString(myBitmap));
			headimage = ImageUtil.bitmaptoString(myBitmap);
			// System.out.println(headimage);
			postHeadImage(id, headimage);
		}	
		 if (myBitmap1!= null) {			
				headimage = URLEncoder.encode(ImageUtil.bitmaptoString(myBitmap1));
				headimage = ImageUtil.bitmaptoString(myBitmap1);
				// System.out.println(headimage);
				postEquHeadImage(id, headimage);
			}if (myBitmap2!= null) {			
				headimage = URLEncoder.encode(ImageUtil.bitmaptoString(myBitmap2));
				headimage = ImageUtil.bitmaptoString(myBitmap2);
				// System.out.println(headimage);
				postEquHeadImage(id, headimage);
			}if (myBitmap3!= null) {			
				headimage = URLEncoder.encode(ImageUtil.bitmaptoString(myBitmap3));
				headimage = ImageUtil.bitmaptoString(myBitmap3);
				// System.out.println(headimage);
				postEquHeadImage(id, headimage);
			}	
			baseInfo(id, sign, address, hobby, intro);		
	}
    //�ϴ�������Ƭ
	private void postEquHeadImage(String id, String headimage) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			public void onSuccess(String response) {
				// System.out.println(response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					Log.i("�ϴ�������Ƭresult", obj.getInt("result") + "");				
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.upLoadEquHeadImage(id, headimage, res);
	}

	private void baseInfo(String id, String sign, String place, String hobby,
			String info) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				progressDialog.setMessage(getString(R.string.submiting));
				progressDialog.show();
			}
			
			public void onSuccess(String response) {
				// System.out.println(response);
				// parseInfo(response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					Log.i("response-----result", obj.getInt("result") + "");
					if (obj.getInt("result") == 1) {
						postfinish();
					} else {

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				progressDialog.dismiss();
			}
			

		};
		MyHttpClient client = new MyHttpClient();
		client.BaseInfo(id, sign, place, hobby, info, res);
	}

	private void postHeadImage(String id, String headimage) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			public void onSuccess(String response) {
				// System.out.println(response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					Log.i("response-----result", obj.getInt("result") + "");
					} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		MyHttpClient client = new MyHttpClient();
		client.postHeadImage(id, headimage, res);
	}

	private void postfinish() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "��ӳɹ���", Toast.LENGTH_LONG)
				.show();
		myhead.setImageBitmap(myBitmap);
		Intent intent = new Intent(CompleteInfo.this, CompleteFinish.class);
		startActivity(intent);
		//���㲥����ǰ��ע����Ǽ�������finish��
		Intent intent2 = new Intent();
		intent2.setAction("finish.before.regist.page");			
		CompleteInfo.this.sendBroadcast(intent);
		finish();
	}

	// ͼƬ�ϴ�ѡ��;��
	private void MyDialog() {
		final CharSequence[] items = { "���", "����" };
		AlertDialog dlg = new AlertDialog.Builder(CompleteInfo.this)
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
				} else if(current_index == 1){
					myBitmap1 = myBitmap;
					mydevice1.setImageBitmap(myBitmap1);
				} else if(current_index == 2){
					myBitmap2 = myBitmap;
					mydevice2.setImageBitmap(myBitmap2);
				} else if(current_index == 3){
					myBitmap3 = myBitmap;
					mydevice3.setImageBitmap(myBitmap3);
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
			}  else if(current_index == 1){
				myBitmap1 = myBitmap;
				mydevice1.setImageBitmap(myBitmap1);
			}  else if(current_index == 2){
				myBitmap2 = myBitmap;
				mydevice2.setImageBitmap(myBitmap2);
			} else if(current_index == 3){
				myBitmap3 = myBitmap;
				mydevice3.setImageBitmap(myBitmap3);
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
	private void initFilter() {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter();
		filter.addAction("finish.before.regist.page");		
		registerReceiver(msgReceiver, filter);
	}
	private BroadcastReceiver msgReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub			
			finish();		
		}
	};
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(msgReceiver);
	}
}
