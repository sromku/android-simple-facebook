package com.sromku.simple.fb.example.friends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.example.R;

public class FriendsActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_friends);

		// add fragment of friends list
		addFragment();
	}

	private void addFragment() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		FriendsListFragment fragment = new FriendsListFragment();
		fragmentTransaction.add(R.id.frame_layout, fragment);
		fragmentTransaction.commit();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		SimpleFacebook.getInstance(this).onActivityResult(this, requestCode, resultCode, data);
	}
}
