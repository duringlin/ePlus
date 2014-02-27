package com.eve.eplusweibo.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

public class MessageDump {
	private static MessageDump instance = null;
	
	private HashMap<String, IMsgCallback> mCallbackMap = null;
	private Object obj = new Object();
	private Handler mHandler;
	private final int DISPACH = 0x1;
	
	public static MessageDump getInstance() {
		if (instance == null) {
			instance = new MessageDump();
		}
		return instance;
	}
	
	@SuppressLint("HandlerLeak")
	private MessageDump() {
		mCallbackMap = new HashMap<String, IMsgCallback>();
		mHandler = new Handler() {
			@Override
			public void dispatchMessage(Message msg) {

				if (msg.what == DISPACH) {
					synchronized (obj) {
						if (!mCallbackMap.isEmpty()) {
							Iterator<Entry<String, IMsgCallback>> it = mCallbackMap.entrySet().iterator();
							while (it.hasNext()) {
								Entry<String, IMsgCallback> entry = it.next();
								entry.getValue().onMsg((AppMessage) msg.obj);
							}
						}
					}
				}
			}
		};
	}
	
	public void sendMessageTo(String callBack,AppMessage msg)
	{
		IMsgCallback iBack = mCallbackMap.get(callBack);
		if(iBack != null)
			iBack.onMsg(msg);
	}
	
	public void destroy() {
		synchronized (obj) {
			if (mCallbackMap != null)
				mCallbackMap.clear();
		}
	}

	public String RegistryCallback(IMsgCallback con) {
		synchronized (obj) {
			String key = generateUUID();
			while (mCallbackMap.containsKey(key))
				key = generateUUID();

			MessageObj mo = new MessageObj(key, con);
			mCallbackMap.put(mo.getKey(), mo.getCallback());
			return key;
		}
	}

	public void UnRegistryCallback(String key) {
		synchronized (obj) {
			if (mCallbackMap != null && mCallbackMap.size() > 0) {
				mCallbackMap.remove(key);
			}
		}
	}

	public int dispatch(AppMessage msg) {
		synchronized (obj) {
			Message hMsg = mHandler.obtainMessage(DISPACH);
			hMsg.what = DISPACH;
			hMsg.obj = msg;
			if (mHandler != null)
				mHandler.sendMessage(hMsg);
		}
		return 0;
	}
	
	class MessageObj {
		String key;
		IMsgCallback callback;

		MessageObj(String key, IMsgCallback callback) {
			this.key = key;
			this.callback = callback;
		}

		public String getKey() {
			return key;
		}

		public IMsgCallback getCallback() {
			return callback;
		}
	}

	private String generateUUID() {
		String strUuid = "";
		Random rd = new Random(new Date().getTime());
		for (int i = 0; i < 32; i++) {
			char nibble = (char) (rd.nextInt() % 16);
			strUuid += (char) ((nibble < 10) ? ('0' + nibble) : ('a' + (nibble - 10)));
			if (i == 7 || i == 11 || i == 15 || i == 19) {
				strUuid += "-";
			}
		}
		return strUuid;
	}
}
