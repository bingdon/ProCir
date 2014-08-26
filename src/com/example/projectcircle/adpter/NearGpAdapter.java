package com.example.projectcircle.adpter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectcircle.HomeActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.util.DistentsUtil;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class NearGpAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	ArrayList<HashMap<String, Object>> listItem;
	Context context;

	// ͼƬ����
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	/* ���캯�� */
	public NearGpAdapter(ArrayList<HashMap<String, Object>> listItem,
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.near_list, null);
			holder = new ViewHolder();
			holder.groupimg = (ImageView) convertView
					.findViewById(R.id.n_group_headimg);
			holder.name = (TextView) convertView
					.findViewById(R.id.n_group_name);
			holder.location = (TextView) convertView
					.findViewById(R.id.n_group_near);
			holder.people = (TextView) convertView
					.findViewById(R.id.n_group_people);
			holder.content = (TextView) convertView
					.findViewById(R.id.n_group_introduce);
			holder.distance = (TextView) convertView
					.findViewById(R.id.near_group_distance_host);
			convertView.setTag(holder);// ��ViewHolder����
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String name = listItem.get(position).get("gname") + "";
		holder.name.setText(name);
		String address = listItem.get(position).get("gaddress") + "";
		holder.location.setText(address);
		String content = listItem.get(position).get("content") + "";
		holder.content.setText(content);
		String distance = listItem.get(position).get("distance") + "";		
		holder.distance.setText(distance+"km");
		String gaddress = listItem.get(position).get("gaddress") + "";		
		holder.location.setText(gaddress);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(MyHttpClient.IMAGE_URL
				+ listItem.get(position).get("headimage") + "",
				holder.groupimg, options, animateFirstListener);
		return convertView;
	}

	/* ��ſؼ� */
	public final class ViewHolder {

		public TextView distance;
		public TextView content;
		public TextView people;
		public TextView location;
		public TextView name;
		public ImageView groupimg;

	}
}
