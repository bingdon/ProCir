package com.example.projectcircle.adpter;

import java.util.List;

import com.example.projectcircle.R;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;


import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;

import android.widget.ImageView;


public class GroupGallaryAdapter extends BaseAdapter {

	private Context context;  
    private List<String> list; 
    
	// ͼƬ����
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();	
	ImageLoader imageLoader = ImageLoader.getInstance();
	private LayoutInflater mInflater;
	public GroupGallaryAdapter( Context context, List<String> list) {  
        this.context = context;  
        this.list = list;  
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
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("InlinedApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		// TODO Auto-generated method stub
		 // ʵ����  
        ImageView img = new ImageView(context);          
        // ���ñ߽����  
        img.setAdjustViewBounds(true);  
        // ���ò��ֲ���  
        img.setLayoutParams(new Gallery.LayoutParams(LayoutParams.MATCH_PARENT,  
                LayoutParams.WRAP_CONTENT)); 
        
		 // ʵ����  
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.gallery_list1, null);
			holder = new ViewHolder();		
			holder.headimg = (ImageView) convertView
					.findViewById(R.id.gallery_head_img_host);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		imageLoader.displayImage(MyHttpClient.IMAGE_URL
				+  list.get(position) + "",holder.headimg, options, animateFirstListener);
		Log.i("MyHttpClient.IMAGE_URL+ list", MyHttpClient.IMAGE_URL
				+ list);
	
		return convertView;
	}
	/* ��ſؼ� */
	public final class ViewHolder {
	   public ImageView headimg;	
	}
}
