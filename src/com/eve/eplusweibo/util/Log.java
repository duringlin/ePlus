package com.eve.eplusweibo.util;

import com.eve.eplusweibo.Global;

public class Log {

	public static void i(String TAG, String msg) {
		if (Global.DEBUG)
			android.util.Log.i(TAG, msg);
	}

	public static void v(String TAG, String msg) {
		if (Global.DEBUG)
			android.util.Log.v(TAG, msg);
	}

	public static void d(String TAG, String msg) {
		if (Global.DEBUG)
			android.util.Log.d(TAG, msg);
	}

	public static void e(String TAG, String msg) {
		if (Global.DEBUG)
			android.util.Log.e(TAG, msg);
	}

	public static void w(String TAG, String msg) {
		if (Global.DEBUG)
			android.util.Log.w(TAG, msg);
	}

}
