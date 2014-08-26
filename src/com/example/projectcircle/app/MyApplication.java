package com.example.projectcircle.app;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.widget.Toast;

import com.baidu.frontia.FrontiaApplication;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.example.projectcircle.bean.MyPersonBean;
import com.example.projectcircle.bean.PersonalInfo;
import com.example.projectcircle.bean.UserInfo;
import com.example.projectcircle.other.ImageInfo;
import com.example.projectcircle.util.FileUtils;
import com.example.projectcircle.util.SdUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;


/*
 * ������Ĺ�����ʵ����Application�ļ̳��࣬��ô������Ҫ�������Ϊcom.baidu.frontia.FrontiaApplication��
 * �����û��ʵ��Application�ļ̳��࣬��ô������AndroidManifest.xml��Application��ǩ���������ԣ� 
 * <application android:name="com.baidu.frontia.FrontiaApplication"
 * ������
 */
public class MyApplication  extends FrontiaApplication  {
	private static UserInfo info;
	public static final String strKey = "xerN6Ac7GdD3iAVwVRguCVlQ";
	
	private static MyPersonBean myPersonBean;
	private static MyApplication mInstance = null;
	public boolean m_bKeyRight = true;
	BMapManager mBMapManager = null;

	// public static final String strKey = "ThX0TveZUUOFkv6pjG7w45MF";
	// public static final String strKey = "kX80TaX65sg2aTf6CeF1DEiN";
	// bing api key
	// public static final String strKey = "N0p7SXZ0oy7vi89590qwSYTQ";
//	private static Foods foods;
	private static List<ImageInfo> headerImaList = new ArrayList<ImageInfo>();

	public static MyPersonBean getMyPersonBean() {
		return myPersonBean;
	}

	public static void setMyPersonBean(MyPersonBean myPersonBean) {
		MyApplication.myPersonBean = myPersonBean;
	}
	public static List<ImageInfo> getHeaderImaList() {
		return headerImaList;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		initImageLoader(getApplicationContext());
//		if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
//			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//					.detectAll().penaltyDialog().build());
//			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//					.detectAll().penaltyDeath().build());
//		}

//		super.onCreate();
		mInstance = this;
//		initEngineManager(this);
//		initImageLoader(getApplicationContext());
//		if (SdUtils.ExistSDCard()) {
//		LoadDate();
//		}

	}

	/** ��ʼ��ͼƬ������������Ϣ **/
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)// ����ͼƬ���߳���
				.denyCacheImageMultipleSizesInMemory() // ����ͼ��Ĵ�ߴ罫���ڴ��л�����ǰ����ͼ���С�ߴ硣
				.discCacheFileNameGenerator(new Md5FileNameGenerator())// ���ô��̻����ļ�����
				.tasksProcessingOrder(QueueProcessingType.LIFO)// ���ü�����ʾͼƬ���н���
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);


	}

	public static void setPersonInfo(UserInfo info) {
		MyApplication.info = info;
	}
	public static  UserInfo getPersonInfo() {
		return info;
	}

//	public void initEngineManager(Context context) {
//		if (mBMapManager == null) {
//			mBMapManager = new BMapManager(context);
//		}
//
//		if (!mBMapManager.init(strKey, new MyGeneralListener())) {
//			Toast.makeText(MyApplication.getInstance().getApplicationContext(),
//					"BMapManager  ��ʼ������!", Toast.LENGTH_LONG).show();
//		}
//	}

	public static MyApplication getInstance() {
		return mInstance;
	}

	// �����¼���������������ͨ�������������Ȩ��֤�����
	static class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				Toast.makeText(
						MyApplication.getInstance().getApplicationContext(),
						"���������������", Toast.LENGTH_LONG).show();
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				Toast.makeText(
						MyApplication.getInstance().getApplicationContext(),
						"������ȷ�ļ���������", Toast.LENGTH_LONG).show();
			}
			// ...
		}

		@Override
		public void onGetPermissionState(int iError) {
			// ����ֵ��ʾkey��֤δͨ��
			if (iError != 0) {
				// ��ȨKey����
				// Toast.makeText(
				// MyApplication.getInstance().getApplicationContext(),
				// "���� DemoApplication.java�ļ�������ȷ����ȨKey,������������������Ƿ�������error: "
				// + iError, Toast.LENGTH_LONG).show();
				MyApplication.getInstance().m_bKeyRight = false;
			} else {
				MyApplication.getInstance().m_bKeyRight = true;
				// Toast.makeText(
				// MyApplication.getInstance().getApplicationContext(),
				// "key��֤�ɹ�", Toast.LENGTH_LONG).show();
			}
		}
	}

	/**
	 * ��������
	 */
	public static void LoadDate() {
		FileUtils.createPath();
		File[] files = new File(FileUtils.HEALTH_IMAG).listFiles();
		File f;
		String name;
		String path;

		if (null==files) {
			return;
		}
		int length = files.length;
		if (length == 0) {
			return;
		}
		for (int i = 0; i < length; i++) {
			ImageInfo info = new ImageInfo();
			f = files[i];
			if (!f.canRead()) {
				return;
			}

			name = f.getName().substring(f.getName().indexOf("_") + 1,
					f.getName().length() - 4);
			path = FileUtils.HEALTH_IMAG + "/" + f.getName();
			info.setImaname(name);
			info.setImapath(path);
			headerImaList.add(info);
		}

	}
	
	
}
