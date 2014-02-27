package com.eve.eplusweibo.slidemenu;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.eve.eplusweibo.Global;
import com.eve.eplusweibo.R;
import com.eve.eplusweibo.main.LeftMenuFragment;

public class BaseActivity extends SlidFragmentActivity {



	public void onCreate(Bundle savedInstanceState, String fragment) {
		super.onCreate(savedInstanceState);
		//设置左边
		setBehindContentView(R.layout.menu_frame);
		FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
		t.replace(R.id.menu_frame, new LeftMenuFragment());
		t.commit();

		//菜单相关属性
		SlidMenu sm = getSlidMenu();
		//设置中间和菜单两边的阴影
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.menu_shadow);
		//设置切到菜单时，中间的留多少可见
		int rightSet = (int)(Global.windowsWidth/Global.density)-(int)(426/Global.density);
		if(Global.windowsWidth == 0 || Global.density == 0 || rightSet <= 40) {
			sm.setBehindOffsetRes(R.dimen.slidmenu_offset);
		} else {
			sm.setBehindOffset(rightSet);
		}
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidMenu.TOUCHMODE_MARGIN);
	}

}
