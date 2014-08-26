package com.example.projectcircle.adpter;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectcircle.HomeActivity;
import com.example.projectcircle.LoginActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.group.GroupDetail;
import com.example.projectcircle.util.DistentsUtil;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.ToastUtils;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class GroupNumAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	ArrayList<HashMap<String, Object>> listItem;
	Context context;
	private Button curDel_btn;
	String accept;
	String tel;
	String uid;
	String gid;
	Double mylat = HomeActivity.latitude;
	Double mylon = HomeActivity.longitude;
	// ͼƬ����
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	/* ���캯�� */
	public GroupNumAdapter(ArrayList<HashMap<String, Object>> listItem,
			Context context) {
		this.context = context;
		this.listItem = listItem;
		this.mInflater = LayoutInflater.from(context);
		// ����ͼƬ���ؼ���ʾѡ�����һЩ���������ã�����doc�ĵ��ɣ�
		options = new DisplayImageOptions.Builder().cacheInMemory(true) // ����ͼƬʱ�����ڴ��м��ػ���
				// .showStubImage(R.drawable.ic_launcher)// ����ͼƬ�������ڼ���ʾ��ͼƬ
				// .showImageForEmptyUri(R.drawable.ic_launcher)//
				// ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
				// .showImageOnFail(R.drawable.ic_launcher)//
				// ����ͼƬ����/��������д���ʱ����ʾ��ͼƬ
				.cacheInMemory(true)// �Ƿ񾏴涼�ȴ���
				.cacheOnDisc(true)// �Ƿ񾏴浽sd����
				// .displayer(new RoundedBitmapDisplayer(20)) //
				// �����û�����ͼƬtask(������Բ��ͼƬ��ʾ)
				.build();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItem.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listItem.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.main_list5, null);
			holder = new ViewHolder();
			holder.headimg = (ImageView) convertView
					.findViewById(R.id.group_main_head_img_host);
			holder.name = (TextView) convertView.findViewById(R.id.group_main_name_host);
			holder.time = (TextView) convertView.findViewById(R.id.group_main_time_host);
			holder.career = (TextView) convertView
					.findViewById(R.id.group_main_career_host);
			holder.content = (TextView) convertView
					.findViewById(R.id.group_main_content_host);
			holder.jiguan = (TextView) convertView
					.findViewById(R.id.group_main_native_host);
			holder.device = (TextView) convertView
					.findViewById(R.id.group_main_device_host);
			holder.distance = (TextView) convertView
					.findViewById(R.id.group_main_distance_host);
			holder.flag = (ImageView) convertView
					.findViewById(R.id.group_icon_address);
			holder.phone = (ImageView) convertView.findViewById(R.id.group_main_call_host);		
			convertView.setTag(holder);// ��ViewHolder����
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		uid = LoginActivity.id;
		gid = GroupDetail.gid;
		Log.i("uid+gid", uid + "+" + gid);
		String name = listItem.get(position).get("name") + "";
		holder.name.setText(name);
		String type = listItem.get(position).get("type") + "";
		holder.career.setText(type);
		String content = listItem.get(position).get("sign") + "";
		holder.content.setText("(" + content + ")");
		String city = listItem.get(position).get("address") + "";
		holder.jiguan.setText("���᣺" + city);
		String device = listItem.get(position).get("equipment") + "";
		holder.device.setText("�豸��" + device);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(MyHttpClient.IMAGE_URL
				+ listItem.get(position).get("headimage") + "", holder.headimg,
				options, animateFirstListener);
		// �������
		Double nearlat = (Double) listItem.get(position).get("lat");
		Double nearlon = (Double) listItem.get(position).get("lon");

		Double distance = DistentsUtil.DistanceOfTwoPoints(nearlat, nearlon,
				mylat, mylon) / 1000;
		holder.distance.setText(distance + "km");
		accept = listItem.get(position).get("accept") + "";
		tel = listItem.get(position).get("tel") + "";
		//���Ƶ绰��־��ͼƬ��ʾ���
		if( accept.equals("1")){
			holder.phone.setVisibility(View.VISIBLE);
			}else{
			holder.phone.setVisibility(View.INVISIBLE);
		}
		holder.phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if ((tel != null) && (!"".equals(tel.trim()))) {
					Uri uri = Uri.parse("tel:" + tel);
					Intent intent = new Intent(Intent.ACTION_CALL, uri);
					context.startActivity(intent);
				}
			}
		});
//		holder.delete.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if (curDel_btn != null)
//					curDel_btn.setVisibility(View.GONE);
//				holder.delete.setVisibility(View.INVISIBLE);
//				holder.phone.setVisibility(View.VISIBLE);
//				holder.distance.setVisibility(View.VISIBLE);
//				holder.flag.setVisibility(View.VISIBLE);
//				listItem.remove(position);
//				deleteMember(uid, gid);
//				notifyDataSetChanged();
//			}
//		});
		return convertView;
	}

	private void deleteMember(String uid, String gid) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			public void onSuccess(String response) {
				System.out.println(response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					if (obj.getInt("result") == 1) {
						ToastUtils.showShort(context, "ɾ���ɹ���");
					} else {
						ToastUtils.showShort(context, "ɾ��ʧ�ܣ�");
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
		myhttpclient.deleteMember(uid, gid, res);
	}

	/* ��ſؼ� */
	public final class ViewHolder {

		public TextView career;
		public ImageView flag;
		public Button delete;
		public ImageView phone;
		public TextView distance;
		public TextView jiguan;
		public TextView device;
		public TextView content;
		public TextView time;
		public TextView name;
		public ImageView headimg;

	}
}
