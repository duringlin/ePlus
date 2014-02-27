package com.eve.eplusweibo.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.eve.eplusweibo.R;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		if(getIntent().getBooleanExtra("wannaExit", false)) {
			finish();
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

}
