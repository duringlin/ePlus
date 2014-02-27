package com.eve.eplusweibo.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eve.eplusweibo.R;
import com.eve.eplusweibo.sns.TaskType;
import com.eve.eplusweibo.util.AppMessage;
import com.eve.eplusweibo.util.IMsgCallback;
import com.eve.eplusweibo.util.Log;
import com.eve.eplusweibo.util.MessageDump;

/**
 * 侧滑栏
 * @author zxd
 *
 */
public class LeftMenuFragment extends Fragment implements IMsgCallback {

	private final String TAG = LeftMenuFragment.class.getSimpleName();

	private String mCallbackKey;

	public LeftMenuFragment() {

	}


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mCallbackKey = MessageDump.getInstance().RegistryCallback(this);

		View view = inflater.inflate(R.layout.activity_main, null);
//		initsViews(view);

		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		MessageDump.getInstance().UnRegistryCallback(mCallbackKey);
		mCallbackKey = null;
	}


	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}


	@Override
	public void onMsg(AppMessage msg) {

		Log.i(TAG, "onMsg->" + msg.getMessageType());

		switch (msg.getMessageType()) {
		case TaskType.HTTP_GET_BIND_ACCOUNTS:
			break;
		}
	}

}
