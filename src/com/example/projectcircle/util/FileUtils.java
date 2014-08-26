package com.example.projectcircle.util;

import android.os.Environment;

import java.io.File;

public class FileUtils {

	public static final String HEALTHMA = Environment.getExternalStorageDirectory()+"/wyy";
	public static final String HEALTH_IMAG = Environment.getExternalStorageDirectory()+"/wyy/image";
	public static final String PIC_PATH = HEALTH_IMAG + "/wyy.png";
	public static final String HEAD_PATH="wyyhead";
	// �ֻ�·��
	public static final String WYY_PIC = "bing";

	/**
	 * �����ļ���
	 * 
	 * @param path
	 */
	public static void createAll_Path() {
		File file = new File(HEALTHMA);
		if (!file.exists()) {
			file.mkdir();
		}
	}

	/**
	 * �����ļ���
	 * 
	 * @param path
	 */
	public static void createPath() {
		createAll_Path();
		File file = new File(HEALTH_IMAG);
		if (!file.exists()) {
			file.mkdir();
		}
	}

}
