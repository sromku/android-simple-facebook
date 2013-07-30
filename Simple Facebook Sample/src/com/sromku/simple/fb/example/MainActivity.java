package com.sromku.simple.fb.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sromku.simple.fb.Feed;
import com.sromku.simple.fb.Permissions;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebook.OnLoginOutListener;
import com.sromku.simple.fb.SimpleFacebook.OnPublishListener;
import com.sromku.simple.fb.SimpleFacebookConfiguration;

public class MainActivity extends Activity
{
	protected static final String TAG = "com.sromku.simple.fb.example";

	private static final String APP_ID = "625994234086470";
	private static final String APP_NAMESPACE = "sromkuapp";

	private SimpleFacebook mSimpleFacebook;

	private Button mButtonLogin;
	private Button mButtonLogout;
	private TextView mTextStatus;
	private Button mButtonPublishFeed;
	private Button mButtonPublishStory;
	private Button mButtonInviteAll;
	private Button mButtonInviteSuggested;
	private Button mButtonInviteOne;

	// Login / Logout listener
	private OnLoginOutListener mOnLoginOutListener = new SimpleFacebook.OnLoginOutListener()
	{

		@Override
		public void onFail()
		{
			Log.w(TAG, "Failed to login");
		}

		@Override
		public void onException(Throwable throwable)
		{
			Log.e(TAG, "Bad thing happened", throwable);
		}

		@Override
		public void onThinking()
		{
			// show progress bar or something to the user while login is happening
			mTextStatus.setText("Thinking...");
		}

		@Override
		public void onLogout()
		{
			// change the state of the button or do whatever you want
			mTextStatus.setText("Logged out");
			loggedOutUIState();
			toast("You are logged out");
		}

		@Override
		public void onLogin()
		{
			// change the state of the button or do whatever you want
			mTextStatus.setText("Logged in");
			loggedInUIState();
			toast("You are logged in");
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/*
		 * Initialize facebook
		 */
		inizializeFacebook();
		initUI();
		printHash();

		/*
		 * ---- Examples -----
		 */

		// 1. Login example
		mSimpleFacebook.setLogInOutListener(mOnLoginOutListener);
		loginExample();

		// 2. Logout example
		logoutExample();

		// 3. Publish feed example
		publishFeedExample();

		// 4. Publish open graph story example
		publishStoryExample();

		// 5. Invite examples
		inviteExamples();

		// 6. Get my profile example
		getMyProfileExample();
	}

	/**
	 * Initialize simple facebook
	 */
	private void inizializeFacebook()
	{
		Permissions[] permissions = new Permissions[]
		{
			Permissions.BASIC_INFO,
			Permissions.PUBLISH_ACTION
		};

		SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
			.setAppId(APP_ID)
			.setNamespace(APP_NAMESPACE)
			.setPermissions(permissions)
			.build();

		mSimpleFacebook = SimpleFacebook.getInstance(getApplicationContext());
		mSimpleFacebook.setConfiguration(configuration);
	}

	/**
	 * Login example.
	 */
	private void loginExample()
	{
		mButtonLogin.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				mSimpleFacebook.login(MainActivity.this);
			}
		});
	}

	/**
	 * Logout example
	 */
	private void logoutExample()
	{
		mButtonLogout.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				mSimpleFacebook.logout();
			}
		});
	}

	/**
	 * Publish feed example. <br>
	 * Must use {@link Permissions#PUBLISH_ACTION}
	 */
	private void publishFeedExample()
	{
		mButtonPublishFeed.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				OnPublishListener onPublishListener = new SimpleFacebook.OnPublishListener()
				{

					@Override
					public void onFail()
					{
						// insure that you are logged in before publishing
						Log.w(TAG, "Failed to publish");
					}

					@Override
					public void onException(Throwable throwable)
					{
						Log.e(TAG, "Bad thing happened", throwable);
					}

					@Override
					public void onThinking()
					{
						// show progress bar or something to the user while login is happening
						toast("Thinking...");
					}

					@Override
					public void onComplete(String postId)
					{
						toast("Published successfully. The new post id = " + postId);
					}
				};

				Feed feed = new Feed.Builder()
					.setMessage("Clone it out...")
					.setName("Simple Facebook for Android")
					.setCaption("Code less, do the same.")
					.setDescription("The Simple Facebook library project makes the life much easier by coding less code for being able to login, publish feeds and open graph stories, invite friends and more.")
					.setPicture("https://raw.github.com/sromku/android-simple-facebook/master/Refs/android_facebook_sdk_logo.png")
					.setLink("https://github.com/sromku/android-simple-facebook")
					.build();

				mSimpleFacebook.publish(feed, onPublishListener);
			}
		});
	}

	/**
	 * Publish story (open graph) example
	 */
	private void publishStoryExample()
	{
		// TODO Auto-generated method stub

	}

	/**
	 * Invite all, several, one friend/s examples
	 */
	private void inviteExamples()
	{
		// TODO Auto-generated method stub

	}

	/**
	 * Get my profile example
	 */
	private void getMyProfileExample()
	{
		// TODO Auto-generated method stub

	}

	private void initUI()
	{
		mButtonLogin = (Button)findViewById(R.id.button_login);
		mButtonLogout = (Button)findViewById(R.id.button_logout);
		mTextStatus = (TextView)findViewById(R.id.text_status);
		mButtonPublishFeed = (Button)findViewById(R.id.button_publish_feed);
		mButtonPublishStory = (Button)findViewById(R.id.button_publish_story);
		mButtonInviteAll = (Button)findViewById(R.id.button_invite_all);
		mButtonInviteSuggested = (Button)findViewById(R.id.button_invite_suggested);
		mButtonInviteOne = (Button)findViewById(R.id.button_invite_one);

		if (mSimpleFacebook.isLogin())
		{
			loggedInUIState();
		}
		else
		{
			loggedOutUIState();
		}
	}

	/**
	 * Show toast
	 * 
	 * @param message
	 */
	private void toast(String message)
	{
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}

	private void loggedInUIState()
	{
		mButtonLogin.setEnabled(false);
		mButtonLogout.setEnabled(true);
		mButtonPublishFeed.setEnabled(true);
		mButtonPublishStory.setEnabled(true);
		mButtonInviteAll.setEnabled(true);
		mButtonInviteSuggested.setEnabled(true);
		mButtonInviteOne.setEnabled(true);
		mTextStatus.setText("Logged in");
	}

	private void loggedOutUIState()
	{
		mButtonLogin.setEnabled(true);
		mButtonLogout.setEnabled(false);
		mButtonPublishFeed.setEnabled(false);
		mButtonPublishStory.setEnabled(false);
		mButtonInviteAll.setEnabled(false);
		mButtonInviteSuggested.setEnabled(false);
		mButtonInviteOne.setEnabled(false);
		mTextStatus.setText("Logged out");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * Print hash
	 */
	private void printHash()
	{
		try
		{
			PackageInfo info = getPackageManager().getPackageInfo(TAG,
				PackageManager.GET_SIGNATURES);
			for (Signature signature: info.signatures)
			{
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("YOURHASH KEY:",
					Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		}
		catch (NameNotFoundException e)
		{

		}
		catch (NoSuchAlgorithmException e)
		{

		}
	}

}
