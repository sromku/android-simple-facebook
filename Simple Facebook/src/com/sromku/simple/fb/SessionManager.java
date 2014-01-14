package com.sromku.simple.fb;

import java.security.Permissions;
import java.util.List;

import android.app.Activity;
import android.content.Intent;

import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnLogoutListener;
import com.sromku.simple.fb.listeners.OnPermissionListener;
import com.sromku.simple.fb.listeners.OnReopenSessionListener;
import com.sromku.simple.fb.utils.Errors;
import com.sromku.simple.fb.utils.Logger;
import com.sromku.simple.fb.utils.Errors.ErrorMsg;

public class SessionManager {

    static Activity activity;
    static SimpleFacebookConfiguration configuration;
    private final SessionStatusCallback mSessionStatusCallback;

    public SessionManager(Activity activity, SimpleFacebookConfiguration configuration) {
	SessionManager.activity = activity;
	SessionManager.configuration = configuration;
	mSessionStatusCallback = new SessionStatusCallback();
    }

    /**
     * Login to Facebook
     * 
     * @param onLoginListener
     */
    public void login(OnLoginListener onLoginListener) {
	if (isLogin()) {
	    Logger.logInfo(SessionManager.class, "You were already logged in before calling 'login()' method");
	    if (onLoginListener != null) {
		onLoginListener.onLogin();
	    }
	} else {
	    Session session = Session.getActiveSession();
	    if (session == null || session.getState().isClosed()) {
		session = new Session.Builder(activity.getApplicationContext()).setApplicationId(configuration.getAppId()).build();
		Session.setActiveSession(session);
	    }
	    mSessionStatusCallback.mOnLoginListener = onLoginListener;
	    session.addCallback(mSessionStatusCallback);

	    /*
	     * If session is not opened, then open it
	     */
	    if (!session.isOpened()) {
		openSession(session, true);
	    } else {
		if (onLoginListener != null) {
		    onLoginListener.onLogin();
		}
	    }
	}
    }

    /**
     * Logout from Facebook
     */
    public void logout(OnLogoutListener onLogoutListener) {
	if (isLogin()) {
	    Session session = Session.getActiveSession();
	    if (session != null && !session.isClosed()) {
		mSessionStatusCallback.mOnLogoutListener = onLogoutListener;
		session.closeAndClearTokenInformation();
		session.removeCallback(mSessionStatusCallback);
		if (onLogoutListener != null) {
		    onLogoutListener.onLogout();
		}
	    }
	} else {
	    Logger.logInfo(SessionManager.class, "You were already logged out before calling 'logout()' method");
	    if (onLogoutListener != null) {
		onLogoutListener.onLogout();
	    }
	}
    }

    /**
     * Indicate if you are logged in or not.
     * 
     * @return <code>True</code> if you is logged in, otherwise return
     *         <code>False</code>
     */
    public boolean isLogin() {
	Session session = Session.getActiveSession();
	if (session == null) {
	    if (activity == null) {
		/*
		 * You can't create a session if the activity/context hasn't
		 * been initialized This is now possible because the library can
		 * be started without context.
		 */
		return false;
	    }
	    session = new Session.Builder(activity.getApplicationContext()).setApplicationId(configuration.getAppId()).build();
	    Session.setActiveSession(session);
	}
	if (session.isOpened()) {
	    return true;
	}

	/*
	 * Check if we can reload the session when it will be necessary. We
	 * won't do it now.
	 */
	if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
	    List<String> permissions = session.getPermissions();
	    if (permissions.containsAll(configuration.getReadPermissions())) {
		reopenSession();
		return true;
	    } else {
		return false;
	    }
	} else {
	    return false;
	}
    }
    
    /**
     * 
     * Requests {@link Permissions#PUBLISH_ACTION} and nothing else. Useful when
     * you just want to request the action and won't be publishing at the time,
     * but still need the updated <b>access token</b> with the permissions
     * (possibly to pass back to your backend). You must add
     * {@link Permissions#PUBLISH_ACTION} to your SimpleFacebook configuration
     * before calling this.
     * 
     * <b>Must be logged to use.</b>
     * 
     * @param onPermissionListener
     *            The listener for the request permission action
     */
    public void requestPublish(final OnPermissionListener onPermissionListener) {
	if (isLogin()) {
	    if (configuration.getPublishPermissions().contains(Permission.PUBLISH_ACTION.getValue())) {
		if (onPermissionListener != null) {
		    onPermissionListener.onThinking();
		}
		/*
		 * Check if session to facebook has 'publish_action' permission.
		 * If not, we will ask user for this permission.
		 */
		if (getOpenSessionPermissions().contains(Permission.PUBLISH_ACTION.getValue())) {
		    getSessionStatusCallback().mOnReopenSessionListener = new OnReopenSessionListener() {
			@Override
			public void onSuccess() {
			    if (onPermissionListener != null) {
				onPermissionListener.onSuccess(getAccessToken());
			    }
			}

			@Override
			public void onNotAcceptingPermissions() {
			    // this fail can happen when user doesn't accept the
			    // publish permissions
			    String reason = Errors.getError(ErrorMsg.CANCEL_PERMISSIONS_PUBLISH, String.valueOf(configuration.getPublishPermissions()));
			    Logger.logError(SessionManager.class, reason, null);
			    if (onPermissionListener != null) {
				onPermissionListener.onFail(reason);
			    }
			}
		    };
		    // extend publish permissions automatically
		    extendPublishPermissions();
		} else {
		    // We already have the permission.
		    if (onPermissionListener != null) {
			onPermissionListener.onSuccess(getAccessToken());
		    }
		}
	    }
	} else {
	    // callback with 'fail' due to not being loged
	    if (onPermissionListener != null) {
		String reason = Errors.getError(ErrorMsg.LOGIN);
		Logger.logError(SessionManager.class, reason, null);
		onPermissionListener.onFail(reason);
	    }
	}
    }

    public void openSession(Session session, boolean isRead) {
	Session.OpenRequest request = new Session.OpenRequest(activity);
	if (request != null) {
	    request.setDefaultAudience(configuration.getSessionDefaultAudience());
	    request.setLoginBehavior(configuration.getSessionLoginBehavior());

	    if (isRead) {
		request.setPermissions(configuration.getReadPermissions());

		/*
		 * In case there are also PUBLISH permissions, then we would ask
		 * for these permissions second time (after, user accepted the
		 * read permissions)
		 */
		if (configuration.hasPublishPermissions()) {
		    mSessionStatusCallback.askPublishPermissions();
		}

		// Open session with read permissions
		session.openForRead(request);
	    } else {
		request.setPermissions(configuration.getPublishPermissions());
		session.openForPublish(request);
	    }
	}
    }

    /**
     * Call this method only if session really needs to be reopened for read or
     * for publish. <br>
     * <br>
     * 
     * <b>Important</b><br>
     * Any open method must be called at most once, and cannot be called after
     * the Session is closed. Calling the method at an invalid time will result
     * in {@link UnsupportedOperationException}.
     */
    public void reopenSession() {
	Session session = Session.getActiveSession();
	if (session != null && session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
	    List<String> permissions = session.getPermissions();
	    List<String> publishPermissions = configuration.getPublishPermissions();
	    if (publishPermissions != null && publishPermissions.size() > 0 && permissions.containsAll(publishPermissions)) {
		openSession(session, false);
	    } else if (permissions.containsAll(configuration.getReadPermissions())) {
		openSession(session, true);
	    }
	}
    }

    /**
     * Extend and ask user for PUBLISH permissions
     * 
     * @param activity
     */
    public void extendPublishPermissions() {
	Session session = Session.getActiveSession();

	Session.NewPermissionsRequest request = new Session.NewPermissionsRequest(activity, configuration.getPublishPermissions());
	session.requestNewPublishPermissions(request);
    }

    /**
     * Get access token of open session
     * 
     * @return a {@link String} containing the Access Token of the current
     *         {@link Session} or null if no session.
     */
    public String getAccessToken() {
	Session session = getOpenSession();
	if (session != null) {
	    return session.getAccessToken();
	}
	return null;
    }

    /**
     * Get open session
     * 
     * @return the open session
     */
    public Session getOpenSession() {
	return Session.getActiveSession();
    }

    public SessionStatusCallback getSessionStatusCallback() {
	return mSessionStatusCallback;
    }

    /**
     * Get permissions that are accepted by user for current token
     * 
     * @return the list of accepted permissions
     */
    public List<String> getOpenSessionPermissions() {
	return getOpenSession().getPermissions();
    }
    
    public Activity getActivity() {
	return activity;
    }
    
    public boolean onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
	if (Session.getActiveSession() != null) {
	    return Session.getActiveSession().onActivityResult(activity, requestCode, resultCode, data);
	} else {
	    return false;
	}
    }

    public class SessionStatusCallback implements Session.StatusCallback {
	private boolean mAskPublishPermissions = false;
	private boolean mDoOnLogin = false;
	OnLoginListener mOnLoginListener = null;
	OnLogoutListener mOnLogoutListener = null;
	OnReopenSessionListener mOnReopenSessionListener = null;

	public void setOnReopenSessionListener(OnReopenSessionListener onReopenSessionListener) {
	    mOnReopenSessionListener = onReopenSessionListener;
	}

	@Override
	public void call(Session session, SessionState state, Exception exception) {
	    /*
	     * These are already authorized permissions
	     */
	    List<String> permissions = session.getPermissions();

	    if (exception != null) {
		// log
		// logError("SessionStatusCallback: exception=", exception);

		if (exception instanceof FacebookOperationCanceledException) {
		    /*
		     * If user canceled the read permissions dialog
		     */
		    if (permissions.size() == 0) {
			mOnLoginListener.onNotAcceptingPermissions();
		    } else {
			/*
			 * User canceled the WRITE permissions. We do nothing
			 * here. Once the user will try to do some action that
			 * require WRITE permissions, the dialog will be shown
			 * automatically.
			 */
		    }
		} else {
		    mOnLoginListener.onException(exception);
		}
	    }

	    // log
	    // logInfo("SessionStatusCallback: state=" + state.name() +
	    // ", session=" + String.valueOf(session));

	    switch (state) {
	    case CLOSED:
		if (mOnLogoutListener != null) {
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
		if (mOnLoginListener != null) {
		    mOnLoginListener.onThinking();
		}
		break;

	    case OPENED:

		/*
		 * Check if we came from publishing actions where we ask again
		 * for publish permissions
		 */
		if (mOnReopenSessionListener != null) {
		    mOnReopenSessionListener.onNotAcceptingPermissions();
		    mOnReopenSessionListener = null;
		}

		/*
		 * Check if WRITE permissions were also defined in the
		 * configuration. If so, then ask in another dialog for WRITE
		 * permissions.
		 */
		else if (mAskPublishPermissions && session.getState().equals(SessionState.OPENED)) {
		    if (mDoOnLogin) {
			/*
			 * If user didn't accept the publish permissions, we
			 * still want to notify about complete
			 */
			mDoOnLogin = false;
			mOnLoginListener.onLogin();
		    } else {

			mDoOnLogin = true;
			extendPublishPermissions();
			mAskPublishPermissions = false;
		    }
		} else {
		    if (mOnLoginListener != null) {
			mOnLoginListener.onLogin();
		    }
		}
		break;

	    case OPENED_TOKEN_UPDATED:

		/*
		 * Check if came from publishing actions and we need to reask
		 * for publish permissions
		 */
		if (mOnReopenSessionListener != null) {
		    mOnReopenSessionListener.onSuccess();
		    mOnReopenSessionListener = null;
		} else if (mDoOnLogin) {
		    mDoOnLogin = false;

		    if (mOnLoginListener != null) {
			mOnLoginListener.onLogin();
		    }
		}

		break;

	    default:
		break;
	    }
	}

	/**
	 * If we want to open another dialog with publish permissions just after
	 * showing read permissions, then this method should be called
	 */
	public void askPublishPermissions() {
	    mAskPublishPermissions = true;
	}
    }
}
