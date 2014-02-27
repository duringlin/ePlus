package com.eve.eplusweibo.slidemenu;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.eve.eplusweibo.R;
import com.eve.eplusweibo.util.Util;

public class SlidActivityHelper {

	private Activity mActivity;

	private SlidMenu mSlidMenu;

	private View mViewAbove;

	private View mViewBehind;

	private boolean mBroadcasting = false;

	private boolean mOnPostCreateCalled = false;

	private boolean mEnableSlide = true;
	
	private long mlTouchTime = 0;

	/**
	 * Instantiates a new SlidActivityHelper.
	 *
	 * @param activity the associated activity
	 */
	public SlidActivityHelper(Activity activity) {
		mActivity = activity;
	}

	/**
	 * Sets mSlidMenu as a newly inflated mSlidMenu. Should be called within the activitiy's onCreate()
	 *
	 * @param savedInstanceState the saved instance state (unused)
	 */
	public void onCreate(Bundle savedInstanceState) {
		mSlidMenu = (SlidMenu) LayoutInflater.from(mActivity).inflate(R.layout.menu_slidmenumain, null);
	}

	/**
	 * Further mSlidMenu initialization. Should be called within the activitiy's onPostCreate()
	 *
	 * @param savedInstanceState the saved instance state (unused)
	 */
	public void onPostCreate(Bundle savedInstanceState) {
		if (mViewBehind == null || mViewAbove == null) {
			throw new IllegalStateException("Both setBehindContentView must be called " +
					"in onCreate in addition to setContentView.");
		}

		mOnPostCreateCalled = true;

		mSlidMenu.attachToActivity(mActivity, 
				mEnableSlide ? SlidMenu.SLID_WINDOW : SlidMenu.SLID_CONTENT);
		
		final boolean open;
		final boolean secondary;
		if (savedInstanceState != null) {
			open = savedInstanceState.getBoolean("SlidActivityHelper.open");
			secondary = savedInstanceState.getBoolean("SlidActivityHelper.secondary");
		} else {
			open = false;
			secondary = false;
		}
		new Handler().post(new Runnable() {
			public void run() {
				if (open) {
					if (secondary) {
						mSlidMenu.showSecondaryMenu(false);
					} else {
						mSlidMenu.showMenu(false);
					}
				} else {
					mSlidMenu.showContent(false);					
				}
			}
		});
	}

	/**
	 * Controls whether the ActionBar slides along with the above view when the menu is opened,
	 * or if it stays in place.
	 *
	 * @param slidingActionBarEnabled True if you want the ActionBar to slide along with the SlidMenu,
	 * false if you want the ActionBar to stay in place
	 */
	public void setSlidingActionBarEnabled(boolean slidingActionBarEnabled) {
		if (mOnPostCreateCalled)
			throw new IllegalStateException("enableSlidingActionBar must be called in onCreate.");
		mEnableSlide = slidingActionBarEnabled;
	}

	/**
	 * Finds a view that was identified by the id attribute from the XML that was processed in onCreate(Bundle).
	 * 
	 * @param id the resource id of the desired view
	 * @return The view if found or null otherwise.
	 */
	public View findViewById(int id) {
		View v;
		if (mSlidMenu != null) {
			v = mSlidMenu.findViewById(id);
			if (v != null)
				return v;
		}
		return null;
	}

	/**
	 * Called to retrieve per-instance state from an activity before being killed so that the state can be
	 * restored in onCreate(Bundle) or onRestoreInstanceState(Bundle) (the Bundle populated by this method
	 * will be passed to both). 
	 *
	 * @param outState Bundle in which to place your saved state.
	 */
	public void onSaveInstanceState(Bundle outState) {
		outState.putBoolean("SlidActivityHelper.open", mSlidMenu.isMenuShowing());
		outState.putBoolean("SlidActivityHelper.secondary", mSlidMenu.isSecondaryMenuShowing());
	}

	/**
	 * Register the above content view.
	 *
	 * @param v the above content view to register
	 * @param params LayoutParams for that view (unused)
	 */
	public void registerAboveContentView(View v, LayoutParams params) {
		if (!mBroadcasting)
			mViewAbove = v;
	}

	/**
	 * Set the activity content to an explicit view. This view is placed directly into the activity's view
	 * hierarchy. It can itself be a complex view hierarchy. When calling this method, the layout parameters
	 * of the specified view are ignored. Both the width and the height of the view are set by default to
	 * MATCH_PARENT. To use your own layout parameters, invoke setContentView(android.view.View,
	 * android.view.ViewGroup.LayoutParams) instead.
	 *
	 * @param v The desired content to display.
	 */
	public void setContentView(View v) {
		mBroadcasting = true;
		mActivity.setContentView(v);
	}

	/**
	 * Set the behind view content to an explicit view. This view is placed directly into the behind view 's view hierarchy.
	 * It can itself be a complex view hierarchy.
	 *
	 * @param view The desired content to display.
	 * @param layoutParams Layout parameters for the view. (unused)
	 */
	public void setBehindContentView(View view, LayoutParams layoutParams) {
		mViewBehind = view;
		mSlidMenu.setMenu(mViewBehind);
	}

	/**
	 * Gets the SlidMenu associated with this activity.
	 *
	 * @return the SlidMenu associated with this activity.
	 */
	public SlidMenu getSlidMenu() {
		return mSlidMenu;
	}

	/**
	 * Toggle the SlidMenu. If it is open, it will be closed, and vice versa.
	 */
	public void toggle() {
		mSlidMenu.toggle();
	}

	/**
	 * Close the SlidMenu and show the content view.
	 */
	public void showContent() {
		mSlidMenu.showContent();
	}

	/**
	 * Open the SlidMenu and show the menu view.
	 */
	public void showMenu() {
		mSlidMenu.showMenu();
	}

	/**
	 * Open the SlidMenu and show the secondary menu view. Will default to the regular menu
	 * if there is only one.
	 */
	public void showSecondaryMenu() {
		mSlidMenu.showSecondaryMenu();
	}

	/**
	 * On key up.
	 *
	 * @param keyCode the key code
	 * @param event the event
	 * @return true, if successful
	 */
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && mSlidMenu.isSecondaryMenuShowing()){
			showContent();
			return true;
		}else if (keyCode == KeyEvent.KEYCODE_BACK && !mSlidMenu.isMenuShowing()) {
			showMenu();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK && mSlidMenu.isMenuShowing()) {
			long currentTime = System.currentTimeMillis();
			if ((currentTime - mlTouchTime) >= 2000) {
				Util.showToast(mActivity, mActivity.getString(R.string.quit_again_toast));
				mlTouchTime = currentTime;
			} else {
				Util.release(mActivity);
				return false;
			}
			return true;
		}
		return false;
	}

}
