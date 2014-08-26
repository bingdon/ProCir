package com.example.projectcircle.adpter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectcircle.R;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class MyGroupAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	ArrayList<HashMap<String, Object>> listItem;
	Context context;
	// ͼƬ����
		DisplayImageOptions options;
		private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();	
		ImageLoader imageLoader = ImageLoader.getInstance();
	/* ���캯�� */
	public MyGroupAdapter(ArrayList<HashMap<String, Object>> listItem,
			Context context) {
		this.context = context;
		this.listItem = listItem;
		this.mInflater = LayoutInflater.from(context);
		// ����ͼƬ���ؼ���ʾѡ�����һЩ���������ã�����doc�ĵ��ɣ�
		options = new DisplayImageOptions.Builder().cacheInMemory(true) // ����ͼƬʱ�����ڴ��м��ػ���
				 .showStubImage(R.drawable.icon_qunzu)// ����ͼƬ�������ڼ���ʾ��ͼƬ
				 .showImageForEmptyUri(R.drawable.icon_qunzu)//
				// ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
				 .showImageOnFail(R.drawable.icon_qunzu)//
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.near_list, null);
			holder = new ViewHolder();
			holder.headimage = (ImageView) convertView
					.findViewById(R.id.n_group_headimg);
			holder.gname = (TextView) convertView
					.findViewById(R.id.n_group_name);
			holder.gaddress = (TextView) convertView
					.findViewById(R.id.n_group_near);
			holder.people = (TextView) convertView
					.findViewById(R.id.n_group_people);
			holder.content = (TextView) convertView
					.findViewById(R.id.n_group_introduce);
			holder.right = (ImageView) convertView
					.findViewById(R.id.n_group_right_btn);
			holder.distance = (TextView) convertView
					.findViewById(R.id.near_group_distance_host);
			convertView.setTag(holder);// ��ViewHolder����
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String gname = listItem.get(position).get("gname") + "";
		String headimage = listItem.get(position).get("headimage") + "";
		String gaddress = listItem.get(position).get("gaddress") + "";
		String content = listItem.get(position).get("content") + "";
		String distance = listItem.get(position).get("distance") + "";		
		holder.gname.setText(gname);
		holder.gaddress.setText(gaddress);
		holder.content.setText(content);		
		holder.distance.setText(distance+"km");
		imageLoader.displayImage(MyHttpClient.IMAGE_URL
				+headimage,
				holder.headimage, options, animateFirstListener);
		Log.i("Ⱥ��ͷ��-------------", headimage);
		Log.i("Ⱥ��ͷ��URL;-------------", MyHttpClient.IMAGE_URL+headimage);
		return convertView;
	}

	/* ��ſؼ� */
	public final class ViewHolder {

		public ImageView right;
		public TextView content;
		public TextView people;
		public TextView gaddress;
		public TextView gname;
		public ImageView headimage;
		public TextView distance;

	}
}
