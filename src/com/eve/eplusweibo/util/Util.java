package com.eve.eplusweibo.util;

import java.io.InputStream;
import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.eve.eplusweibo.Global;
import com.eve.eplusweibo.Setting;
import com.eve.eplusweibo.main.StartActivity;
import com.eve.eplusweibo.sns.HttpManager;

public class Util {
	private final static String TAG = Util.class.getSimpleName();
	
	private static Context mContext;
	
	public static void setCurrentContext(Context context) {
		mContext = context;
	}

	public static Context getCurrentoContext() {
		return mContext;
	}

	public static void showToast(Context con, String str) {
		Toast.makeText(con, str, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(Context con, int strResId) {
		Toast.makeText(con, strResId, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 退出程序
	 * @param mContext
	 */
	public static void release(Context mContext) {
		MessageDump.getInstance().destroy();
		HttpManager.getInstance().destroy();

        Intent intent = new Intent();
        intent.setClass(mContext, StartActivity.class); 
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //清空栈
        intent.putExtra("wannaExit", true);
        mContext.startActivity(intent);
	}
	
	/**
	 * 初始化 基本参数
	 * @param context
	 */
	public static void initParameters(Context context)
	{
		Log.v(TAG, ">>>initParameters<<<<");
		Setting.init(context);
		MessageDump.getInstance();
		DisplayMetrics display = context.getResources().getDisplayMetrics();
		Global.windowsWidth = display.widthPixels;
		Global.windowsHeight = display.heightPixels;
		Global.density = display.density;

		//状态栏高度
		if (Global.statusBarHeight == 0 && context != null && context instanceof Activity) {
			Activity ac = (Activity) context;
			Global.statusBarHeight = Util.getStatebarHeight(ac);
//			Log.v(TAG, "statusBarHeight = " + Global.statusBarHeight);
		}
	}
	
	/**
	 * 获取状态栏高度
	 * @param ac
	 * @return
	 */
	public static int getStatebarHeight(Activity ac) {
		int x = 0, sbar = 0;
		Rect frame = new Rect();
		((Activity) ac).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		sbar = frame.top;
		if (sbar == 0) {
			Class<?> c = null;
			Object obj = null;
			Field field = null;
			try {
				c = Class.forName("com.android.internal.R$dimen");
				obj = c.newInstance();
				field = c.getField("status_bar_height");
				x = Integer.parseInt(field.get(obj).toString());
				sbar = ac.getResources().getDimensionPixelSize(x);
			} catch (Exception e1) {
				Log.e(TAG, "get status bar height fail");
				e1.printStackTrace();
			}
		}
		return sbar;
	}
	
	/**
	 * 从apksource.dat中读取APK来源
	 * @param con
	 * @return
	 */
	public static String parseApkSource(Context con) {
		Log.v(TAG, "parseApkSource");
		String retStr = "iniStr";
		try {
			// Return an AssetManager instance for your application's package
			InputStream is = con.getResources().getAssets().open("apksource.dat");
			int size = is.available();

			// Read the entire asset into a local byte buffer.
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			// Convert the buffer into a string.
			String src = new String(buffer);
			if (src != null)
				retStr = src;
			// .substring(src.indexOf("apkSource=") + 10).trim();
			Log.i(TAG, "apkSource ->" + retStr);
		} catch (Exception e) {
			// Should never happen!
			e.printStackTrace();
			Log.e(TAG, "eee-" + e.getMessage());
			retStr = "Exception:" + e.getMessage();
		}
		return retStr;
	}
	
}
