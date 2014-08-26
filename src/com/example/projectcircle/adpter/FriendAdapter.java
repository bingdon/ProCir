package com.example.projectcircle.adpter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

import com.example.projectcircle.HomeActivity;
import com.example.projectcircle.R;
import com.example.projectcircle.util.DistentsUtil;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;



/**
 * Friend��Adapter
 * 
 */
@SuppressLint("SimpleDateFormat")
public class FriendAdapter extends BaseAdapter{
	private LayoutInflater mInflater;
	ArrayList<HashMap<String, Object>> listItem;

	private int []type;
	
	// ͼƬ����
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = (ImageLoadingListener) new AnimateFirstDisplayListener();	
	Context context;
	private String tag;
	ImageLoader imageLoader = ImageLoader.getInstance();
	Double mylat = HomeActivity.latitude;
	Double mylon = HomeActivity.longitude;
	private String timecompute;
	private String lastlogintime;
	private String lastlogintime_new;
	private double distance;
	/* ���캯�� */
	public FriendAdapter(ArrayList<HashMap<String, Object>> listItem,
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
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.main_list1, null);
			holder = new ViewHolder();				
			holder.phone = (ImageView) convertView
					.findViewById(R.id.main_call_host);
			holder.headimg = (ImageView) convertView
					.findViewById(R.id.main_head_img_host);		
			holder.name = (TextView) convertView
					.findViewById(R.id.main_name_host);
			holder.type =(TextView) convertView
					.findViewById(R.id.main_career_host);	
			holder.sign =(TextView) convertView
					.findViewById(R.id.main_content_host);
			holder.device = (TextView)convertView.findViewById(R.id.main_device_host);
			holder.address = (TextView) convertView.findViewById(R.id.main_address_host);
			holder.distance = (TextView) convertView.findViewById(R.id.main_distance_host);
			convertView.setTag(holder);// ��ViewHolder����
		}
		
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		//String id = listItem.get(position).get("friendID") + "";
	       Log.i("friendadapter listItem", listItem+"");
		String username = listItem.get(position).get("username") + "";
		String headimage = listItem.get(position).get("headimage") + "";
		final String phonenum = listItem.get(position).get("tel") + "";
		final String accept = listItem.get(position).get("accept") + "";
		String type = listItem.get(position).get("type") + "";
		String address = listItem.get(position).get("address") + "";
		String sign = listItem.get(position).get("sign") + "";
		String  equipment= listItem.get(position).get("equipment") + "";
		Double commercialLat = (Double) listItem.get(position).get("commercialLat");
		Double commercialLon = (Double) listItem.get(position).get("commercialLon");
		
		Log.i(tag, "MyHttpClient.IMAGE_URL"+MyHttpClient.IMAGE_URL);
		imageLoader.displayImage(MyHttpClient.IMAGE_URL
				+ listItem.get(position).get("headimage") + "",
				holder.headimg, options, animateFirstListener);
		holder.name.setText(username);
		//holder.headimg.setRight(headimage);	
		holder.type.setText(type);
		//��ַֻ��ȡʡ����Ϊʡ�����ɿո�ֿ��ģ����Խ�ȡ��һ���ո�ǰ�����ݼ�Ϊʡ
		String arrays[] = address.split(" ");
		if(!TextUtils.isEmpty(arrays[0]) && !TextUtils.isEmpty(arrays[1])){
		holder.address.setText(arrays[0]+arrays[1]);
		}
		holder.sign.setText(sign);
		holder.device.setText(equipment);
		//�����Һͺ���֮��ľ���
		distance = DistentsUtil.GetDistance(commercialLat,commercialLon,
				mylat,mylon);
		Log.i("his distance", distance+"");
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
			holder.distance.setText(distance+"km|"+timecompute);
		 }else{
		//������ϴε�¼��ʱ�䣬��ʾ���ؼ���	
		holder.distance.setText(distance+"km|"+"��");
		}
		//���Ƶ绰��־��ͼƬ��ʾ���
		if( accept.equals("1")){
			holder.phone.setVisibility(View.VISIBLE);
		}else{
			holder.phone.setVisibility(View.INVISIBLE);
		}
		holder.phone.setOnClickListener(new OnClickListener() {
			
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
	public final class ViewHolder {

		public TextView sign;
		public TextView address;
		public ImageView phone;
		public TextView distance;
		public TextView jiguan;
		public TextView device;
		public TextView content;
		public TextView time;
		public TextView name;
		public ImageView headimg;
		public TextView type;
		public  TextView layout;	

	}
}
