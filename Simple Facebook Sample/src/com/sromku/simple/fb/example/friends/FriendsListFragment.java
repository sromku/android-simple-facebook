package com.sromku.simple.fb.example.friends;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.example.R;
import com.sromku.simple.fb.listeners.OnFriendsListener;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnLogoutListener;

public class FriendsListFragment extends Fragment {
	protected static final String TAG = FriendsListFragment.class.getName();

	private SimpleFacebook mSimpleFacebook;

	private Button mButtonLogin;
	private Button mButtonLogout;
	private Button mButtonGetFriends;
	private TextView mTextStatus;
	private ListView mFriendsList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// inflate layout xml
		View view = inflater.inflate(R.layout.fragment_list_friends, container, false);

		// login button and set on click
		mButtonLogin = (Button) view.findViewById(R.id.button_login);
		mButtonLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mSimpleFacebook.login(mOnLoginListener);
			}
		});

		// logout button and set on click
		mButtonLogout = (Button) view.findViewById(R.id.button_logout);
		mButtonLogout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mSimpleFacebook.logout(mOnLogoutListener);
			}
		});

		// button to get friends
		mButtonGetFriends = (Button) view.findViewById(R.id.button_get_friends);
		mButtonGetFriends.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mSimpleFacebook.getFriends(mOnFriendsListener);
			}
		});

		// the status that shows if you are logged in or not
		mTextStatus = (TextView) view.findViewById(R.id.text_status);

		// list of friends
		mFriendsList = (ListView) view.findViewById(R.id.list);

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		mSimpleFacebook = SimpleFacebook.getInstance(getActivity());
		setUIState();
	}

	/**
	 * Set buttons and all ui controls to be enabled or disabled according to
	 * facebook login status
	 */
	private void setUIState() {
		if (mSimpleFacebook.isLogin()) {
			loggedInUIState();
		}
		else {
			loggedOutUIState();
		}
	}

	/**
	 * You are logged in
	 */
	private void loggedInUIState() {
		mButtonLogin.setEnabled(false);
		mButtonLogout.setEnabled(true);
		mFriendsList.setEnabled(true);
		mButtonGetFriends.setEnabled(true);
		mTextStatus.setText("Logged in");
	}

	/**
	 * You are logged out
	 */
	private void loggedOutUIState() {
		mButtonLogin.setEnabled(true);
		mButtonLogout.setEnabled(false);
		mFriendsList.setEnabled(false);
		mButtonGetFriends.setEnabled(false);
		mTextStatus.setText("Logged out");
	}

	// Login listener
	private OnLoginListener mOnLoginListener = new OnLoginListener() {

		@Override
		public void onFail(String reason) {
			mTextStatus.setText(reason);
			Log.w(TAG, "Failed to login");
		}

		@Override
		public void onException(Throwable throwable) {
			mTextStatus.setText("Exception: " + throwable.getMessage());
			Log.e(TAG, "Bad thing happened", throwable);
		}

		@Override
		public void onThinking() {
			// show progress bar or something to the user while login is
			// happening
			mTextStatus.setText("Thinking...");
		}

		@Override
		public void onLogin() {
			// change the state of the button or do whatever you want
			mTextStatus.setText("Logged in");
			loggedInUIState();
			toast("You are logged in");
		}

		@Override
		public void onNotAcceptingPermissions(Permission.Type type) {
			toast("You didn't accept read permissions");
		}
	};

	// Logout listener
	private OnLogoutListener mOnLogoutListener = new OnLogoutListener() {

		@Override
		public void onFail(String reason) {
			mTextStatus.setText(reason);
			Log.w(TAG, "Failed to login");
		}

		@Override
		public void onException(Throwable throwable) {
			mTextStatus.setText("Exception: " + throwable.getMessage());
			Log.e(TAG, "Bad thing happened", throwable);
		}

		@Override
		public void onThinking() {
			// show progress bar or something to the user while login is
			// happening
			mTextStatus.setText("Thinking...");
		}

		@Override
		public void onLogout() {
			// change the state of the button or do whatever you want
			mTextStatus.setText("Logged out");
			loggedOutUIState();
			toast("You are logged out");
		}
	};

	// get friends listener
	private OnFriendsListener mOnFriendsListener = new OnFriendsListener() {

		@Override
		public void onFail(String reason) {
			// insure that you are logged in before getting the friends
			Log.w(TAG, reason);
		}

		@Override
		public void onException(Throwable throwable) {
			Log.e(TAG, "Bad thing happened", throwable);
		}

		@Override
		public void onThinking() {
			// show progress bar or something to the user while fetching profile
			Log.i(TAG, "Thinking...");
			toast("Getting friends...");
		}

		@Override
		public void onComplete(List<Profile> friends) {
			// populate list
			List<String> values = new ArrayList<String>();
			for (Profile profile : friends) {
				values.add(profile.getName());
			}
			ArrayAdapter<String> friendsListAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
			mFriendsList.setAdapter(friendsListAdapter);
		}

	};

	/**
	 * Show toast
	 * 
	 * @param message
	 */
	private void toast(String message) {
		Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
	}
}
