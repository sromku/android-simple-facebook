package com.sromku.simple.fb;

import java.security.Permissions;
import java.util.List;

import android.app.Activity;
import android.content.Intent;

import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.FacebookDialog.Callback;
import com.facebook.widget.FacebookDialog.PendingCall;
import com.sromku.simple.fb.Permission.Type;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnLogoutListener;
import com.sromku.simple.fb.listeners.OnNewPermissionsListener;
import com.sromku.simple.fb.listeners.OnReopenSessionListener;
import com.sromku.simple.fb.utils.Logger;

public class SessionManager {

    private final static Class<?> TAG = SessionManager.class;

    static Activity activity;
    static SimpleFacebookConfiguration configuration;
    private final SessionStatusCallback mSessionStatusCallback;
    private UiLifecycleHelper uiLifecycleHelper;

    private Callback mFacebookDialogCallback;

    public SessionManager(Activity activity, SimpleFacebookConfiguration configuration) {
	SessionManager.activity = activity;
	SessionManager.configuration = configuration;
	mSessionStatusCallback = new SessionStatusCallback();
	uiLifecycleHelper = new UiLifecycleHelper(activity, mSessionStatusCallback);
    }

    /**
     * Login to Facebook
     * 
     * @param onLoginListener
     */
    public void login(OnLoginListener onLoginListener) {
	if (onLoginListener == null) {
	    Logger.logError(TAG, "OnLoginListener can't be null in -> 'login(OnLoginListener onLoginListener)' method.");
	    return;
	}
	if (activity == null) {
	    onLoginListener.onFail("You must initialize the SimpleFacebook instance with you current Activity.");
	    return;
	}
	if (isLogin(true)) {
	    Logger.logInfo(TAG, "You were already logged in before calling 'login()' method.");
	    onLoginListener.onLogin();
	}
	else {
	    Session session = getOrCreateActiveSession();
	    mSessionStatusCallback.onLoginListener = onLoginListener;
	    session.addCallback(mSessionStatusCallback);
	    if (!session.isOpened()) {
		openSession(session, true);
	    }
	    else {
		onLoginListener.onLogin();
	    }
	}
    }

    /**
     * Logout from Facebook
     */
    public void logout(OnLogoutListener onLogoutListener) {
	if (onLogoutListener == null) {
	    Logger.logError(TAG, "OnLogoutListener can't be null in -> 'logout(OnLogoutListener onLogoutListener)' method");
	    return;
	}
	Session session = getActiveSession();
	if (session != null) {
	    if (session.isClosed()) {
		Logger.logInfo(SessionManager.class, "You were already logged out before calling 'logout()' method");
		onLogoutListener.onLogout();
	    }
	    else {
		mSessionStatusCallback.onLogoutListener = onLogoutListener;
		session.closeAndClearTokenInformation();
		session.removeCallback(mSessionStatusCallback);
		onLogoutListener.onLogout();
	    }
	}
	else {
	    onLogoutListener.onLogout();
	}
    }

    /**
     * Indicate if you are logged in or not.
     * 
     * @return <code>True</code> if you is logged in, otherwise return
     *         <code>False</code>
     */
    public boolean isLogin(boolean reopenIfPossible) {
	Session session = getActiveSession();
	if (session == null) {
	    if (activity == null) {
		return false;
	    }
	    session = new Session.Builder(activity.getApplicationContext()).setApplicationId(configuration.getAppId()).build();
	    Session.setActiveSession(session);
	}
	if (session.isOpened()) {
	    return true;
	}
	if (reopenIfPossible && canReopenSession(session)) {
	    reopenSession();
	    return true;
	}

	return false;
    }

    /**
     * Get the current 'Active' session. <br>
     * <br>
     * <b>Important:</b> The result could be <code>null</code>. If you want to
     * have not null active session, then use
     * {@link #getOrCreateActiveSession()} method.
     * 
     * @return Active session or null.
     */
    public Session getActiveSession() {
	return Session.getActiveSession();
    }

    /**
     * Get access token of open session
     * 
     * @return a {@link String} containing the Access Token of the current
     *         {@link Session} or null if no session.
     */
    public String getAccessToken() {
	Session session = getActiveSession();
	if (session != null) {
	    return session.getAccessToken();
	}
	return null;
    }

    public SessionStatusCallback getSessionStatusCallback() {
	return mSessionStatusCallback;
    }

    /**
     * Get permissions that are accepted by user for current token
     * 
     * @return the list of accepted permissions
     */
    public List<String> getActiveSessionPermissions() {
	return getActiveSession().getPermissions();
    }

    public Activity getActivity() {
	return activity;
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
		if (configuration.hasPublishPermissions() && configuration.isAllPermissionsAtOnce()) {
		    mSessionStatusCallback.setAskPublishPermissions(true);
		}

		// Open session with read permissions
		session.openForRead(request);
	    }
	    else {
		request.setPermissions(configuration.getPublishPermissions());
		session.openForPublish(request);
	    }
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
     * @param onNewPermissionListener
     *            The listener for the request permission action
     */
    public void requestNewPermissions(Permission[] permissions, final boolean showPublish, final OnNewPermissionsListener onNewPermissionListener) {
	configuration.addNewPermissions(permissions);
	logout(new OnLogoutAdapter() {
	    @Override
	    public void onLogout() {
		final boolean prevValue = configuration.allAtOnce;
		configuration.allAtOnce = showPublish;
		login(new OnLoginListener() {

		    @Override
		    public void onFail(String reason) {
			configuration.allAtOnce = prevValue;
			onNewPermissionListener.onFail(reason);
		    }

		    @Override
		    public void onException(Throwable throwable) {
			configuration.allAtOnce = prevValue;
			onNewPermissionListener.onException(throwable);
		    }

		    @Override
		    public void onThinking() {
			configuration.allAtOnce = prevValue;
			onNewPermissionListener.onThinking();
		    }

		    @Override
		    public void onNotAcceptingPermissions(Type type) {
			configuration.allAtOnce = prevValue;
			onNewPermissionListener.onNotAcceptingPermissions(type);
		    }

		    @Override
		    public void onLogin() {
			configuration.allAtOnce = prevValue;
			onNewPermissionListener.onSuccess(getAccessToken());
		    }
		});
	    }
	});
    }

    /**
     * Call this method only if session really needs to be reopened for read or
     * for publish. <br>
     * <br>
     * 
     * <b>Important:</b><br>
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
	    }
	    else
		if (permissions.containsAll(configuration.getReadPermissions())) {
		    openSession(session, true);
		}
	}
    }

    public void trackFacebookDialogPendingCall(PendingCall pendingCall, FacebookDialog.Callback callback) {
	mFacebookDialogCallback = callback;
	uiLifecycleHelper.trackPendingDialogCall(pendingCall);
    }

    public void untrackPendingCall() {
	mFacebookDialogCallback = null;
    }

    public boolean onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
	uiLifecycleHelper.onActivityResult(requestCode, resultCode, data, mFacebookDialogCallback);
	return true;
    }

    /**
     * Check if we already have Active session. If such exists, then return it,
     * otherwise create and set new Active session. <br>
     * <br>
     * 
     * <b>Note:</b><br>
     * <li>'Active' session doesn't meant that it's ready for running requests
     * on facebook side. For being able to run requests we need this session to
     * be 'Open'.</li> <li>You can't create a session if the activity/context
     * hasn't been initialized This is now possible because the library can be
     * started without context.</li><br>
     * 
     * @return Active session or <code>null</code> if activity wasn't
     *         initialized in SimpleFacebook class.
     */
    private Session getOrCreateActiveSession() {
	if (activity == null) {
	    Logger.logError(TAG, "You must initialize the SimpleFacebook instance with you current Activity.");
	    return null;
	}

	if (getActiveSession() == null || getActiveSession().isClosed()) {
	    Session session = new Session.Builder(activity.getApplicationContext()).setApplicationId(configuration.getAppId()).build();
	    Session.setActiveSession(session);
	}
	return getActiveSession();
    }

    /**
     * @param session
     * @return <code>True</code> if is possible to relive and reopen the current
     *         active session. Otherwise return <code>False</code>.
     */
    private boolean canReopenSession(Session session) {
	if (activity == null) {
	    Logger.logError(TAG, "You must initialize the SimpleFacebook instance with you current Activity.");
	    return false;
	}

	if (SessionState.CREATED_TOKEN_LOADED.equals(session.getState())) {
	    List<String> permissions = getActiveSessionPermissions();
	    if (permissions.containsAll(configuration.getReadPermissions())) {
		return true;
	    }
	}
	return false;
    }

    public class SessionStatusCallback implements Session.StatusCallback {
	private boolean askPublishPermissions = false;
	private boolean doOnLogin = false;
	private OnReopenSessionListener onReopenSessionListener = null;
	OnLoginListener onLoginListener = null;
	OnLogoutListener onLogoutListener = null;

	public void setOnReopenSessionListener(OnReopenSessionListener onReopenSessionListener) {
	    this.onReopenSessionListener = onReopenSessionListener;
	}

	@Override
	public void call(Session session, SessionState state, Exception exception) {
	    List<String> permissions = getActiveSessionPermissions();
	    if (exception != null) {
		if (exception instanceof FacebookOperationCanceledException) {
		    if (permissions.size() == 0) {
			onLoginListener.onNotAcceptingPermissions(Permission.Type.READ);
		    }
		    else {
			onLoginListener.onNotAcceptingPermissions(Permission.Type.PUBLISH);
		    }
		}
		else {
		    onLoginListener.onException(exception);
		}
	    }

	    switch (state) {
	    case CLOSED:
		if (onLogoutListener != null) {
		    onLogoutListener.onLogout();
		}
		break;

	    case CLOSED_LOGIN_FAILED:
		break;

	    case CREATED:
		break;

	    case CREATED_TOKEN_LOADED:
		break;

	    case OPENING:
		if (onLoginListener != null) {
		    onLoginListener.onThinking();
		}
		break;

	    case OPENED:

		/*
		 * Check if we came from publishing actions where we ask again
		 * for publish permissions
		 */
		if (onReopenSessionListener != null) {
		    onReopenSessionListener.onNotAcceptingPermissions(Permission.Type.PUBLISH);
		    onReopenSessionListener = null;
		}

		/*
		 * Check if WRITE permissions were also defined in the
		 * configuration. If so, then ask in another dialog for WRITE
		 * permissions.
		 */
		else
		    if (askPublishPermissions && session.getState().equals(SessionState.OPENED)) {
			if (doOnLogin) {
			    /*
			     * If user didn't accept the publish permissions, we
			     * still want to notify about complete
			     */
			    doOnLogin = false;
			    onLoginListener.onLogin();
			}
			else {
			    doOnLogin = true;
			    extendPublishPermissions();
			    askPublishPermissions = false;
			}
		    }
		    else {
			if (onLoginListener != null) {
			    onLoginListener.onLogin();
			}
		    }
		break;

	    case OPENED_TOKEN_UPDATED:

		/*
		 * Check if came from publishing actions and we need to re-ask
		 * for publish permissions
		 */
		if (onReopenSessionListener != null) {
		    onReopenSessionListener.onSuccess();
		    onReopenSessionListener = null;
		}
		else
		    if (doOnLogin) {
			doOnLogin = false;

			if (onLoginListener != null) {
			    onLoginListener.onLogin();
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
	public void setAskPublishPermissions(boolean ask) {
	    askPublishPermissions = ask;
	}
    }

    private class OnLogoutAdapter implements OnLogoutListener {

	@Override
	public void onThinking() {
	}

	@Override
	public void onException(Throwable throwable) {
	}

	@Override
	public void onFail(String reason) {
	}

	@Override
	public void onLogout() {
	}

    }
}
