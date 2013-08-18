package com.sromku.simple.fb.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sromku.simple.fb.Feed;
import com.sromku.simple.fb.Permissions;
import com.sromku.simple.fb.Profile;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebook.OnFriendsRequestListener;
import com.sromku.simple.fb.SimpleFacebook.OnInviteListener;
import com.sromku.simple.fb.SimpleFacebook.OnLoginOutListener;
import com.sromku.simple.fb.SimpleFacebook.OnProfileRequestListener;
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
	private Button mButtonGetProfile;
	private Button mButtonGetFriends;

	// Login / Logout listener
	private OnLoginOutListener mOnLoginOutListener = new SimpleFacebook.OnLoginOutListener()
	{

		@Override
		public void onFail(String reason)
		{
			mTextStatus.setText("Failed to login");
			Log.w(TAG, "Failed to login");
		}

		@Override
		public void onException(Throwable throwable)
		{
			mTextStatus.setText("Exception: " + throwable.getMessage());
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

		@Override
		public void onNotAcceptingPermissions()
		{
			toast("You didn't accept publish permissions");
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// test local language
		updateLanguage("en");
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
		getProfileExample();

		// 7. Get friends example
		getFriendsExample();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * Initialize simple facebook
	 */
	private void inizializeFacebook()
	{
		Permissions[] permissions = new Permissions[]
		{
			Permissions.BASIC_INFO,
			Permissions.EMAIL,
			Permissions.USER_BIRTHDAY,
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
		// listener for publishing action
		final OnPublishListener onPublishListener = new SimpleFacebook.OnPublishListener()
		{

			@Override
			public void onFail(String reason)
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
				// show progress bar or something to the user while publishing
				toast("Thinking...");
			}

			@Override
			public void onComplete(String postId)
			{
				toast("Published successfully. The new post id = " + postId);
			}
		};

		// feed builder
		final Feed feed = new Feed.Builder()
			.setMessage("Clone it out...")
			.setName("Simple Facebook for Android")
			.setCaption("Code less, do the same.")
			.setDescription("The Simple Facebook library project makes the life much easier by coding less code for being able to login, publish feeds and open graph stories, invite friends and more.")
			.setPicture("https://raw.github.com/sromku/android-simple-facebook/master/Refs/android_facebook_sdk_logo.png")
			.setLink("https://github.com/sromku/android-simple-facebook")
			.build();

		// click on button and publish
		mButtonPublishFeed.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
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
		// listener for invite action
		final OnInviteListener onInviteListener = new SimpleFacebook.OnInviteListener()
		{

			@Override
			public void onFail(String reason)
			{
				// insure that you are logged in before inviting
				Log.w(TAG, reason);
			}

			@Override
			public void onException(Throwable throwable)
			{
				Log.e(TAG, "Bad thing happened", throwable);
			}

			@Override
			public void onComplete()
			{
				toast("Invitation was sent");
			}

			@Override
			public void onCancel()
			{
				toast("Canceled the dialog");
			}
		};

		// invite all
		mButtonInviteAll.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// will open dialog with all my friends
				mSimpleFacebook.invite(MainActivity.this, "I invite you to use this app", onInviteListener);
			}
		});

		// invite suggested
		mButtonInviteSuggested.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String[] friends = new String[]
				{
					"630243197",
					"584419361",
					"1456233371",
					"100000490891462"
				};
				mSimpleFacebook.invite(MainActivity.this, friends, "I invite you to use this app", onInviteListener);
			}
		});

		// invite one
		mButtonInviteOne.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				String friend = "630243197";
				mSimpleFacebook.invite(MainActivity.this, friend, "I invite you to use this app", onInviteListener);
			}
		});
	}

	/**
	 * Get my profile example
	 */
	private void getProfileExample()
	{
		// listener for profile request
		final OnProfileRequestListener onProfileRequestListener = new SimpleFacebook.OnProfileRequestListener()
		{

			@Override
			public void onFail(String reason)
			{
				// insure that you are logged in before getting the profile
				Log.w(TAG, reason);
			}

			@Override
			public void onException(Throwable throwable)
			{
				Log.e(TAG, "Bad thing happened", throwable);
			}

			@Override
			public void onThinking()
			{
				// show progress bar or something to the user while fetching profile
				Log.i(TAG, "Thinking...");
			}

			@Override
			public void onComplete(Profile profile)
			{
				Log.i(TAG, "My profile id = " + profile.getId());
				toast("My profile id = " + profile.getId() + ", " + profile.getEmail());
			}
		};

		// set on click button
		mButtonGetProfile.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// example 1
				mSimpleFacebook.getProfile(onProfileRequestListener);

				// // - example 2
				// mSimpleFacebook.getProfile(new OnProfileRequestAdapter()
				// {
				// @Override
				// public void onComplete(Profile profile)
				// {
				// String id = profile.getId();
				// String firstName = profile.getFirstName();
				// String birthday = profile.getBirthday();
				// String email = profile.getEmail();
				// String bio = profile.getBio();
				// // ... and many more properties of profile ...
				// }
				//
				// });
			}
		});

	}

	/**
	 * Get my profile example
	 */
	private void getFriendsExample()
	{
		// listener for friends request
		final OnFriendsRequestListener onFriendsRequestListener = new SimpleFacebook.OnFriendsRequestListener()
		{

			@Override
			public void onFail(String reason)
			{
				// insure that you are logged in before getting the friends
				Log.w(TAG, reason);
			}

			@Override
			public void onException(Throwable throwable)
			{
				Log.e(TAG, "Bad thing happened", throwable);
			}

			@Override
			public void onThinking()
			{
				// show progress bar or something to the user while fetching profile
				Log.i(TAG, "Thinking...");
			}

			@Override
			public void onComplete(List<Profile> friends)
			{
				Log.i(TAG, "Number of friends = " + friends.size());
				toast("Number of friends = " + friends.size());
			}

		};

		// set button
		mButtonGetFriends.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				mSimpleFacebook.getFriends(onFriendsRequestListener);
			}
		});

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
		mButtonGetProfile = (Button)findViewById(R.id.button_get_profile);
		mButtonGetFriends = (Button)findViewById(R.id.button_get_friends);

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
		mButtonGetProfile.setEnabled(true);
		mButtonGetFriends.setEnabled(true);
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
		mButtonGetProfile.setEnabled(false);
		mButtonGetFriends.setEnabled(false);
		mTextStatus.setText("Logged out");
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
				String keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
				Log.d(TAG, "keyHash: " + keyHash);
			}
		}
		catch (NameNotFoundException e)
		{

		}
		catch (NoSuchAlgorithmException e)
		{

		}
	}

	/**
	 * Update language
	 * 
	 * @param code The language code. Like: en, cz, iw, ...
	 */
	private void updateLanguage(String code)
	{
		Locale locale = new Locale(code);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
	}

	public class OnProfileRequestAdapter implements OnProfileRequestListener
	{

		@Override
		public void onThinking()
		{
		}

		@Override
		public void onException(Throwable throwable)
		{
		}

		@Override
		public void onFail(String reason)
		{
		}

		@Override
		public void onComplete(Profile profile)
		{
		}

	}

}
