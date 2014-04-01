package com.sromku.simple.fb;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.AppEventsLogger;
import com.facebook.Session;
import com.sromku.simple.fb.actions.DeleteRequestAction;
import com.sromku.simple.fb.actions.GetAction;
import com.sromku.simple.fb.actions.GetAlbumsAction;
import com.sromku.simple.fb.actions.GetAppRequestsAction;
import com.sromku.simple.fb.actions.GetCheckinsAction;
import com.sromku.simple.fb.actions.GetCommentsAction;
import com.sromku.simple.fb.actions.GetEventsAction;
import com.sromku.simple.fb.actions.GetFriendsAction;
import com.sromku.simple.fb.actions.GetGroupsAction;
import com.sromku.simple.fb.actions.GetLikesAction;
import com.sromku.simple.fb.actions.GetPageAction;
import com.sromku.simple.fb.actions.GetPhotosAction;
import com.sromku.simple.fb.actions.GetPostsAction;
import com.sromku.simple.fb.actions.GetProfileAction;
import com.sromku.simple.fb.actions.GetScoresAction;
import com.sromku.simple.fb.actions.GetVideosAction;
import com.sromku.simple.fb.actions.InviteAction;
import com.sromku.simple.fb.actions.PublishAction;
import com.sromku.simple.fb.actions.PublishFeedDialogAction;
import com.sromku.simple.fb.entities.Album;
import com.sromku.simple.fb.entities.Checkin;
import com.sromku.simple.fb.entities.Comment;
import com.sromku.simple.fb.entities.Event;
import com.sromku.simple.fb.entities.Event.EventDecision;
import com.sromku.simple.fb.entities.Feed;
import com.sromku.simple.fb.entities.Group;
import com.sromku.simple.fb.entities.Page;
import com.sromku.simple.fb.entities.Photo;
import com.sromku.simple.fb.entities.Post;
import com.sromku.simple.fb.entities.Post.PostType;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.entities.Profile.Properties;
import com.sromku.simple.fb.entities.Publishable;
import com.sromku.simple.fb.entities.Score;
import com.sromku.simple.fb.entities.Story;
import com.sromku.simple.fb.entities.Video;
import com.sromku.simple.fb.listeners.OnActionListener;
import com.sromku.simple.fb.listeners.OnAlbumsListener;
import com.sromku.simple.fb.listeners.OnAppRequestsListener;
import com.sromku.simple.fb.listeners.OnCheckinsListener;
import com.sromku.simple.fb.listeners.OnCommentsListener;
import com.sromku.simple.fb.listeners.OnDeleteListener;
import com.sromku.simple.fb.listeners.OnEventsListener;
import com.sromku.simple.fb.listeners.OnFriendsListener;
import com.sromku.simple.fb.listeners.OnGroupsListener;
import com.sromku.simple.fb.listeners.OnInviteListener;
import com.sromku.simple.fb.listeners.OnLikesListener;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnLogoutListener;
import com.sromku.simple.fb.listeners.OnNewPermissionsListener;
import com.sromku.simple.fb.listeners.OnPageListener;
import com.sromku.simple.fb.listeners.OnPhotosListener;
import com.sromku.simple.fb.listeners.OnPostsListener;
import com.sromku.simple.fb.listeners.OnProfileListener;
import com.sromku.simple.fb.listeners.OnPublishListener;
import com.sromku.simple.fb.listeners.OnScoresListener;
import com.sromku.simple.fb.listeners.OnVideosListener;

/**
 * Simple Facebook SDK which wraps original Facebook SDK
 * 
 * @author sromku
 */
public class SimpleFacebook {
	private static SimpleFacebook mInstance = null;
	private static SimpleFacebookConfiguration mConfiguration = new SimpleFacebookConfiguration.Builder().build();

	private static Activity mActivity;
	private static SessionManager mSessionManager = null;

	private SimpleFacebook() {
	}

	/**
	 * Initialize the library and pass an {@link Activity}. This kind of
	 * initialization is good in case you have a one base activity and many
	 * fragments. In this case you just initialize this library and then just
	 * get an instance of this library by {@link SimpleFacebook#getInstance()}
	 * in any other place.
	 * 
	 * @param activity
	 *            Activity
	 */
	public static void initialize(Activity activity) {
		if (mInstance == null) {
			mInstance = new SimpleFacebook();
			mSessionManager = new SessionManager(mActivity, mConfiguration);
		}
		mActivity = activity;
		SessionManager.activity = activity;
	}

	/**
	 * Get the instance of {@link SimpleFacebook}. This method, not only returns
	 * a singleton instance of {@link SimpleFacebook} but also updates the
	 * current activity with the passed activity. <br>
	 * If you have more than one <code>Activity</code> in your application. And
	 * more than one activity do something with facebook. Then, call this method
	 * in {@link Activity#onResume()} method
	 * 
	 * <pre>
	 * &#064;Override
	 * protected void onResume() {
	 * 	super.onResume();
	 * 	mSimpleFacebook = SimpleFacebook.getInstance(this);
	 * }
	 * </pre>
	 * 
	 * @param activity
	 * @return {@link SimpleFacebook} instance
	 */
	public static SimpleFacebook getInstance(Activity activity) {
		if (mInstance == null) {
			mInstance = new SimpleFacebook();
			mSessionManager = new SessionManager(activity, mConfiguration);
		}
		mActivity = activity;
		SessionManager.activity = activity;
		return mInstance;
	}

	/**
	 * Get the instance of {@link SimpleFacebook}. <br>
	 * <br>
	 * <b>Important:</b> Use this method only after you initialized this library
	 * or by: {@link #initialize(Activity)} or by {@link #getInstance(Activity)}
	 * 
	 * @return The {@link SimpleFacebook} instance
	 */
	public static SimpleFacebook getInstance() {
		return mInstance;
	}

	/**
	 * Set facebook configuration. <b>Make sure</b> to set a configuration
	 * before first actual use of this library like (login, getProfile, etc..).
	 * 
	 * @param configuration
	 *            The configuration of this library
	 */
	public static void setConfiguration(SimpleFacebookConfiguration configuration) {
		mConfiguration = configuration;
		SessionManager.configuration = configuration;
	}

	/**
	 * Get configuration
	 * 
	 * @return
	 */
	public static SimpleFacebookConfiguration getConfiguration() {
		return mConfiguration;
	}

	/**
	 * Login to Facebook
	 * 
	 * @param onLoginListener
	 */
	public void login(OnLoginListener onLoginListener) {
		mSessionManager.login(onLoginListener);
	}

	/**
	 * Logout from Facebook
	 */
	public void logout(OnLogoutListener onLogoutListener) {
		mSessionManager.logout(onLogoutListener);
	}

	/**
	 * Are we logged in to facebook
	 * 
	 * @return <code>True</code> if we have active and open session to facebook
	 */
	public boolean isLogin() {
		return mSessionManager.isLogin(true);
	}

	/**
	 * General GET method.
	 * 
	 * @param entityId
	 *            The id of the entity you want to retrieve.
	 * @param edge
	 *            The graph edge. Like "friends", "groups" ...
	 * @param bundle
	 *            The 'get' parameters
	 * @param onActionListener
	 *            The listener with the type you expect as response.
	 */
	public <T> void get(String entityId, String edge, final Bundle bundle, OnActionListener<T> onActionListener) {
		GetAction<T> getAction = new GetAction<T>(mSessionManager) {
			@Override
			protected Bundle getBundle() {
				if (bundle != null) {
					return bundle;
				}
				return super.getBundle();
			}
		};
		getAction.setActionListener(onActionListener);
		getAction.setTarget(entityId);
		getAction.setEdge(edge);
		getAction.execute();
	}

	/**
	 * Get my albums.<br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * {@link Permission#USER_PHOTOS}
	 * 
	 * @param onAlbumsListener
	 *            The callback listener
	 */
	public void getAlbums(OnAlbumsListener onAlbumsListener) {
		GetAlbumsAction getAlbumsAction = new GetAlbumsAction(mSessionManager);
		getAlbumsAction.setActionListener(onAlbumsListener);
		getAlbumsAction.execute();
	}

	/**
	 * Get albums of entity. <br>
	 * <br>
	 * The entity can be one of:<br>
	 * - <b>Profile</b>. It can be you, your friend or any other profile. To get
	 * id of the profile: {@link Profile#getId()}<br>
	 * - <b>Page</b>. It can be any page. To get the page id:
	 * {@link Page#getId()}<br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * {@link Permission#USER_PHOTOS}<br>
	 * {@link Permission#FRIENDS_PHOTOS}
	 * 
	 * @param entityId
	 *            profile id or page id.
	 * @param onAlbumsListener
	 *            The callback listener.
	 */
	public void getAlbums(String entityId, OnAlbumsListener onAlbumsListener) {
		GetAlbumsAction getAlbumsAction = new GetAlbumsAction(mSessionManager);
		getAlbumsAction.setActionListener(onAlbumsListener);
		getAlbumsAction.setTarget(entityId);
		getAlbumsAction.execute();
	}

	/**
	 * Get all app requests made by me to others or by others to me.
	 * 
	 * @param onAppRequestsListener
	 *            The callback listener.
	 */
	public void getAppRequests(OnAppRequestsListener onAppRequestsListener) {
		GetAppRequestsAction getAppRequestsAction = new GetAppRequestsAction(mSessionManager);
		getAppRequestsAction.setActionListener(onAppRequestsListener);
		getAppRequestsAction.execute();
	}

	/**
	 * Get checkins of the user.<br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * {@link Permission#USER_CHECKINS}<br>
	 * {@link Permission#FRIENDS_CHECKINS}
	 * 
	 * @param onCheckinsListener
	 *            The callback listener.
	 */
	public void getCheckins(OnCheckinsListener onCheckinsListener) {
		GetCheckinsAction getCheckinsAction = new GetCheckinsAction(mSessionManager);
		getCheckinsAction.setActionListener(onCheckinsListener);
		getCheckinsAction.execute();
	}

	/**
	 * Get checkins of entity.<br>
	 * <br>
	 * The entity can be one of:<br>
	 * - <b>Profile</b>. It can be you, your friend or any other profile. To get
	 * id of the profile: {@link Profile#getId()}<br>
	 * - <b>Page</b>. It can be any page. To get the page id:
	 * {@link Page#getId()}<br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * {@link Permission#USER_CHECKINS}<br>
	 * {@link Permission#FRIENDS_CHECKINS}
	 * 
	 * @param entityId
	 *            profile id or page id.
	 * @param onCheckinsListener
	 *            The callback listener.
	 */
	public void getCheckins(String entityId, OnCheckinsListener onCheckinsListener) {
		GetCheckinsAction getCheckinsAction = new GetCheckinsAction(mSessionManager);
		getCheckinsAction.setActionListener(onCheckinsListener);
		getCheckinsAction.setTarget(entityId);
		getCheckinsAction.execute();
	}

	/**
	 * Get comments of specific entity.<br>
	 * <br>
	 * The entity can be one of:<br>
	 * - <b>Album</b>. Any album. To get the album id: {@link Album#getId()}<br>
	 * - <b>Checkin</b>. Any checkin. To get the checkin id:
	 * {@link Checkin#getId()}<br>
	 * - <b>Comment</b>. Get all replied comments to this original comment. To
	 * get comment id: {@link Comment#getId()} <br>
	 * - <b>Photo</b>. Any photo. To get the photo id: {@link Photo#getId()} <br>
	 * - <b>Post</b>. Any post. To get the post id: {@link Post#getId()} <br>
	 * - <b>Video</b>. Any video. To get the video id: {@link Video#getId()} <br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * No special permission is needed, except the permission you asked for
	 * getting the entity itself. For example, if you want to get comments of
	 * album, you need to have the {@link Permission#USER_PHOTOS} or
	 * {@link Permission#FRIENDS_PHOTOS} for getting the comments of this album.
	 * 
	 * @param entityId
	 *            Album, Checkin, Comment, Link, Photo, Post or Video.
	 * @param onCommentsListener
	 *            The callback listener.
	 */
	public void getComments(String entityId, OnCommentsListener onCommentsListener) {
		GetCommentsAction getCommentsAction = new GetCommentsAction(mSessionManager);
		getCommentsAction.setActionListener(onCommentsListener);
		getCommentsAction.setTarget(entityId);
		getCommentsAction.execute();
	}

	/**
	 * Get events of the user. Select which events you want to get by passing
	 * {@link EventDesicion}.<br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * {@link Permission#USER_EVENTS}<br>
	 * {@link Permission#FRIENDS_EVENTS}
	 * 
	 * @param eventDecision
	 *            The type of event: attending, maybe, declined.
	 * @param onEventsListener
	 *            The callback listener.
	 */
	public void getEvents(EventDecision eventDecision, OnEventsListener onEventsListener) {
		GetEventsAction getEventsAction = new GetEventsAction(mSessionManager);
		getEventsAction.setActionListener(onEventsListener);
		getEventsAction.setEventDecision(eventDecision);
		getEventsAction.execute();
	}

	/**
	 * Get events of specific entity.<br>
	 * <br>
	 * The entity can be one of:<br>
	 * - <b>Profile</b>. It can be you, your friend or any other profile. To get
	 * id of the profile: {@link Profile#getId()}<br>
	 * - <b>Page</b>. Any page. To get the page id: {@link Page#getId()}<br>
	 * - <b>Group</b>. Any group. To get the group id: {@link Group#getId()}<br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * {@link Permission#USER_EVENTS}<br>
	 * {@link Permission#FRIENDS_EVENTS}
	 * 
	 * @param entityId
	 *            Profile, Page or Group.
	 * @param eventDesicion
	 *            The type of event: attending, maybe, declined.
	 * @param onEventsListener
	 *            The callback listener.
	 */
	public void getEvents(String entityId, EventDecision eventDecision, OnEventsListener onEventsListener) {
		GetEventsAction getEventsAction = new GetEventsAction(mSessionManager);
		getEventsAction.setActionListener(onEventsListener);
		getEventsAction.setEventDecision(eventDecision);
		getEventsAction.setTarget(entityId);
		getEventsAction.execute();
	}

	/**
	 * Get my friends from facebook.<br>
	 * This method will return profile with next default properties depends on
	 * permissions you have: <b><em>id, name</em></b><br>
	 * <br>
	 * 
	 * If you need additional or other friend properties like:
	 * <em>education, location and more</em>, then use this method:
	 * {@link #getFriends(Properties, OnFriendsRequestListener)} <br>
	 * <br>
	 * 
	 * @param onFriendsListener
	 *            The callback listener.
	 */
	public void getFriends(OnFriendsListener onFriendsListener) {
		getFriends(null, onFriendsListener);
	}

	/**
	 * Get my friends from facebook by mentioning specific parameters. <br>
	 * For example, if you need: <em>id, last_name, picture, birthday</em>
	 * 
	 * @param onFriendsListener
	 *            The callback listener.
	 * @param properties
	 *            The {@link Properties}. <br>
	 *            To create {@link Properties} instance use:
	 * 
	 *            <pre>
	 * // define the friend picture we want to get
	 * PictureAttributes pictureAttributes = Attributes.createPictureAttributes();
	 * pictureAttributes.setType(PictureType.SQUARE);
	 * pictureAttributes.setHeight(500);
	 * pictureAttributes.setWidth(500);
	 * 
	 * // create properties
	 * Properties properties = new Properties.Builder().add(Properties.ID).add(Properties.LAST_NAME).add(Properties.PICTURE, attributes).add(Properties.BIRTHDAY).build();
	 * </pre>
	 */
	public void getFriends(Properties properties, OnFriendsListener onFriendsListener) {
		GetFriendsAction getFriendsAction = new GetFriendsAction(mSessionManager);
		getFriendsAction.setProperties(properties);
		getFriendsAction.setActionListener(onFriendsListener);
		getFriendsAction.execute();
	}

	/**
	 * Get my groups.<br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * {@link Permission#USER_GROUPS}
	 * 
	 * @param onGroupsListener
	 *            The callback listener.
	 */
	public void getGroups(OnGroupsListener onGroupsListener) {
		GetGroupsAction getGroupsAction = new GetGroupsAction(mSessionManager);
		getGroupsAction.setActionListener(onGroupsListener);
		getGroupsAction.execute();
	}

	/**
	 * Get groups that user belongs to.<br>
	 * <br>
	 * 
	 * The entity can be:<br>
	 * - <b>Profile</b>. It can be you, your friend or any other profile. To get
	 * id of the profile: {@link Profile#getId()}<br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * {@link Permission#USER_GROUPS}<br>
	 * {@link Permission#FRIENDS_GROUPS}
	 * 
	 * @param entityId
	 *            Profile
	 * @param onGroupsListener
	 *            The callback listener.
	 */
	public void getGroups(String entityId, OnGroupsListener onGroupsListener) {
		GetGroupsAction getGroupsAction = new GetGroupsAction(mSessionManager);
		getGroupsAction.setActionListener(onGroupsListener);
		getGroupsAction.setTarget(entityId);
		getGroupsAction.execute();
	}

	/**
	 * Get likes of specific entity.<br>
	 * <br>
	 * The entity can be one of:<br>
	 * - <b>Album</b>. Any album. To get the album id: {@link Album#getId()}<br>
	 * - <b>Checkin</b>. Any checkin. To get the checkin id:
	 * {@link Checkin#getId()}<br>
	 * - <b>Comment</b>. Get all likes of the comment. To get comment id:
	 * {@link Comment#getId()} <br>
	 * - <b>Photo</b>. Any photo. To get the photo id: {@link Photo#getId()} <br>
	 * - <b>Post</b>. Any post. To get the post id: {@link Post#getId()} <br>
	 * - <b>Video</b>. Any video. To get the video id: {@link Video#getId()} <br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * No special permission is needed, except the permission you asked for
	 * getting the entity itself. For example, if you want to get likes of
	 * album, you need to have the {@link Permission#USER_PHOTOS} or
	 * {@link Permission#FRIENDS_PHOTOS} for getting likes of this album.
	 * 
	 * @param entityId
	 *            Album, Checkin, Comment, Link, Photo, Post or Video.
	 * @param onCommentsListener
	 *            The callback listener.
	 */
	public void getLikes(String entityId, OnLikesListener onLikesListener) {
		GetLikesAction getLikesAction = new GetLikesAction(mSessionManager);
		getLikesAction.setActionListener(onLikesListener);
		getLikesAction.setTarget(entityId);
		getLikesAction.execute();
	}

	/**
	 * Get page by page id.
	 * 
	 * @param entityId
	 *            The page id.
	 * @param onPageListener
	 *            The callback listener.
	 */
	public void getPage(String entityId, OnPageListener onPageListener) {
		GetPageAction getPageAction = new GetPageAction(mSessionManager);
		getPageAction.setActionListener(onPageListener);
		getPageAction.setTarget(entityId);
		getPageAction.execute();
	}

	/**
	 * Get page by page id.
	 * 
	 * @param entityId
	 *            The page id.
	 * @param properties
	 *            Properties you want to get.
	 * @param onPageListener
	 *            The callback listener.
	 */
	public void getPage(String entityId, Page.Properties properties, OnPageListener onPageListener) {
		GetPageAction getPageAction = new GetPageAction(mSessionManager);
		getPageAction.setActionListener(onPageListener);
		getPageAction.setTarget(entityId);
		getPageAction.setProperties(properties);
		getPageAction.execute();
	}

	/**
	 * Get my photos.
	 * 
	 * <b>Permission:</b><br>
	 * {@link Permission#USER_PHOTOS}
	 * 
	 * @param onPhotosListener
	 *            The callback listener.
	 */
	public void getPhotos(OnPhotosListener onPhotosListener) {
		GetPhotosAction getPhotosAction = new GetPhotosAction(mSessionManager);
		getPhotosAction.setActionListener(onPhotosListener);
		getPhotosAction.execute();
	}

	/**
	 * Get photos of specific entity.<br>
	 * <br>
	 * The entity can be one of:<br>
	 * - <b>Album</b>. Any album. To get the album id: {@link Album#getId()}<br>
	 * - <b>Event</b>. Any event. To get the event id: {@link Event#getId()}<br>
	 * - <b>Page</b>. Any page. To get page id: {@link Page#getId()} <br>
	 * - <b>Profile</b>. Any profile. To get profile id: {@link Profile#getId()} <br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * {@link Permission#USER_PHOTOS}<br>
	 * {@link Permission#FRIENDS_PHOTOS}
	 * 
	 * @param entityId
	 *            Album, Event, Page, Profile
	 * @param onPhotosListener
	 *            The callback listener.
	 */
	public void getPhotos(String entityId, OnPhotosListener onPhotosListener) {
		GetPhotosAction getPhotosAction = new GetPhotosAction(mSessionManager);
		getPhotosAction.setActionListener(onPhotosListener);
		getPhotosAction.setTarget(entityId);
		getPhotosAction.execute();
	}

	/**
	 * Get my profile from facebook.<br>
	 * This method will return profile with next default properties depends on
	 * permissions you have:<br>
	 * <em>id, name, first_name, middle_name, last_name, gender, locale, languages, link, username, timezone, updated_time, verified, bio, birthday, education, email, 
	 * hometown, location, political, favorite_athletes, favorite_teams, quotes, relationship_status, religion, website, work</em>
	 * 
	 * <br>
	 * <br>
	 * If you need additional or other profile properties like:
	 * <em>age_range, picture and more</em>, then use this method:
	 * {@link #getProfile(Properties, OnProfileRequestListener)} <br>
	 * <br>
	 * <b>Note:</b> If you need only few properties for your app, then it is
	 * recommended <b>not</b> to use this method, since getting unnecessary
	 * properties is time consuming task from facebook side.<br>
	 * It is recommended in this case, to use
	 * {@link #getProfile(Properties, OnProfileRequestListener)} and mention
	 * only needed properties.
	 * 
	 * @param onProfileListener
	 *            The callback listener.
	 */
	public void getProfile(OnProfileListener onProfileListener) {
		getProfile(null, onProfileListener);
	}

	/**
	 * Get my profile from facebook by mentioning specific parameters. <br>
	 * For example, if you need: <em>square picture 500x500 pixels</em>
	 * 
	 * @param onProfileListener
	 *            The callback listener.
	 * @param properties
	 *            The {@link Properties}. <br>
	 *            To create {@link Properties} instance use:
	 * 
	 *            <pre>
	 * // define the profile picture we want to get
	 * PictureAttributes pictureAttributes = Attributes.createPictureAttributes();
	 * pictureAttributes.setType(PictureType.SQUARE);
	 * pictureAttributes.setHeight(500);
	 * pictureAttributes.setWidth(500);
	 * 
	 * // create properties
	 * Properties properties = new Properties.Builder().add(Properties.ID).add(Properties.FIRST_NAME).add(Properties.PICTURE, attributes).build();
	 * </pre>
	 */
	public void getProfile(Profile.Properties properties, OnProfileListener onProfileListener) {
		GetProfileAction getProfileAction = new GetProfileAction(mSessionManager);
		getProfileAction.setProperties(properties);
		getProfileAction.setActionListener(onProfileListener);
		getProfileAction.execute();
	}

	/**
	 * Get all my feeds on the wall. It includes: links, statuses, photos..
	 * everything that appears on my wall.<br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * No special permissions are needed for getting the public posts. If you
	 * want to get more private posts, then you need
	 * {@link Permission#READ_STREAM}
	 * 
	 * @param onPostsListener
	 *            The callback listener.
	 */
	public void getPosts(OnPostsListener onPostsListener) {
		GetPostsAction getPostsAction = new GetPostsAction(mSessionManager);
		getPostsAction.setActionListener(onPostsListener);
		getPostsAction.execute();
	}

	/**
	 * Get all feeds on the wall of specific entity. It includes: links,
	 * statuses, photos.. everything that appears on that wall.<br>
	 * 
	 * <br>
	 * The entity can be one of:<br>
	 * - <b>Group</b>. Any group. To get the group id: {@link Group#getId()}<br>
	 * - <b>Event</b>. Any event. To get the event id: {@link Event#getId()}<br>
	 * - <b>Page</b>. Any page. To get page id: {@link Page#getId()} <br>
	 * - <b>Profile</b>. Any profile. To get profile id: {@link Profile#getId()} <br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * No special permissions are needed for getting the public posts. If you
	 * want to get more private posts, then you need
	 * {@link Permission#READ_STREAM}
	 * 
	 * @param entityId
	 *            Event, Group, Page, Profile
	 * @param onPostsListener
	 *            The callback listener.
	 */
	public void getPosts(String entityId, OnPostsListener onPostsListener) {
		getPosts(entityId, PostType.ALL, onPostsListener);
	}

	/**
	 * Get posts of specific entity filtered by {@link PostType}.<br>
	 * 
	 * <br>
	 * In case of:
	 * 
	 * <pre>
	 * {@link PostType#ALL}
	 * </pre>
	 * 
	 * the entity can be one of:<br>
	 * - <b>Group</b>. Any group. To get the group id: {@link Group#getId()}<br>
	 * - <b>Event</b>. Any event. To get the event id: {@link Event#getId()}<br>
	 * - <b>Page</b>. Any page. To get page id: {@link Page#getId()} <br>
	 * - <b>Profile</b>. Any profile. To get profile id: {@link Profile#getId()} <br>
	 * <br>
	 * ---------<br>
	 * In case of:
	 * 
	 * <pre>
	 * 	{@link PostType#LINKS} 
	 * 	{@link PostType#POSTS}
	 * 	{@link PostType#STATUSES}
	 * 	{@link PostType#TAGGED}
	 * </pre>
	 * 
	 * the entity can be one of:<br>
	 * - <b>Page</b>. Any page. To get page id: {@link Page#getId()} <br>
	 * - <b>Profile</b>. Any profile. To get profile id: {@link Profile#getId()} <br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * No special permissions are needed for getting the public posts. If you
	 * want to get more private posts, then you need
	 * {@link Permission#READ_STREAM}<br>
	 * <br>
	 * 
	 * @param entityId
	 *            Event, Group, Page, Profile
	 * @param postType
	 *            Filter all wall feeds and get posts that you need.
	 * @param onPostsListener
	 *            The callback listener.
	 */
	public void getPosts(String entityId, PostType postType, OnPostsListener onPostsListener) {
		GetPostsAction getPostsAction = new GetPostsAction(mSessionManager);
		getPostsAction.setActionListener(onPostsListener);
		getPostsAction.setTarget(entityId);
		getPostsAction.setPostType(postType);
		getPostsAction.execute();
	}

	/**
	 * 
	 * Gets scores using Scores API for games. <br>
	 * <br>
	 * 
	 * @param onScoresListener
	 *            The callback listener.
	 * @see https://developers.facebook.com/docs/games/scores/
	 */
	public void getScores(OnScoresListener onScoresListener) {
		GetScoresAction getScoresAction = new GetScoresAction(mSessionManager);
		getScoresAction.setActionListener(onScoresListener);
		getScoresAction.execute();
	}

	/**
	 * Get my videos.<br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * {@link Permission#USER_VIDEOS}
	 * 
	 * @param onVideosListener
	 *            The callback listener.
	 */
	public void getVideos(OnVideosListener onVideosListener) {
		GetVideosAction getVideosAction = new GetVideosAction(mSessionManager);
		getVideosAction.setActionListener(onVideosListener);
		getVideosAction.execute();
	}

	/**
	 * Get videos of specific entity.<br>
	 * <br>
	 * 
	 * The entity can be one of:<br>
	 * - <b>Event</b>. Any event. To get the event id: {@link Event#getId()}<br>
	 * - <b>Page</b>. Any page. To get page id: {@link Page#getId()} <br>
	 * - <b>Profile</b>. Any profile. To get profile id: {@link Profile#getId()} <br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * {@link Permission#USER_VIDEOS}<br>
	 * {@link Permission#FRIENDS_VIDEOS}
	 * 
	 * @param entityId
	 *            Profile, Page, Event
	 * @param onVideosListener
	 *            The callback listener.
	 */
	public void getVideos(String entityId, OnVideosListener onVideosListener) {
		GetVideosAction getVideosAction = new GetVideosAction(mSessionManager);
		getVideosAction.setActionListener(onVideosListener);
		getVideosAction.setTarget(entityId);
		getVideosAction.execute();
	}

	/**
	 * 
	 * Posts a score using Scores API for games. If missing publish_actions
	 * permission, we do not ask again for it.<br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * {@link Permission#PUBLISH_ACTION}
	 * 
	 * 
	 * @param score
	 *            Score to be posted. <code>int</code>
	 * @param onPostScoreListener
	 *            The listener for posting score
	 * @see https://developers.facebook.com/docs/games/scores/
	 */
	public void publish(Score score, OnPublishListener onPublishListener) {
		publish((Publishable) score, "me", onPublishListener);
	}

	/**
	 * 
	 * Publish {@link Feed} on the wall.<br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * {@link Permission#PUBLISH_ACTION}
	 * 
	 * @param feed
	 *            The feed to publish. Use {@link Feed.Builder} to create a new
	 *            <code>Feed</code>
	 * @param onPublishListener
	 *            The listener for publishing action
	 * @see https
	 *      ://developers.facebook.com/docs/howtos/androidsdk/3.0/publish-to
	 *      -feed/
	 */
	public void publish(Feed feed, OnPublishListener onPublishListener) {
		publish((Publishable) feed, "me", onPublishListener);
	}

	/**
	 * Share to feed by using dialog or do it silently without dialog. <br>
	 * If you use dialog for sharing, you don't have to configure
	 * {@link Permission#PUBLISH_ACTION} since user does the share by himself.<br>
	 * <br>
	 * <b>Important:</b><br>
	 * By setting <code>withDialog=true</code> the default implementation will
	 * try to use a native facebook dialog. If option of native dialog will not
	 * succeed, then a web facebook dialog will be used.<br>
	 * <br>
	 * 
	 * For having the native dialog, you must add to your <b>manifest.xml</b>
	 * 'app_id' meta value:
	 * 
	 * <pre>
	 * {@code <}meta-data
	 *      android:name="com.facebook.sdk.ApplicationId"
	 *      android:value="@string/app_id" /{@code >}
	 * </pre>
	 * 
	 * And in your <b>string.xml</b> add your app_id. For example:
	 * 
	 * <pre>
	 * {@code <}string name="app_id"{@code >}625994234086470{@code <}/string{@code >}
	 * </pre>
	 * 
	 * @param feed
	 *            The feed to post
	 * @param withDialog
	 *            Set <code>True</code> if you want to use dialog.
	 * @param onPublishListener
	 */
	public void publish(Feed feed, boolean withDialog, OnPublishListener onPublishListener) {
		if (!withDialog) {
			// make it silently
			publish(feed, onPublishListener);
		}
		else {
			PublishFeedDialogAction publishFeedDialogAction = new PublishFeedDialogAction(mSessionManager);
			publishFeedDialogAction.setFeed(feed);
			publishFeedDialogAction.setOnPublishListener(onPublishListener);
			publishFeedDialogAction.execute();
		}
	}

	/**
	 * Publish open graph story.<br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * {@link Permission#PUBLISH_ACTION}
	 * 
	 * @param openGraph
	 * @param onPublishListener
	 */
	public void publish(Story story, OnPublishListener onPublishListener) {
		publish((Publishable) story, "me", onPublishListener);
	}

	/**
	 * Publish photo to specific album. You can use
	 * {@link #getAlbums(OnAlbumsRequestListener)} to retrieve all user's
	 * albums.<br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * {@link Permission#PUBLISH_STREAM}<br>
	 * <br>
	 * 
	 * <b>Important:</b><br>
	 * - The user must own the album<br>
	 * - The album should not be full (Max: 200 photos). Check it by
	 * {@link Album#getCount()}<br>
	 * - The app can add photos to the album<br>
	 * - The privacy setting of the app should be at minimum as the privacy
	 * setting of the album ( {@link Album#getPrivacy()}
	 * 
	 * @param photo
	 *            The photo to upload
	 * @param albumId
	 *            The album to which the photo should be uploaded
	 * @param onPublishListener
	 *            The callback listener
	 */
	public void publish(Photo photo, String albumId, OnPublishListener onPublishListener) {
		publish((Publishable) photo, albumId, onPublishListener);
	}

	/**
	 * Publish photo to application default album.<br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * {@link Permission#PUBLISH_STREAM}<br>
	 * <br>
	 * 
	 * <b>Important:</b><br>
	 * - The album should not be full (Max: 200 photos). Check it by
	 * {@link Album#getCount()}<br>
	 * {@link Album#getPrivacy()}
	 * 
	 * @param photo
	 *            The photo to upload
	 * @param onPublishListener
	 *            The callback listener
	 */
	public void publish(Photo photo, OnPublishListener onPublishListener) {
		publish((Publishable) photo, "me", onPublishListener);
	}

	/**
	 * Publish video to "Videos" album. <br>
	 * 
	 * <b>Permission:</b><br>
	 * {@link Permission#PUBLISH_STREAM}<br>
	 * <br>
	 * 
	 * @param video
	 *            The video to upload
	 * @param onPublishListener
	 *            The callback listener
	 */
	public void publish(Video video, OnPublishListener onPublishListener) {
		publish((Publishable) video, "me", onPublishListener);
	}

	/**
	 * Publish any publishable entity
	 * 
	 * @param publishable
	 * @param onPublishListener
	 */
	public void publish(Publishable publishable, String target, OnPublishListener onPublishListener) {
		PublishAction publishAction = new PublishAction(mSessionManager);
		publishAction.setPublishable(publishable);
		publishAction.setTarget(target);
		publishAction.setOnPublishListener(onPublishListener);
		publishAction.execute();
	}

	/**
	 * Open invite dialog and can add multiple friends
	 * 
	 * @param message
	 *            (Optional) The message inside the dialog. It could be
	 *            <code>null</code>
	 * @param data
	 *            (Optional) The data you want to send within the request. It
	 *            could be <code>null</code>
	 * @param onInviteListener
	 *            The listener. It could be <code>null</code>
	 */
	public void invite(String message, final OnInviteListener onInviteListener, String data) {
		InviteAction inviteAction = new InviteAction(mSessionManager);
		inviteAction.setMessage(message);
		inviteAction.setData(data);
		inviteAction.setOnInviteListener(onInviteListener);
		inviteAction.execute();
	}

	/**
	 * Open invite dialog and invite only specific friend
	 * 
	 * @param to
	 *            The id of the friend profile
	 * @param message
	 *            The message inside the dialog. It could be <code>null</code>
	 * @param data
	 *            (Optional) The data you want to send within the request. It
	 *            could be <code>null</code>
	 * @param onInviteListener
	 *            The listener. It could be <code>null</code>
	 */
	public void invite(String to, String message, final OnInviteListener onInviteListener, String data) {
		InviteAction inviteAction = new InviteAction(mSessionManager);
		inviteAction.setTo(to);
		inviteAction.setMessage(message);
		inviteAction.setData(data);
		inviteAction.setOnInviteListener(onInviteListener);
		inviteAction.execute();
	}

	/**
	 * Open invite dialog and invite several specific friends
	 * 
	 * @param suggestedFriends
	 *            The ids of friends' profiles
	 * @param message
	 *            The message inside the dialog. It could be <code>null</code>
	 * @param data
	 *            (Optional) The data you want to send within the request. It
	 *            could be <code>null</code>
	 * @param onInviteListener
	 *            The error listener. It could be <code>null</code>
	 */
	public void invite(String[] suggestedFriends, String message, final OnInviteListener onInviteListener, String data) {
		InviteAction inviteAction = new InviteAction(mSessionManager);
		inviteAction.setSuggestions(suggestedFriends);
		inviteAction.setMessage(message);
		inviteAction.setData(data);
		inviteAction.setOnInviteListener(onInviteListener);
		inviteAction.execute();
	}

	/**
	 * 
	 * Deletes an apprequest.<br>
	 * <br>
	 * 
	 * @param inRequestId
	 *            Input request id to be deleted. Note that it should have the
	 *            form {USERID}_{REQUESTID} <code>String</code>
	 * @param onDeleteListener
	 *            The listener for deletion action
	 * @see https
	 *      ://developers.facebook.com/docs/android/app-link-requests/#step3
	 */
	public void deleteRequest(String inRequestId, final OnDeleteListener onDeleteListener) {
		DeleteRequestAction deleteRequestAction = new DeleteRequestAction(mSessionManager);
		deleteRequestAction.setRequestId(inRequestId);
		deleteRequestAction.setOnDeleteListener(onDeleteListener);
		deleteRequestAction.execute();
	}

	/**
	 * 
	 * Requests any new permission in a runtime. <br>
	 * <br>
	 * Useful when you just want to request the action and won't be publishing
	 * at the time, but still need the updated <b>access token</b> with the
	 * permissions (possibly to pass back to your backend).
	 * 
	 * <br>
	 * <b>Must be logged to use.</b>
	 * 
	 * @param permissions
	 *            New permissions you want to have. This array can include READ
	 *            and PUBLISH permissions in the same time. Just ask what you
	 *            need.<br>
	 * <br>
	 * @param showPublish
	 *            This flag is relevant only in cases when new permissions
	 *            include PUBLISH permission. Then you can decide if you want
	 *            the dialog of requesting publish permission to appear <b>right
	 *            away</b> or <b>later</b>, at first time of real publish
	 *            action.<br>
	 * <br>
	 * @param onNewPermissionsListener
	 *            The listener for the requesting new permission action.
	 */
	public void requestNewPermissions(Permission[] permissions, boolean showPublish, OnNewPermissionsListener onNewPermissionsListener) {
		mSessionManager.requestNewPermissions(permissions, showPublish, onNewPermissionsListener);
	}

	/**
	 * Get the list of all granted permissions. <br>
	 * Use {@link Permission#fromValue(String)} to get the {@link Permission}
	 * object from string in this list.
	 * 
	 * @return List of granted permissions
	 */
	public List<String> getGrantedPermissions() {
		return mSessionManager.getActiveSessionPermissions();
	}

	/**
	 * @return <code>True</code> if all permissions were granted by the user,
	 *         otherwise return <code>False</code>
	 */
	public boolean isAllPermissionsGranted() {
		List<String> grantedPermissions = getGrantedPermissions();
		List<String> readPermissions = new ArrayList<String>(mConfiguration.getReadPermissions());
		List<String> publishPermissions = new ArrayList<String>(mConfiguration.getPublishPermissions());
		readPermissions.removeAll(grantedPermissions);
		publishPermissions.removeAll(grantedPermissions);
		if (readPermissions.size() > 0 || publishPermissions.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * Get the current 'Active' session. <br>
	 * 
	 * @return Active session or null.
	 */
	public Session getSession() {
		return mSessionManager.getActiveSession();
	}

	/**
	 * Install report to facebook. Notifies the events system that the app has
	 * launched & logs an activatedApp event. Should be called whenever your app
	 * becomes active, typically in the onResume() method of each long-running
	 * Activity of your app.
	 */
	public void eventAppLaunched() {
		AppEventsLogger.activateApp(mActivity.getApplicationContext(), mConfiguration.getAppId());
	}

	/**
	 * Call this inside your activity in {@link Activity#onActivityResult}
	 * method
	 * 
	 * @param activity
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 */
	public boolean onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
		return mSessionManager.onActivityResult(activity, requestCode, resultCode, data);
	}

	/**
	 * Clean all references like Activity to prevent memory leaks
	 */
	public void clean() {
		mActivity = null;
		SessionManager.activity = null;
	}

}
