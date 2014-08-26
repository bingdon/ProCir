package com.example.projectcircle.adpter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projectcircle.HomeActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.util.DistentsUtil;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Home��Adapter
 * 
 */
public class HomeAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	ArrayList<HashMap<String, Object>> listItem;
	Context context;

	Double mylat = HomeActivity.latitude;
	Double mylon = HomeActivity.longitude;

	// ͼƬ����
	DisplayImageOptions options;
	ImageLoader imageLoader = ImageLoader.getInstance();
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private String tag;
	private double distance;
	private String lastlogintime;
	private String lastlogintime_new;
	private String timecompute;
	/* ���캯�� */
	public HomeAdapter(ArrayList<HashMap<String, Object>> listItem,
			Context context) {
		this.context = context;
		this.listItem = listItem;
		this.mInflater = LayoutInflater.from(context);

		// ����ͼƬ���ؼ���ʾѡ�����һЩ���������ã�����doc�ĵ��ɣ�
		options = new DisplayImageOptions.Builder().cacheInMemory(true) // ����ͼƬʱ�����ڴ��м��ػ���
				 .showStubImage(R.drawable.header)// ����ͼƬ�������ڼ���ʾ��ͼƬ
				 .showImageForEmptyUri(R.drawable.header)//
				// ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
				 .showImageOnFail(R.drawable.header)//
				// ����ͼƬ����/��������д���ʱ����ʾ��ͼƬ
				.cacheInMemory(true)// �Ƿ񾏴涼�ȴ���
				.cacheOnDisc(true)// �Ƿ񾏴浽sd����
				 .displayer(new RoundedBitmapDisplayer(20)) //
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
		ViewHolder1 holder1 = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.main_list1, null);
			holder1 = new ViewHolder1();
			holder1.headimg = (ImageView) convertView
					.findViewById(R.id.main_head_img_host);
			holder1.name = (TextView) convertView.findViewById(R.id.main_name_host);
			holder1.sign =(TextView) convertView
					.findViewById(R.id.main_content_host);
			holder1.career = (TextView) convertView
					.findViewById(R.id.main_career_host);
			holder1.content = (TextView) convertView
					.findViewById(R.id.main_content_host);
			holder1.jiguan = (TextView) convertView
					.findViewById(R.id.main_address_host);
			holder1.device = (TextView) convertView
					.findViewById(R.id.main_device_host);
			holder1.distance = (TextView) convertView
					.findViewById(R.id.main_distance_host);
			holder1.phone = (ImageView) convertView
					.findViewById(R.id.main_call_host);
			holder1.type =(TextView) convertView
					.findViewById(R.id.main_career_host);	
			holder1.address = (TextView) convertView.findViewById(R.id.main_address_host);
			convertView.setTag(holder1);// ��ViewHolder����
		} else {
			holder1 = (ViewHolder1) convertView.getTag();
		}
		
		String username = listItem.get(position).get("name") + "";
		String headimage = listItem.get(position).get("headimage") + "";
		final String phonenum = listItem.get(position).get("tel") + "";
		final String accept = listItem.get(position).get("accept") + "";
		String type = listItem.get(position).get("type") + "";
		String address = listItem.get(position).get("address") + "";
		String sign = listItem.get(position).get("sign") + "";
		String distance =  listItem.get(position).get("distance") + "";
		String equipment =  listItem.get(position).get("equipment") + "";
//		Double commercialLat = (Double) listItem.get(position).get("commercialLat");
//		Double commercialLon = (Double) listItem.get(position).get("commercialLon");
		
		Log.i(tag, "MyHttpClient.IMAGE_URL"+MyHttpClient.IMAGE_URL);
		imageLoader.displayImage(MyHttpClient.IMAGE_URL
				+ listItem.get(position).get("headimage") + "",
				holder1.headimg, options, animateFirstListener);
		holder1.name.setText(username);
		//holder.headimg.setRight(headimage);	
		holder1.type.setText(type);
		//��ַֻ��ȡʡ����Ϊʡ�����ɿո�ֿ��ģ����Խ�ȡ��һ���ո�ǰ�����ݼ�Ϊʡ
		if(!address.equals("null")){
		String arrays[] = address.split(" ");		
		if(arrays.length >= 2 && !TextUtils.isEmpty(arrays[0]) && !TextUtils.isEmpty(arrays[1])){
		holder1.address.setText(arrays[0]+" "+arrays[1]);
		}
		}
        //��ֹ����ǩ���ǲ���Ļ�����null 
		if(!TextUtils.isEmpty(sign) && !sign.equals("null"))
		 {
			holder1.sign.setText(sign);
		 }else if(TextUtils.isEmpty(sign) || sign.equals("null"))
		 {
			holder1.sign.setText("");
		 }
		holder1.device.setText(equipment);
     	holder1.distance.setText(distance+"km");
		//��ȡ��ǰʱ��
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy��MM��dd��   HH:mm:ss"); 
		 Date   curDate   =   new   Date(System.currentTimeMillis());//��ȡ��ǰʱ��     
		 String  now   =   formatter.format(curDate);
		 lastlogintime =  listItem.get(position).get("lastlogintime")+"";//�ӷ������ǵõ���ʱ�䣬���ǲ���ת������һ���ո񣬼�һ���ո����ת���ɸ�������ʱ��	
		 Log.i("now----------", now);
		 Log.i("lastlogintime----------", lastlogintime);		
		//���㵱ǰʱ������������ص��û��ϴε�¼��ʱ���ʱ���������1�죬��ʾ1��ǰ������1Сʱ��ʾXСʱǰ��������ʾX����ǰ
		 if(lastlogintime != null && !lastlogintime.equals("null")){
			try
			{ 	//ʱ��ת���ɸ�������ʱ��	
			   lastlogintime_new = lastlogintime.substring(0,4)+"��"+lastlogintime.substring(5,7)+"��"+lastlogintime.substring(8,10)+"��   "+lastlogintime.substring(11,lastlogintime.length());
               Log.i("lastlogintime_new", lastlogintime_new);
			   java.util.Date d1 = (formatter).parse(now);			   
			   java.util.Date d2 = (formatter).parse(lastlogintime_new);		
			   long diff = d1.getTime() - d2.getTime();//�����õ��Ĳ�ֵ��΢�뼶��					
			   long days = diff / (1000 * 60 * 60 * 24);
			   long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
			   long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);					
			   System.out.println(""+days+"��"+hours+"Сʱ"+minutes+"��");
			   if(days>=1){
				   timecompute = "1��ǰ";
			   }else if(hours>=1)
			   {
				   timecompute = hours+"Сʱǰ";
			   }else{
				   timecompute = minutes+"����ǰ";
			   }
			  		  
			}
			catch (Exception e)
			{
			}
			holder1.distance.setText(distance+"km|"+timecompute);
		 }else{
		//������ϴε�¼��ʱ�䣬��ʾ���ؼ���	
		holder1.distance.setText(distance+"km|"+"��");
		}
		//���Ƶ绰��־��ͼƬ��ʾ���
		if( accept.equals("1")){
			holder1.phone.setVisibility(View.VISIBLE);
		}else{
			holder1.phone.setVisibility(View.INVISIBLE);
		}
		holder1.phone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//������벻Ϊ�մ�����绰��Intent
				if(phonenum.trim().length()!=0 && accept.equals("1"))
				  {
                   Intent phoneIntent = new Intent("android.intent.action.CALL",
				   Uri.parse("tel:" + phonenum));
			       //����
                   context.startActivity(phoneIntent);
                   }

			}

		});
		return convertView;
	}

	/* ��ſؼ� */
	public final class ViewHolder1 {

		public TextView career;
		public ImageView phone;
		public TextView distance;
		public TextView jiguan;
		public TextView device;
		public TextView content;
		public TextView time;
		public TextView name;
		public ImageView headimg;
		public TextView type;
		public TextView sign;
		public TextView address;
	}
}
