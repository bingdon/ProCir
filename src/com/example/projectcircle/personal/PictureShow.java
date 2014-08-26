package com.example.projectcircle.personal;

import com.example.projectcircle.R;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class PictureShow extends Activity{
	
	ImageLoader imageLoader = ImageLoader.getInstance();	
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private String image;	
	DisplayImageOptions options;
	private ImageView picture_show;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picture_show);
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
		picture_show =(ImageView)findViewById(R.id.picture_show);
		Intent intent = getIntent();
		image = intent.getStringExtra("image");
		 imageLoader.displayImage(
		 MyHttpClient.IMAGE_URL
		 + image,
		 picture_show, options, animateFirstListener);
		}
}
