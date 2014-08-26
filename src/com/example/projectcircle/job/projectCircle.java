package com.example.projectcircle.job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.projectcircle.R;
import com.example.projectcircle.adpter.CircleAdapter;
import com.example.projectcircle.adpter.ProCirAdapter;
import com.example.projectcircle.adpter.ProCirAdapter.ShaiItemOnclickListener;
import com.example.projectcircle.app.MyApplication;
import com.example.projectcircle.bean.CommentBean;
import com.example.projectcircle.bean.MoodBean;
import com.example.projectcircle.bean.MoodInfo;
import com.example.projectcircle.bean.StatusInfo;
import com.example.projectcircle.other.MsgListView.IXListViewListener;
import com.example.projectcircle.util.BingDateUtils;
import com.example.projectcircle.util.JsonUtils;
import com.example.projectcircle.util.MyHttpClient;
import com.example.projectcircle.util.TimeUtility;
import com.example.projectcircle.util.ToastUtils;
import com.example.projectcircle.view.BingListView;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class projectCircle extends Activity implements OnRefreshListener,
		IXListViewListener, ShaiItemOnclickListener {
	private static final String TAG = projectCircle.class.getSimpleName();
	ListView listview;
	CircleAdapter myAdapter;
	ProCirAdapter proCirAdapter;
	Button button;
	GridView gridview;
	Button status_button;
	String userid;
	private String id;
	private String context;
	private String headimage;
	private String myid;
	private ArrayList<StatusInfo> mymoodList;
	private eoeBroadcastReceiver receiver;
	private BingListView bingListView;
	private String moodid;
	private String moodid0;
	private String pic;
	private RelativeLayout wrapper;
	private ArrayList<MoodInfo> usermoodsList = new ArrayList<MoodInfo>();
	private List<MoodBean> list = new ArrayList<>();
	private String limit = "10";
	ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
	private TextView commentTxt;// ���ۿؼ�
	private String comment_content;// ��������
	private Button sendButton;
	private EditText sendEditText;
	private View sendView;
	private String circlemoodsid;
	private String commentid;// �������ݵ�id
	private String pingluncontent;// �г��������۵�����
	private boolean isRefresh = false;// �Ƿ�ˢ����
	private SwipeRefreshLayout mRefreshLayout;
	private int comPostion = 0;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ����ͷ��title��������ʾ
		setContentView(R.layout.project_circle);
		// myid = LoginActivity.id;
		myid = "1";
		// ��ʼ��ˢ�¿ؼ�
		refeshInit();
		doListMood();
		post_status();
		// ע��㲥
		IntentFilter filter = new IntentFilter();
		filter.addAction("refesh.CircleAdapter");
		receiver = new eoeBroadcastReceiver();
		registerReceiver(receiver, filter);
		// ���۷��ͽ���
		sendView = getLayoutInflater().inflate(R.layout.send_msg_edit_layout,
				null);
		initSendView(sendView);
	}

	private void refeshInit() {
		// TODO Auto-generated method stub
		mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.list_swipe);
		bingListView = (BingListView) findViewById(R.id.m_listview);
		wrapper = (RelativeLayout) findViewById(R.id.home_line);
		mRefreshLayout.setOnRefreshListener(this);
		mRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_blue_dark,
				android.R.color.holo_green_light,
				android.R.color.holo_green_dark);
	}

	private void post_status() {
		// TODO Auto-generated method stub
		status_button = (Button) findViewById(R.id.project_circle_right);
		status_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(projectCircle.this, postStatus.class);
				startActivity(intent);
			}
		});
	}

	@SuppressLint("InlinedApi")
	private void list_circle() {

		// TODO Auto-generated method stub
		listItem.clear();
		// Log.i("��պ�ĵ�listItem", listItem+"");
		// listItem = getList_Mood();
		// Log.i("���»�ȡ��listItem", listItem+"");
		// myAdapter = new CircleAdapter(listItem, this);
		proCirAdapter = new ProCirAdapter(projectCircle.this, list);
		// myAdapter.notifyDataSetChanged();
		bingListView.setAdapter(proCirAdapter);
		proCirAdapter.setListener(this);
		// myAdapter.setOnClickListener(this);
		// ������ؼ�������
		back();
	}

	// //������ؼ�������
	private void back() {
		// TODO Auto-generated method stub
		button = (Button) findViewById(R.id.project_circle_left);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				projectCircle.this.setResult(RESULT_OK, getIntent());
				projectCircle.this.finish();
			}
		});
	}

	private void initSendView(View v) {
		sendButton = (Button) v.findViewById(R.id.send_msg_btn);
		sendEditText = (EditText) v.findViewById(R.id.send_msg_editText);
		sendButton.setOnClickListener(listener);
	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {

			case R.id.send_msg_btn:
				comment_content = sendEditText.getText().toString();
				if (!TextUtils.isEmpty(circlemoodsid)) {

					sendMoodComment(circlemoodsid, comPostion);

				}
				break;

			default:
				break;
			}
		}

	};

	// ��������
	private void sendMoodComment(String moodid, final int position) {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			// private String laudcount;

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i("���۵�response", response);
				try {
					JSONObject jsonObject = new JSONObject(response);
					if ("1".equals(jsonObject.getString("result"))) {

						ToastUtils.showLong(
								projectCircle.this,
								projectCircle.this.getResources().getString(
										R.string.commentsuccess));
						// �ٰѷ������۵Ľ���ȥ��
						wrapper.removeView(sendView);
					} else {

						ToastUtils.showLong(
								projectCircle.this,
								projectCircle.this.getResources().getString(
										R.string.commentfailure));
						wrapper.removeView(sendView);
					}

					CommentBean commentBean = new CommentBean();
					commentBean.setContent(sendEditText.getText().toString());
					sendEditText.setText("");
					commentBean.setUser(MyApplication.getMyPersonBean());
					list.get(comPostion).getComment().add(commentBean);
					list.get(comPostion).setCommentcount(
							list.get(comPostion).getCommentcount() + 1);
					proCirAdapter.notifyDataSetChanged();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ToastUtils.showLong(projectCircle.this, projectCircle.this
							.getResources().getString(R.string.commentfailure));
					wrapper.removeView(sendView);
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1, arg2, arg3);
				Log.i("failuer ", "failuer");

			}

		};
		MyHttpClient client = new MyHttpClient();
		client.PostComment(circlemoodsid, comment_content, myid, commentid, res);
	}

	// �ҵ������г���
	private void doListMyMood() {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			public void onSuccess(String response) {
				Log.i("list mood response", response);
				parseListMyMood(response);
				JSONObject obj;
				try {
					obj = new JSONObject(response);
					Log.i("obj", obj + "");
					if (obj.getInt("result") == 1) {
						list_circle();
					} else {
						ToastUtils.showShort(getApplicationContext(),
								"δ��������Ӧ�ĺ��ѣ�");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@SuppressWarnings("deprecation")
			@Override
			public void onFailure(Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(error);
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				mRefreshLayout.setRefreshing(false);
			}

		};
		MyHttpClient myhttpclient = new MyHttpClient();
		myhttpclient.userMood(myid, res);
	}

	// ���ѵ������г���
	protected void doListMood() {
		// TODO Auto-generated method stub
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				mRefreshLayout.setRefreshing(true);
			}

			public void onSuccess(String response) {
				Log.i("listmood��response", response);
				parselistMood(response);
				doListMyMood();
				// list_circle();

			}

			@SuppressWarnings("deprecation")
			@Override
			public void onFailure(Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(error);
				mRefreshLayout.setRefreshing(false);
			}
		};
		MyHttpClient myhttpclient = new MyHttpClient();
		myhttpclient.listMood(myid, res);
	}

	private void parseListMyMood(String response) {
		// TODO Auto-generated method stub
		try {
			JSONObject objo = new JSONObject(response);
			JSONArray moods = objo.getJSONArray("moods");
			for (int i = 0; i < moods.length(); i++) {
				MoodInfo moodinfo = new MoodInfo();

				MoodBean moodBean = JsonUtils.getMoodBean(moods
						.getJSONObject(i));
				moodBean.setCn_time(TimeUtility.getListTime(BingDateUtils
						.getTime(moodBean.getCreatetime())));
				list.add(moodBean);

				moodinfo.setMoodid(moods.getJSONObject(i).getString("id"));
				moodinfo.setContext(moods.getJSONObject(i).getString("context"));
				moodinfo.setMoodid(moods.getJSONObject(i).getString("id"));
				moodinfo.setCommentcount(moods.getJSONObject(i).getString(
						"commentcount"));
				moodinfo.setLaudcount(moods.getJSONObject(i).getString(
						"laudcount"));
				// moodinfo.setCommentid(moods.getJSONObject(i).getJSONObject("user").getString("commentid"));
				moodinfo.setUsername(moods.getJSONObject(i)
						.getJSONObject("user").getString("username"));
				moodinfo.setUheadimg(moods.getJSONObject(i)
						.getJSONObject("user").getString("headimage"));
				moodinfo.setTime(moods.getJSONObject(i).getString("createtime"));
				JSONArray imgs = moods.getJSONObject(i).getJSONArray("img");
				List<String> list = new ArrayList<String>();
				for (int j = 0; j < imgs.length(); j++) {
					list.add(MyHttpClient.IMAGE_URL + imgs.get(j));
					Log.i("imageURL", MyHttpClient.IMAGE_URL + imgs.get(j));
				}

				moodinfo.setImgs(list);
				int commentlength = moods.getJSONObject(i)
						.getJSONArray("comment").length();
				Log.i("commentlength", commentlength + "");
				Log.i("----comment----",
						moods.getJSONObject(i).getJSONArray("comment") + "");
				String comString = "";
				if (commentlength > 0) {
					for (int j = 0; j < commentlength; j++) {
						JSONObject comment = moods.getJSONObject(i)
								.getJSONArray("comment").getJSONObject(j);
						try {

							pingluncontent = comment.getString("content");

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						JSONObject userobj = comment.getJSONObject("user");
						String username = userobj.getString("username");

						comString = comString + username + ":" + pingluncontent
								+ "\n";
					}
					moodinfo.setComment(comString);
				}
				usermoodsList.add(moodinfo);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void parselistMood(String response) {
		try {
			JSONObject objo = new JSONObject(response);
			JSONArray moods = objo.getJSONArray("moods");
			for (int i = 0; i < moods.length(); i++) {
				MoodInfo moodinfo = new MoodInfo();
				MoodBean moodBean = JsonUtils.getMoodBean(moods
						.getJSONObject(i));
				moodBean.setCn_time(TimeUtility.getListTime(BingDateUtils
						.getTime(moodBean.getCreatetime())));
				list.add(moodBean);
				moodinfo.setMoodid(moods.getJSONObject(i).getString("id"));
				moodinfo.setContext(moods.getJSONObject(i).getString("context"));
				moodinfo.setMoodid(moods.getJSONObject(i).getString("id"));
				moodinfo.setCommentcount(moods.getJSONObject(i).getString(
						"commentcount"));
				moodinfo.setLaudcount(moods.getJSONObject(i).getString(
						"laudcount"));
				// moodinfo.setCommentid(moods.getJSONObject(i).getJSONObject("user").getString("commentid"));
				moodinfo.setUsername(moods.getJSONObject(i)
						.getJSONObject("user").getString("username"));
				moodinfo.setUheadimg(moods.getJSONObject(i)
						.getJSONObject("user").getString("headimage"));
				moodinfo.setTime(moods.getJSONObject(i).getString("createtime"));
				JSONArray imgs = moods.getJSONObject(i).getJSONArray("img");
				List<String> list = new ArrayList<String>();
				for (int j = 0; j < imgs.length(); j++) {
					list.add(MyHttpClient.IMAGE_URL + imgs.get(j));
					Log.i("imageURL", MyHttpClient.IMAGE_URL + imgs.get(j));
				}

				moodinfo.setImgs(list);
				int commentlength = moods.getJSONObject(i)
						.getJSONArray("comment").length();
				Log.i("commentlength", commentlength + "");
				Log.i("----comment----",
						moods.getJSONObject(i).getJSONArray("comment") + "");
				String comString = "";
				if (commentlength > 0) {
					for (int j = 0; j < commentlength; j++) {
						JSONObject comment = moods.getJSONObject(i)
								.getJSONArray("comment").getJSONObject(j);
						try {

							pingluncontent = comment.getString("content");

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						JSONObject userobj = comment.getJSONObject("user");
						String username = userobj.getString("username");

						comString = comString + username + ":" + pingluncontent
								+ "\n";
					}
					moodinfo.setComment(comString);
				}
				usermoodsList.add(moodinfo);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private ArrayList<HashMap<String, Object>> getList_Mood() {

		Log.i("����:usermoodsList", "����:usermoodsList" + usermoodsList);

		if (usermoodsList != null) {
			for (int i = 0; i < usermoodsList.size(); i++) {
				MoodInfo moods = usermoodsList.get(i);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("time", moods.getTime());
				map.put("moodid", moods.getMoodid());
				map.put("username", moods.getUsername());
				map.put("headimage", moods.getUheadimg());
				map.put("commentcount", moods.getCommentcount());
				// map.put("commentid", moods.getCommentid());
				map.put("laudcount", moods.getLaudcount());
				map.put("context", moods.getContext());
				map.put("username", moods.getUsername());
				map.put("comment", moods.getComment());
				List<String> imgurl = moods.getImgs();
				map.put("grid_pic", imgurl);
				listItem.add(map);
			}
			Log.i("----listItem------", listItem + "");
		}
		// ʱ������
		bingSort(listItem);
		Log.i("��ʱ��������listItem", listItem + "");
		// ʱ��ת�������켸�㡢���켸�㡣����
		getCnTime(listItem);
		// ɾ���ظ�Ԫ��
		removeDuplicate(listItem);
		Log.i(TAG, "listItem �µ�" + listItem);
		return listItem;
	}

	// ɾ���ظ�Ԫ��
	private void removeDuplicate(ArrayList<HashMap<String, Object>> listItem) {
		// TODO Auto-generated method stub
		for (int i = 0; i < listItem.size() - 1; i++) {
			for (int j = listItem.size() - 1; j > i; j--) {
				if (listItem.get(j).equals(listItem.get(i))) {
					listItem.remove(j);
				}
			}
		}
	}

	private class eoeBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			usermoodsList.clear();
			listItem.clear();
			doListMood();
			proCirAdapter.notifyDataSetChanged();

		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	/**
	 * ���ؽ��
	 * 
	 * @param jsonObject
	 *            json����
	 * @param key
	 *            ��ֵ
	 * @return
	 */
	public static String getKey(JSONObject jsonObject, String key) {
		if (jsonObject.has(key)) {
			try {
				return jsonObject.getString(key);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
		}

		return "";

	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		doListMood();
	}

	// ��������
	@Override
	public void onCommentClick(int position) {
		// TODO Auto-generated method stub
		circlemoodsid = list.get(position).getId();
		comPostion = position;
		// commentid = listItem.get(position).get("commentid").toString();
		if (sendView.isShown()) {
			wrapper.removeView(sendView);
		} else {
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					ActionBar.LayoutParams.FILL_PARENT,
					ActionBar.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			wrapper.addView(sendView, params);
		}
		// if (listItem.get(position).containsKey("moodid")) {
		// Log.i(TAG, "" + listItem.get(position).get("moodid"));
		// circlemoodsid = listItem.get(position).get("moodid").toString();
		// // commentid = listItem.get(position).get("commentid").toString();
		// if (sendView.isShown()) {
		// wrapper.removeView(sendView);
		// } else {
		// RelativeLayout.LayoutParams params=new
		// RelativeLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT,
		// ActionBar.LayoutParams.WRAP_CONTENT);
		// params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		// wrapper.addView(sendView,params);
		// }
		//
		// }

	}

	// ����
	@Override
	public void onZanClick(int position) {
		// TODO Auto-generated method stub
		// if (listItem.get(position).containsKey("moodid")) {
		// Log.i(TAG, "" + listItem.get(position).get("moodid"));
		// circlemoodsid = listItem.get(position).get("moodid").toString();
		// }
		final int mypostion = position;
		circlemoodsid = list.get(position).getId();
		AsyncHttpResponseHandler res = new AsyncHttpResponseHandler() {
			// private String laudcount;
			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				Log.i(TAG, response);
				try {
					JSONObject jsonObject = new JSONObject(response);
					if ("1".equals(jsonObject.getString("result"))) {
						// laudcount = jsonObject.getString("laudCount");
						// HashMap<String, Object> map = new HashMap<String,
						// Object>();
						// map.put("laudcount", laudcount);
						// listItem.add(map);
						list.get(mypostion).setLaudcount(
								list.get(mypostion).getLaudcount() + 1);
						proCirAdapter.notifyDataSetChanged();
						ToastUtils.showLong(
								projectCircle.this,
								projectCircle.this.getResources().getString(
										R.string.zansuccess));
					} else {
						ToastUtils.showLong(
								projectCircle.this,
								projectCircle.this.getResources().getString(
										R.string.zanfailure));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ToastUtils.showLong(projectCircle.this, projectCircle.this
							.getResources().getString(R.string.zanfailure));
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1, arg2, arg3);
				Log.i("failuer ", "failuer");

			}

		};
		MyHttpClient client = new MyHttpClient();
		client.postMoodLand(myid, circlemoodsid, res);

	}

	@Override
	public void onPicClick(int listPostino, int picPostion) {
		List<String> list = this.list.get(picPostion).getImg();
		Intent intent = new Intent();
		intent.putStringArrayListExtra("imgurls", (ArrayList<String>) list);
		intent.putExtra("postion", picPostion);
		intent.setClass(projectCircle.this, PhotoPagerActivity.class);
		startActivity(intent);
		// }
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub

	}

	/**
	 * ����ʱ������
	 * 
	 * @param list
	 */
	public static void bingSort(ArrayList<HashMap<String, Object>> list) {
		if (!list.isEmpty()) {
			Collections.sort(list, new Comparator<Map<String, Object>>() {

				@Override
				public int compare(Map<String, Object> lhs,
						Map<String, Object> rhs) {
					// TODO Auto-generated method stub
					return rhs.get("time").toString()
							.compareTo(lhs.get("time").toString());
				}
			});
		}
	}

	public static void getCnTime(ArrayList<HashMap<String, Object>> list) {
		int length = list.size();
		for (int i = 0; i < length; i++) {
			long time = BingDateUtils.getTime(list.get(i).get("time")
					.toString());
			list.get(i).put("new_time", TimeUtility.getListTime(time));
		}
	}

}
