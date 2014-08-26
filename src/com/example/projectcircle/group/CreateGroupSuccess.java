package com.example.projectcircle.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.projectcircle.R;

public class CreateGroupSuccess extends Activity {
	TextView share, finish;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_group_success);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		share = (TextView) findViewById(R.id.c_group_share);
		finish = (TextView) findViewById(R.id.c_group_finish);
		share.setOnClickListener(listener);
		finish.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.c_group_share:
//				 Intent intent = new Intent(FriendPage.this,
//				 MaybeFriend.class);
//				 startActivity(intent);
//				 finish();
				break;
			case R.id.c_group_finish:
				Intent intent1 = new Intent(CreateGroupSuccess.this,
						MyGroup.class);
				startActivity(intent1);
				//���͹㲥����CreateGroup ���activity finishdiao
				   Intent intent = new Intent();
				   intent.setAction("cn.abel.action.broadcast");				   
				   //Ҫ���͵�����
				   intent.putExtra("author", "Abel");				   
				   //���� һ������㲥
				   CreateGroupSuccess.this.sendBroadcast(intent);
				finish();
				break;
			default:
				break;
			}
		}
	};
}
