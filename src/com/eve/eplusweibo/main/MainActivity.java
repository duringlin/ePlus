package com.eve.eplusweibo.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;

import com.eve.eplusweibo.R;
import com.eve.eplusweibo.slidemenu.BaseActivity;
import com.eve.eplusweibo.slidemenu.SlidMenu;
import com.eve.eplusweibo.util.AppMessage;
import com.eve.eplusweibo.util.IMsgCallback;
import com.eve.eplusweibo.util.MessageDump;
import com.eve.eplusweibo.util.Util;

public class MainActivity extends BaseActivity implements IMsgCallback{

	private final String TAG = MainActivity.class.getSimpleName();
	private String mCallbackKey;
	private SlidMenu mSlidMenu;
	private Fragment mContent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mCallbackKey = MessageDump.getInstance().RegistryCallback(this);

		Util.initParameters(this);
		Util.setCurrentContext(MainActivity.this);
		
		loadSlideMenu();
		
		// 设置中间
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment( savedInstanceState, "mContent");
		if (mContent == null) {
				mContent = new MainFragment();
		}
		setContentView(R.layout.menu_content_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mContent).commit();
		
	}

	/**
	 * 配置侧滑栏相关属性
	 */
	private void loadSlideMenu(){
		mSlidMenu = getSlidMenu();
		mSlidMenu.setMode(SlidMenu.LEFT);
		mSlidMenu.setTouchModeAbove(SlidMenu.TOUCHMODE_FULLSCREEN);
	}
	
	/**
	 * 切换中间Fragment
	 * @param -fragment -要换成的fragment
	 * @param -hideRight -true(隐藏右边),false(显示)
	 * @param -isMargin -true(两边触摸),false(全屏触摸)
	 */
	public void switchContent(Fragment fragment, boolean hideRight, boolean isMargin) {
		if (fragment != null) {
			mContent = fragment;
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
			if (hideRight) {
				mSlidMenu.setMode(SlidMenu.LEFT);
			} else {
				mSlidMenu.setMode(SlidMenu.LEFT_RIGHT);
			}
			if (isMargin) {
				mSlidMenu.setTouchModeAbove(SlidMenu.TOUCHMODE_MARGIN);
			} else {
				mSlidMenu.setTouchModeAbove(SlidMenu.TOUCHMODE_FULLSCREEN);
			}
		}
		getSlidMenu().showContent();
	}

	/**
	 * 切到右边
	 */
	public void switchToRight() {
		mSlidMenu.showSecondaryMenu(true);
	}

	/**
	 * 切到左边
	 */
	public void switchToLeft() {
		mSlidMenu.showMenu();
	}
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mContent = null;
		mSlidMenu = null;
		MessageDump.getInstance().UnRegistryCallback(mCallbackKey);
		mCallbackKey = null;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onMsg(AppMessage msg) {

	}

}
