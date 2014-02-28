package com.eve.eplusweibo.account;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import android.util.Log;

import com.eve.eplusweibo.struct.AccountModel;

/**
 * SharedPreferences 帐号信息管理
 * @author zxd
 *
 */
public class AccountKeeper {
	private final String TAG = AccountKeeper.class.getSimpleName();
	
	
	public static final String  SPNAME = "accountkeeper";
	
	public static final String  ACCOUNT_COUNT = "account_count";
	
	
	SharedPreferences mSharePreference = null;
	static AccountKeeper mInstance = null;

	public static AccountKeeper getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new AccountKeeper(context);
		}
		return mInstance;
	}

	private AccountKeeper(Context context) {
		mSharePreference = context.getSharedPreferences(SPNAME, Activity.MODE_PRIVATE);
	}
	
	/**
	 * 增
     * 创建新帐号，缓存中不能有已存在的Key
     * 创建成功后，增加ACCOUNT_COUNT的值
     * 将model对象转换成byte数组并编码   写到SharePreference中
     * @param model
     * @return
     */
    public boolean createNewAccount(AccountModel model){
    	if(model.getUid().isEmpty()){
    		Log.e(TAG, "createNewAccount faild , no uid in model!");
    		return false;
    	}
    	
    	if(getString(model.getUid(), null) != null){
    		Log.e(TAG, "createNewAccount faild , already have this uid!");
    		return false;
    	}
    	
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(baos);
	    	oos.writeObject(model);
	    	oos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
    	
    	String modelBase64 = Base64.encodeToString(baos.toByteArray() , Base64.DEFAULT); 
    	boolean boolPutString = putString(model.getUid(), modelBase64);
    	if(boolPutString){
    		int count = getInt(ACCOUNT_COUNT);
    		if(count == -1){
    			putInt(ACCOUNT_COUNT, 0);
    			count = 0;
    		}
    		if(putInt(ACCOUNT_COUNT, ++count)){
    			Log.i(TAG, "you add a new account.you have "+count+" now.");
    			return true;
    		}else return false;
    	}else return false;
    }
    

    /**
     * 删
     * 通过UID移除某个帐号
     * @param uid
     * @return
     */
    public boolean deleteAccount(String uid){
    	if(uid.isEmpty()){
    		Log.e(TAG, "deleteAccount faild , uid is null");
    		return false;
    	}
    	
        Editor editor = mSharePreference.edit();
        editor.remove(uid);
        boolean boolDelete =editor.commit();

        if(boolDelete){
    		int count = getInt(ACCOUNT_COUNT);
    		if(count == -1){
    			putInt(ACCOUNT_COUNT, 0);
    			count = 0;
    		}
    		if(putInt(ACCOUNT_COUNT, ++count)){
    			Log.i(TAG, "you add a new account.you have "+count+" now.");
    			return true;
    		}else return false;
    	}else return false;
    }
    
    /**
     * 查
     * 从SP中获取编码，并解码成AccountModel类型
     * @param uid
     * @return
     */
    public AccountModel getAccount(String uid){
    	if(uid.isEmpty()){
    		Log.e(TAG, "getAccount faild , uid is null");
    		return null;
    	}

    	AccountModel model;
    	String dataString = getString(uid, null);
    	
    	byte[] bytes = Base64.decode(dataString.getBytes() , Base64.DEFAULT);
    	ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
    	
    	try {
        	ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
    		model = (AccountModel) objectInputStream.readObject();
			objectInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
    		return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
    		return null;
		}
    	return model;
    }
    
	
	/**
	 * 改
     * 修改帐号信息，缓存中必须有该账户信息 ，并且不能修改UID
     * 将model对象转换成byte数组并编码   写到SharePreference中
     * @param model
     * @return
     */
	public boolean putAccount(AccountModel model){
    	if(model.getUid().isEmpty()){
    		Log.e(TAG, "putAccount faild , no uid in model!");
    		return false;
    	}
    	
    	if(getString(model.getUid(), null) == null){
    		Log.e(TAG, "putAccount faild , no this account data!");
    		return false;
    	}
    	
    	if(getAccount(model.getUid()) != null && !getAccount(model.getUid()).equals(model.getUid())){
    		Log.e(TAG, "putAccount faild , uid can't changed!");
    		return false;
    	}
    	
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(baos);
	    	oos.writeObject(model);
	    	oos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
    	
    	String modelBase64 = Base64.encodeToString(baos.toByteArray() , Base64.DEFAULT); 
    	return putString(model.getUid(), modelBase64);
    }
	
	/** 
     * 获取配置中名称为name项的数值，如果配置中没有该项目，返回为null。
     * @param name 项目名称
     * @return 获取的数据值
     */
    public String getString(String name) {
        return mSharePreference.getString(name, null);
    }
    
    /** 
     * 获取配置中名称为name项的数值，如果配置中没有该项目，返回默认值。
     * @param name 项目名称
     * @param defultValue 默认值
     * @return 获取的数据值
     */
    public String getString(String name,String defultValue) {    
        return mSharePreference.getString(name, defultValue);
    }

    /** 
     * 设置配置项的值,如果配置项没有，就新增一条配置信息，否则修改原有配置信息。
     * @param name 配置项名称
     * @param value 配置项数值
     * @return 设置是否成功
     */
    public boolean putString(String name, String value) {
        Editor editor = mSharePreference.edit();
        editor.putString(name, value);
        return editor.commit();
    }

    public int getInt(String name) {     
        return mSharePreference.getInt(name, -1);
    }

    public boolean putInt(String name, int value) {
        Editor editor = mSharePreference.edit();
        editor.putInt(name, value);
        return editor.commit();
    }
    
    public Long getLong(String name) {   
        return mSharePreference.getLong(name,0);
    }
    
    public Long getLong(String name,long defultValue) {    
        return mSharePreference.getLong(name, defultValue);
    }

    public boolean putLong(String name, long value) {
        Editor editor = mSharePreference.edit();
        editor.putLong(name, value);
        return editor.commit();
    }
    
    public boolean getBoolean(String name) {   
        return mSharePreference.getBoolean(name,false);
    }
    
    public boolean getBoolean(String name,boolean defultValue) {    
        return mSharePreference.getBoolean(name, defultValue);
    }

    public boolean putBoolean(String name, boolean value) {
        Editor editor = mSharePreference.edit();
        editor.putBoolean(name, value);
        return editor.commit();
    }

}
