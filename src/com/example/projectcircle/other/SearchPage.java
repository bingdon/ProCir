package com.example.projectcircle.other;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.projectcircle.R;

public class SearchPage extends Activity {
	// ������
	EditText edit_search;
	Button back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		initBtn();
		edit_search = (EditText) findViewById(R.id.search_edit);
		edit_search.setOnKeyListener(new OnKeyListener() {// ������󰴼����ϵ����������س�����Ϊ����������

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						// TODO Auto-generated method stub
						if (keyCode == KeyEvent.KEYCODE_ENTER) {// �޸Ļس�������
							// �����ؼ���
							((InputMethodManager) edit_search.getContext()
									.getSystemService(
											Context.INPUT_METHOD_SERVICE))
									.hideSoftInputFromWindow(
											SearchPage.this.getCurrentFocus()
													.getWindowToken(),
											InputMethodManager.HIDE_NOT_ALWAYS);

							return true;
						}
						return false;
					}

				});
	}

	private void initBtn() {
		// TODO Auto-generated method stub
		back = (Button) findViewById(R.id.search_left);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent(SearchPage.this,
				// MainActivity.class);
				// startActivity(intent);
				finish();
			}
		});
	}
}
