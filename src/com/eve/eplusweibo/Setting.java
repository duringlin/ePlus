package com.eve.eplusweibo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import com.eve.eplusweibo.util.Log;
import com.eve.eplusweibo.util.Util;


/**
 * 缓存一些 配置 和 数据
 * @author zxd
 *
 */
public class Setting {

	private final  String TAG = Setting.class.getSimpleName();

	private static Setting instance = null;

	private SharedPreferences sharedPreferences;

	private final String PREFERENCES_FILE = "setting";
	private final String ACCOUNT_KEY = "account";
	

	private String account;
	private String sourceCode;
	
	private Setting(Context con) {
		sharedPreferences = con.getSharedPreferences(PREFERENCES_FILE, 0);
		sharedPreferences
		.registerOnSharedPreferenceChangeListener(new OnSharedPreferenceChangeListener() {
			public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
					String key) {
				Log.v(TAG, "onSharedPreferenceChanged==>" + key);
			}
		});
		
		read();

		this.sourceCode = Util.parseApkSource(con);
	}
	
	
	/**
	 * Setting is not s standard singleton , make sure init() is called first.
	 * @return
	 */
	public static Setting getInstance() {
		if (instance == null) {
			throw new IllegalStateException("call init() first.");
		}
		return instance;
	}
	
	public static void init(Context context) {
		if (context == null) {
			throw new NullPointerException();
		}

		instance = new Setting(context);
	}
	
	public static boolean isInitialized() {
		return (instance != null);
	}
	
	public static void reset() {
//		instance.setToken(null);
//		instance.setMoney(0);
	}
	
	private void read() {
		account = sharedPreferences.getString(ACCOUNT_KEY, null);
	}
	

	public void logout() {
		Editor editor = sharedPreferences.edit();
		editor.putString(ACCOUNT_KEY, null);
		editor.commit();
	}
	
	public String getAccount() {
		return account;
	}
	
	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	
	public void setAccount(String account) {
		this.account = account;
		Editor editor = sharedPreferences.edit();
		editor.putString(ACCOUNT_KEY, account);
		editor.commit();
	}
	
}
