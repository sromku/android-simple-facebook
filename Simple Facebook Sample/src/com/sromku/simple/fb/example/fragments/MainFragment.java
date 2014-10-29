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
import android.widget.Toast;

import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.example.Example;
import com.sromku.simple.fb.example.ExamplesAdapter;
import com.sromku.simple.fb.example.R;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnLogoutListener;

public class MainFragment extends Fragment implements OnItemClickListener {

	protected static final String TAG = MainFragment.class.getName();
	private Button mButtonLogin;
	private Button mButtonLogout;
	private TextView mTextStatus;
	private ListView mListView;

	private ArrayList<Example> mExamples;

	private SimpleFacebook mSimpleFacebook;
	private ExamplesAdapter mExamplesAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSimpleFacebook = SimpleFacebook.getInstance();

		mExamples = new ArrayList<Example>();
		mExamples.add(new Example("Requests", null, false));
		mExamples.add(new Example("Invite", InviteFragment.class, true));
		mExamples.add(new Example("Send message to one", SendMessageToOneFragment.class, true));
		mExamples.add(new Example("Send message to suggested", SendMessageToSuggestedFragment.class, true));
		mExamples.add(new Example("Publish - No Dialog", null, false));
		mExamples.add(new Example("Publish <strong>feed</strong>", PublishFeedFragment.class, true));
		mExamples.add(new Example("Publish <strong>feed</strong> - more options", PublishFeedMoreFragment.class, true));
		mExamples.add(new Example("Publish <strong>story</strong> - url", PublishStoryUrlFragment.class, true));
		mExamples.add(new Example("Publish <strong>story</strong> - id", PublishStoryIdFragment.class, true));
		mExamples.add(new Example("Publish <strong>story</strong> - user-owned", PublishStoryUserOwnedFragment.class, true));
		mExamples.add(new Example("Publish <strong>photo</strong>", PublishPhotoFragment.class, true));
		mExamples.add(new Example("Publish <strong>video</strong>", PublishVideoFragment.class, true));
		mExamples.add(new Example("Publish <strong>score</strong>", PublishScoreFragment.class, true));
		mExamples.add(new Example("Publish <strong>comment - text</strong>", PublishCommentFragment.class, true));
		mExamples.add(new Example("Publish <strong>comment - image</strong>", PublishCommentImageFragment.class, true));
		mExamples.add(new Example("Publish <strong>like</strong>", PublishLikeFragment.class, true));
		mExamples.add(new Example("Publish - With Dialog", null, false));
		mExamples.add(new Example("Publish <strong>feed</strong>", PublishFeedDialogFragment.class, false));
		mExamples.add(new Example("Publish <strong>story</strong> - url", PublishStoryUrlDialogFragment.class, false));
		mExamples.add(new Example("Publish <strong>story</strong> - id", PublishStoryIdDialogFragment.class, false));
		mExamples.add(new Example("Publish <strong>story</strong> - user-owned", PublishStoryUserOwnedDialogFragment.class, false));
		mExamples.add(new Example("Publish <strong>photo</strong>", PublishPhotoDialogFragment.class, false));
		mExamples.add(new Example("Publish multiple <strong>photos</strong>", PublishMultiplePhotosDialogFragment.class, false));
		mExamples.add(new Example("Get", null, false));
		mExamples.add(new Example("Get accounts", GetAccountsFragment.class, true));
		mExamples.add(new Example("Get albums", GetAlbumsFragment.class, true));
		mExamples.add(new Example("Get app requests", GetAppRequestsFragment.class, true));
		mExamples.add(new Example("Get books", GetBooksFragment.class, true));
		mExamples.add(new Example("Get comments", GetCommentsFragment.class, true));
		mExamples.add(new Example("Get events (attending)", GetEventsFragment.class, true));
		mExamples.add(new Example("Get family", GetFamilyFragment.class, true));
		mExamples.add(new Example("Get friends", GetFriendsFragment.class, true));
		mExamples.add(new Example("Get games", GetGamesFragment.class, true));
		mExamples.add(new Example("Get groups", GetGroupsFragment.class, true));
		mExamples.add(new Example("Get likes", GetLikesFragment.class, true));
		mExamples.add(new Example("Get movies", GetMoviesFragment.class, true));
		mExamples.add(new Example("Get music", GetMusicFragment.class, true));
		mExamples.add(new Example("Get notifications", GetNotificationsFragment.class, true));
		mExamples.add(new Example("Get page", GetPageFragment.class, true));
		mExamples.add(new Example("Get pages user like", GetPagesLikesFragment.class, true));
		mExamples.add(new Example("Get photos", GetPhotosFragment.class, true));
		mExamples.add(new Example("Get posts", GetPostsFragment.class, true));
		mExamples.add(new Example("Get profile", GetProfileFragment.class, true));
		mExamples.add(new Example("Get scores", GetScoresFragment.class, true));
		mExamples.add(new Example("Get television", GetTelevisionFragment.class, true));
		mExamples.add(new Example("Get videos", GetVideosFragment.class, true));
		mExamples.add(new Example("Open Graph Hosted Objects", null, false));
		mExamples.add(new Example("Create object", CreateStoryObjectFragment.class, true));
		mExamples.add(new Example("Get objects", GetStoryObjectsFragment.class, true));
		mExamples.add(new Example("Permissions", null, false));
		mExamples.add(new Example("Show granted permissions", GrantedPermissionsFragment.class, true));
		mExamples.add(new Example("Request new permissions", RequestPermissionsFragment.class, true));
		mExamples.add(new Example("Misc", null, false));
		mExamples.add(new Example("LikeView button", PublishLikeButtonFragment.class, true));
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

		mExamplesAdapter = new ExamplesAdapter(mExamples);
		mListView.setAdapter(mExamplesAdapter);
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
		} catch (Exception e) {
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
				mTextStatus.setText("Logged out");
				Toast.makeText(getActivity(), String.format("You didn't accept %s permissions", type.name()), Toast.LENGTH_SHORT).show();
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
		} else {
			loggedOutUIState();
		}
	}

	private void loggedInUIState() {
		mButtonLogin.setEnabled(false);
		mButtonLogout.setEnabled(true);
		mExamplesAdapter.setLogged(true);
		mTextStatus.setText("Logged in");
	}

	private void loggedOutUIState() {
		mButtonLogin.setEnabled(true);
		mButtonLogout.setEnabled(false);
		mExamplesAdapter.setLogged(false);
		mTextStatus.setText("Logged out");
	}
}
