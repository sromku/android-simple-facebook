package com.sromku.simple.fb.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.example.fragments.MainFragment;
import com.sromku.simple.fb.example.utils.Utils;

public class MainActivity extends FragmentActivity {
	protected static final String TAG = MainActivity.class.getName();

	private SimpleFacebook mSimpleFacebook;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSimpleFacebook = SimpleFacebook.getInstance(this);

		// test local language
		Utils.updateLanguage(getApplicationContext(), "en");
		Utils.printHashKey(getApplicationContext());

		setContentView(R.layout.activity_main);
		addFragment();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSimpleFacebook = SimpleFacebook.getInstance(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data);
	}

	private void addFragment() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.frame_layout, new MainFragment());
		fragmentTransaction.commit();
	}

}
