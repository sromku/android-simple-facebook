package com.sromku.simple.fb;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

/**
 * Simple API which wraps Facebook SDK 3.0
 * 
 * <br>
 * <b>Features:</b>
 * <ul>
 * <li>Simple configuration</li>
 * <li>No need to use LoginButton view</li>
 * <li>Login/logout</li>
 * <li>Publish feed</li>
 * <li>Publish open graph story</li>
 * <li>Invite friends</li>
 * <li>Fetch my profile</li>
 * <li>Fetch friends</li>
 * <li>Predefined all possible permissions. See {@link Permissions}</li>
 * <li>No need to care for correct sequence logging with READ and PUBLISH permissions</li>
 * </ul>
 * 
 * @author sromku
 */
public class SimpleFacebook
{
	private static final String TAG = SimpleFacebook.class.getName();

	private static SimpleFacebook mInstance = null;
	private static SimpleFacebookConfiguration mConfiguration = new SimpleFacebookConfiguration.Builder().build();

	private static Activity mActivity;
	private SessionStatusCallback mSessionStatusCallback = null;

	private WebDialog mDialog = null;

	private static final String FAIL_LOGIN = "You are not logged in";
	private static final String FAIL_PERMISSIONS_PUBLISH = "You didn't set 'publish_action' permission in configuration";
	private static final String FAIL_CANCEL_PERMISSIONS_PUBLISH = "Publish permissions weren't accepted";

	private SimpleFacebook()
	{
		mSessionStatusCallback = new SessionStatusCallback();
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

	/**
	 * Set facebook configuration
	 * 
	 * @param facebookToolsConfiguration
	 */
	public void setConfiguration(SimpleFacebookConfiguration facebookToolsConfiguration)
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
			 * If session is not opened, the open it
			 */
			if (!session.isOpened())
			{
				openSession(session);
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
				mSessionStatusCallback.onLogoutListener = onLogoutListener;
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
			if (onLogoutListener != null)
			{
				onLogoutListener.onLogout();
			}
		}
	}

	/**
	 * Get my profile from facebook
	 * 
	 * @param onProfileRequestListener
	 */
	public void getProfile(final OnProfileRequestListener onProfileRequestListener)
	{
		// if we are logged in
		if (isLogin())
		{
			Session session = getOpenSession();
			Request request = new Request(session, "me", null, HttpMethod.GET, new Request.Callback()
			{
				@Override
				public void onCompleted(Response response)
				{
					GraphUser graphUser = response.getGraphObjectAs(GraphUser.class);

					FacebookRequestError error = response.getError();
					if (error != null)
					{
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
	}

	/**
	 * Get my friends from facebook
	 * 
	 * @param onFriendsRequestListener
	 */
	public void getFriends(final OnFriendsRequestListener onFriendsRequestListener)
	{
		// if we are logged in
		if (isLogin())
		{
			// move these params to method call parameters
			Session session = getOpenSession();
			Request request = new Request(session, "me/friends", null, HttpMethod.GET, new Request.Callback()
			{
				@Override
				public void onCompleted(Response response)
				{
					List<GraphUser> graphUsers = typedListFromResponse(response, GraphUser.class);

					FacebookRequestError error = response.getError();
					if (error != null)
					{
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
	}

	/**
	 * Publish {@link Feed} on the wall.
	 * 
	 * @param feed The feed to publish. Use {@link Feed.Builder} to create a new <code>Feed</code>
	 * @see https://developers.facebook.com/docs/howtos/androidsdk/3.0/publish-to-feed/
	 */
	public void publish(Feed feed)
	{
		publish(feed, null);
	}

	/**
	 * 
	 * Publish {@link Feed} on the wall.
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
							onPublishListener.onFail(FAIL_CANCEL_PERMISSIONS_PUBLISH);
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
				// callback with 'fail' due to insufficient permissions
				if (onPublishListener != null)
				{
					onPublishListener.onFail(FAIL_PERMISSIONS_PUBLISH);
				}
			}
		}
		else
		{
			// callback with 'fail' due to not being loged
			if (onPublishListener != null)
			{
				onPublishListener.onFail(FAIL_LOGIN);
			}
		}
	}

	/**
	 * Publish open graph story
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
							onPublishListener.onFail(FAIL_CANCEL_PERMISSIONS_PUBLISH);
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
					onPublishListener.onFail(FAIL_PERMISSIONS_PUBLISH);
				}
			}
		}
		else
		{
			// callback with 'fail' due to not being loged
			if (onPublishListener != null)
			{
				onPublishListener.onFail(FAIL_LOGIN);
			}
		}
	}

	/**
	 * Open invite dialog and can add multiple friends
	 * 
	 * @param message The message inside the dialog. It could be <code>null</code>
	 * @param onInviteListener The listener. It could be <code>null</code>
	 */
	public void invite(Activity activity, String message, final OnInviteListener onInviteListener)
	{
		if (isLogin())
		{

			Bundle params = new Bundle();
			params.putString("message", message);
			openInviteDialog(activity, params, onInviteListener);
		}
		else
		{
			onInviteListener.onFail(FAIL_LOGIN);
		}
	}

	/**
	 * Open invite dialog and invite only specific friend
	 * 
	 * @param to The id of the friend profile
	 * @param message The message inside the dialog. It could be <code>null</code>
	 * @param onInviteListener The listener. It could be <code>null</code>
	 */
	public void invite(Activity activity, String to, String message, final OnInviteListener onInviteListener)
	{
		if (isLogin())
		{

			Bundle params = new Bundle();
			if (message != null)
			{
				params.putString("message", message);
			}
			params.putString("to", to);
			openInviteDialog(activity, params, onInviteListener);
		}
		else
		{
			onInviteListener.onFail(FAIL_LOGIN);
		}
	}

	/**
	 * Open invite dialog and invite several specific friends
	 * 
	 * @param suggestedFriends The ids of friends' profiles
	 * @param message The message inside the dialog. It could be <code>null</code>
	 * @param onInviteListener The error listener. It could be <code>null</code>
	 */
	public void invite(Activity activity, String[] suggestedFriends, String message, final OnInviteListener onInviteListener)
	{
		if (isLogin())
		{

			Bundle params = new Bundle();
			if (message != null)
			{
				params.putString("message", message);
			}
			params.putString("suggestions", TextUtils.join(",", suggestedFriends));
			openInviteDialog(activity, params, onInviteListener);
		}
		else
		{
			onInviteListener.onFail(FAIL_LOGIN);
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
			if (session == null)
			{
				session = new Session.Builder(mActivity.getApplicationContext())
					.setApplicationId(mConfiguration.getAppId())
					.build();
			}
			Session.setActiveSession(session);
		}
		if (session.isOpened())
		{
			// mSessionNeedsToReopen = false;
			return true;
		}

		/*
		 * Check if we can reload the session when it will be nessecary. We won't do it now.
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
	 * @return
	 */
	public String getAccessToken()
	{
		// TODO - check if I didn't made login
		Session session = getOpenSession();
		return session.getAccessToken();
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
						Log.i(TAG, "JSON error " + e.getMessage());
					}
	
					FacebookRequestError error = response.getError();
					if (error != null)
					{
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
					onPublishListener.onComplete("0");
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
						Log.i(TAG, "JSON error " + e.getMessage());
					}
	
					FacebookRequestError error = response.getError();
					if (error != null)
					{
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
					onPublishListener.onComplete("0");
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
							onInviteListener.onComplete();
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
	 * Get open session
	 * 
	 * @return the open session
	 */
	private static Session getOpenSession()
	{
		return Session.getActiveSession();
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

	private void openSession(Session session)
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

			// Open session with read permissions
			session.openForRead(request);
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
	private static void reopenSession()
	{
		Session session = Session.getActiveSession();
		if (session != null && session.getState().equals(SessionState.CREATED_TOKEN_LOADED))
		{
			List<String> permissions = session.getPermissions();
			if (permissions.containsAll(mConfiguration.getPublishPermissions()))
			{
				session.openForPublish(new Session.OpenRequest(mActivity));
			}
			else if (permissions.containsAll(mConfiguration.getReadPermissions()))
			{
				session.openForRead(new Session.OpenRequest(mActivity));
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
		OnLogoutListener onLogoutListener = null;
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

			switch (state)
			{
			case CLOSED:
				onLogoutListener.onLogout();
				break;

			case CLOSED_LOGIN_FAILED:
				Log.i(TAG, state.name());
				break;

			case CREATED:
				Log.i(TAG, state.name());
				break;

			case CREATED_TOKEN_LOADED:
				Log.i(TAG, state.name());
				break;

			case OPENING:
				mOnLoginListener.onThinking();
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
					mOnLoginListener.onLogin();
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
					mOnLoginListener.onLogin();
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
	 * On publishing action listener
	 * 
	 * @author sromku
	 * 
	 */
	public interface OnPublishListener extends OnActionListener
	{
		void onComplete(String postId);
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
	 * On invite action listener
	 * 
	 * @author sromku
	 * 
	 */
	public interface OnInviteListener extends OnErrorListener
	{
		void onComplete();

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
