package com.example.projectcircle.friend;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.example.projectcircle.R;
import com.example.projectcircle.adpter.NewFriendAdapter;
import com.example.projectcircle.personal.PersonalPage;
import com.example.projectcircle.view.SildeDelListView;
import com.example.projectcircle.view.SildeDelListView.SildeDeleteListener;

public class NewFriend extends Activity {
	Button back;
	private static final String TAG = "NewFriend";
	SildeDelListView listview;
	NewFriendAdapter myAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_friend_apply);
		initBtn();
		initList();
	}

	private void initBtn() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.nfa_left);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent(NewFriend.this,
				// MainActivity.class);
				// startActivity(intent);
				finish();
			}
		});
	}

	private void initList() {
		// TODO Auto-generated method stub
		listview = (SildeDelListView) findViewById(R.id.nfa_listView);
		myAdapter = new NewFriendAdapter(getList(), this);
		listview.setAdapter(myAdapter);
		listview.setFilpperDeleteListener(new SildeDeleteListener() {

			@Override
			public void filpperOnclick(float xPosition, float yPosition) {
				// TODO Auto-generated method stub

				if (listview.getChildCount() == 0)
					return;
				// ���������û���ɾ����item��index
				final int index = listview.pointToPosition((int) xPosition,
						(int) yPosition);
				// Toast.makeText(NewFriend.this, index + "���", 1000).show();
			}

			@Override
			public void filpperDelete(float xPosition, float yPosition) {
				// TODO Auto-generated method stub
				// listview��Ҫ��item�����򷵻�
				if (listview.getChildCount() == 0)
					return;
				// ���������û���ɾ����item��index
				final int index = listview.pointToPosition((int) xPosition,
						(int) yPosition);
				// һ�������ǻ�ø���Ŀ����Ļ��ʾ�е����λ�ã�ֱ�Ӹ���indexɾ�����ָ��쳣����Ϊlistview�е�childֻ�е�ǰ����Ļ����ʾ�ĲŲ���Ϊ��
				int firstVisiblePostion = listview.getFirstVisiblePosition();
				View view = listview.getChildAt(index - firstVisiblePostion);
				view.findViewById(R.id.delete).setVisibility(View.VISIBLE);
				view.findViewById(R.id.new_friend_pass).setVisibility(
						View.INVISIBLE);
				// Toast.makeText(NewFriend.this, index + "���򻬶�", 1000).show();

			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(NewFriend.this, PersonalPage.class);
				startActivity(intent);
			}
		});
	}

	private ArrayList<HashMap<String, Object>> getList() {
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		// TODO Auto-generated method stub
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("name", "����");
			listItem.add(map);
		}
		return listItem;
	}
}
