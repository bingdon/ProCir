package com.example.projectcircle.adpter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectcircle.R;
import com.example.projectcircle.friend.FriendSelectListenter;
import com.example.projectcircle.friend.GroupChat;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.view.AnimateFirstDisplayListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

/**
 * Friend��Adapter
 * 
 */
public class GroupChatAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	ArrayList<HashMap<String, Object>> listItem;
	Context context;
	private int []type;
	View setIcon;
	boolean isIconChange;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();	
	ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	private FriendSelectListenter groupchatListenter;
	/* ���캯�� */
	public GroupChatAdapter(ArrayList<HashMap<String, Object>> listItem,
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
	public void setFriendSelectListener(FriendSelectListenter groupchatListenter) {
		this.groupchatListenter = groupchatListenter;
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
		 final ViewHolder holder;
		 final int groupbuttonposition = position;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.main_list4, null);
			holder = new ViewHolder();
			holder.selector = (ImageView) convertView
					.findViewById(R.id.select_img);
			holder.headimg = (ImageView) convertView
					.findViewById(R.id.select_head_img);
			holder.name = (TextView) convertView.findViewById(R.id.main_name);
			holder.time = (TextView) convertView.findViewById(R.id.main_time);
			holder.content = (TextView) convertView
					.findViewById(R.id.main_content);
			holder.jiguan = (TextView) convertView
					.findViewById(R.id.main_native);
			holder.device = (TextView) convertView
					.findViewById(R.id.main_device);
			holder.distance = (TextView) convertView
					.findViewById(R.id.main_distance);
			holder.type = (TextView) convertView
					.findViewById(R.id.main_career);
			holder.phone = (ImageView) convertView.findViewById(R.id.main_call);
			convertView.setTag(holder);// ��ViewHolder����
		}
		
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		String username = listItem.get(position).get("username") + "";
		holder.name.setText(username);
		String equipment = listItem.get(position).get("equipment") + "";
		holder.device.setText(equipment);
		String address = listItem.get(position).get("address") + "";
		holder.jiguan.setText(address);
		String type = listItem.get(position).get("type") + "";
		holder.type.setText(type);
		imageLoader.displayImage(MyHttpClient.IMAGE_URL
				+ listItem.get(position).get("headimage") + "",
				holder.headimg, options, animateFirstListener);
		//����ѡ�кͲ�ѡ��
		isIconChange = false;
		holder.selector.setOnClickListener(new OnClickListener() {
			
		
			@Override
			public void onClick(View arg0) {
				
				// TODO Auto-generated method stub
				
				     if(isIconChange){
				    	 holder.selector.setBackgroundResource(R.drawable.radiobtn);
				           isIconChange = false;				               
				     }else{
				    	 holder.selector.setBackgroundResource(R.drawable.radiobtn_pressed);
				           isIconChange = true;				    
						}
				      //��ѡ����˵Ķ�Ӧͷ��ŵ��±ߵ�Gallery�� 
//				     public void onClick(View v) {
							// TODO Auto-generated method stub
				     if(isIconChange){
							 groupchatListenter.onFriendSelectListenter(groupbuttonposition); 
				     }else if(!isIconChange)
				     {
				    	 groupchatListenter.onFriendNotSelectListenter(groupbuttonposition); 
				     }
				        
				       
			}
		});
		return convertView;
	}

	/* ��ſؼ� */
	public final class ViewHolder {

		public TextView type;
		public ImageView phone;
		public TextView distance;
		public TextView jiguan;
		public TextView device;
		public TextView content;
		public TextView time;
		public TextView name;
		public ImageView headimg;
		public ImageView selector;

	}
}
