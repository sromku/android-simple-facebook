package com.sromku.simple.fb;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphMultiResult;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphUser;
import com.facebook.widget.WebDialog;
import com.sromku.simple.fb.entities.Album;
import com.sromku.simple.fb.entities.Feed;
import com.sromku.simple.fb.entities.Photo;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.entities.Story;
import com.sromku.simple.fb.utils.Errors;
import com.sromku.simple.fb.utils.Errors.ErrorMsg;
import com.sromku.simple.fb.utils.Logger;

/**
 * Simple Facebook SDK which wraps original Facebook SDK 3.5
 * 
 * <br>
 * <br>
 * <b>Features:</b>
 * <ul>
 * <li>Simple configuration</li>
 * <li>No need to use LoginButton view</li>
 * <li>Login/logout</li>
 * <li>Publish feed</li>
 * <li>Publish open graph story</li>
 * <li>Publish photo</li>
 * <li>Invite friends</li>
 * <li>Fetch my profile</li>
 * <li>Fetch friends</li>
 * <li>Fetch albums</li>
 * <li>Predefined all possible permissions. See {@link Permissions}</li>
 * <li>No need to care for correct sequence logging with READ and PUBLISH permissions</li>
 * </ul>
 * 
 * @author sromku
 */
public class SimpleFacebook
{
	private static SimpleFacebook mInstance = null;
	private static SimpleFacebookConfiguration mConfiguration = new SimpleFacebookConfiguration.Builder().build();

	private static Activity mActivity;
	private SessionStatusCallback mSessionStatusCallback = null;

	private WebDialog mDialog = null;

	private SimpleFacebook()
	{
		mSessionStatusCallback = new SessionStatusCallback();
	}
	
	public static void initialize(Activity activity)
	{
		if (mInstance == null) {
			mInstance = new SimpleFacebook();
		}
		
		mActivity = activity;
	}

	public static SimpleFacebook getInstance(Activity activity)
	{
		if (mInstance == null)
		{
			mInstance = new SimpleFacebook();
		}

		mActivity = activity;
		return mInstance;
	}
	
	public static SimpleFacebook getInstance()
	{
		return mInstance;
	}

	/**
	 * Set facebook configuration
	 * 
	 * @param facebookToolsConfiguration
	 */
	public static void setConfiguration(SimpleFacebookConfiguration facebookToolsConfiguration)
	{
		mConfiguration = facebookToolsConfiguration;
	}

	/**
	 * Login to Facebook
	 * 
	 * @param onLoginListener
	 */
	public void login(OnLoginListener onLoginListener)
	{
		if (isLogin())
		{
			// log
			logInfo("You were already logged in before calling 'login()' method");

			if (onLoginListener != null)
			{
				onLoginListener.onLogin();
			}
		}
		else
		{
			Session session = Session.getActiveSession();
			if (session == null || session.getState().isClosed())
			{
				session = new Session.Builder(mActivity.getApplicationContext())
					.setApplicationId(mConfiguration.getAppId())
					.build();
				Session.setActiveSession(session);
			}

			mSessionStatusCallback.mOnLoginListener = onLoginListener;
			session.addCallback(mSessionStatusCallback);

			/*
			 * If session is not opened, then open it
			 */
			if (!session.isOpened())
			{
				openSession(session, true);
			}
			else
			{
				if (onLoginListener != null)
				{
					onLoginListener.onLogin();
				}
			}
		}
	}

	/**
	 * Logout from Facebook
	 */
	public void logout(OnLogoutListener onLogoutListener)
	{
		if (isLogin())
		{
			Session session = Session.getActiveSession();
			if (session != null && !session.isClosed())
			{
				mSessionStatusCallback.mOnLogoutListener = onLogoutListener;
				session.closeAndClearTokenInformation();
				session.removeCallback(mSessionStatusCallback);

				if (onLogoutListener != null)
				{
					onLogoutListener.onLogout();
				}
			}
		}
		else
		{
			// log
			logInfo("You were already logged out before calling 'logout()' method");

			if (onLogoutListener != null)
			{
				onLogoutListener.onLogout();
			}
		}
	}

	/**
	 * Get my profile from facebook.<br>
	 * This method will return profile with next default properties depends on permissions you have:<br>
	 * <em>id, name, first_name, middle_name, last_name, gender, locale, languages, link, username, timezone, updated_time, verified, bio, birthday, education, email, 
	 * hometown, location, political, favorite_athletes, favorite_teams, quotes, relationship_status, religion, website, work</em>
	 * 
	 * <br>
	 * <br>
	 * If you need additional or other profile properties like: <em>age_range, picture and more</em>, then use
	 * this method: {@link #getProfile(Properties, OnProfileRequestListener)} <br>
	 * <br>
	 * <b>Note:</b> If you need only few properties for your app, then it is recommended <b>not</b> to use
	 * this method, since getting unnecessary properties is time consuming task from facebook side.<br>
	 * It is recommended in this case, to use {@link #getProfile(Properties, OnProfileRequestListener)} and
	 * mention only needed properties.
	 * 
	 * @param onProfileRequestListener
	 */
	public void getProfile(final OnProfileRequestListener onProfileRequestListener)
	{
		getProfile(null, onProfileRequestListener);
	}

	/**
	 * Get my profile from facebook by mentioning specific parameters. <br>
	 * For example, if you need: <em>square picture 500x500 pixels</em>
	 * 
	 * @param onProfileRequestListener
	 * @param properties The {@link Properties}. <br>
	 *            To create {@link Properties} instance use:
	 * 
	 * <pre>
	 * // define the profile picture we want to get
	 * PictureAttributes pictureAttributes = Attributes.createPictureAttributes();
	 * pictureAttributes.setType(PictureType.SQUARE);
	 * pictureAttributes.setHeight(500);
	 * pictureAttributes.setWidth(500);
	 * 
	 * // create properties
	 * Properties properties = new Properties.Builder()
	 * 	.add(Properties.ID)
	 * 	.add(Properties.FIRST_NAME)
	 * 	.add(Properties.PICTURE, attributes)
	 * 	.build();
	 * </pre>
	 */
	public void getProfile(Properties properties, final OnProfileRequestListener onProfileRequestListener)
	{
		// if we are logged in
		if (isLogin())
		{
			Session session = getOpenSession();
			Bundle bundle = null;
			if (properties != null)
			{
				bundle = properties.getBundle();
			}
			Request request = new Request(session, "me", bundle, HttpMethod.GET, new Request.Callback()
			{
				@Override
				public void onCompleted(Response response)
				{
					GraphUser graphUser = response.getGraphObjectAs(GraphUser.class);

					FacebookRequestError error = response.getError();
					if (error != null)
					{
						// log
						logError("failed to get profile", error.getException());

						// callback with 'exception'
						if (onProfileRequestListener != null)
						{
							onProfileRequestListener.onException(error.getException());
						}
					}
					else
					{
						// callback with 'complete'
						if (onProfileRequestListener != null)
						{
							Profile profile = Profile.create(graphUser);
							onProfileRequestListener.onComplete(profile);
						}
					}

				}
			});

			RequestAsyncTask task = new RequestAsyncTask(request);
			task.execute();

			// callback with 'thinking'
			if (onProfileRequestListener != null)
			{
				onProfileRequestListener.onThinking();
			}
		}
		else
		{
			String reason = Errors.getError(ErrorMsg.LOGIN);
			logError(reason, null);

			// callback with 'fail' due to not being loged
			if (onProfileRequestListener != null)
			{
				onProfileRequestListener.onFail(reason);
			}
		}
	}

	/**
	 * Get my friends from facebook.<br>
	 * This method will return profile with next default properties depends on permissions you have:<br>
	 * <em>id, name</em>
	 * 
	 * <br>
	 * <br>
	 * If you need additional or other friend properties like: <em>education, location and more</em>, then use
	 * this method: {@link #getFriends(Properties, OnFriendsRequestListener)} <br>
	 * <br>
	 * 
	 * @param onFriendsRequestListener
	 */
	public void getFriends(final OnFriendsRequestListener onFriendsRequestListener)
	{
		getFriends(null, onFriendsRequestListener);
	}

	/**
	 * Get my friends from facebook by mentioning specific parameters. <br>
	 * For example, if you need: <em>id, last_name, picture, birthday</em>
	 * 
	 * @param onFriendsRequestListener
	 * @param properties The {@link Properties}. <br>
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
	 * Properties properties = new Properties.Builder()
	 * 	.add(Properties.ID)
	 * 	.add(Properties.LAST_NAME)
	 * 	.add(Properties.PICTURE, attributes)
	 * 	.add(Properties.BIRTHDAY)
	 * 	.build();
	 * </pre>
	 */
	public void getFriends(Properties properties, final OnFriendsRequestListener onFriendsRequestListener)
	{
		// if we are logged in
		if (isLogin())
		{
			// move these params to method call parameters
			Session session = getOpenSession();
			Bundle bundle = null;
			if (properties != null)
			{
				bundle = properties.getBundle();
			}
			Request request = new Request(session, "me/friends", bundle, HttpMethod.GET, new Request.Callback()
			{
				@Override
				public void onCompleted(Response response)
				{
					List<GraphUser> graphUsers = typedListFromResponse(response, GraphUser.class);

					FacebookRequestError error = response.getError();
					if (error != null)
					{
						// log
						logError("failed to get friends", error.getException());

						// callback with 'exception'
						if (onFriendsRequestListener != null)
						{
							onFriendsRequestListener.onException(error.getException());
						}
					}
					else
					{
						// callback with 'complete'
						if (onFriendsRequestListener != null)
						{
							List<Profile> friends = new ArrayList<Profile>(graphUsers.size());
							for (GraphUser graphUser: graphUsers)
							{
								friends.add(Profile.create(graphUser));
							}
							onFriendsRequestListener.onComplete(friends);
						}
					}

				}
			});

			RequestAsyncTask task = new RequestAsyncTask(request);
			task.execute();

			// callback with 'thinking'
			if (onFriendsRequestListener != null)
			{
				onFriendsRequestListener.onThinking();
			}
		}
		else
		{
			String reason = Errors.getError(ErrorMsg.LOGIN);
			logError(reason, null);

			// callback with 'fail' due to not being loged
			if (onFriendsRequestListener != null)
			{
				onFriendsRequestListener.onFail(reason);
			}
		}
	}

	/**
	 * Get albums
	 * 
	 * <b>Permission:</b><br>
	 * {@link Permissions#USER_PHOTOS}
	 */
	public void getAlbums(final OnAlbumsRequestListener onAlbumsRequestListener)
	{
		// if we are logged in
		if (isLogin())
		{
			// move these params to method call parameters
			Session session = getOpenSession();
			Bundle bundle = new Bundle();
			bundle.putString("date_format", "U");
			Request request = new Request(session, "me/albums", bundle, HttpMethod.GET, new Request.Callback()
			{
				@Override
				public void onCompleted(Response response)
				{
					List<GraphObject> graphObjects = typedListFromResponse(response, GraphObject.class);

					FacebookRequestError error = response.getError();
					if (error != null)
					{
						// log
						logError("failed to get albums", error.getException());

						// callback with 'exception'
						if (onAlbumsRequestListener != null)
						{
							onAlbumsRequestListener.onException(error.getException());
						}
					}
					else
					{
						// callback with 'complete'
						if (onAlbumsRequestListener != null)
						{
							List<Album> albums = new ArrayList<Album>(graphObjects.size());
							for (GraphObject graphObject: graphObjects)
							{
								Album album = Album.create(graphObject);
								albums.add(album);
							}
							onAlbumsRequestListener.onComplete(albums);
						}
					}

				}
			});

			RequestAsyncTask task = new RequestAsyncTask(request);
			task.execute();

			// callback with 'thinking'
			if (onAlbumsRequestListener != null)
			{
				onAlbumsRequestListener.onThinking();
			}
		}
		else
		{
			String reason = Errors.getError(ErrorMsg.LOGIN);
			logError(reason, null);

			// callback with 'fail' due to not being loged
			if (onAlbumsRequestListener != null)
			{
				onAlbumsRequestListener.onFail(reason);
			}
		}
	}
	
	public void getAppRequests(final OnAppRequestsListener onAppRequestsListener)
	{
		// if we are logged in
		if (isLogin())
		{
			Session session = getOpenSession();
			Bundle bundle = null;
			Request request = new Request(session, "me/apprequests", bundle, HttpMethod.GET, new Request.Callback()
			{
				@Override
				public void onCompleted(Response response)
				{
					FacebookRequestError error = response.getError();
					if (error != null)
					{
						// log
						logError("failed to get app requests", error.getException());

						// callback with 'exception'
						if (onAppRequestsListener != null)
						{
							onAppRequestsListener.onException(error.getException());
						}						
					}
					else
					{
						GraphObject graphObject = response.getGraphObject();					
						if (graphObject != null)
						{						
							JSONObject graphResponse = graphObject.getInnerJSONObject();
							try {
								JSONArray result = graphResponse.getJSONArray( "data" );
								if (onAppRequestsListener != null)
								{
									onAppRequestsListener.onComplete(result);
								}
							} catch (JSONException e) {
								if (onAppRequestsListener != null)
								{
									onAppRequestsListener.onException(e);
								}
							}
						}
						else
						{
							// log
							logError("The GraphObject in Response of getAppRequests has null value. Response=" + response.toString(), null);
						}	
					}	
				}
			});

			RequestAsyncTask task = new RequestAsyncTask(request);
			task.execute();

			// callback with 'thinking'
			if (onAppRequestsListener != null)
			{
				onAppRequestsListener.onThinking();
			}
		}
		else
		{
			String reason = Errors.getError(ErrorMsg.LOGIN);
			logError(reason, null);

			// callback with 'fail' due to not being logged in
			if (onAppRequestsListener != null)
			{
				onAppRequestsListener.onFail(reason);
			}
		}
	}
	
	/**
	 * 
	 * Deletes an apprequest.<br>
	 * <br>
	 * 
	 * @param inRequestId Input request id to be deleted. Note that it should have the form {USERID}_{REQUESTID} <code>String</code>
	 * @param onDeleteRequestListener The listener for deletion action
	 * @see https://developers.facebook.com/docs/android/app-link-requests/#step3
	 */
	public void deleteRequest(String inRequestId, final OnDeleteRequestListener onDeleteRequestListener) 
	{
		if(isLogin())
		{
		    // Create a new request for an HTTP delete with the
		    // request ID as the Graph path.
			Session session = getOpenSession();
		    Request request = new Request(session, inRequestId, null, HttpMethod.DELETE, new Request.Callback() 
		    {
	            @Override
	            public void onCompleted(Response response)
	            {
	            	FacebookRequestError error = response.getError();
					if (error != null)
					{
						// log
						logError("failed to delete requests", error.getException());

						// callback with 'exception'
						if (onDeleteRequestListener != null)
						{
							onDeleteRequestListener.onException(error.getException());
						}
					}
					else
					{
						// callback with 'complete'
						if (onDeleteRequestListener != null)
						{
							onDeleteRequestListener.onComplete();
						}
					}
	            }
	        });
		    // Execute the request asynchronously.
		    Request.executeBatchAsync(request);
		}
		else
		{
			String reason = Errors.getError(ErrorMsg.LOGIN);
			logError(reason, null);

			// callback with 'fail' due to not being logged in
			if (onDeleteRequestListener != null)
			{
				onDeleteRequestListener.onFail(reason);
			}
		}
	}
	
	/**
	 * 
	 * Gets scores using Scores API for games. <br>
	 * <br>
	 * 
	 * 
	 * @param onScoresRequestListener The listener for getting scores
	 * @see https://developers.facebook.com/docs/games/scores/
	 */
	public void getScores(final OnScoresRequestListener onScoresRequestListener)
	{
		// if we are logged in
		if (isLogin())
		{
			Session session = getOpenSession();
			Bundle bundle = null;
			Request request = new Request(session, mConfiguration.getAppId() + "/scores", bundle, HttpMethod.GET, new Request.Callback()
			{
				@Override
				public void onCompleted(Response response)
				{
					FacebookRequestError error = response.getError();
					if (error != null)
					{
						// log
						logError("failed to get scores", error.getException());

						// callback with 'exception'
						if (onScoresRequestListener != null)
						{
							onScoresRequestListener.onException(error.getException());
						}						
					}
					else
					{
						GraphObject graphObject = response.getGraphObject();					
						if (graphObject != null)
						{						
							JSONObject graphResponse = graphObject.getInnerJSONObject();
							try {
								JSONArray result = graphResponse.getJSONArray( "data" );
								if (onScoresRequestListener != null)
								{
									onScoresRequestListener.onComplete(result);
								}
							} catch (JSONException e) {
								if (onScoresRequestListener != null)
								{
									onScoresRequestListener.onException(e);
								}
							}
						}
						else
						{
							// log
							logError("The GraphObject in Response of getScores has null value. Response=" + response.toString(), null);
						}	
					}	
				}
			});

			RequestAsyncTask task = new RequestAsyncTask(request);
			task.execute();

			// callback with 'thinking'
			if (onScoresRequestListener != null)
			{
				onScoresRequestListener.onThinking();
			}
		}
		else
		{
			String reason = Errors.getError(ErrorMsg.LOGIN);
			logError(reason, null);

			// callback with 'fail' due to not being logged in
			if (onScoresRequestListener != null)
			{
				onScoresRequestListener.onFail(reason);
			}
		}
	}
	
	/**
	 * 
	 * Posts a score using Scores API for games. If missing publish_actions permission, we do not ask again for it.<br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * {@link Permissions#PUBLISH_ACTION}
	 * 
	 * 
	 * @param score Score to be posted. <code>int</code>
	 * @param onPostScoreListener The listener for posting score
	 * @see https://developers.facebook.com/docs/games/scores/
	 */
	public void postScore(int score, final OnPostScoreListener onPostScoreListener)
	{
		if(isLogin())
		{
			// if we defined the publish permission
			if (mConfiguration.getPublishPermissions().contains(Permissions.PUBLISH_ACTION.getValue()))
			{
				// callback with 'thinking'
				if (onPostScoreListener != null)
				{
					onPostScoreListener.onThinking();
				}

				/*
				 * Check if session to facebook has 'publish_action' permission. If not, we will return fail, 
				 * client app may try to ask for permission later (not to annoy users).
				 */
				if (!getOpenSessionPermissions().contains(Permissions.PUBLISH_ACTION.getValue()))
				{
					String reason = Errors.getError(ErrorMsg.CANCEL_PERMISSIONS_PUBLISH, String.valueOf(mConfiguration.getPublishPermissions()));
					logError(reason, null);

					// callback with 'fail' due to not being logged in
					if (onPostScoreListener != null)
					{
						onPostScoreListener.onFail(reason);
					}
					return;
				}
			}
			else
			{
				String reason = Errors.getError(ErrorMsg.PERMISSIONS_PUBLISH);
				logError(reason, null);

				// callback with 'fail' due to not being logged in
				if (onPostScoreListener != null)
				{
					onPostScoreListener.onFail(reason);
				}
				return;
			}

			Bundle param = new Bundle();
			param.putInt("score", score);
			Request request = new Request(getOpenSession(), "me/scores", param, HttpMethod.POST, new Request.Callback()
			{
				@Override
				public void onCompleted(Response response)
				{
					FacebookRequestError error = response.getError();
					if (error != null)
					{
						// log
						logError("Failed to publish score", error.getException());
	
						// callback with 'exception'
						if (onPostScoreListener != null)
						{
							onPostScoreListener.onException(error.getException());
						}
					}
					else
					{
						// callback with 'complete'
						if (onPostScoreListener != null)
						{
							onPostScoreListener.onComplete();
						}
					}				
				}
			});
	
			RequestAsyncTask task = new RequestAsyncTask(request);
			task.execute();
		}
		else
		{
			String reason = Errors.getError(ErrorMsg.LOGIN);
			logError(reason, null);

			// callback with 'fail' due to not being logged in
			if (onPostScoreListener != null)
			{
				onPostScoreListener.onFail(reason);
			}
		}
	}

	/**
	 * 
	 * Publish {@link Feed} on the wall.<br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * {@link Permissions#PUBLISH_ACTION}
	 * 
	 * @param feed The feed to publish. Use {@link Feed.Builder} to create a new <code>Feed</code>
	 * @param onPublishListener The listener for publishing action
	 * @see https://developers.facebook.com/docs/howtos/androidsdk/3.0/publish-to-feed/
	 */
	public void publish(final Feed feed, final OnPublishListener onPublishListener)
	{
		// if we are logged in
		if (isLogin())
		{
			// if we defined the publish permission
			if (mConfiguration.getPublishPermissions().contains(Permissions.PUBLISH_ACTION.getValue()))
			{
				// callback with 'thinking'
				if (onPublishListener != null)
				{
					onPublishListener.onThinking();
				}

				/*
				 * Check if session to facebook has 'publish_action' permission. If not, we will ask user for
				 * this permission.
				 */
				if (!getOpenSessionPermissions().contains(Permissions.PUBLISH_ACTION.getValue()))
				{
					mSessionStatusCallback.mOnReopenSessionListener = new OnReopenSessionListener()
					{
						@Override
						public void onSuccess()
						{
							publishImpl(feed, onPublishListener);
						}

						@Override
						public void onNotAcceptingPermissions()
						{
							// this fail can happen when user doesn't accept the publish permissions
							String reason = Errors.getError(ErrorMsg.CANCEL_PERMISSIONS_PUBLISH, String.valueOf(mConfiguration.getPublishPermissions()));
							logError(reason, null);
							if (onPublishListener != null)
							{
								onPublishListener.onFail(reason);
							}
						}
					};

					// extend publish permissions automatically
					extendPublishPermissions();
				}
				else
				{
					publishImpl(feed, onPublishListener);
				}
			}
			else
			{
				String reason = Errors.getError(ErrorMsg.PERMISSIONS_PUBLISH, Permissions.PUBLISH_ACTION.getValue());
				logError(reason, null);

				// callback with 'fail' due to insufficient permissions
				if (onPublishListener != null)
				{
					onPublishListener.onFail(reason);
				}
			}
		}
		else
		{
			// callback with 'fail' due to not being loged
			if (onPublishListener != null)
			{
				String reason = Errors.getError(ErrorMsg.LOGIN);
				logError(reason, null);

				onPublishListener.onFail(reason);
			}
		}
	}

	/**
	 * Publish open graph story.<br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * {@link Permissions#PUBLISH_ACTION}
	 * 
	 * @param openGraph
	 * @param onPublishListener
	 */
	public void publish(final Story story, final OnPublishListener onPublishListener)
	{
		if (isLogin())
		{

			// if we defined the publish permission
			if (mConfiguration.getPublishPermissions().contains(Permissions.PUBLISH_ACTION.getValue()))
			{
				// callback with 'thinking'
				if (onPublishListener != null)
				{
					onPublishListener.onThinking();
				}

				/*
				 * Check if session to facebook has 'publish_action' permission. If not, we will ask user for
				 * this permission.
				 */
				if (!getOpenSessionPermissions().contains(Permissions.PUBLISH_ACTION.getValue()))
				{
					mSessionStatusCallback.mOnReopenSessionListener = new OnReopenSessionListener()
					{
						@Override
						public void onSuccess()
						{
							publishImpl(story, onPublishListener);
						}

						@Override
						public void onNotAcceptingPermissions()
						{
							// this fail can happen when user doesn't accept the publish permissions
							String reason = Errors.getError(ErrorMsg.CANCEL_PERMISSIONS_PUBLISH, String.valueOf(mConfiguration.getPublishPermissions()));
							logError(reason, null);

							if (onPublishListener != null)
							{
								onPublishListener.onFail(reason);
							}
						}
					};

					// extend publish permissions automatically
					extendPublishPermissions();
				}
				else
				{
					publishImpl(story, onPublishListener);
				}
			}
			else
			{
				// callback with 'fail' due to insufficient permissions
				if (onPublishListener != null)
				{
					String reason = Errors.getError(ErrorMsg.PERMISSIONS_PUBLISH, Permissions.PUBLISH_ACTION.getValue());
					logError(reason, null);

					onPublishListener.onFail(reason);
				}
			}
		}
		else
		{
			// callback with 'fail' due to not being loged
			if (onPublishListener != null)
			{
				String reason = Errors.getError(ErrorMsg.LOGIN);
				logError(reason, null);

				onPublishListener.onFail(reason);
			}
		}
	}

	/**
	 * Publish photo to specific album. You can use {@link #getAlbums(OnAlbumsRequestListener)} to retrieve
	 * all user's albums.<br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * {@link Permissions#PUBLISH_STREAM}<br>
	 * <br>
	 * 
	 * <b>Important:</b><br>
	 * - The user must own the album<br>
	 * - The album should not be full (Max: 200 photos). Check it by {@link Album#getCount()}<br>
	 * - The app can add photos to the album<br>
	 * - The privacy setting of the app should be at minimum as the privacy setting of the album (
	 * {@link Album#getPrivacy()}
	 * 
	 * @param photo The photo to upload
	 * @param albumId The album to which the photo should be uploaded
	 * @param onPublishListener The callback listener
	 */
	public void publish(final Photo photo, final String albumId, final OnPublishListener onPublishListener)
	{
		if (isLogin())
		{
			// if we defined the publish permission
			if (mConfiguration.getPublishPermissions().contains(Permissions.PUBLISH_STREAM.getValue()))
			{
				// callback with 'thinking'
				if (onPublishListener != null)
				{
					onPublishListener.onThinking();
				}

				/*
				 * Check if session to facebook has 'publish_action' permission. If not, we will ask user for
				 * this permission.
				 */
				if (!getOpenSessionPermissions().contains(Permissions.PUBLISH_STREAM.getValue()))
				{
					mSessionStatusCallback.mOnReopenSessionListener = new OnReopenSessionListener()
					{
						@Override
						public void onSuccess()
						{
							publishImpl(photo, albumId, onPublishListener);
						}

						@Override
						public void onNotAcceptingPermissions()
						{
							// this fail can happen when user doesn't accept the publish permissions
							String reason = Errors.getError(ErrorMsg.CANCEL_PERMISSIONS_PUBLISH, String.valueOf(mConfiguration.getPublishPermissions()));
							logError(reason, null);

							if (onPublishListener != null)
							{
								onPublishListener.onFail(reason);
							}
						}
					};

					// extend publish permissions automatically
					extendPublishPermissions();
				}
				else
				{
					publishImpl(photo, albumId, onPublishListener);
				}
			}
			else
			{
				// callback with 'fail' due to insufficient permissions
				if (onPublishListener != null)
				{
					String reason = Errors.getError(ErrorMsg.PERMISSIONS_PUBLISH, Permissions.PUBLISH_STREAM.getValue());
					logError(reason, null);

					onPublishListener.onFail(reason);
				}
			}
		}
		else
		{
			// callback with 'fail' due to not being loged
			if (onPublishListener != null)
			{
				String reason = Errors.getError(ErrorMsg.LOGIN);
				logError(reason, null);

				onPublishListener.onFail(reason);
			}
		}
	}

	/**
	 * Publish photo to application default album.<br>
	 * <br>
	 * 
	 * <b>Permission:</b><br>
	 * {@link Permissions#PUBLISH_STREAM}<br>
	 * <br>
	 * 
	 * <b>Important:</b><br>
	 * - The album should not be full (Max: 200 photos). Check it by {@link Album#getCount()}<br>
	 * {@link Album#getPrivacy()}
	 * 
	 * @param photo The photo to upload
	 * @param onPublishListener The callback listener
	 */
	public void publish(final Photo photo, final OnPublishListener onPublishListener)
	{
		publish(photo, "me", onPublishListener);
	}

	/**
	 * Open invite dialog and can add multiple friends
	 * 
	 * @param message The message inside the dialog. It could be <code>null</code>
	 * @param onInviteListener The listener. It could be <code>null</code>
	 */
	public void invite(String message, final OnInviteListener onInviteListener)
	{
		if (isLogin())
		{

			Bundle params = new Bundle();
			params.putString("message", message);
			openInviteDialog(mActivity, params, onInviteListener);
		}
		else
		{
			String reason = Errors.getError(ErrorMsg.LOGIN);
			logError(reason, null);

			onInviteListener.onFail(reason);
		}
	}

	/**
	 * Open invite dialog and invite only specific friend
	 * 
	 * @param to The id of the friend profile
	 * @param message The message inside the dialog. It could be <code>null</code>
	 * @param onInviteListener The listener. It could be <code>null</code>
	 */
	public void invite(String to, String message, final OnInviteListener onInviteListener)
	{
		if (isLogin())
		{

			Bundle params = new Bundle();
			if (message != null)
			{
				params.putString("message", message);
			}
			params.putString("to", to);
			openInviteDialog(mActivity, params, onInviteListener);
		}
		else
		{
			String reason = Errors.getError(ErrorMsg.LOGIN);
			logError(reason, null);

			onInviteListener.onFail(reason);
		}
	}

	/**
	 * Open invite dialog and invite several specific friends
	 * 
	 * @param suggestedFriends The ids of friends' profiles
	 * @param message The message inside the dialog. It could be <code>null</code>
	 * @param onInviteListener The error listener. It could be <code>null</code>
	 */
	public void invite(String[] suggestedFriends, String message, final OnInviteListener onInviteListener)
	{
		if (isLogin())
		{

			Bundle params = new Bundle();
			if (message != null)
			{
				params.putString("message", message);
			}
			params.putString("suggestions", TextUtils.join(",", suggestedFriends));
			openInviteDialog(mActivity, params, onInviteListener);
		}
		else
		{
			String reason = Errors.getError(ErrorMsg.LOGIN);
			logError(reason, null);

			onInviteListener.onFail(reason);
		}
	}

	/**
	 *
	 * Requests {@link Permissions#PUBLISH_ACTION} and nothing else.
	 * Useful when you just want to request the action and won't be publishing at the time, but still need the
	 * updated <b>access token</b> with the permissions (possibly to pass back to your backend).
	 * You must add {@link Permissions#PUBLISH_ACTION} to your SimpleFacebook configuration before calling this.
	 *
	 * <b>Must be logged to use.</b>
	 *
	 * @param onPermissionListener The listener for the request permission action
	 */
	public void requestPublish(final OnPermissionListener onPermissionListener) 
	{
		if (isLogin()) 
		{
			if (mConfiguration.getPublishPermissions().contains(Permissions.PUBLISH_ACTION.getValue())) 
			{
				if (onPermissionListener != null) 
				{
					onPermissionListener.onThinking();
				}
				/*
				 * Check if session to facebook has 'publish_action' permission. If not, we will ask user for
				 * this permission.
				 */
				if (!getOpenSessionPermissions().contains(Permissions.PUBLISH_ACTION.getValue())) 
				{
					mSessionStatusCallback.mOnReopenSessionListener = new OnReopenSessionListener() 
					{
						@Override
						public void onSuccess() 
						{
							if (onPermissionListener != null) 
							{
								onPermissionListener.onSuccess(getAccessToken());
							}
						}
						@Override
						public void onNotAcceptingPermissions() 
						{
							// this fail can happen when user doesn't accept the publish permissions
							String reason = Errors.getError(ErrorMsg.CANCEL_PERMISSIONS_PUBLISH, String.valueOf(mConfiguration.getPublishPermissions()));
							logError(reason, null);
							if (onPermissionListener != null) 
							{
								onPermissionListener.onFail(reason);
							}
						}
					};
					// extend publish permissions automatically
					extendPublishPermissions();
				} 
				else 
				{
					// We already have the permission.
					if (onPermissionListener != null) 
					{
						onPermissionListener.onSuccess(getAccessToken());
					}
				}
			}
		} 
		else 
		{
			// callback with 'fail' due to not being loged
			if (onPermissionListener != null) 
			{
				String reason = Errors.getError(ErrorMsg.LOGIN);
				logError(reason, null);
				onPermissionListener.onFail(reason);
			}
		}
	}

	/**
	 * Call this inside your activity in {@link Activity#onActivityResult} method
	 * 
	 * @param activity
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 * @return
	 */
	public boolean onActivityResult(Activity activity, int requestCode, int resultCode, Intent data)
	{
		if (Session.getActiveSession() != null)
		{
			return Session.getActiveSession().onActivityResult(activity, requestCode, resultCode, data);
		}
		else
		{
			return false;
		}
	}

	/**
	 * Indicate if you are logged in or not.
	 * 
	 * @return <code>True</code> if you is logged in, otherwise return <code>False</code>
	 */
	public boolean isLogin()
	{
		Session session = Session.getActiveSession();
		if (session == null)
		{
			if ( mActivity == null )
			{
				// You can't create a session if the activity/context hasn't been initialized
				// This is now possible because the library can be started without context.
				return false;
			}
			session = new Session.Builder(mActivity.getApplicationContext())
					.setApplicationId(mConfiguration.getAppId())
					.build();
			Session.setActiveSession(session);
		}
		if (session.isOpened())
		{
			// mSessionNeedsToReopen = false;
			return true;
		}

		/*
		 * Check if we can reload the session when it will be neccesary. We won't do it now.
		 */
		if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED))
		{
			List<String> permissions = session.getPermissions();
			if (permissions.containsAll(mConfiguration.getReadPermissions()))
			{
				reopenSession();
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	/**
	 * Get access token of open session
	 * 
	 * @return a {@link String} containing the Access Token of the current {@link Session} or null if no session.
	 */
	public String getAccessToken()
	{
		Session session = getOpenSession();
		if (session != null)
		{
			return session.getAccessToken();
		}
		return null;
	}

	/**
	 * Get open session
	 * 
	 * @return the open session
	 */
	public static Session getOpenSession()
	{
		return Session.getActiveSession();
	}

	/**
	 * Clean all references like Activity to prevent memory leaks
	 */
	public void clean()
	{
		mActivity = null;
	}
	
	private static void publishImpl(Feed feed, final OnPublishListener onPublishListener)
	{
		Session session = getOpenSession();
		Request request = new Request(session, "me/feed", feed.getBundle(), HttpMethod.POST, new Request.Callback()
		{
			@Override
			public void onCompleted(Response response)
			{
				GraphObject graphObject = response.getGraphObject();
				if (graphObject != null)
				{
					JSONObject graphResponse = graphObject.getInnerJSONObject();
					String postId = null;
					try
					{
						postId = graphResponse.getString("id");
					}
					catch (JSONException e)
					{
						// log
						logError("JSON error", e);
					}

					FacebookRequestError error = response.getError();
					if (error != null)
					{
						// log
						logError("Failed to publish", error.getException());

						// callback with 'exception'
						if (onPublishListener != null)
						{
							onPublishListener.onException(error.getException());
						}
					}
					else
					{
						// callback with 'complete'
						if (onPublishListener != null)
						{
							onPublishListener.onComplete(postId);
						}
					}
				}
				else
				{
					// log
					logError("The GraphObject in Response of publish action has null value. Response=" + response.toString(), null);

					if (onPublishListener != null)
					{
						onPublishListener.onComplete("0");
					}
				}
			}
		});

		RequestAsyncTask task = new RequestAsyncTask(request);
		task.execute();
	}

	private static void publishImpl(Story story, final OnPublishListener onPublishListener)
	{
		Session session = getOpenSession();
		String appNamespace = mConfiguration.getNamespace();

		Request request = new Request(session, story.getGraphPath(appNamespace), story.getActionBundle(), HttpMethod.POST, new Request.Callback()
		{
			@Override
			public void onCompleted(Response response)
			{
				GraphObject graphObject = response.getGraphObject();
				if (graphObject != null)
				{
					JSONObject graphResponse = graphObject.getInnerJSONObject();
					String postId = null;
					try
					{
						postId = graphResponse.getString("id");
					}
					catch (JSONException e)
					{
						// log
						logError("JSON error", e);
					}

					FacebookRequestError error = response.getError();
					if (error != null)
					{
						// log
						logError("Failed to publish", error.getException());

						// callback with 'exception'
						if (onPublishListener != null)
						{
							onPublishListener.onException(error.getException());
						}
					}
					else
					{
						// callback with 'complete'
						if (onPublishListener != null)
						{
							onPublishListener.onComplete(postId);
						}
					}
				}
				else
				{
					// log
					logError("The GraphObject in Response of publish action has null value. Response=" + response.toString(), null);

					if (onPublishListener != null)
					{
						onPublishListener.onComplete("0");
					}
				}
			}
		});

		RequestAsyncTask task = new RequestAsyncTask(request);
		task.execute();
	}

	private static void publishImpl(Photo photo, String albumId, final OnPublishListener onPublishListener)
	{
		Session session = getOpenSession();
		Request request = new Request(session, albumId + "/photos", photo.getBundle(), HttpMethod.POST, new Request.Callback()
		{
			@Override
			public void onCompleted(Response response)
			{
				GraphObject graphObject = response.getGraphObject();
				if (graphObject != null)
				{
					JSONObject graphResponse = graphObject.getInnerJSONObject();
					String postId = null;
					try
					{
						postId = graphResponse.getString("id");
					}
					catch (JSONException e)
					{
						// log
						logError("JSON error", e);
					}

					FacebookRequestError error = response.getError();
					if (error != null)
					{
						// log
						logError("Failed to publish", error.getException());

						// callback with 'exception'
						if (onPublishListener != null)
						{
							onPublishListener.onException(error.getException());
						}
					}
					else
					{
						// callback with 'complete'
						if (onPublishListener != null)
						{
							onPublishListener.onComplete(postId);
						}
					}
				}
				else
				{
					// log
					logError("The GraphObject in Response of publish action has null value. Response=" + response.toString(), null);

					if (onPublishListener != null)
					{
						onPublishListener.onComplete("0");
					}
				}
			}
		});

		RequestAsyncTask task = new RequestAsyncTask(request);
		task.execute();
	}
	
	private void openInviteDialog(Activity activity, Bundle params, final OnInviteListener onInviteListener)
	{
		mDialog = new WebDialog.RequestsDialogBuilder(activity, Session.getActiveSession(), params).
			setOnCompleteListener(new WebDialog.OnCompleteListener()
			{
				@Override
				public void onComplete(Bundle values, FacebookException error)
				{
					if (error != null)
					{
						// log
						logError("Failed to invite", error);

						if (error instanceof FacebookOperationCanceledException)
						{
							onInviteListener.onCancel();
						}
						else
						{
							if (onInviteListener != null)
							{
								onInviteListener.onException(error);
							}
						}
					}
					else
					{
						Object object = values.get("request");
						if (object == null)
						{
							onInviteListener.onCancel();
						}
						else
						{
							List<String> invitedFriends = fetchInvitedFriends(values);
							onInviteListener.onComplete(invitedFriends, object.toString());
						}
					}
					mDialog = null;
				}

			}).build();

		Window dialogWindow = mDialog.getWindow();
		dialogWindow.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		mDialog.show();
	}

	/**
	 * Fetch invited friends from response bundle
	 * 
	 * @param values
	 * @return list of invited friends
	 */
	@SuppressLint("DefaultLocale")
	private static List<String> fetchInvitedFriends(Bundle values)
	{
		List<String> friends = new ArrayList<String>();

		int size = values.size();
		int numOfFriends = size - 1;
		if (numOfFriends > 0)
		{
			for (int i = 0; i < numOfFriends; i++)
			{
				String key = String.format("to[%d]", i);
				String friendId = values.getString(key);
				if (friendId != null)
				{
					friends.add(friendId);
				}
			}
		}

		return friends;
	}

	/**
	 * Get permissions that are accepted by user for current token
	 * 
	 * @return the list of accepted permissions
	 */
	private static List<String> getOpenSessionPermissions()
	{
		return getOpenSession().getPermissions();
	}

	private void openSession(Session session, boolean isRead)
	{
		Session.OpenRequest request = new Session.OpenRequest(mActivity);
		if (request != null)
		{
			request.setDefaultAudience(mConfiguration.getSessionDefaultAudience());
			request.setPermissions(mConfiguration.getReadPermissions());
			request.setLoginBehavior(mConfiguration.getSessionLoginBehavior());

			/*
			 * In case there are also PUBLISH permissions, then we would ask for these permissions second time
			 * (after, user accepted the read permissions)
			 */
			if (mConfiguration.hasPublishPermissions())
			{
				mSessionStatusCallback.askPublishPermissions();
			}

			if (isRead)
			{
				// Open session with read permissions
				session.openForRead(request);
			}
			else
			{
				session.openForPublish(request);
			}
		}
	}

	/**
	 * Call this method only if session really needs to be reopened for read or for publish. <br>
	 * <br>
	 * 
	 * <b>Important</b><br>
	 * Any open method must be called at most once, and cannot be called after the Session is closed. Calling
	 * the method at an invalid time will result in {@link UnsupportedOperationException}.
	 */
	private void reopenSession()
	{
		Session session = Session.getActiveSession();
		if (session != null && session.getState().equals(SessionState.CREATED_TOKEN_LOADED))
		{
			List<String> permissions = session.getPermissions();
			if (permissions.containsAll(mConfiguration.getPublishPermissions()))
			{
				openSession(session, false);
			}
			else if (permissions.containsAll(mConfiguration.getReadPermissions()))
			{
				openSession(session, true);
			}
		}
	}

	/**
	 * Extend and ask user for PUBLISH permissions
	 * 
	 * @param activity
	 */
	private static void extendPublishPermissions()
	{
		Session session = Session.getActiveSession();

		Session.NewPermissionsRequest request = new Session.NewPermissionsRequest(mActivity, mConfiguration.getPublishPermissions());
		session.requestNewPublishPermissions(request);
	}

	/**
	 * Helper method
	 */
	private static <T extends GraphObject> List<T> typedListFromResponse(Response response, Class<T> clazz)
	{
		GraphMultiResult multiResult = response.getGraphObjectAs(GraphMultiResult.class);
		if (multiResult == null)
		{
			return null;
		}

		GraphObjectList<GraphObject> data = multiResult.getData();
		if (data == null)
		{
			return null;
		}

		return data.castToListOf(clazz);
	}

	private class SessionStatusCallback implements Session.StatusCallback
	{
		private boolean mAskPublishPermissions = false;
		private boolean mDoOnLogin = false;
		OnLoginListener mOnLoginListener = null;
		OnLogoutListener mOnLogoutListener = null;
		OnReopenSessionListener mOnReopenSessionListener = null;

		@Override
		public void call(Session session, SessionState state, Exception exception)
		{
			/*
			 * These are already authorized permissions
			 */
			List<String> permissions = session.getPermissions();

			if (exception != null)
			{
				// log
				logError("SessionStatusCallback: exception=", exception);

				if (exception instanceof FacebookOperationCanceledException)
				{
					/*
					 * If user canceled the read permissions dialog
					 */
					if (permissions.size() == 0)
					{
						mOnLoginListener.onNotAcceptingPermissions();
					}
					else
					{
						/*
						 * User canceled the WRITE permissions. We do nothing here. Once the user will try to
						 * do some action that require WRITE permissions, the dialog will be shown
						 * automatically.
						 */
					}
				}
				else
				{
					mOnLoginListener.onException(exception);
				}
			}

			// log
			logInfo("SessionStatusCallback: state=" + state.name() + ", session=" + String.valueOf(session));

			switch (state)
			{
			case CLOSED:
			    if (mOnLogoutListener != null) 
			    {
					mOnLogoutListener.onLogout();
				}
				break;

			case CLOSED_LOGIN_FAILED:
				break;

			case CREATED:
				break;

			case CREATED_TOKEN_LOADED:
				break;

			case OPENING:
			    if (mOnLoginListener != null)
			    {
					mOnLoginListener.onThinking();
				}
				break;

			case OPENED:

				/*
				 * Check if we came from publishing actions where we ask again for publish permissions
				 */
				if (mOnReopenSessionListener != null)
				{
					mOnReopenSessionListener.onNotAcceptingPermissions();
					mOnReopenSessionListener = null;
				}

				/*
				 * Check if WRITE permissions were also defined in the configuration. If so, then ask in
				 * another dialog for WRITE permissions.
				 */
				else if (mAskPublishPermissions && session.getState().equals(SessionState.OPENED))
				{
					if (mDoOnLogin)
					{
						/*
						 * If user didn't accept the publish permissions, we still want to notify about
						 * complete
						 */
						mDoOnLogin = false;
						mOnLoginListener.onLogin();
					}
					else
					{

						mDoOnLogin = true;
						extendPublishPermissions();
						mAskPublishPermissions = false;
					}
				}
				else
				{
					if (mOnLoginListener != null) 
				    {
						mOnLoginListener.onLogin();
					}
				}
				break;

			case OPENED_TOKEN_UPDATED:

				/*
				 * Check if came from publishing actions and we need to reask for publish permissions
				 */
				if (mOnReopenSessionListener != null)
				{
					mOnReopenSessionListener.onSuccess();
					mOnReopenSessionListener = null;
				}
				else if (mDoOnLogin)
				{
					mDoOnLogin = false;

					if (mOnLoginListener != null)
					{
						mOnLoginListener.onLogin();
					}
				}

				break;

			default:
				break;
			}
		}

		/**
		 * If we want to open another dialog with publish permissions just after showing read permissions,
		 * then this method should be called
		 */
		public void askPublishPermissions()
		{
			mAskPublishPermissions = true;
		}
	}

	private static void logInfo(String message)
	{
		Logger.logInfo(SimpleFacebook.class, message);
	}

	private static void logError(String error, Throwable throwable)
	{
		if (throwable != null)
		{
			Logger.logError(SimpleFacebook.class, error, throwable);
		}
		else
		{
			Logger.logError(SimpleFacebook.class, error);
		}
	}

	private interface OnReopenSessionListener
	{
		void onSuccess();

		void onNotAcceptingPermissions();
	}

	/**
	 * On my profile request listener
	 * 
	 * @author sromku
	 * 
	 */
	public interface OnProfileRequestListener extends OnActionListener
	{
		void onComplete(Profile profile);
	}

	/**
	 * On friends request listener
	 * 
	 * @author sromku
	 * 
	 */
	public interface OnFriendsRequestListener extends OnActionListener
	{
		void onComplete(List<Profile> friends);
	}
	
	/**
	 * On get app requests listener
	 * 
	 * @author koraybalci
	 * 
	 */
	public interface OnAppRequestsListener extends OnActionListener
	{
		void onComplete(JSONArray result);
	}

	/**
	 * On delete request listener
	 * 
	 * @author koraybalci
	 * 
	 */
	public interface OnDeleteRequestListener extends OnActionListener
	{
		void onComplete();
	}
	
	/**
	 * On get scores requests listener
	 * 
	 * @author koraybalci
	 * 
	 */
	public interface OnScoresRequestListener extends OnActionListener
	{
		void onComplete(JSONArray result);
	}
	
	/**
	 * On post score listener
	 * 
	 * @author koraybalci
	 * 
	 */
	public interface OnPostScoreListener extends OnActionListener
	{
		void onComplete();
	}

	/**
	 * On albums request listener
	 * 
	 * @author sromku
	 * 
	 */
	public interface OnAlbumsRequestListener extends OnActionListener
	{
		void onComplete(List<Album> albums);
	}

	/**
	 * On publishing action listener
	 * 
	 * @author sromku
	 * 
	 */
	public interface OnPublishListener extends OnActionListener
	{
		void onComplete(String id);
	}

	/**
	 * On login/logout actions listener
	 * 
	 * @author sromku
	 */
	public interface OnLoginListener extends OnActionListener
	{
		/**
		 * If user performed {@link FacebookTools#login(Activity)} action, this callback method will be
		 * invoked
		 */
		void onLogin();

		/**
		 * If user pressed 'cancel' in READ (First) permissions dialog
		 */
		void onNotAcceptingPermissions();
	}

	public interface OnLogoutListener extends OnActionListener
	{
		/**
		 * If user performed {@link FacebookTools#logout()} action, this callback method will be invoked
		 */
		void onLogout();
	}

    /**
	 * On permission listener - If the App must request a specific permission (only to obtain the new Access Token)
	 *
	 * @author Gryzor
	 *
	 */
	public interface OnPermissionListener extends OnActionListener
	{
		/**
		 * If the permission was granted, this callback is invoked.
		 */
		void onSuccess(final String accessToken);
		/**
		 * If user pressed 'cancel' in PUBLISH permissions dialog
		 */
		void onNotAcceptingPermissions();
	}

	/**
	 * On invite action listener
	 * 
	 * @author sromku
	 * 
	 */
	public interface OnInviteListener extends OnErrorListener
	{
		void onComplete(List<String> invitedFriends, String requestId);

		void onCancel();
	}

	/**
	 * General interface in this simple sdk
	 * 
	 * @author sromku
	 * 
	 */
	public interface OnActionListener extends OnErrorListener
	{
		void onThinking();
	}

	public interface OnErrorListener
	{
		void onException(Throwable throwable);

		void onFail(String reason);
	}

}
