package com.example.projectcircle.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import com.example.projectcircle.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

/**
 * ͼƬ������
 * 
 * @author lyl
 * 
 */
public class PhotoUtils {

	public static Uri imageFileUri;

	// ͼƬ�ϴ�ѡ��;��
	public static void MyDialog(final Activity context) {
		final CharSequence[] items = { "���", "����" };
		AlertDialog dlg = new AlertDialog.Builder(context).setTitle("��������")
				.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						// ����item�Ǹ���ѡ��ķ�ʽ��
						// ��items�������涨�������ַ�ʽ�����յ��±�Ϊ1���Ծ͵������շ���
						if (item == 1) {
							Intent getImageByCamera = new Intent(
									"android.media.action.IMAGE_CAPTURE");
							context.startActivityForResult(getImageByCamera, 1);
						} else {
							Intent getImage = new Intent(
									Intent.ACTION_GET_CONTENT);
							getImage.addCategory(Intent.CATEGORY_OPENABLE);
							getImage.setType("image/jpeg");
							context.startActivityForResult(getImage, 0);
						}
					}
				}).create();
		dlg.show();
	}

	/**
	 * ����ͼƬ���ֻ��ڴ���
	 * 
	 * @param data
	 *            ���������
	 * @param context
	 *            ������
	 */
	public static void saveChatCode(byte[] data, Context context) {

		File file = new File(FileUtils.WYY_PIC);
		try {
			if (file.exists()) {
				file.delete();
			}
			FileOutputStream ops = context.openFileOutput(FileUtils.WYY_PIC,
					Context.MODE_PRIVATE);
			ObjectOutputStream outputStream = null;
			outputStream = new ObjectOutputStream(ops);
			outputStream.writeObject(data);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * ����ͼƬ���ֻ��ڴ�
	 * 
	 * @param bitmap
	 * @param context
	 */
	public static void saveChatCode(Bitmap bitmap, Context context) {

		File file = new File(FileUtils.HEAD_PATH);
		try {
			if (file.exists()) {
				file.delete();
			}
			FileOutputStream ops = context.openFileOutput(FileUtils.HEAD_PATH,
					Context.MODE_PRIVATE);
			ObjectOutputStream outputStream = null;
			outputStream = new ObjectOutputStream(ops);
			outputStream.writeObject(bitmap);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	
	// ͼƬ�ϴ�ѡ��;��
	public static void secPic(final Activity context) {
		final CharSequence[] items = { "���", "����" };
		AlertDialog dlg = new AlertDialog.Builder(context).setTitle("ѡ��ͼƬ")
				.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						// ����item�Ǹ���ѡ��ķ�ʽ��
						// ��items�������涨�������ַ�ʽ�����յ��±�Ϊ1���Ծ͵������շ���
						if (item == 1) {
							if (SdUtils.ExistSDCard()) {
								try {
									imageFileUri = context
											.getContentResolver()
											.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
													new ContentValues());
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}

							} else {
								imageFileUri = context
										.getContentResolver()
										.insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
												new ContentValues());
							}

							if (imageFileUri != null) {
								Intent getImageByCamera = new Intent(
										"android.media.action.IMAGE_CAPTURE");

								getImageByCamera
										.putExtra(
												android.provider.MediaStore.EXTRA_OUTPUT,
												imageFileUri);

								context.startActivityForResult(
										getImageByCamera, 1);
							} else {
								Toast.makeText(
										context,
										context.getResources().getString(
												R.string.cant_insert_album),
										Toast.LENGTH_SHORT).show();
							}

						} else {
							Intent getImage = new Intent(
									Intent.ACTION_GET_CONTENT);
							getImage.addCategory(Intent.CATEGORY_OPENABLE);
							getImage.setType("image/jpeg");
							context.startActivityForResult(getImage, 0);
						}
					}
				}).create();
		dlg.show();
	}

	@SuppressWarnings("deprecation")
	public static String getPicPathFromUri(Uri uri, Activity activity) {
		String value = uri.getPath();

		if (value.startsWith("/external")) {
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = activity.managedQuery(uri, proj, null, null, null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} else {
			return value;
		}
	}

}
