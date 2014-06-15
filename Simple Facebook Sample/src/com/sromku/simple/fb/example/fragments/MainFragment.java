package com.sromku.simple.fb.example.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.example.Example;
import com.sromku.simple.fb.example.ExamplesAdapter;
import com.sromku.simple.fb.example.R;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnLogoutListener;

public class MainFragment extends Fragment implements OnItemClickListener{

	protected static final String TAG = MainFragment.class.getName();
	private Button mButtonLogin;
	private Button mButtonLogout;
	private TextView mTextStatus;
	private ListView mListView;

	private ArrayList<Example> mExamples;

	private SimpleFacebook mSimpleFacebook;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSimpleFacebook = SimpleFacebook.getInstance();
		
		mExamples = new ArrayList<Example>();
		mExamples.add(new Example("Requests", null));
		mExamples.add(new Example("Invite", InviteFragment.class));
		mExamples.add(new Example("Send message to one", SendMessageToOneFragment.class));
		mExamples.add(new Example("Send message to suggested", SendMessageToSuggestedFragment.class));
		mExamples.add(new Example("Publish", null));
		mExamples.add(new Example("Publish feed - dialog", PublishFeedDialogFragment.class));
		mExamples.add(new Example("Publish feed - no dialog", PublishFeedFragment.class));
		mExamples.add(new Example("Publish feed more options - no dialog", PublishFeedMoreFragment.class));
		mExamples.add(new Example("Publish story - no dialog", PublishStoryFragment.class));
		mExamples.add(new Example("Publish photo", PublishPhotoFragment.class));
		mExamples.add(new Example("Publish video", PublishVideoFragment.class));
		mExamples.add(new Example("Publish score", PublishScoreFragment.class));
		mExamples.add(new Example("Get", null));
		mExamples.add(new Example("Get accounts", GetAccountsFragment.class));
		mExamples.add(new Example("Get albums", GetAlbumsFragment.class));
		mExamples.add(new Example("Get app requests", GetAppRequestsFragment.class));
		mExamples.add(new Example("Get books", GetBooksFragment.class));
		mExamples.add(new Example("Get comments", GetCommentsFragment.class));
		mExamples.add(new Example("Get events (attending)", GetEventsFragment.class));
		mExamples.add(new Example("Get family", GetFamilyFragment.class));
		mExamples.add(new Example("Get friends", GetFriendsFragment.class));
		mExamples.add(new Example("Get games", GetGamesFragment.class));
		mExamples.add(new Example("Get groups", GetGroupsFragment.class));
		mExamples.add(new Example("Get likes", GetLikesFragment.class));
		mExamples.add(new Example("Get movies", GetMoviesFragment.class));
		mExamples.add(new Example("Get music", GetMusicFragment.class));
		mExamples.add(new Example("Get notifications", GetNotificationsFragment.class));
		mExamples.add(new Example("Get page", GetPageFragment.class));
		mExamples.add(new Example("Get pages user like", GetPagesLikesFragment.class));
		mExamples.add(new Example("Get photos", GetPhotosFragment.class));
		mExamples.add(new Example("Get posts", GetPostsFragment.class));
		mExamples.add(new Example("Get profile", GetProfileFragment.class));
		mExamples.add(new Example("Get scores", GetScoresFragment.class));
		mExamples.add(new Example("Get objects (open graph)", GetStoryObjectsFragment.class));
		mExamples.add(new Example("Get television", GetTelevisionFragment.class));
		mExamples.add(new Example("Get videos", GetVideosFragment.class));
		mExamples.add(new Example("Misc", null));
		mExamples.add(new Example("Create object", CreateStoryObjectFragment.class));
//		mExamples.add(new Example("Pagination", PermissionsFragment.class));
//		mExamples.add(new Example("Privacy", PermissionsFragment.class));
//		mExamples.add(new Example("Configuration", PermissionsFragment.class));
//		mExamples.add(new Example("Granted permissions", PermissionsFragment.class));
//		mExamples.add(new Example("Request new permissions", PermissionsFragment.class));
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getActivity().setTitle("Simple Facebook Sample");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		
		mButtonLogin = (Button) view.findViewById(R.id.button_login);
		mButtonLogout = (Button) view.findViewById(R.id.button_logout);
		mTextStatus = (TextView) view.findViewById(R.id.text_status);
		mListView = (ListView) view.findViewById(R.id.list);

		setLogin();
		setLogout();
		
		mListView.setAdapter(new ExamplesAdapter(mExamples));
		mListView.setOnItemClickListener(this);
		
		setUIState();
		return view;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Class<? extends Fragment> fragment = mExamples.get(position).getFragment();
		if (fragment != null) {
			addFragment(fragment);
		}
	}

	private void addFragment(Class<? extends Fragment> fragment) {
		try {
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.frame_layout, fragment.newInstance());
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
		}
		catch (Exception e) {
			Log.e(TAG, "Failed to add fragment", e);
		}
	}
	
	/**
	 * Login example.
	 */
	private void setLogin() {
		// Login listener
		final OnLoginListener onLoginListener = new OnLoginListener() {

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
			}

			@Override
			public void onNotAcceptingPermissions(Permission.Type type) {
//				toast(String.format("You didn't accept %s permissions", type.name()));
			}
		};

		mButtonLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mSimpleFacebook.login(onLoginListener);
			}
		});
	}

	/**
	 * Logout example
	 */
	private void setLogout() {
		final OnLogoutListener onLogoutListener = new OnLogoutListener() {

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
			}

		};

		mButtonLogout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mSimpleFacebook.logout(onLogoutListener);
			}
		});
	}
	
	private void setUIState() {
		if (mSimpleFacebook.isLogin()) {
			loggedInUIState();
		}
		else {
			loggedOutUIState();
		}
	}

	private void loggedInUIState() {
		mButtonLogin.setEnabled(false);
		mButtonLogout.setEnabled(true);
		mListView.setVisibility(View.VISIBLE);
		mTextStatus.setText("Logged in");
	}

	private void loggedOutUIState() {
		mButtonLogin.setEnabled(true);
		mButtonLogout.setEnabled(false);
		mListView.setVisibility(View.INVISIBLE);
		mTextStatus.setText("Logged out");
	}
}
