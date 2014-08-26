package com.example.projectcircle.adpter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.projectcircle.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class PostStatusGridAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	ArrayList<HashMap<String, Object>> listItems;
	Context context;
	DisplayImageOptions options;

	/* ���캯�� */
	public PostStatusGridAdapter(ArrayList<HashMap<String, Object>> listItems,
			Context context) {
		this.context = context;
		this.listItems = listItems;
		this.mInflater = LayoutInflater.from(context);
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
		return listItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listItems.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.member_image, null);
			holder = new ViewHolder();
			holder.photo = (ImageView) convertView
					.findViewById(R.id.member_headimages);
		
			// ImageLoader imageLoader = ImageLoader.getInstance();
			// imageLoader.displayImage(
			// MyHttpClient.IMAGE_URL
			// + listItem.get(position).get("headimage") + "",
			// holder.headimg, options, animateFirstListener);
			convertView.setTag(holder);// ��ViewHolder����
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Bitmap id = (Bitmap) listItems.get(position).get("photo_array");
		holder.photo.setImageBitmap(id);
		return convertView;
	}

	/* ��ſؼ� */
	public final class ViewHolder {

		public ImageView photo;

	}

}
