package com.example.projectcircle.adpter;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.logging.Log;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.projectcircle.R;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

public class HisDeviceGridAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	ArrayList<HashMap<String, Object>> listItem;
	Context context;
	DisplayImageOptions options;
	ImageLoader imageLoader = ImageLoader.getInstance();
	android.view.ViewGroup.LayoutParams para;  
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();	
	/* ���캯�� */
	public HisDeviceGridAdapter(ArrayList<HashMap<String, Object>> listItem,
			Context context) {
		this.context = context;
		this.listItem = listItem;
		this.mInflater = LayoutInflater.from(context);
		options = new DisplayImageOptions.Builder().cacheInMemory(true) // ����ͼƬʱ�����ڴ��м��ػ���
				 .showStubImage(R.drawable.header)// ����ͼƬ�������ڼ���ʾ��ͼƬ
				 .showImageForEmptyUri(R.drawable.header)//
				// ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
				 .showImageOnFail(R.drawable.header)//
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
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listItem.get(arg0);
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
			para = holder.photo.getLayoutParams();  
		    para.height = 100;  
	        para.width = 100;  
	        holder.photo.setLayoutParams(para);  
			convertView.setTag(holder);// ��ViewHolder����
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//String username = listItems.get(position).get("id") + "";
		String headimage = listItem.get(position).get("headimage") + "";
		System.out.println("��Ƭ"+headimage);
		if(!TextUtils.isEmpty(headimage) && !headimage.equals("") && headimage.equals("null")){	
		 imageLoader.displayImage(
		 MyHttpClient.IMAGE_URL
		 + headimage,
		 holder.photo, options, animateFirstListener);
		}
		return convertView;
	}

	/* ��ſؼ� */
	public final class ViewHolder {

		public ImageView photo;

	}

}
