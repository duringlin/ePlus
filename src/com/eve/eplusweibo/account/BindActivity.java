package com.eve.eplusweibo.account;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.eve.eplusweibo.Global;
import com.eve.eplusweibo.R;
import com.eve.eplusweibo.util.AppMessage;
import com.eve.eplusweibo.util.IMsgCallback;
import com.eve.eplusweibo.util.Log;
import com.eve.eplusweibo.util.MessageDump;
import com.eve.eplusweibo.util.Util;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.sso.SsoHandler;

/**
 * 绑定SNS帐号
 * @author zxd
 *
 */
public class BindActivity extends Activity implements IMsgCallback{

	private final String TAG = BindActivity.class.getSimpleName();
	private String mCallbackKey;
	private Weibo mSinaWeibo;
	private SsoHandler mSinaSsoHandler;
	
	// 新浪微博SSO Handle
	private View mBtnSina;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind);

		mCallbackKey = MessageDump.getInstance().RegistryCallback(this);

		mSinaWeibo = Weibo.getInstance(Global.SINA_CONSUMER_KEY, Global.SINA_REDIRECT_URL);
		
		init();
		
	}
	
	private void init(){
		mBtnSina = (View) findViewById(R.id.btn_sina);
		mBtnSina.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mSinaSsoHandler = new SsoHandler(BindActivity.this , mSinaWeibo);
				mSinaSsoHandler.authorize(new AuthDialogListener());
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	/**
	 * 新浪微博 SSO认证回调
	 * @author zxd
	 */
	class AuthDialogListener implements WeiboAuthListener {
		@SuppressLint("SimpleDateFormat")
		@Override
		public void onComplete(Bundle values) {
			Log.i(TAG , values.toString());
			String uid = values.getString("uid");
			String expires_in = values.getString("expires_in");
			String token = values.getString("access_token");
			
			Oauth2AccessToken sinaAccessToken = new Oauth2AccessToken(token, expires_in);
			if(sinaAccessToken.isSessionValid()){
				String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(sinaAccessToken.getExpiresTime()));
				Log.i(TAG , "认证授权成功: uid："+uid+" ,access_token：" + token + " ,expires_in：" + expires_in + " ,有效期：" + date);
				//TODO 保存信息
			}
		}

		@Override
		public void onError(WeiboDialogError e) {
			Log.i(TAG , "Auth error : " + e.getMessage());
			Util.showToast(BindActivity.this, "认证错误:" + e.getMessage() );
		}

		@Override
		public void onCancel() {
			Log.i(TAG ,"Auth cancel");
			Util.showToast(BindActivity.this, "认证取消");
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Log.e(TAG ,"Auth exception : " + e.getMessage());
			Util.showToast(BindActivity.this, "认证失败:" + e.getMessage() );
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		MessageDump.getInstance().UnRegistryCallback(mCallbackKey);
		mCallbackKey = null;
	}
	
	@Override
	public void onMsg(AppMessage msg) {
		
	}

}
