package com.eve.eplusweibo.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eve.eplusweibo.R;
import com.eve.eplusweibo.util.AppMessage;
import com.eve.eplusweibo.util.IMsgCallback;
import com.eve.eplusweibo.util.MessageDump;

/**
 * MainFragment
 * 用于放置SNS timeline
 * @author zxd
 *
 */
public class MainFragment extends Fragment implements IMsgCallback{

	private final String TAG = MainFragment.class.getSimpleName();
	
	private String mCallbackKey;
	
	public MainFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_main, null);

//		initsViews(view);
		mCallbackKey = MessageDump.getInstance().RegistryCallback(this);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mCallbackKey != null) {
			MessageDump.getInstance().UnRegistryCallback(mCallbackKey);
			mCallbackKey = null;
		}
	}
	
	@Override
	public void onMsg(AppMessage msg) {
//		switch (msg.getMessageType()) {
//		case TaskType.HTTP_GET_FAMILY_LIST:
//			break;
//		}
		
	}

}
