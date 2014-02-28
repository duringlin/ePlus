package com.eve.eplusweibo.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.eve.eplusweibo.R;
import com.eve.eplusweibo.account.BindActivity;

public class StartActivity extends Activity {
	private final String TAG = StartActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		if(getIntent().getBooleanExtra("wannaExit", false)) {
			finish();
		}
		
		
		
		Intent intent = new Intent(this,BindActivity.class);
		startActivity(intent);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

}
