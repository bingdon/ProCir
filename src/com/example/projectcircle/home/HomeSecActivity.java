package com.example.projectcircle.home;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;







import com.example.projectcircle.R;
import com.example.projectcircle.personal.ModifyMySelf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class HomeSecActivity extends Activity implements OnWheelChangedListener {


	/**
	 * ��ȫ����ʡ��������Ϣ��json�ĸ�ʽ���棬������ɺ�ֵΪnull
	 */
	private JSONObject mJsonObj;
	/**
	 * ʡ��WheelView�ؼ�
	 */
	private WheelView mProvince;
	/**
	 * �е�WheelView�ؼ�
	 */
	private WheelView mCity;
	/**
	 * ����WheelView�ؼ�
	 */
	private WheelView mArea;

	/**
	 * ����ʡ
	 */
	private String[] mProvinceDatas;
	/**
	 * key - ʡ value - ��s
	 */
	private Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/**
	 * key - �� values - ��s
	 */
	private Map<String, String[]> mAreaDatasMap = new HashMap<String, String[]>();

	/**
	 * ��ǰʡ������
	 */
	private String mCurrentProviceName;
	/**
	 * ��ǰ�е�����
	 */
	private String mCurrentCityName;
	/**
	 * ��ǰ��������
	 */
	private String mCurrentAreaName ="";

	private static final String TAG=HomeSecActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home_sec);

		initJsonData();

		mProvince = (WheelView) findViewById(R.id.id_province);
		mCity = (WheelView) findViewById(R.id.id_city);
		mArea = (WheelView) findViewById(R.id.id_area);

		initDatas();

		mProvince.setViewAdapter(new ArrayWheelAdapter<String>(this, mProvinceDatas));
		// ���change�¼�
		mProvince.addChangingListener(this);
		// ���change�¼�
		mCity.addChangingListener(this);
		// ���change�¼�
		mArea.addChangingListener(this);

		mProvince.setVisibleItems(5);
		mCity.setVisibleItems(5);
		mArea.setVisibleItems(5);
		updateCities();
		updateAreas();

	}

	/**
	 * ���ݵ�ǰ���У�������WheelView����Ϣ
	 */
	private void updateAreas()
	{
		int pCurrent = mCity.getCurrentItem();
		if (null==mCitisDatasMap.get(mCurrentProviceName)) {
			return;
		}
		Log.i(TAG, "����:"+mCitisDatasMap.get(mCurrentProviceName));
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mAreaDatasMap.get(mCurrentCityName);
	
		if (areas == null)
		{
			areas = new String[] { "" };
		}	
		mCurrentAreaName = areas[0];		
		mArea.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
		mArea.setCurrentItem(0);		
	}

	/**
	 * ���ݵ�ǰ��ʡ��������WheelView����Ϣ
	 */
	private void updateCities()
	{
		int pCurrent = mProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null)
		{
			cities = new String[] { "" };
		}
		mCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
		mCity.setCurrentItem(0);		
		updateAreas();
	}

	/**
	 * ��������Json������ɺ��ͷ�Json������ڴ�
	 */
	private void initDatas()
	{
		try
		{
			JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
			mProvinceDatas = new String[jsonArray.length()];
			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject jsonP = jsonArray.getJSONObject(i);// ÿ��ʡ��json����
				String province = jsonP.getString("p");// ʡ����

				mProvinceDatas[i] = province;

				JSONArray jsonCs = null;
				try
				{
					/**
					 * Throws JSONException if the mapping doesn't exist or is
					 * not a JSONArray.
					 */
					jsonCs = jsonP.getJSONArray("c");
				} catch (Exception e1)
				{
					continue;
				}
				String[] mCitiesDatas = new String[jsonCs.length()];
				for (int j = 0; j < jsonCs.length(); j++)
				{
					JSONObject jsonCity = jsonCs.getJSONObject(j);
					String city = jsonCity.getString("n");// ������
					mCitiesDatas[j] = city;
					JSONArray jsonAreas = null;
					try
					{
						/**
						 * Throws JSONException if the mapping doesn't exist or
						 * is not a JSONArray.
						 */
						jsonAreas = jsonCity.getJSONArray("a");
					} catch (Exception e)
					{
						continue;
					}

					String[] mAreasDatas = new String[jsonAreas.length()];// ��ǰ�е�������
					for (int k = 0; k < jsonAreas.length(); k++)
					{
						String area = jsonAreas.getJSONObject(k).getString("s");// ���������
						mAreasDatas[k] = area;
					}
					mAreaDatasMap.put(city, mAreasDatas);
				}

				mCitisDatasMap.put(province, mCitiesDatas);
			}

		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		mJsonObj = null;
	}

	/**
	 * ��assert�ļ����ж�ȡʡ������json�ļ���Ȼ��ת��Ϊjson����
	 */
	private void initJsonData()
	{
		try
		{
			StringBuffer sb = new StringBuffer();
			// assets�ļ���������ļ����Ǳ���ԭʼ���ļ���ʽ����Ҫ��AssetManager���ֽ�������ʽ��ȡ�ļ���
		    // 1. ����Activity�������getAssets() ����ȡAssetManager���á�
		    //2. ����AssetManager��open(String fileName, int accessMode) ������ָ����ȡ���ļ��Լ�����ģʽ���ܵõ�������InputStream��
			InputStream is = getAssets().open("city.json");
			int len = -1;
			byte[] buf = new byte[1024];
//			���������ж�ȡһ���������ֽڣ�������洢�ڻ��������� b �С�
//			��������ʽ����ʵ�ʶ�ȡ���ֽ��������������ݿ��á���⵽�ļ�ĩβ�����׳��쳣ǰ���˷���һֱ������
//			��� b �ĳ���Ϊ 0���򲻶�ȡ�κ��ֽڲ����� 0�����򣬳��Զ�ȡ����һ���ֽڡ�
//			�����Ϊ��λ���ļ�ĩβ��û�п��õ��ֽڣ��򷵻�ֵ -1���������ٶ�ȡһ���ֽڲ�����洢�� b �С�����ȡ�ĵ�һ���ֽڴ洢��Ԫ�� b[0] �У���һ���洢�� b[1] �У��������ơ�
//			��ȡ���ֽ��������� b �ĳ��ȡ��� k Ϊʵ�ʶ�ȡ���ֽ�������Щ�ֽڽ��洢�� b[0] �� b[k-1] ��Ԫ���У���Ӱ�� b[k] �� b[b.length-1] ��Ԫ�ء� 
			while ((len = is.read(buf)) != -1)
			{
				sb.append(new String(buf, 0, len, "gbk"));
			}
			is.close();
			mJsonObj = new JSONObject(sb.toString());
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * change�¼��Ĵ���
	 */
	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue)
	{
		if (wheel == mProvince)
		{
			updateCities();				  
	
		} else if (wheel == mCity)
		{
			updateAreas();		
		
		} else if (wheel == mArea)
		{
			
		mCurrentAreaName = mAreaDatasMap.get(mCurrentCityName)[newValue];
		Log.i("mCurrentAreaName2", mCurrentAreaName);
		}
	}

	public void showChoose(View view)
	{
		
		 Intent intent = new Intent(HomeSecActivity.this, ModifyMySelf.class);  
         String passString = mCurrentProviceName +" "+ mCurrentCityName + " "+mCurrentAreaName;  
         intent.putExtra("home", passString);  
         setResult(RESULT_OK, intent);  
         finish();
		
		Toast.makeText(this, mCurrentProviceName + mCurrentCityName + mCurrentAreaName, 1).show();
	}

	
}
