package com.sromku.simple.fb;

import java.lang.ref.WeakReference;

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
	public void getMyProfile(final OnProfileRequestListener onProfileRequestListener)
	{
		// if we are logged in
		if (isLogin())
		{
			// move these params to method call parameters
			Bundle params = new Bundle();
			params.putString("fields", "id"); // params.putString("fields", "id,name,...");

			// TODO - check this also: http://stackoverflow.com/a/13341550/334522
			Session session = getOpenSession();
			Request request = new Request(session, "me", params, HttpMethod.GET, new Request.Callback()
			{
				@Override
				public void onCompleted(Response response)
				{
					JSONObject graphResponse = response.getGraphObject().getInnerJSONObject();
					String userId = null;
					try
					{
						userId = graphResponse.getString("id");
					}
					catch (JSONException e)
					{
						Log.i(TAG, "JSON error " + e.getMessage());
					}

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
							onProfileRequestListener.onComplete(userId);
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
	 * Login with Facebook
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
	 * Logout with Facebook
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
	 * Return <code>True</code> if the session exist and open, otherwise return <code>False</code>
	 * 
	 * @return
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

	public void publish(Feed feed)
	{
		publish(feed, null);
	}

	/**
	 * https://developers.facebook.com/docs/howtos/androidsdk/3.0/publish-to-feed/
	 * 
	 * @param feed
	 */
	public void publish(Feed feed, final OnPublishListener onPublishListener)
	{
		// if we are logged in
		if (isLogin())
		{
			// if we defined the publish permission
			if (mConfiguration.getPublishPermissions().contains(Permissions.PUBLISH_ACTION.getValue()))
			{
				Session session = getOpenSession();
				Request request = new Request(session, "me/feed", feed.getBundle(), HttpMethod.POST, new Request.Callback()
				{
					@Override
					public void onCompleted(Response response)
					{
						JSONObject graphResponse = response.getGraphObject().getInnerJSONObject();
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
				});

				RequestAsyncTask task = new RequestAsyncTask(request);
				task.execute();

				// callback with 'thinking'
				if (onPublishListener != null)
				{
					onPublishListener.onThinking();
				}
			}
			else
			{
				// callback with 'fail' due to insufficient permissions
				if (onPublishListener != null)
				{
					onPublishListener.onFail();
				}
			}
		}
		else
		{
			// callback with 'fail' due to not being loged
			if (onPublishListener != null)
			{
				onPublishListener.onFail();
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

				Request request = new Request(session, story.getGraphPath(appNamespace), story.getActionBundle(), HttpMethod.POST, new Request.Callback()
				{
					@Override
					public void onCompleted(Response response)
					{
						JSONObject graphResponse = response.getGraphObject().getInnerJSONObject();
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
				});

				RequestAsyncTask task = new RequestAsyncTask(request);
				task.execute();

				// callback with 'thinking'
				if (onPublishListener != null)
				{
					onPublishListener.onThinking();
				}
			}
		}
		else
		{
			// callback with 'fail' due to not being loged
			if (onPublishListener != null)
			{
				onPublishListener.onFail();
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
			onInviteListener.onFail();
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
			onInviteListener.onFail();
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
			onInviteListener.onFail();
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
		dialog = new WebDialog.Builder(activity, Session.getActiveSession(), "apprequests", params).
			setOnCompleteListener(new WebDialog.OnCompleteListener()
			{
				@Override
				public void onComplete(Bundle values, FacebookException error)
				{
					if (error != null && !(error instanceof FacebookOperationCanceledException))
					{
						if (onInviteListener != null)
						{
							onInviteListener.onException(error);
						}
					}
					else
					{
						onInviteListener.onComplete();
					}
					dialog = null;
				}
			}).build();

		Window dialogWindow = dialog.getWindow();
		dialogWindow.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		dialog.show();
	}

	private Session getOpenSession()
	{
		return mSessionTracker.getOpenSession();
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

	private class SessionStatusCallback implements Session.StatusCallback
	{
		private boolean mAskPublishPermissions = false;
		private WeakReference<Activity> mWeakReference = null;

		@Override
		public void call(Session session, SessionState state, Exception exception)
		{
			if (mLogInOutListener != null)
			{
				if (exception != null)
				{
					mLogInOutListener.onException(exception);
				}

				switch (state)
				{
				case CLOSED:
					mLogInOutListener.onLogout();
					break;

				case CLOSED_LOGIN_FAILED:
					mLogInOutListener.onFail();
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
					mLogInOutListener.onLogin();

					if (mAskPublishPermissions && mWeakReference != null && session.getState().equals(SessionState.OPENED))
					{
						extendPublishPermissions(mWeakReference.get());
						mAskPublishPermissions = false;
						mWeakReference = null;
					}
					break;

				case OPENED_TOKEN_UPDATED:
					Log.i(TAG, state.name());
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

	public interface OnProfileRequestListener extends OnActionListener
	{
		void onComplete(String userId);
	}

	public interface OnPublishListener extends OnActionListener
	{
		void onComplete(String postId);
	}

	/**
	 * On login/logout listener
	 * 
	 * @author sromku
	 */
	public interface OnLoginOutListener extends OnActionListener
	{
		void onLogin();

		void onLogout();
	}

	public interface OnInviteListener extends OnErrorListener
	{
		void onComplete();
	}

	/**
	 * General interface in this simple sdk
	 * 
	 * @author Romku
	 * 
	 */
	public interface OnActionListener extends OnErrorListener
	{
		void onThinking();
	}

	public interface OnErrorListener
	{
		void onException(Throwable throwable);

		void onFail();
	}

}
