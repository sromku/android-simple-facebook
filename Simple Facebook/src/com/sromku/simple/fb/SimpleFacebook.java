package com.sromku.simple.fb;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
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
import com.facebook.internal.SessionTracker;
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

	private final Context mContext;
	private SessionTracker mSessionTracker = null;
	private OnLoginOutListener mLogInOutListener = null;
	private SessionStatusCallback mSessionStatusCallback = null;

	private WebDialog dialog = null;

	private static final String FAIL_LOGIN = "You are not logged in";
	private static final String FAIL_PERMISSIONS_PUBLISH = "You didn't set 'publish_action' permission in configuration";

	private SimpleFacebook(Context context)
	{
		mContext = context;
		mSessionStatusCallback = new SessionStatusCallback();
	}

	public static SimpleFacebook getInstance(Context context)
	{
		if (mInstance == null)
		{
			mInstance = new SimpleFacebook(context);
		}
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

	public void setLogInOutListener(OnLoginOutListener logInOutListener)
	{
		mLogInOutListener = logInOutListener;
	}

	/**
	 * Get access token
	 * 
	 * @return
	 */
	public String getAccessToken()
	{
		Session session = getOpenSession();
		return session.getAccessToken();
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
	 * Login to Facebook
	 * 
	 * @param onLoginListener
	 */
	public void login(Activity activity)
	{
		initSessionTracker();

		if (isLogin())
		{
			if (mLogInOutListener != null)
			{
				mLogInOutListener.onLogin();
			}
		}
		else
		{
			mSessionTracker.startTracking();

			Session session = mSessionTracker.getSession();
			if (session == null || session.getState().isClosed())
			{
				mSessionTracker.setSession(null);
				session = new Session.Builder(mContext)
					.setApplicationId(mConfiguration.getAppId())
					.build();
				Session.setActiveSession(session);
			}

			/*
			 * If session is not opened, the open it
			 */
			if (!session.isOpened())
			{
				openSession(activity);
			}
			else
			{
				if (mLogInOutListener != null)
				{
					mLogInOutListener.onLogin();
				}
			}
		}
	}

	/**
	 * Logout from Facebook
	 */
	public void logout()
	{
		if (isLogin())
		{
			Session openSession = mSessionTracker.getOpenSession();
			openSession.closeAndClearTokenInformation();
		}
		else
		{
			if (mLogInOutListener != null)
			{
				mLogInOutListener.onLogout();
			}
		}
	}

	/**
	 * Indicate if you are logged in or not.
	 * 
	 * @return <code>True</code> if you is logged in, otherwise return <code>False</code>
	 */
	public boolean isLogin()
	{
		initSessionTracker();

		if (mSessionTracker != null && mSessionTracker.getOpenSession() != null)
		{
			return true;
		}
		return false;
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
	public void publish(Feed feed, final OnPublishListener onPublishListener)
	{
		// if we are logged in
		if (isLogin())
		{
			// if we defined the publish permission
			if (mConfiguration.getPublishPermissions().contains(Permissions.PUBLISH_ACTION.getValue()))
			{
				/*
				 * Check if session to facebook has 'publish_action' permission. If not, we will ask user for
				 * this permission.
				 */
				if (!getOpenSessionPermissions().contains(Permissions.PUBLISH_ACTION.getValue()))
				{
					// TODO - do auto extend publish permissions
					onPublishListener.onFail(FAIL_PERMISSIONS_PUBLISH);
				}
				else
				{
					// callback with 'thinking'
					if (onPublishListener != null)
					{
						onPublishListener.onThinking();
					}

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
	public void publish(Story story, final OnPublishListener onPublishListener)
	{
		if (isLogin())
		{
			// if we defined the publish permission
			if (mConfiguration.getPublishPermissions().contains(Permissions.PUBLISH_ACTION.getValue()))
			{
				Session session = getOpenSession();
				String appNamespace = mConfiguration.getNamespace();

				// callback with 'thinking'
				if (onPublishListener != null)
				{
					onPublishListener.onThinking();
				}

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
		if (mSessionTracker != null && mSessionTracker.getSession() != null)
		{
			return mSessionTracker.getSession().onActivityResult(activity, requestCode, resultCode, data);
		}
		else
		{
			return false;
		}
	}

	private void openInviteDialog(Activity activity, Bundle params, final OnInviteListener onInviteListener)
	{
		dialog = new WebDialog.RequestsDialogBuilder(activity, Session.getActiveSession(), params).
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
					dialog = null;
				}
			}).build();

		Window dialogWindow = dialog.getWindow();
		dialogWindow.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		dialog.show();
	}

	/**
	 * Get open session
	 * 
	 * @return the open session
	 */
	private Session getOpenSession()
	{
		return mSessionTracker.getOpenSession();
	}

	/**
	 * Get permissions that are accepted by user for current token
	 * 
	 * @return the list of accepted permissions
	 */
	private List<String> getOpenSessionPermissions()
	{
		return getOpenSession().getPermissions();
	}

	private void initSessionTracker()
	{
		if (mSessionTracker == null)
		{
			mSessionTracker = new SessionTracker(mContext, mSessionStatusCallback, null, false);
			mSessionTracker.stopTracking();
		}
	}

	private void openSession(Activity activity)
	{
		Session session = mSessionTracker.getSession();

		Session.OpenRequest request = new Session.OpenRequest(activity);
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
				mSessionStatusCallback.askPublishPermissions(activity);
			}

			// Open session with read permissions
			session.openForRead(request);
		}
	}

	/**
	 * Extend and ask user for PUBLISH permissions
	 * 
	 * @param activity
	 */
	private void extendPublishPermissions(Activity activity)
	{
		Session session = mSessionTracker.getOpenSession();

		Session.NewPermissionsRequest request = new Session.NewPermissionsRequest(activity, mConfiguration.getPublishPermissions());
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
		private WeakReference<Activity> mWeakReference = null;
		private boolean mDoOnLogin = false;

		@Override
		public void call(Session session, SessionState state, Exception exception)
		{
			if (mLogInOutListener != null)
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
							mLogInOutListener.onNotAcceptingPermissions();
						}
						else
						{
							/*
							 * User canceled the WRITE permissions. We do nothing here. Once the user will try
							 * to do some action that require WRITE permissions, the dialog will be shown
							 * automatically.
							 */
						}
					}
					else
					{
						mLogInOutListener.onException(exception);
					}
				}

				switch (state)
				{
				case CLOSED:
					mLogInOutListener.onLogout();
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
					mLogInOutListener.onThinking();
					break;

				case OPENED:

					/*
					 * Check if WRITE permissions were also defined in the configuration. If so, then ask in
					 * another dialog for WRITE permissions.
					 */
					if (mAskPublishPermissions && mWeakReference != null && session.getState().equals(SessionState.OPENED))
					{
						/*
						 * If user didn't accepte the publish permissions, we still want to notify about
						 * complete
						 */
						if (mDoOnLogin)
						{
							mDoOnLogin = false;
							mLogInOutListener.onLogin();
						}
						else
						{
							mDoOnLogin = true;
							extendPublishPermissions(mWeakReference.get());
							mAskPublishPermissions = false;
							mWeakReference = null;
						}
					}
					else
					{
						mLogInOutListener.onLogin();
					}
					break;

				case OPENED_TOKEN_UPDATED:
					if (mDoOnLogin)
					{
						mDoOnLogin = false;
						mLogInOutListener.onLogin();
					}
					break;

				default:
					break;
				}
			}
		}

		/**
		 * If we want to open another dialog with publish permissions just after showing read permissions,
		 * then this method should be called
		 */
		public void askPublishPermissions(Activity activity)
		{
			mAskPublishPermissions = true;
			mWeakReference = new WeakReference<Activity>(activity);
		}
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
	public interface OnLoginOutListener extends OnActionListener
	{
		/**
		 * If user performed {@link FacebookTools#login(Activity)} action, this callback method will be
		 * invoked
		 */
		void onLogin();

		/**
		 * If user performed {@link FacebookTools#logout()} action, this callback method will be invoked
		 */
		void onLogout();

		/**
		 * If user pressed 'cancel' in READ (First) permissions dialog
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
