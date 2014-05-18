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

	// /**
	// * Publish feed example. <br>
	// * Must use {@link Permissions#PUBLISH_ACTION}
	// */
	// private void publishFeedExample() {
	// // listener for publishing action
	// final OnPublishListener onPublishListener = new OnPublishListener() {
	//
	// @Override
	// public void onFail(String reason) {
	// hideDialog();
	// // insure that you are logged in before publishing
	// Log.w(TAG, "Failed to publish");
	// }
	//
	// @Override
	// public void onException(Throwable throwable) {
	// hideDialog();
	// Log.e(TAG, "Bad thing happened", throwable);
	// }
	//
	// @Override
	// public void onThinking() {
	// // show progress bar or something to the user while publishing
	// showDialog();
	// }
	//
	// @Override
	// public void onComplete(String postId) {
	// hideDialog();
	// toast("Published successfully. The new post id = " + postId);
	// }
	// };
	//
	// // feed builder
	// final Feed feed = new Feed.Builder()
	// .setMessage("Clone it out...")
	// .setName("Simple Facebook SDK for Android")
	// .setCaption("Code less, do the same.")
	// .setDescription("Login, publish feeds and stories, invite friends and more....")
	// .setPicture("https://raw.github.com/sromku/android-simple-facebook/master/Refs/android_facebook_sdk_logo.png").setLink("https://github.com/sromku/android-simple-facebook")
	// .build();
	//
	//
	// // click on button and publish
	// mButtonPublishFeed.setOnClickListener(new View.OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// mSimpleFacebook.publish(feed, true, onPublishListener);
	// }
	// });
	// }
	//
	// /**
	// * Publish story (open graph) example
	// */
	// private void publishStoryExample() {
	// }
	//
	// /**
	// * Publish photo example. <br>
	// * Must use {@link Permissions#PUBLISH_STREAM}
	// */
	// private void publishPhotoExample() {
	// mButtonPublishPhoto.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// // take screenshot
	// final Bitmap bitmap = Utils.takeScreenshot(MainActivity.this);
	//
	// // set privacy
	// Privacy privacy = new Privacy.Builder()
	// .setPrivacySettings(PrivacySettings.SELF)
	// .build();
	//
	//
	// // create Photo instance and add some properties
	// Photo photo = new Photo.Builder()
	// .setImage(bitmap)
	// .setName("Screenshot from #android_simple_facebook sample application")
	// .setPlace("110619208966868")
	// .setPrivacy(privacy)
	// .build();
	//
	// // publish
	// mSimpleFacebook.publish(photo, new OnPublishListener() {
	//
	// @Override
	// public void onFail(String reason) {
	// hideDialog();
	// // insure that you are logged in before publishing
	// Log.w(TAG, "Failed to publish");
	// toast(reason);
	// }
	//
	// @Override
	// public void onException(Throwable throwable) {
	// hideDialog();
	// Log.e(TAG, "Bad thing happened", throwable);
	// }
	//
	// @Override
	// public void onThinking() {
	// // show progress bar or something to the user while
	// // publishing
	// showDialog();
	// }
	//
	// @Override
	// public void onComplete(String id) {
	// hideDialog();
	// toast("Published successfully. The new image id = " + id);
	// }
	// });
	// }
	// });
	// }
	//
	// /**
	// * Invite all, several, one friend/s examples
	// */
	// private void inviteExamples() {
	// // listener for invite action
	// final OnInviteListener onInviteListener = new OnInviteListener() {
	//
	// @Override
	// public void onFail(String reason) {
	// // insure that you are logged in before inviting
	// Log.w(TAG, reason);
	// }
	//
	// @Override
	// public void onException(Throwable throwable) {
	// Log.e(TAG, "Bad thing happened", throwable);
	// }
	//
	// @Override
	// public void onComplete(List<String> invitedFriends, String requestId) {
	// toast("Invitation was sent to " + invitedFriends.size() +
	// " users, invite request=" + requestId);
	// }
	//
	// @Override
	// public void onCancel() {
	// toast("Canceled the dialog");
	// }
	//
	// };
	//
	// // invite all
	// mButtonInviteAll.setOnClickListener(new View.OnClickListener() {
	//
	// @Override
	// public void onClick(View arg0) {
	// // will open dialog with all my friends
	// mSimpleFacebook.invite("I invite you to use this app", onInviteListener,
	// "secret data");
	// }
	// });
	//
	// // invite suggested
	// mButtonInviteSuggested.setOnClickListener(new View.OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// String[] friends = new String[] { "630243197", "584419361", "1456233371",
	// "100000490891462" };
	// mSimpleFacebook.invite(friends, "I invite you to use this app",
	// onInviteListener, "secret data");
	// }
	// });
	//
	// // invite one
	// mButtonInviteOne.setOnClickListener(new View.OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// String friend = "630243197";
	// mSimpleFacebook.invite(friend, "I invite you to use this app",
	// onInviteListener, "secret data");
	// }
	// });
	// }
	//
	// /**
	// * Get my profile example
	// */
	// private void getProfileExample() {
	// // listener for profile request
	// final OnProfileListener onProfileListener = new OnProfileListener() {
	//
	// @Override
	// public void onFail(String reason) {
	// hideDialog();
	// // insure that you are logged in before getting the profile
	// Log.w(TAG, reason);
	// }
	//
	// @Override
	// public void onException(Throwable throwable) {
	// hideDialog();
	// Log.e(TAG, "Bad thing happened", throwable);
	// }
	//
	// @Override
	// public void onThinking() {
	// showDialog();
	// // show progress bar or something to the user while fetching
	// // profile
	// Log.i(TAG, "Thinking...");
	// }
	//
	// @Override
	// public void onComplete(Profile profile) {
	// hideDialog();
	// Log.i(TAG, "My profile id = " + profile.getId());
	// String name = profile.getName();
	// String email = profile.getEmail();
	// toast("name = " + name + ", email = " + String.valueOf(email));
	// }
	// };
	//
	// // set on click button
	// mButtonGetProfile.setOnClickListener(new View.OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// // example 1
	// mSimpleFacebook.getProfile(onProfileListener);
	//
	// // // - example 2
	// // mSimpleFacebook.getProfile(new OnProfileRequestAdapter()
	// // {
	// // @Override
	// // public void onComplete(Profile profile)
	// // {
	// // String id = profile.getId();
	// // String firstName = profile.getFirstName();
	// // String birthday = profile.getBirthday();
	// // String email = profile.getEmail();
	// // String bio = profile.getBio();
	// // // ... and many more properties of profile ...
	// // }
	// //
	// // });
	// }
	// });
	//
	// }
	//
	// /**
	// * Get my profile with properties example
	// */
	// private void getProfileWithPropertiesExample() {
	// // listener for profile request
	// final OnProfileListener onProfileListener = new OnProfileListener() {
	//
	// @Override
	// public void onFail(String reason) {
	// hideDialog();
	// // insure that you are logged in before getting the profile
	// Log.w(TAG, reason);
	// }
	//
	// @Override
	// public void onException(Throwable throwable) {
	// hideDialog();
	// Log.e(TAG, "Bad thing happened", throwable);
	// }
	//
	// @Override
	// public void onThinking() {
	// showDialog();
	// // show progress bar or something to the user while fetching
	// // profile
	// Log.i(TAG, "Thinking...");
	// }
	//
	// @SuppressWarnings("unchecked")
	// @Override
	// public void onComplete(Profile profile) {
	// hideDialog();
	// String id = profile.getId();
	// String firstName = profile.getFirstName();
	// Photo photo = profile.getCover();
	// String pictureUrl = profile.getPicture();
	//
	// // this is just to show the results
	// AlertDialog dialog = Utils.buildProfileResultDialog(MainActivity.this,
	// new Pair<String, String>(Profile.Properties.ID, id), new Pair<String,
	// String>(Profile.Properties.FIRST_NAME,
	// firstName), new Pair<String, String>(Profile.Properties.COVER,
	// photo.getSource()), new Pair<String, String>(Profile.Properties.PICTURE,
	// pictureUrl));
	// dialog.show();
	// }
	// };
	//
	// // set on click button
	// mButtonGetProfileProperties.setOnClickListener(new View.OnClickListener()
	// {
	// @Override
	// public void onClick(View v) {
	// // prepare specific picture that we need
	// PictureAttributes pictureAttributes =
	// Attributes.createPictureAttributes();
	// pictureAttributes.setHeight(500);
	// pictureAttributes.setWidth(500);
	// pictureAttributes.setType(PictureType.SQUARE);
	//
	// // prepare the properties that we need
	// Profile.Properties properties = new Profile.Properties.Builder()
	// .add(Profile.Properties.ID)
	// .add(Profile.Properties.FIRST_NAME)
	// .add(Profile.Properties.COVER)
	// .add(Profile.Properties.PICTURE, pictureAttributes)
	// .build();
	//
	// // do the get profile action
	// mSimpleFacebook.getProfile(properties, onProfileListener);
	// }
	// });
	//
	// }
	//
	// /**
	// * Get friends example
	// */
	// private void getFriendsExample() {
	// // listener for friends request
	// final OnFriendsListener onFriendsListener = new OnFriendsListener() {
	//
	// @Override
	// public void onFail(String reason) {
	// hideDialog();
	// // insure that you are logged in before getting the friends
	// Log.w(TAG, reason);
	// }
	//
	// @Override
	// public void onException(Throwable throwable) {
	// hideDialog();
	// Log.e(TAG, "Bad thing happened", throwable);
	// }
	//
	// @Override
	// public void onThinking() {
	// showDialog();
	// // show progress bar or something to the user while fetching
	// // profile
	// Log.i(TAG, "Thinking...");
	// }
	//
	// @Override
	// public void onComplete(List<Profile> friends) {
	// hideDialog();
	// Log.i(TAG, "Number of friends = " + friends.size());
	// toast("Number of friends = " + friends.size());
	// }
	//
	// };
	//
	// // set button
	// mButtonGetFriends.setOnClickListener(new View.OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// mSimpleFacebook.getFriends(onFriendsListener);
	// }
	// });
	//
	// }
	//
	// /**
	// * Get albums example <br>
	// * Must use {@link Permissions#USER_PHOTOS}
	// */
	// private void getAlbumsExample() {
	// // listener for friends request
	// final OnAlbumsListener onAlbumsListener = new OnAlbumsListener() {
	//
	// @Override
	// public void onFail(String reason) {
	// hideDialog();
	// Log.w(TAG, reason);
	// }
	//
	// @Override
	// public void onException(Throwable throwable) {
	// hideDialog();
	// Log.e(TAG, "Bad thing happened", throwable);
	// }
	//
	// @Override
	// public void onThinking() {
	// showDialog();
	// Log.i(TAG, "Thinking...");
	// }
	//
	// @Override
	// public void onComplete(List<Album> albums) {
	// hideDialog();
	// Log.i(TAG, "Number of albums = " + albums.size());
	// toast("Number of albums = " + albums.size());
	// if (hasNext()) {
	// toast("Has more albums");
	// }
	// }
	// };
	//
	// // set button
	// mButtonGetAlbums.setOnClickListener(new View.OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// mSimpleFacebook.getAlbums(onAlbumsListener);
	// }
	// });
	//
	// }
	//
	// private void getCheckinsExample() {
	// final OnCheckinsListener onCheckinsListener = new OnCheckinsListener() {
	//
	// @Override
	// public void onFail(String reason) {
	// hideDialog();
	// Log.w(TAG, reason);
	// }
	//
	// @Override
	// public void onException(Throwable throwable) {
	// hideDialog();
	// Log.e(TAG, "Bad thing happened", throwable);
	// }
	//
	// @Override
	// public void onThinking() {
	// showDialog();
	// Log.i(TAG, "Thinking...");
	// }
	//
	// @Override
	// public void onComplete(List<Checkin> response) {
	// hideDialog();
	// Log.i(TAG, "Number of checkins = " + response.size());
	// toast("Number of checkins = " + response.size());
	// }
	// };
	//
	// mButtonGetCheckins.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// mSimpleFacebook.getCheckins(onCheckinsListener);
	// }
	// });
	// }
	//
	// private void getCommentsExample() {
	// final String entityId = "14104316802_522484207864952";
	// final OnCommentsListener onCommentsListener = new OnCommentsListener() {
	//
	// @Override
	// public void onFail(String reason) {
	// hideDialog();
	// Log.w(TAG, reason);
	// }
	//
	// @Override
	// public void onException(Throwable throwable) {
	// hideDialog();
	// Log.e(TAG, "Bad thing happened", throwable);
	// }
	//
	// @Override
	// public void onThinking() {
	// showDialog();
	// Log.i(TAG, "Thinking...");
	// }
	//
	// @Override
	// public void onComplete(List<Comment> response) {
	// hideDialog();
	// Log.i(TAG, "Number of comments = " + response.size());
	// toast("Number of comments = " + response.size());
	// }
	// };
	//
	// mButtonGetComments.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// mSimpleFacebook.getComments(entityId, onCommentsListener);
	// }
	// });
	// }
	//
	// private void getEventsExample() {
	// final EventDecision eventDesicion = EventDecision.ATTENDING;
	// final OnEventsListener onEventsListener = new OnEventsListener() {
	//
	// @Override
	// public void onFail(String reason) {
	// hideDialog();
	// Log.w(TAG, reason);
	// }
	//
	// @Override
	// public void onException(Throwable throwable) {
	// hideDialog();
	// Log.e(TAG, "Bad thing happened", throwable);
	// }
	//
	// @Override
	// public void onThinking() {
	// showDialog();
	// Log.i(TAG, "Thinking...");
	// }
	//
	// @Override
	// public void onComplete(List<Event> response) {
	// hideDialog();
	// Log.i(TAG, "Number of events = " + response.size());
	// toast("Number of events = " + response.size());
	// }
	// };
	//
	// mButtonGetEvents.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// mSimpleFacebook.getEvents(eventDesicion, onEventsListener);
	// }
	// });
	// }
	//
	// private void getGroupsExample() {
	// final OnGroupsListener onGroupsListener = new OnGroupsListener() {
	//
	// @Override
	// public void onFail(String reason) {
	// hideDialog();
	// Log.w(TAG, reason);
	// }
	//
	// @Override
	// public void onException(Throwable throwable) {
	// hideDialog();
	// Log.e(TAG, "Bad thing happened", throwable);
	// }
	//
	// @Override
	// public void onThinking() {
	// showDialog();
	// Log.i(TAG, "Thinking...");
	// }
	//
	// @Override
	// public void onComplete(List<Group> response) {
	// hideDialog();
	// Log.i(TAG, "Number of groups = " + response.size());
	// toast("Number of groups = " + response.size());
	// }
	// };
	//
	// mButtonGetGroups.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// mSimpleFacebook.getGroups(onGroupsListener);
	// }
	// });
	// }
	//
	// private void getLikesExample() {
	// final String entityId = "14104316802_522484207864952";
	// final OnLikesListener onLikesListener = new OnLikesListener() {
	//
	// @Override
	// public void onFail(String reason) {
	// hideDialog();
	// Log.w(TAG, reason);
	// }
	//
	// @Override
	// public void onException(Throwable throwable) {
	// hideDialog();
	// Log.e(TAG, "Bad thing happened", throwable);
	// }
	//
	// @Override
	// public void onThinking() {
	// showDialog();
	// Log.i(TAG, "Thinking...");
	// }
	//
	// @Override
	// public void onComplete(List<Like> response) {
	// hideDialog();
	// Log.i(TAG, "Number of likes = " + response.size());
	// toast("Number of likes = " + response.size());
	// }
	// };
	//
	// mButtonGetLikes.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// mSimpleFacebook.getLikes(entityId, onLikesListener);
	// }
	// });
	// }
	//
	// private void getPhotosExample() {
	//
	// final OnPhotosListener onPhotosListener = new OnPhotosListener() {
	//
	// @Override
	// public void onFail(String reason) {
	// hideDialog();
	// Log.w(TAG, reason);
	// }
	//
	// @Override
	// public void onException(Throwable throwable) {
	// hideDialog();
	// Log.e(TAG, "Bad thing happened", throwable);
	// }
	//
	// @Override
	// public void onThinking() {
	// showDialog();
	// Log.i(TAG, "Thinking...");
	// }
	//
	// @Override
	// public void onComplete(List<Photo> response) {
	// hideDialog();
	// Log.i(TAG, "Number of photos = " + response.size());
	// toast("First photo id = " + response.get(0).getId());
	// if (hasNext()) {
	// toast("Has more photos");
	// // getNext();
	// }
	// }
	// };
	//
	// mButtonGetPhotos.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// mSimpleFacebook.getPhotos(onPhotosListener);
	// }
	// });
	// }
	//
	// private void getPostsExample() {
	// final OnPostsListener onPostsListener = new OnPostsListener() {
	//
	// @Override
	// public void onFail(String reason) {
	// hideDialog();
	// Log.w(TAG, reason);
	// }
	//
	// @Override
	// public void onException(Throwable throwable) {
	// hideDialog();
	// Log.e(TAG, "Bad thing happened", throwable);
	// }
	//
	// @Override
	// public void onThinking() {
	// showDialog();
	// Log.i(TAG, "Thinking...");
	// }
	//
	// @Override
	// public void onComplete(List<Post> response) {
	// hideDialog();
	// Log.i(TAG, "Number of posts = " + response.size());
	// toast("Number of posts = " + response.size());
	// }
	// };
	//
	// mButtonGetPosts.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// mSimpleFacebook.getPosts(onPostsListener);
	// }
	// });
	// }
	//
	// private void getVideosExample() {
	// final OnVideosListener onVideosListener = new OnVideosListener() {
	//
	// @Override
	// public void onFail(String reason) {
	// hideDialog();
	// Log.w(TAG, reason);
	// }
	//
	// @Override
	// public void onException(Throwable throwable) {
	// hideDialog();
	// Log.e(TAG, "Bad thing happened", throwable);
	// }
	//
	// @Override
	// public void onThinking() {
	// showDialog();
	// Log.i(TAG, "Thinking...");
	// }
	//
	// @Override
	// public void onComplete(List<Video> response) {
	// hideDialog();
	// Log.i(TAG, "Number of videos = " + response.size());
	// toast("Number of videos = " + response.size());
	// }
	// };
	//
	// mButtonGetVideos.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// mSimpleFacebook.getVideos(onVideosListener);
	// }
	// });
	// }
	//
	// private void getPage() {
	// final String pageId = "109984829031269";
	// final Page.Properties properties = new Page.Properties.Builder()
	// .add(Page.Properties.NAME)
	// .add(Page.Properties.DESCRIPTION)
	// .build();
	//
	// final OnPageListener onPageListener = new OnPageListener() {
	// @Override
	// public void onFail(String reason) {
	// hideDialog();
	// Log.w(TAG, reason);
	// }
	//
	// @Override
	// public void onException(Throwable throwable) {
	// hideDialog();
	// Log.e(TAG, "Bad thing happened", throwable);
	// }
	//
	// @Override
	// public void onThinking() {
	// showDialog();
	// Log.i(TAG, "Thinking...");
	// }
	//
	// @Override
	// public void onComplete(Page response) {
	// hideDialog();
	// Log.i(TAG, "Page name = " + response.getName());
	// toast("Page name = " + response.getName());
	// }
	// };
	//
	// mButtonGetPage.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// mSimpleFacebook.getPage(pageId, properties, onPageListener);
	// }
	// });
	//
	// }

	// private void setEnabled(List<Button> buttons, boolean isEnabled) {
	// for (Button button : buttons) {
	// Log.i(TAG, "-");
	// button.setEnabled(isEnabled);
	// }
	// }

	// private void showDialog() {
	// mProgress = ProgressDialog.show(this, "Thinking", "Waiting for Facebook",
	// true);
	// }
	//
	// private void hideDialog() {
	// if (mProgress != null) {
	// mProgress.hide();
	// }
	// }

}
